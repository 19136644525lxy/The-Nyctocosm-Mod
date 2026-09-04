package com.yifei.nyctocosm.curio;

import com.yifei.nyctocosm.NyctocosmMod;
import com.yifei.nyctocosm.damage.NyctocosmDamageSources;
import com.yifei.nyctocosm.item.curio.IArtifactItem;
import com.yifei.nyctocosm.item.curio.NyctocosmCurioItems;
import com.yifei.nyctocosm.item.curio.PlanarCircletItem;
import com.yifei.nyctocosm.item.curio.SoulBottleItem;
import com.yifei.nyctocosm.spell.ElementType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * 10 圣物 + 戒指 全局被动事件驱动（SRP：只负责"分派事件到具体圣物效果"）。
 * <p>
 * 设计原理（OCP + DIP）：
 * 不依赖具体 Artifact 子类，只依赖 {@link IArtifactItem#getPassiveType()} + {@link IArtifactItem#getElementType()}
 * 两个最小接口来分派。以后新增圣物时，只需新增 ArtifactItem 构造参数的枚举值 + 扩展 switch，不改动现有代码块。
 * <p>
 * 性能：
 * - 所有被动放在 PlayerTick End 阶段，每秒 20 次（MC 原生）。
 * - 单次扫描 charm 槽最多 5 个物品（槽数由 NyctocosmCurios 定义），O(5)，可忽略。
 * - 借助 WeakHashMap 缓存玩家 → 已激活被动集合，避免每 tick 多次重复扫描和重复触发（主要给 locomotion dash/expulsion 冷却用）。
 */
@Mod.EventBusSubscriber(modid = NyctocosmMod.MOD_ID)
public final class ArtifactPassiveHandler {

    private ArtifactPassiveHandler() {}

    // 玩家维度 cooldown 状态（用 ConcurrentHashMap 避免跨线程访问，实际只在 Server 线程读写）
    private static final Map<UUID, CooldownState> COOLDOWNS =
            Collections.synchronizedMap(new WeakHashMap<>());

    private static final class CooldownState {
        int locomotionDashCd = 0;    // locomotion: 10 tick 冷却
        int locomotionDashTimer = 0; // locomotion: 冲刺 tick 计数（4 tick 上限）
        boolean locomotionDashing = false;
    }

    @Nonnull
    private static CooldownState state(Player p) {
        return COOLDOWNS.computeIfAbsent(p.getUUID(), k -> new CooldownState());
    }

    // ================================================================
    // Tick 驱动：所有"每 tick 生效"类被动（LOCOMOTION / 冷却计时）
    // ================================================================

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        Player p = event.player;
        if (p.level().isClientSide) return;

        List<ItemStack> curios = allArtifacts(p);
        // EnumSet 是抽象类，必须通过 noneOf/allOf/copyOf 静态工厂创建（JDK 标准用法）。
        // 先 noneOf 初始化为空集，再逐个 add，避免抽象类 new 实例的编译错误。
        Set<IArtifactItem.ArtifactPassiveType> passives =
                EnumSet.noneOf(IArtifactItem.ArtifactPassiveType.class);
        for (ItemStack s : curios) if (s.getItem() instanceof IArtifactItem art) {
            passives.add(art.getPassiveType());
        }

        CooldownState st = state(p);

        // --- LOCOMOTION（冲刺突进）---
        if (st.locomotionDashCd > 0) st.locomotionDashCd--;
        if (st.locomotionDashing) {
            st.locomotionDashTimer++;
            // 原数据包 dash 持续 4 tick: speed 20 + resistance 255，1s（20 ticks 计速值）
            p.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1, 20,
                    true, false, false));
            p.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1, 255,
                    true, false, false));
            // 每 tick 把玩家向视线方向推进 0.8（等效 speed XX 的加速度体感）
            Vec3 look = p.getLookAngle();
            p.setDeltaMovement(p.getDeltaMovement().add(look.scale(0.35)));
            if (st.locomotionDashTimer >= 4) {
                // enddash 等价：清除 speed/resistance + 冷却 10
                st.locomotionDashing = false;
                st.locomotionDashTimer = 0;
                st.locomotionDashCd = 10;
                p.removeEffect(MobEffects.MOVEMENT_SPEED);
                p.removeEffect(MobEffects.DAMAGE_RESISTANCE);
            }
        } else if (passives.contains(IArtifactItem.ArtifactPassiveType.LOCOMOTION)
                && p.isShiftKeyDown() && st.locomotionDashCd == 0 && !p.onGround()) {
            // 条件：潜行+非地面（防止原地）+ 装备 LOCOMOTION
            st.locomotionDashing = true;
            st.locomotionDashTimer = 0;
        }

        // --- LEVITATION（潜行+空中 → 缓慢下落 5s）---
        if (passives.contains(IArtifactItem.ArtifactPassiveType.LEVITATION)
                && p.isShiftKeyDown() && !p.onGround()
                && SoulBottleItem.getTotalSoulsHeld(p) + PlanarCircletItem.readSoulsHeld(p) >= 3
                && p.level().getBlockState(p.blockPosition().below(1)).isAir()
                && p.level().getBlockState(p.blockPosition().below(2)).isAir()) {
            if (SoulBottleItem.tryConsumeSouls(p, 3)) {
                p.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 5 * 20, 0,
                        true, false, true));
            }
        }
    }

    // ================================================================
    // 潜行右键：从饰品槽触发戒指 & Extraction
    // ================================================================

    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        Player p = event.getEntity();
        if (p.level().isClientSide) return;
        if (!p.isShiftKeyDown()) return;
        ItemStack hand = p.getItemInHand(event.getHand());
        // 戒指在手上时，PlanarCircletItem#use 已处理；仅当戒指不在手上但在 ring 槽里时，这里作为替身触发
        // （防止玩家必须取出戒指才能施法，优化操作手感）
        if (!(hand.getItem() instanceof PlanarCircletItem)) {
            Optional<ItemStack> circlet = firstCurio(p, NyctocosmCurios.SLOT_RING, PlanarCircletItem.class);
            if (circlet.isPresent()) {
                boolean ok = PlanarCircletItem.onSneakRightClick(p, circlet.get());
                if (ok) event.setCanceled(true);
            }
        }
    }

    // 潜行+对空气左击（EXTRACTION：原地召唤 1 魂；TRANSPOSITION：前向瞬移等效短 dash）
    @SubscribeEvent
    public static void onLeftClickEmpty(PlayerInteractEvent.LeftClickEmpty event) {
        // LeftClickEmpty 仅客户端会触发，所以这里不处理 —— 服务端对应使用 AttackEntityEvent / ActionInput（无）
        // 我们采用 PlayerInteractEvent.RightClickBlock(空气+潜行) 作为"萃取"触发点，见下
    }

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Player p = event.getEntity();
        if (p.level().isClientSide) return;
        if (!p.isShiftKeyDown()) return;
        List<ItemStack> arts = allArtifacts(p);
        boolean extraction = arts.stream().anyMatch(s -> s.getItem() instanceof IArtifactItem a
                && a.getPassiveType() == IArtifactItem.ArtifactPassiveType.EXTRACTION);
        if (extraction) {
            // 萃取：在玩家位置上方召唤 soul marker（以 1 个魂 = 给 SoulBottle +1 等价）
            // 原数据包 artifacts/extraction/extract.mcfunction = summon marker + soul_reap 音效
            ServerLevel sl = (ServerLevel) p.level();
            // 1.20.1 正确 API：SoundEvent.createFixedRangeEvent(rl, range) —— 替代 1.19 的 createFixedEvent。
            sl.playSound(null, p.getX(), p.getY(), p.getZ(),
                    net.minecraft.sounds.SoundEvent.createFixedRangeEvent(
                            net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("nyctocosm", "spells.soul_reap"), 4),
                    SoundSource.PLAYERS, 4F, 1F);
            // +1 魂到任意一个 SoulBottle 或戒指
            var inv = p.getCapability(top.theillusivec4.curios.api.CuriosCapability.INVENTORY).resolve();
            if (inv.isPresent()) {
                boolean added = false;
                for (String slotId : List.of(NyctocosmCurios.SLOT_RING, NyctocosmCurios.SLOT_SOUL_BOTTLE)) {
                    var sh = inv.get().getStacksHandler(slotId).orElse(null);
                    if (sh == null) continue;
                    for (int i = 0; i < sh.getSlots() && !added; i++) {
                        ItemStack s = sh.getStacks().getStackInSlot(i);
                        if (s.getItem() instanceof SoulBottleItem || s.getItem() instanceof PlanarCircletItem) {
                            int cur = s.getOrCreateTag().getInt(SoulBottleItem.TAG_SOULS_HELD);
                            if (cur < SoulBottleItem.MAX_SOULS) {
                                s.getOrCreateTag().putInt(SoulBottleItem.TAG_SOULS_HELD, cur + 1);
                                added = true;
                            }
                        }
                    }
                }
            }
            event.setCanceled(true);
        }
    }

    // ================================================================
    // LivingDamageEvent：Protection 6 圣物的抗性减免
    // ================================================================

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        if (!(event.getEntity() instanceof Player p)) return;
        if (p.level().isClientSide) return;
        DamageSource src = event.getSource();
        ElementType el = NyctocosmDamageSources.resolveElement(src);
        if (el == null) return;

        // 若玩家戴有该元素对应的 Protection 圣物，则按 40% 减免伤害
        // 等价于原数据包 m.{element}.resistance.modifier += a.protection.resAmt × hypertrace_charge
        for (ItemStack s : allArtifacts(p)) {
            if (!(s.getItem() instanceof IArtifactItem a)) continue;
            if (a.getPassiveType() != IArtifactItem.ArtifactPassiveType.ELEMENT_RESISTANCE) continue;
            if (!el.equals(a.getElementType())) continue;
            // 每戴一个 Protection X 圣物（一般只戴 1 个），减免 40% 伤害
            event.setAmount(event.getAmount() * 0.60F);
            // 产生抗性粒子提示
            if (p.level() instanceof ServerLevel sl) {
                sl.sendParticles(ParticleTypes.ENCHANTED_HIT,
                        p.getX(), p.getY() + 1, p.getZ(),
                        12, 0.4, 0.6, 0.4, 0.02);
            }
            break; // 每种元素一个圣物，命中后 break
        }
    }

    // ================================================================
    // LivingDeathEvent：Absorption（击杀 void 怪 → +1 魂）/ Expulsion（击杀 dissonance 怪 → 爆炸）
    // / Amelioration（死亡 hypertrace → 周围回血）
    // 简化：Absorption 任何来源死亡只要 killer 是玩家 + 戴对应圣物，就触发（等价吸收灵魂）
    // ================================================================

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        LivingEntity dead = event.getEntity();
        if (dead.level().isClientSide) return;
        DamageSource src = event.getSource();
        if (src == null || !(src.getEntity() instanceof Player p)) return;

        List<ItemStack> arts = allArtifacts(p);
        boolean hasAbsorption = false;
        boolean hasExpulsion = false;
        boolean hasAmelioration = false;
        for (ItemStack s : arts) {
            if (!(s.getItem() instanceof IArtifactItem a)) continue;
            switch (a.getPassiveType()) {
                case ABSORPTION -> hasAbsorption = true;
                case EXPULSION -> hasExpulsion = true;
                case AMELIORATION -> hasAmelioration = true;
            }
        }

        ElementType killedElement = NyctocosmDamageSources.resolveElement(src);

        // Absorption：戴圣物 + 死的是对应元素怪（Void）→ 玩家获得 1 魂；
        // 为避免死循环，killedElement==null 时只要怪在"nyctocosm 怪物标签"同样触发（兼容非 nyctocosm 元素伤害）
        if (hasAbsorption && (killedElement == ElementType.VOID
                || (killedElement == null && dead.getType().is(
                        net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ENTITY_TYPE,
                                net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("nyctocosm", "livingthings")))))) {
            giveSoul(p, 1);
        }

        // Expulsion：击杀 dissonance 元素怪时触发 6 格爆炸（原 expulsion explosion.mcfunction）
        if (hasExpulsion && (killedElement == ElementType.DISSONANCE
                || (killedElement == null && dead.getType().is(
                        net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ENTITY_TYPE,
                                net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("nyctocosm", "livingthings")))))) {
            ServerLevel sl = (ServerLevel) dead.level();
            // 1.20.1 Level.explode 签名第 7 个参数为 Level.ExplosionInteraction（不是旧版 Explosion.BlockInteraction）。
            // 使用 NONE：既不破坏方块，也不受 mob/grief 规则影响（与原 Expulsion 爆炸无破坏效果一致）。
            sl.explode(p, dead.getX(), dead.getY() + dead.getBbHeight() / 2, dead.getZ(),
                    3.0F, false, net.minecraft.world.level.Level.ExplosionInteraction.NONE);
            sl.sendParticles(ParticleTypes.EXPLOSION, dead.getX(), dead.getY(), dead.getZ(),
                    20, 1, 1, 1, 0);
            sl.sendParticles(ParticleTypes.FLAME, dead.getX(), dead.getY(), dead.getZ(),
                    20, 0, 0, 0, 0.3);
            sl.playSound(null, dead.getX(), dead.getY(), dead.getZ(),
                    SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 1F, 1F);
        }

        // Amelioration：死了"任何 hypertrace（这里用 nyctocosm livingthings 近似）"→ 半径 6 格内玩家治疗 + 饱和
        if (hasAmelioration && dead.getType().is(
                net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ENTITY_TYPE,
                        net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("nyctocosm", "livingthings")))) {
            Vec3 center = dead.position();
            for (Player healee : dead.level().getEntitiesOfClass(Player.class,
                    new net.minecraft.world.phys.AABB(center, center).inflate(6.0))) {
                healee.addEffect(new MobEffectInstance(MobEffects.HEAL, 1, 0, true, false));
                healee.addEffect(new MobEffectInstance(MobEffects.SATURATION, 3 * 20, 0, true, false));
                if (dead.level() instanceof ServerLevel sl) {
                    sl.sendParticles(ParticleTypes.FIREWORK, healee.getX(), healee.getY() + 1, healee.getZ(),
                            50, 0.2, 0.2, 0.2, 0.2);
                }
            }
        }
    }

    // ================================================================
    // 小工具
    // ================================================================

    private static List<ItemStack> allArtifacts(Player p) {
        List<ItemStack> list = new ArrayList<>(NyctocosmCurios.getCurioStacks(p, NyctocosmCurios.SLOT_RELIC));
        list.addAll(NyctocosmCurios.getCurioStacks(p, NyctocosmCurios.SLOT_ARTIFACT));
        return list;
    }

    private static <T> Optional<ItemStack> firstCurio(Player p, String slot, Class<T> itemCls) {
        for (ItemStack s : NyctocosmCurios.getCurioStacks(p, slot)) {
            if (itemCls.isInstance(s.getItem())) return Optional.of(s);
        }
        return Optional.empty();
    }

    /** 向玩家的 SoulBottle 或 PlanarCirclet 加 1 魂（优先 SoulBottle） */
    private static void giveSoul(Player p, int amount) {
        var inv = p.getCapability(top.theillusivec4.curios.api.CuriosCapability.INVENTORY).resolve();
        if (inv.isEmpty()) return;
        int left = amount;
        for (String slotId : List.of(NyctocosmCurios.SLOT_SOUL_BOTTLE, NyctocosmCurios.SLOT_RING)) {
            var sh = inv.get().getStacksHandler(slotId).orElse(null);
            if (sh == null) continue;
            for (int i = 0; i < sh.getSlots() && left > 0; i++) {
                ItemStack s = sh.getStacks().getStackInSlot(i);
                if (!(s.getItem() instanceof SoulBottleItem) && !(s.getItem() instanceof PlanarCircletItem)) continue;
                int cur = s.getOrCreateTag().getInt(SoulBottleItem.TAG_SOULS_HELD);
                int space = SoulBottleItem.MAX_SOULS - cur;
                if (space <= 0) continue;
                int add = Math.min(left, space);
                s.getOrCreateTag().putInt(SoulBottleItem.TAG_SOULS_HELD, cur + add);
                left -= add;
            }
        }
    }
}
