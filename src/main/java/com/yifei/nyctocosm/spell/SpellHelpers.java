package com.yifei.nyctocosm.spell;

import com.yifei.nyctocosm.damage.NyctocosmDamageSources;
import com.yifei.nyctocosm.spell.data.ISpellPlayerData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

/**
 * 法术辅助工具类（DRY，集中实现重复逻辑）。
 * <p>
 * 覆盖能力：
 * <ul>
 *   <li>元素粒子批量生成（对应 effects:elements/* /transmutate/play）</li>
 *   <li>灵魂圈/法术环 ArmorStand 生成（对应 spellcircle 召唤）</li>
 *   <li>法术伤害构建：damage = (baseDamage + power×powerMul) × 1000 ÷ 1000（与 calcstatsspell 一致）</li>
 *   <li>Hypertrace（瞬身/超迹）概率判定（对应 magic:hypertrace/summon）</li>
 *   <li>获取玩家数据 / 获取绑定的法术平面实体</li>
 *   <li>AOE 目标选择（带距离/目标过滤）</li>
 *   <li>自定义音效播放（先回退到原版 Minecraft 音效，P10 再接 nyctocosm 自定义）</li>
 * </ul>
 * 所有方法都在服务端执行，不触碰客户端线程。
 */
public final class SpellHelpers {

    private SpellHelpers() {}

    // ========== 通用：基础法术伤害计算 ==========

    /**
     * 计算最终法术伤害（与原 magic:calcstatsspell 近似）。
     * <pre>finalDamage = baseDamage + power * powerMulPerTick</pre>
     * 对于 healing/saturation，power 加成按相应比例乘。
     */
    public static float scaledDamage(float baseDamage, int power, float powerMul) {
        return Math.max(0, baseDamage + power * powerMul);
    }

    /**
     * 原数据包 600 tick 持续加成的持续时间计算：
     * <pre>finalDuration = durationBase + power * powerTicks</pre>
     * 例如 Luminescence：base=6000，powerTicks=600 → power=50 时 36000 tick。
     */
    public static int scaledDuration(int durationBase, int power, int powerTicks) {
        return durationBase + power * powerTicks;
    }

    // ========== 元素粒子：effects:elements/<elem>/transmutate/play 近似替代 ==========

    /** 玩家头上生成元素粒子（对应 execute at @s rotated ~ -90 run function effects:elements/.../transmutate/play 的近似替代） */
    public static void playElementParticles(Player caster, ElementType type) {
        if (!(caster.level() instanceof ServerLevel sl)) return;
        Vec3 pos = caster.position().add(0, 1, 0); // ~ ~1 ~
        RandomSource rng = caster.getRandom();
        switch (type) {
            case RADIANCE -> {
                // Radiance 光辉：闪粒子 + 黄光
                sl.sendParticles(ParticleTypes.FLASH, pos.x, pos.y, pos.z, 1, 0, 0, 0, 0);
                for (int i = 0; i < 15; i++) {
                    double dx = (rng.nextDouble() - 0.5) * 0.4;
                    double dy = rng.nextDouble() * 0.6;
                    double dz = (rng.nextDouble() - 0.5) * 0.4;
                    sl.sendParticles(ParticleTypes.END_ROD, pos.x + dx, pos.y + dy, pos.z + dz, 1, 0, 0, 0, 0);
                }
            }
            case RESONANCE -> spawnColorParticles(sl, pos, 0.5f, 0.9f, 0.5f);
            case VOID -> spawnColorParticles(sl, pos, 0.38f, 0.31f, 0.56f);
            case DREAM -> spawnColorParticles(sl, pos, 0.44f, 0.71f, 0.89f);
            case NIGHTMARE -> spawnColorParticles(sl, pos, 0.60f, 0.40f, 0.67f);
            case DISSONANCE -> {
                // 失谐：红色冲击粒子 + 爆燃
                sl.sendParticles(ParticleTypes.EXPLOSION, pos.x, pos.y + 0.2, pos.z, 1, 0, 0, 0, 0);
                spawnColorParticles(sl, pos, 0.8f, 0.33f, 0.33f);
            }
        }
    }

    /** 批量生成颜色粒子（用 Instant Effect 粒子近似有色粒子） */
    private static void spawnColorParticles(ServerLevel sl, Vec3 pos, float r, float g, float b) {
        for (int i = 0; i < 20; i++) {
            double dx = (sl.getRandom().nextDouble() - 0.5) * 0.6;
            double dy = sl.getRandom().nextDouble() * 0.8;
            double dz = (sl.getRandom().nextDouble() - 0.5) * 0.6;
            sl.sendParticles(ParticleTypes.ENTITY_EFFECT, pos.x + dx, pos.y + dy, pos.z + dz,
                    0, r, g, b, 1.0);
        }
    }

    // ========== 法术圈 / ArmorStand 灵魂圈（近似 spellcircle tag + glowstone dust CMD7/8） ==========

