package com.yifei.nyctocosm.spell;

import com.yifei.nyctocosm.NyctocosmMod;
import com.yifei.nyctocosm.curio.NyctocosmCurios;
import com.yifei.nyctocosm.entity.NyctocosmEntities;
import com.yifei.nyctocosm.entity.spell.SpellPlaneEntity;
import com.yifei.nyctocosm.item.curio.IArtifactItem;
import com.yifei.nyctocosm.spell.data.ISpellPlayerData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

/**
 * 施法调度器（服务端权威）。
 * <p>
 * 职责单一（SRP）：
 * <ol>
 *   <li>PlayerTick 推进吟唱（onTickCasting → onComplete）</li>
 *   <li>PlayerTick 推进持续法术（onTickActive → onExpire）</li>
 *   <li>施法入口：扫描 16 蜡烛 + 匹配法术（canCast）→ 进入吟唱</li>
 *   <li>取消吟唱：玩家移动/死亡/切换维度等触发 onCancelled</li>
 *   <li>驱动法术平面实体播放 CAST/FIZZLE 动画（通过 UUID 绑定）</li>
 * </ol>
 *
 * 通过 {@code @Mod.EventBusSubscriber(modid = NyctocosmMod.MOD_ID)} 自动注册到 Forge 事件总线。
 * 所有方法在服务端执行（Level.isClientSide 校验前置）。
 */
@Mod.EventBusSubscriber(modid = NyctocosmMod.MOD_ID)
public final class SpellCastingManager {

    private SpellCastingManager() {}

    // =======================
    // 每 tick 推进（PlayerTickEvent.Server）
    // =======================

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        Player p = event.player;
        if (p.level().isClientSide) return;

        // 0. 推进 SpellHelpers 中的定时实体移除（例如 spellcircle）
        SpellHelpers.tickScheduledDespawns(p.level());

        // 0.5 统一施法冷却递减（原 m.spell.cooldown = 50）
        tickCastCooldown(p);

        // [修复 #6] TRANSPOSITION（置换圣物）：吟唱期间移动法术平面跟随施法者
        movePlaneWithCaster(p);

        // 1. 推进吟唱
        tickCasting(p);

        // 2. 推进持续法术（onTickActive / onExpire）
        tickActive(p);