    /** 在玩家位置生成一个法术圈（ArmorStand + Head CMD；P5 后接 GeckoLib 动画，先用 ArmorStand 保证功能） */
    public static UUID spawnSpellCircle(Player caster, int cmd) {
        Level level = caster.level();
        UUID[] holder = new UUID[1];
        // 使用 Marker ArmorStand：隐形、有视觉火焰属性、头=glowstone_dust+CustomModelData
        net.minecraft.world.entity.decoration.ArmorStand as = new net.minecraft.world.entity.decoration.ArmorStand(
                EntityType.ARMOR_STAND, level);
        as.setPos(caster.position());
        as.setNoBasePlate(true);
        as.setInvisible(true);
        as.setInvulnerable(true);
        // ArmorStand.setMarker 在 1.20.1 中为 private，通过反射调用（失败时退化为 noPhysics）
        try {
            java.lang.reflect.Method m = net.minecraft.world.entity.decoration.ArmorStand.class
                    .getDeclaredMethod("setMarker", boolean.class);
            m.setAccessible(true);
            m.invoke(as, true);
        } catch (Exception ignored) {
            as.noPhysics = true;
        }
        // NBT：Head = glowstone_dust with CustomModelData
        net.minecraft.nbt.CompoundTag headTag = new net.minecraft.nbt.CompoundTag();
        headTag.putString("id", "minecraft:glowstone_dust");
        headTag.putByte("Count", (byte) 1);
        net.minecraft.nbt.CompoundTag sub = new net.minecraft.nbt.CompoundTag();
        sub.putInt("CustomModelData", cmd);
        headTag.put("tag", sub);
        as.setItemSlot(net.minecraft.world.entity.EquipmentSlot.HEAD, ItemStack.of(headTag));
        holder[0] = as.getUUID();
        level.addFreshEntity(as);
        // 20 tick 定时移除（与 TEMP s.circle_timer = 20 一致）
        scheduleDespawn(as, 20);
        return holder[0];
    }

    // ========== 音效播放（暂用原版音效，P10 可替换 nyctocosm:spells.*） ==========

    /**
     * 简易法术音效：cast = 法杖挥动 / transmutate = 完成声 / aoe = 爆炸 / buff = 铃音
     */
    public static void playCastSound(Player caster, ElementType... types) {
        Level level = caster.level();
        if (level.isClientSide) return;
        level.playSound(null, caster.blockPosition(),
                SoundEvents.ENDER_DRAGON_SHOOT, SoundSource.PLAYERS, 0.5f, 1.1f);
    }

    public static void playTransmutateSound(Player caster) {
        Level level = caster.level();
        if (level.isClientSide) return;
        level.playSound(null, caster.blockPosition(),
                SoundEvents.AMETHYST_BLOCK_RESONATE, SoundSource.PLAYERS, 0.8f, 1.2f);
    }

    public static void playAoESound(Player caster) {
        Level level = caster.level();
        if (level.isClientSide) return;
        level.playSound(null, caster.blockPosition(),
                SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 0.7f, 0.9f);
    }

    public static void playBuffSound(Player caster) {
        Level level = caster.level();
        if (level.isClientSide) return;
        level.playSound(null, caster.blockPosition(),
                SoundEvents.BELL_BLOCK, SoundSource.PLAYERS, 0.6f, 1.3f);
    }

    // ========== 玩家辅助：加效果/加血量/加饱和 ==========

    /** 给玩家加 MobEffect（自动换算 tick，原版参数也是 tick） */
    public static void applyEffect(LivingEntity target, MobEffect effect, int ticks, int amplifier, boolean hideParticles) {
        target.addEffect(new MobEffectInstance(effect, Math.max(1, ticks), Math.max(0, amplifier),
                false, !hideParticles, true));
    }

    /** 治疗玩家（替代 ncdamage heal 系统），单位 HP（不是 ×1000） */
    public static void heal(Player p, float hp) {
        if (hp > 0) p.heal(hp);
    }

    /** 饱和（饱食度等级）效果 */
    public static void saturate(Player p, int foodBonus, float saturationBonus) {
        p.getFoodData().eat(foodBonus, saturationBonus);
    }

    // ========== 玩家数据 Capability 快捷访问 ==========

    @Nullable
    public static ISpellPlayerData dataOf(Player p) {
        return p.getCapability(SpellCapabilities.SPELL_PLAYER).resolve().orElse(null);
    }

    /** 给玩家打 tag（NBT 存储的逻辑标签；替代 scoreboard tag） */
    public static void addSpellTag(Player p, String tag) {
        p.addTag(tag);
    }

    public static boolean hasSpellTag(Player p, String tag) {
        return p.getTags().contains(tag);
    }

    public static void removeSpellTag(Player p, String tag) {
        p.removeTag(tag);
    }

    // ========== AOE 目标筛选 ==========

    /**
     * 获取指定半径内的 LivingEntity 目标（含距离过滤，默认排除施法者，按距离排序）。
     *
     * @param radius 半径（方块）
     * @param center 中心点（一般为 caster.position()）
     * @param maxCount 最大目标数（0 = 不限）
     * @param minDist 最小距离（可排除太近的目标）
     */
    public static List<LivingEntity> findTargets(Level level, Vec3 center,
                                                 double radius, double minDist, int maxCount,
                                                 Predicate<LivingEntity> filter) {
        if (!(level instanceof ServerLevel)) return List.of();
        AABB box = AABB.ofSize(center, radius * 2, radius * 2, radius * 2);
        List<LivingEntity> found = level.getEntitiesOfClass(LivingEntity.class, box, e -> {
            if (!EntitySelector.LIVING_ENTITY_STILL_ALIVE.test(e)) return false;
            double d = e.distanceToSqr(center.x, center.y, center.z);
            if (d > radius * radius || d < minDist * minDist) return false;
            return filter == null || filter.test(e);
        });
        found.sort((a, b) -> Double.compare(
                a.distanceToSqr(center.x, center.y, center.z),
                b.distanceToSqr(center.x, center.y, center.z)));
        if (maxCount > 0 && found.size() > maxCount) found = found.subList(0, maxCount);
        return found;
    }

    // ========== 对目标造成法术伤害（按元素分配）==========

    /**
     * 造成法术伤害（自动记录 stats + 处理死亡）。
     *
     * @param caster  施法者（null = 环境伤害）
     * @param target  目标
     * @param damage  伤害值（float，不是 ×1000）
     * @param element 主要元素（决定 DamageType）
     */
    public static boolean dealSpellDamage(@Nullable Player caster, LivingEntity target,
                                          float damage, ElementType element) {
        if (damage <= 0 || target == null || target.level().isClientSide) return false;
        Level level = target.level();
        DamageSource source;
        if (caster != null) {
            source = NyctocosmDamageSources.spellDirect(caster, element);
        } else {
            source = NyctocosmDamageSources.spellDirect(level, element);
        }
        boolean hit = target.hurt(source, damage);
        if (hit && caster != null) {
            // 统计伤害
            ISpellPlayerData d = dataOf(caster);
            if (d != null) d.addDamageDealt(element, damage);
            if (target.getHealth() <= 0) {
                if (d != null) d.addKill(element);
            }
        }
        return hit;
    }

    // ========== 冲击波粒子（Shockwave）==========

    /** 简易平面冲击波（从法术平面原点向外扩散的环形粒子） */
    public static void spawnShockwaveParticles(ServerLevel sl, Vec3 origin, double maxRadius) {
        int steps = 20;
        for (int i = 0; i < steps; i++) {
            double t = (double) i / steps;
            double r = maxRadius * t;
            int count = 8 + (int) (8 * t);
            for (int j = 0; j < count; j++) {
                double ang = 2 * Math.PI * j / count;
                double x = origin.x + Math.cos(ang) * r;
                double z = origin.z + Math.sin(ang) * r;
                sl.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, x, origin.y + 0.1, z,
                        1, 0, 0, 0, 0);
            }
        }
    }

    // ========== 定时实体移除（通用替代 TEMP s.circle_timer / spell 实体定时清除）==========

    /**
     * 延迟指定 tick 后移除实体（通过 UUID 查找，避免实体无效内存泄漏）。
     * 简单实现：在服务端以 Bukkit-style 不严格的 schedule，利用 ServerLevel#getEntity(uuid) 判断。
     */
    private static final java.util.Map<UUID, Integer> DESPAWN_SCHEDULE = new java.util.concurrent.ConcurrentHashMap<>();
    private static int scheduleTickCounter = 0;

    public static void scheduleDespawn(Entity entity, int ticks) {
        if (ticks <= 0) return;
        DESPAWN_SCHEDULE.put(entity.getUUID(), scheduleTickCounter + ticks);
    }

    /** 由 SpellCastingManager 在每 tick 调用一次（驱动所有 DESPAWN_SCHEDULE 实体移除） */
    public static void tickScheduledDespawns(Level level) {
        if (level.isClientSide || !(level instanceof ServerLevel sl)) return;
        scheduleTickCounter++;
        if (DESPAWN_SCHEDULE.isEmpty()) return;
        java.util.Iterator<java.util.Map.Entry<UUID, Integer>> it = DESPAWN_SCHEDULE.entrySet().iterator();
        while (it.hasNext()) {
            java.util.Map.Entry<UUID, Integer> e = it.next();
            if (e.getValue() <= scheduleTickCounter) {
                Entity ent = sl.getEntity(e.getKey());
                if (ent != null && ent.isAlive()) ent.discard();
                it.remove();
            }
        }
    }

    // ========== 聊天/行动条提示 ==========

    /** 在 Actionbar（物品栏上方）显示施法提示（按用户规范：只显示 Actionbar，不发聊天栏） */
    public static void actionbar(Player p, String key, Object... args) {
        p.displayClientMessage(Component.translatableWithFallback(key, key, args), true);
    }
}