        // 3. 吟唱期间位置变化过大取消
        checkCancelByMove(p);
    }

    /** 玩家死亡时清理吟唱 */
    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        // Forge 1.20.1：clone 发生在死亡重生/返回主世界后
        // 由于 ISpellPlayerData#copyFrom 在事件里被调用（见 P2-5 注册），这里只清理当前被取消的吟唱
        Player newPlayer = event.getEntity();
        if (newPlayer.level().isClientSide) return;
        newPlayer.getCapability(com.yifei.nyctocosm.spell.SpellCapabilities.SPELL_PLAYER).ifPresent(data -> {
            if (data.isCasting()) {
                Spell s = data.getCurrentSpell();
                if (s != null) s.onCancelled(newPlayer, newPlayer.level());
                data.cancelCasting();
                playPlaneAnim(newPlayer, SpellPlaneEntity.STATE_FIZZLE);
            }
        });
    }

    // =======================
    // 吟唱推进
    // =======================

    private static void tickCasting(Player p) {
        p.getCapability(SpellCapabilities.SPELL_PLAYER).ifPresent(data -> {
            if (!data.isCasting()) return;
            Spell spell = data.getCurrentSpell();
            if (spell == null) {
                // 法术引用失效（热更新/注册表变更）→ 直接取消
                data.cancelCasting();
                return;
            }
            int left = data.decrementCastTick();
            Level level = p.level();

            if (left > 0) {
                // 仍在吟唱：执行 tickCasting 钩子；返回 false 表示取消
                if (!spell.onTickCasting(p, level, left)) {
                    spell.onCancelled(p, level);
                    data.cancelCasting();
                    playPlaneAnim(p, SpellPlaneEntity.STATE_FIZZLE);
                    NyctocosmMod.LOGGER.debug("[Spell] 玩家 {} 吟唱 {} 被 onTickCasting 取消",
                            p.getName().getString(), spell.getId());
                }
                return;
            }

            // left == 0 → 吟唱完成：走统一完成路径（onComplete + 统计 + 持续法术注册 + CAST 动画）
            completeCast(p, data, spell);
        });
    }

    /**
     * 法术完成统一路径（onComplete + 元素统计 + 持续法术注册 + CAST 动画 + 清理吟唱状态）。
     * <p>
     * 修复 #1：castTime==0 的瞬发法术（如 Luminescence）经 startCasting 后 isCasting()==false，
     * 原实现靠 tickCasting() 收尾会提前 return，导致 onComplete 永不执行——瞬发法术入口改为直接调本方法。
     * 修复：完成帧不再立即重复调用 onTickActive（下一 tick 由 tickActive 统一推进，避免首帧双调）；
     * 持续法术时长若由 onComplete 按 power 等自定义写入（如 Forbiddance 的 5p+35），则尊重法术自身值，
     * 否则回退到元数据 duration。
     */
    private static void completeCast(Player p, ISpellPlayerData data, Spell spell) {
        Level level = p.level();
        try {
            spell.onComplete(p, level);
            // 统计：施法次数 +1（按每个元素累加）
            for (ElementType t : ElementType.values()) {
                if (spell.hasElement(t)) data.addSpellCast(t);
            }
        } catch (RuntimeException ex) {
            // 单个法术异常不崩整个服务器
            NyctocosmMod.LOGGER.error("[Spell] onComplete 异常 {} @ {}",
                    spell.getId(), p.getName().getString(), ex);
        }

        // 持续法术注册：仅在法术未自行注册（自定义时长）时按元数据 duration 写入
        if (spell.getDuration() > 0 && !data.getActiveSpells().containsKey(spell.getId())) {
            data.setActiveSpell(spell.getId(), spell.getDuration());
        }

        // 播放 CAST 动画（法术平面在施法结束时仍需一个闪光反馈）
        playPlaneAnim(p, SpellPlaneEntity.STATE_CAST);

        // 清理吟唱状态（对 duration == 0 的瞬时法术同样需要）
        data.cancelCasting();

        // 统一施法冷却：一次法术完成后 50t 内不能再次施法（原 m.spell.cooldown = 50）
        startCastCooldown(p);
    }

    // =======================
    // 持续法术推进
    // =======================

    private static void tickActive(Player p) {
        p.getCapability(SpellCapabilities.SPELL_PLAYER).ifPresent(data -> {
            if (data.getActiveSpells().isEmpty()) return;
            Level level = p.level();

            // 先 onTickActive（每个法术递减前的值）
            for (var entry : data.getActiveSpells().entrySet()) {
                Spell s = SpellRegistry.byId(entry.getKey());
                if (s == null) continue;
                int remaining = entry.getValue();
                try {
                    s.onTickActive(p, level, remaining);
                } catch (RuntimeException ex) {
                    NyctocosmMod.LOGGER.error("[Spell] onTickActive 异常 {} @ {}",
                            s.getId(), p.getName().getString(), ex);
                }
            }

            // 再递减并收集到期
            List<String> expired = data.tickActiveSpells();
            for (String sid : expired) {
                Spell s = SpellRegistry.byId(sid);
                if (s == null) continue;
                try {
                    s.onExpire(p, level);
                } catch (RuntimeException ex) {
                    NyctocosmMod.LOGGER.error("[Spell] onExpire 异常 {} @ {}",
                            s.getId(), p.getName().getString(), ex);
                }
            }
        });
    }

    // =======================
    // 取消：移动/离开蜡烛阵
    // =======================

    /** 启动吟唱时记录的原始位置（玩家 UUID → 启动位置） */
    private static final java.util.Map<UUID, Vec3> CAST_START_POS =
            new java.util.concurrent.ConcurrentHashMap<>();

    private static void checkCancelByMove(Player p) {
        p.getCapability(SpellCapabilities.SPELL_PLAYER).ifPresent(data -> {
            UUID id = p.getUUID();
            if (data.isCasting()) {
                Vec3 origin = CAST_START_POS.computeIfAbsent(id, k -> p.position());
                double distSqr = p.distanceToSqr(origin);
                // 移动超过 0.6 格视为取消（原数据包里是玩家必须站定+俯视角度，这里简化）
                if (distSqr > 0.6 * 0.6) {
                    Spell s = data.getCurrentSpell();
                    if (s != null) s.onCancelled(p, p.level());
                    data.cancelCasting();
                    playPlaneAnim(p, SpellPlaneEntity.STATE_FIZZLE);
                    CAST_START_POS.remove(id);
                }
            } else {
                CAST_START_POS.remove(id);
            }
        });
    }

    // =======================
    // 统一施法冷却（原 m.spell.cooldown = 50 tick）
    // =======================

    /** 法术完成后的统一冷却时长：50 tick（2.5 秒），对齐原数据包 m.spell.cooldown = 50 */
    private static final int SPELL_CAST_COOLDOWN_TICKS = 50;

    /** 玩家 UUID → 剩余冷却 tick（运行时瞬态状态，不持久化） */
    private static final java.util.Map<UUID, Integer> CAST_COOLDOWNS =
            new java.util.concurrent.ConcurrentHashMap<>();

    /** 是否处于施法冷却中 */
    private static boolean isOnCastCooldown(Player p) {
        return CAST_COOLDOWNS.getOrDefault(p.getUUID(), 0) > 0;
    }

    /** 每 tick 递减冷却（归零自动移除条目，防止 Map 无限增长） */
    private static void tickCastCooldown(Player p) {
        CAST_COOLDOWNS.computeIfPresent(p.getUUID(), (k, v) -> v <= 1 ? null : v - 1);
    }

    /** 开始施法冷却（在 completeCast 成功后设置） */
    private static void startCastCooldown(Player p) {
        CAST_COOLDOWNS.put(p.getUUID(), SPELL_CAST_COOLDOWN_TICKS);
    }

    // =======================
    // 外部 API：触发施法入口（戒指+潜行+蜡烛阵）
    // =======================

    /**
     * 尝试为玩家启动一个吟唱（由 Curios 戒指右键 / 交互 / 其他外部触发条件调用）。
     * <p>
     * <b>P3.5 精确流程（完全对应原 spells 数据包）</b>：
     * <ol>
     *   <li>{@link CandleMatrixMatcher#verifyMatrix(Player)}：16 个固定 (dx,dz) 坐标必须全部是点燃蜡烛 → 失败直接 FIZZLE 返回 false</li>
     *   <li>{@link CandleMatrixMatcher#matchAndConsumeCatalyst(Player)}：3 格范围内扫描 17 种互斥掉落物 Item，
     *       映射到对应 Spell → 再校验 spell.canCast() → 通过则「消耗 1 个催化剂」并返回 Spell</li>
     *   <li>spell.onStartCast → data.startCasting → 记录吟唱起点 → castTime=0 立即 tick 完成</li>
     * </ol>
     * 与 P2 简化版的本质区别：<b>法术唯一由「催化剂掉落物 Item」决定</b>，而非遍历注册表取首个 canCast。
     *
     * @return 成功启动吟唱 true；蜡烛阵不合格 / 没有匹配掉落物 / canCast=false 返回 false（调用方可选择播放 FIZZLE 或忽略）
     */
    public static boolean tryStartCast(Player p) {
        if (p.level().isClientSide) return false;
        if (p.isSpectator() || !p.isAlive()) return false;

        return p.getCapability(SpellCapabilities.SPELL_PLAYER).map(data -> {
            if (data.isCasting()) return false;

            // 统一施法冷却中不允许再次施法（原 m.spell.cooldown = 50）
            if (isOnCastCooldown(p)) {
                playPlaneAnim(p, SpellPlaneEntity.STATE_FIZZLE);
                return false;
            }

            // [P3.5-1] 精确蜡烛阵：16 固定坐标必须全部点燃
            if (!CandleMatrixMatcher.verifyMatrix(p)) {
                playPlaneAnim(p, SpellPlaneEntity.STATE_FIZZLE);
                return false;
            }

            // [P3.5-2] 掉落物匹配：17 互斥材料 → 17 法术（命中即消耗 1 个）
            Spell s = CandleMatrixMatcher.matchAndConsumeCatalyst(p);
            if (s == null) {
                playPlaneAnim(p, SpellPlaneEntity.STATE_FIZZLE);
                return false;
            }

            // [修复 #4] 施法开始前按法术元素统计重算法术强度（默认全 0 = 原包无加成态）
            recalcSpellPower(p, data, s);

            // [修复 #3] 施法成功 → 生成/复用施法平面并绑定 UUID（此前平面从不生成，playPlaneAnim 恒空转）
            ensureSpellPlane(p);

            try {
                s.onStartCast(p, p.level());
            } catch (RuntimeException ex) {
                NyctocosmMod.LOGGER.error("[Spell] onStartCast 异常 {} @ {}",
                        s.getId(), p.getName().getString(), ex);
                playPlaneAnim(p, SpellPlaneEntity.STATE_FIZZLE);
                return false;
            }
            data.startCasting(s.getId(), s.getCastTime());
            CAST_START_POS.put(p.getUUID(), p.position());
            if (s.getCastTime() == 0) {
                // 瞬发法术（如 Luminescence castTime=0）：startCasting 后 isCasting()==false，
                // 原实现调用 tickCasting() 会因 isCasting()==false 提前 return → onComplete 永不执行。
                // 修复：直接走统一完成路径 completeCast。
                completeCast(p, data, s);
            }
            return true;
        }).orElse(false);
    }

    /**
     * 原 P2 简化扫描入口保留（仅用于调试命令 / 向后兼容），委托给精确实现。
     * <p>
     * 真实触发入口请使用 {@link #tryStartCast(Player)}（含催化剂匹配 + 消耗）。
     */
    public static boolean scanLitCandles(Player p) {
        return CandleMatrixMatcher.verifyMatrix(p);
    }

    // =======================
    // 法术平面动画驱动（通过 UUID 绑定查找实体）
    // =======================

    /**
     * 施法前按法术所含元素统计法术强度：sum(m.<element>.power)，钳制上限 50 后写入
     * {@link ISpellPlayerData#setSpellPower(int)}。
     * [修复 #4] 打通 power 通路：此前 spellPower 无任何运行期来源（仅 Clone 复制），
     * scaledDamage/scaledDuration 恒退化为基础值。默认元素统计全 0 = 原包无加成态（数值不变）；
     * 各元素 power 的数值来源由后续 magic 属性系统（圣物/击杀/装备加成）Java 化时写入。
     */
    private static void recalcSpellPower(Player p, ISpellPlayerData data, Spell spell) {
        int sum = 0;
        for (ElementType t : ElementType.fromMask(spell.getElementMask())) {
            sum += data.getElementPower(t);
        }
        data.setSpellPower(Math.min(50, sum));
    }

    /** 施法平面最长存活 tick：施法完成后自动清理，防止实体泄漏 */
    private static final int SPELL_PLANE_TTL = 200;

    /** 玩家是否佩戴了指定被动类型的圣物（relic + artifact 槽） */
    private static boolean hasPassive(Player p, IArtifactItem.ArtifactPassiveType type) {
        for (ItemStack s : NyctocosmCurios.getCurioStacks(p, NyctocosmCurios.SLOT_RELIC)) {
            if (s.getItem() instanceof IArtifactItem a && a.getPassiveType() == type) return true;
        }
        for (ItemStack s : NyctocosmCurios.getCurioStacks(p, NyctocosmCurios.SLOT_ARTIFACT)) {
            if (s.getItem() instanceof IArtifactItem a && a.getPassiveType() == type) return true;
        }
        return false;
    }

    /**
     * [修复 #6] TRANSPOSITION（置换圣物）语义还原：佩戴者吟唱期间，把绑定法术平面移动到
     * 玩家头顶上方（原数据包 a.transposition → cast_ring_plane 选择"移动平面"分支）。
     * 通过 moveTo + ClientboundTeleportEntityPacket 强制同步给客户端（纯 Entity 无自身移动逻辑）。
     */
    private static void movePlaneWithCaster(Player p) {
        if (p.level().isClientSide) return;
        if (!(p.level() instanceof ServerLevel sl)) return;
        ISpellPlayerData data = getData(p);
        if (data == null || !data.isCasting()) return;
        if (!hasPassive(p, IArtifactItem.ArtifactPassiveType.TRANSPOSITION)) return;
        UUID planeId = data.getSpellPlaneUUID();
        if (planeId == null) return;
        net.minecraft.world.entity.Entity e = sl.getEntity(planeId);
        if (!(e instanceof SpellPlaneEntity plane)) return;
        plane.moveTo(p.getX(), p.getY() + 1.8, p.getZ(), p.getYRot(), 0);
        sl.getChunkSource().broadcastAndSend(plane,
                new net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket(plane));
    }

    /**
     * 为玩家生成（或复用已有的）施法平面实体并绑定 UUID 到玩家数据。
     * [修复 #3] 此前 SpellPlaneEntity 注册后从未有生成点，setSpellPlaneUUID 无调用者，
     * playPlaneAnim 每次查 UUID 都为空 → CAST/FIZZLE 动画实际从不播放。
     * 生成位置：玩家头顶上方约 1.8 格（视觉上替代原 ajspellplane 跟随平面）。
     */
    private static void ensureSpellPlane(Player p) {
        if (p.level().isClientSide) return;
        if (!(p.level() instanceof ServerLevel sl)) return;
        ISpellPlayerData data = getData(p);
        if (data == null) return;

        UUID existing = data.getSpellPlaneUUID();
        if (existing != null) {
            net.minecraft.world.entity.Entity e = sl.getEntity(existing);
            if (e instanceof SpellPlaneEntity plane && e.isAlive()) {
                return; // 复用现有平面（动画通过 playPlaneAnim 驱动）
            }
        }

        SpellPlaneEntity plane = new SpellPlaneEntity(NyctocosmEntities.SPELL_PLANE.get(), sl);
        plane.moveTo(p.getX(), p.getY() + 1.8, p.getZ(), p.getYRot(), 0);
        if (sl.addFreshEntity(plane)) {
            data.setSpellPlaneUUID(plane.getUUID());
            SpellHelpers.scheduleDespawn(plane, SPELL_PLANE_TTL);
        }
    }

    private static void playPlaneAnim(Player p, byte state) {
        p.getCapability(SpellCapabilities.SPELL_PLAYER).ifPresent(data -> {
            UUID planeId = data.getSpellPlaneUUID();
            if (planeId == null || !(p.level() instanceof ServerLevel sl)) return;
            net.minecraft.world.entity.Entity e = sl.getEntity(planeId);
            if (!(e instanceof SpellPlaneEntity plane)) return;
            switch (state) {
                case SpellPlaneEntity.STATE_CAST -> {
                    plane.playCast();
                    // 一次性演出动画：播完后短时移除平面，避免遗留实体
                    SpellHelpers.scheduleDespawn(plane, 40);
                }
                case SpellPlaneEntity.STATE_FIZZLE -> {
                    plane.playFizzle();
                    SpellHelpers.scheduleDespawn(plane, 40);
                }
                default -> plane.resetIdle();
            }
        });
    }

    // =======================
    // 调试命令 API：按 ID 直接施法（绕过蜡烛阵/掉落物匹配）
    // =======================

    /**
     * 按法术 ID 直接启动吟唱（P3 调试用：绕过蜡烛阵检测与自动匹配）。
     * <p>
     * 仍会执行 canCast 校验（例如 Evanescence 会拒绝在使者领域内使用），
     * 但不检查是否在 16 根点燃蜡烛范围内。castTime == 0 的瞬发法术会立即 tick 一次完成。
     *
     * @param spellId 小写法术 ID，如 "luminescence"
     * @return 1 = 成功启动吟唱；0 = 玩家不在线/旁观/死亡或 Capability 缺失；
     *        -1 = 已在吟唱中；-2 = 法术 ID 不存在；-3 = canCast 返回 false；-4 = 施法冷却中
     */
    public static int castDirect(Player p, String spellId) {
        if (p.level().isClientSide) return 0;
        if (p.isSpectator() || !p.isAlive()) return 0;

        return p.getCapability(SpellCapabilities.SPELL_PLAYER).map(data -> {
            if (data.isCasting()) return -1;

            // 统一施法冷却中不允许再次施法
            if (isOnCastCooldown(p)) return -4;

            Spell s = SpellRegistry.byId(spellId);
            if (s == null) return -2;
            if (!s.canCast(p, p.level())) return -3;

            // [修复 #4] 命令直施法同样先重算法术强度
            recalcSpellPower(p, data, s);

            // [修复 #3] 命令直施法同样生成平面，保证动画链路可观测
            ensureSpellPlane(p);

            try {
                s.onStartCast(p, p.level());
            } catch (RuntimeException ex) {
                NyctocosmMod.LOGGER.error("[Spell] onStartCast 异常 {} @ {}",
                        s.getId(), p.getName().getString(), ex);
                return -3;
            }
            data.startCasting(s.getId(), s.getCastTime());
            CAST_START_POS.put(p.getUUID(), p.position());
            if (s.getCastTime() == 0) {
                // 同 tryStartCast：castTime==0 瞬发法术直接走完成路径（见 completeCast 注释）
                completeCast(p, data, s);
            }
            return 1;
        }).orElse(0);
    }

    // =======================
    // 便捷方法（其他模块调用）
    // =======================

    /** 获取玩家数据（外部常用简写，null-safe 返回空 Optional 语义） */
    @Nullable
    public static ISpellPlayerData getData(Player p) {
        return p.getCapability(SpellCapabilities.SPELL_PLAYER).resolve().orElse(null);
    }
}
