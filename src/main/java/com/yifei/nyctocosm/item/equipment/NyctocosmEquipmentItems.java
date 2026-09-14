package com.yifei.nyctocosm.item.equipment;

import com.yifei.nyctocosm.NyctocosmMod;
import com.yifei.nyctocosm.spell.SpellCastingManager;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

/**
 * 武器类物品注册中心（单一职责：注册 4 把 Nyctocosm 武器）。
 * <p>
 * 设计：
 * <ol>
 *   <li>构造期 ItemStack 自动注入 NBT 标记（{@code scythe:1 / wyrmsliver:1 / nullfissure:1 / semblanceofdoom:1}）
 *       以保留原数据包 predicate 的向后兼容（即使 item type 已改为独立模组物品，NBT 判断仍可命中）。</li>
 *   <li>每把武器重写 {@code use()} 以提供右键交互：镰刀/龙鳞刃近战挥砍冷却反馈，
 *       法杖 NullFissure 尝试触发法术 {@link SpellCastingManager#tryStartCast(Player)}，
 *       末日残影 前向 dash。</li>
 *   <li>镰刀 seelic_scythe 额外注入 {@code reaping:1} 标记（LivingDamageEvent 可识别）。</li>
 * </ol>
 */
public final class NyctocosmEquipmentItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, NyctocosmMod.MOD_ID);

    // 希里克镰刀（收割者，Sword + 右键反馈 + reaping 标记）
    public static final RegistryObject<Item> SEELIC_SCYTHE = ITEMS.register("seelic_scythe",
            () -> new NyctocosmWeaponItem(Tiers.NETHERITE, 7, -2.6F,
                    new Item.Properties().stacksTo(1).rarity(Rarity.RARE).fireResistant(),
                    "seelic_scythe",
                    weaponStack -> {
                        CompoundTag t = weaponStack.getOrCreateTag();
                        t.putBoolean("scythe", true);
                        t.putBoolean("reaping", true);
                    }) {
                @Override
                public boolean hurtEnemy(@Nonnull ItemStack stack, @Nonnull LivingEntity target,
                                         @Nonnull LivingEntity attacker) {
                    stack.hurtAndBreak(1, attacker, e -> e.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                    if (!target.level().isClientSide) {
                        target.level().playSound(null, target.getX(), target.getY(), target.getZ(),
                                SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 0.6F, 0.8F);
                    }
                    return true;
                }

                // [修复 #8] 右键 = 冲刺（对齐 click_scythe → scythe/dash）
                @Nonnull
                @Override
                public InteractionResultHolder<ItemStack> use(@Nonnull Level level, Player player,
                                                               @Nonnull InteractionHand hand) {
                    ItemStack stack = player.getItemInHand(hand);
                    if (tagInit != null) tagInit.accept(stack);
                    scytheDash(player, level);
                    return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
                }

                @Override
                public boolean isCorrectToolForDrops(@Nonnull BlockState state) {
                    return false; // 纯近战武器，不需要加速挖方块
                }
            });

    // 梦魇裂片（巨蛇，Sword + 攻击+毒）
    public static final RegistryObject<Item> WYRMSLIVER = ITEMS.register("wyrmsliver",
            () -> new NyctocosmWeaponItem(Tiers.NETHERITE, 6, -2.2F,
                    new Item.Properties().stacksTo(1).rarity(Rarity.RARE),
                    "wyrmsliver",
                    weaponStack -> weaponStack.getOrCreateTag().putBoolean("wyrmsliver", true)) {
                @Override
                public boolean hurtEnemy(@Nonnull ItemStack stack, @Nonnull LivingEntity target,
                                         @Nonnull LivingEntity attacker) {
                    stack.hurtAndBreak(1, attacker, e -> e.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                    if (!target.level().isClientSide && attacker.getRandom().nextFloat() < 0.4F) {
                        target.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                                net.minecraft.world.effect.MobEffects.POISON, 60, 1));
                    }
                    return true;
                }

                // [修复 #8] 右键 = 剑气横扫（对齐 click_sliver → wyrmsliver/slash，cd20）
                @Nonnull
                @Override
                public InteractionResultHolder<ItemStack> use(@Nonnull Level level, Player player,
                                                               @Nonnull InteractionHand hand) {
                    ItemStack stack = player.getItemInHand(hand);
                    if (tagInit != null) tagInit.accept(stack);
                    wyrmsliverSlash(player, level, 20, stack.getItem());
                    return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
                }

                @Override
                public boolean isCorrectToolForDrops(@Nonnull BlockState state) { return false; }
            });

    // 虚无裂隙（术士法杖：非 SwordItem，右键 = tryStartCast 法术触发）
    public static final RegistryObject<Item> NULLFISSURE = ITEMS.register("nullfissure",
            () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)) {

                @Override
                public void onCraftedBy(@Nonnull ItemStack stack, @Nonnull Level level, @Nonnull Player player) {
                    stack.getOrCreateTag().putBoolean("nullfissure", true);
                }

                @Override
                public void initializeClient(java.util.function.Consumer<net.minecraftforge.client.extensions.common.IClientItemExtensions> consumer) {}

                @Nonnull
                @Override
                public InteractionResultHolder<ItemStack> use(@Nonnull Level level, Player player,
                                                               @Nonnull InteractionHand hand) {
                    ItemStack stack = player.getItemInHand(hand);
                    stack.getOrCreateTag().putBoolean("nullfissure", true); // 防御：给旧物品补标记
                    if (!level.isClientSide) {
                        // 法杖右键等价于戒指的"法术触发"（近似原 nullfissure/cast：副手施法 40 格虚空束；
                        // 虚空束逐格 raycast 的弹道实体还原留待武器验收阶段）
                        boolean ok = SpellCastingManager.tryStartCast(player);
                        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                                ok ? SoundEvents.ILLUSIONER_CAST_SPELL : SoundEvents.VILLAGER_NO,
                                SoundSource.PLAYERS, 1F, 1F);
                        if (ok) {
                            // 对齐 fissurecastcooldown = 25 + 施法惩罚（自身缓慢 1s，原 nullfissure/cast）
                            player.getCooldowns().addCooldown(stack.getItem(), 25);
                            player.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                                    net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN, 20, 0, true, false));
                        }
                    }
                    return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
                }

                @Override
                public boolean isEnchantable(@Nonnull ItemStack stack) { return false; }

                @Override
                public int getEnchantmentValue() { return 0; }

                @Override
                public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level level,
                                            @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
                    tooltip.add(Component.translatable("item.nyctocosm.nullfissure.lore")
                            .withStyle(ChatFormatting.DARK_GRAY));
                    tooltip.add(Component.translatable("item.nyctocosm.nullfissure.lore1",
                            Component.translatable("item.nyctocosm.nullfissure.lore2")
                                    .withStyle(ChatFormatting.DARK_PURPLE))
                            .withStyle(ChatFormatting.DARK_GRAY));
                    tooltip.add(Component.literal("右键：尝试施法（消耗蜡烛阵/能量）")
                            .withStyle(ChatFormatting.UNDERLINE, ChatFormatting.DARK_PURPLE));
                }
            });

    // 末日残影（使者剑：高伤害 + 主手烈焰斩 / 副手火球，右键直驱 [修复 #8]）
    public static final RegistryObject<Item> SEMBLANCE_OF_DOOM = ITEMS.register("semblanceofdoom",
            () -> new NyctocosmWeaponItem(Tiers.NETHERITE, 9, -2.6F,
                    new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant(),
                    "semblanceofdoom",
                    weaponStack -> weaponStack.getOrCreateTag().putBoolean("semblanceofdoom", true)) {
                @Override
                public boolean hurtEnemy(@Nonnull ItemStack stack, @Nonnull LivingEntity target,
                                         @Nonnull LivingEntity attacker) {
                    stack.hurtAndBreak(1, attacker, e -> e.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                    if (!target.level().isClientSide && attacker.getRandom().nextFloat() < 0.3F) {
                        target.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                                net.minecraft.world.effect.MobEffects.WITHER, 80, 1));
                    }
                    return true;
                }

                @Override
                public boolean isCorrectToolForDrops(@Nonnull BlockState state) { return false; }

                // [修复 #8] 右键 = 主手烈焰斩（doom/swing，cd30）；潜行+右键 = 副手火球（doom/shoot，cd30）
                @Nonnull
                @Override
                public InteractionResultHolder<ItemStack> use(@Nonnull Level level, Player player,
                                                               @Nonnull InteractionHand hand) {
                    ItemStack stack = player.getItemInHand(hand);
                    if (tagInit != null) tagInit.accept(stack);
                    if (player.isShiftKeyDown()) {
                        doomShoot(player, level, 30, stack.getItem());
                    } else {
                        doomSwing(player, level, 30, stack.getItem());
                    }
                    return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
                }
            });

    // ==================== [修复 #8] Java 事件直驱：武器技能（对齐原 items 命名空间）====================
    // 原触发映射：click_scythe→scythe/dash；click_sliver→wyrmsliver/slash(cd20)；
    //   click_fissure 主手→nullfissure/swing(cd20)、副手→nullfissure/cast(cd25)；
    //   click_doom 主手→semblanceofdoom/swing(cd30)、副手→semblanceofdoom/shoot(cd30)。
    // 数值口径：原包 damageAmt 为 ×1000 内部单位，实际伤害 = 值/1000（swinghit 15500→15.5）。
    // 注：原包"移动弹道实体"（wyrmsliver 剑气 marker 逐 t 前移 / doomfireball 飞行实体）本轮以
    //   "即时锥形/射线命中 + 粒子轨迹"近似（伤害/冷却/元素与原包一致），逐 tick 弹道实体还原
    //   留待武器验收阶段（P11）逐项对照。

    /** 命中集获取：以 origin 为中心、radius 半径内的可攻击生物（排除玩家自身） */
    private static List<LivingEntity> livingAround(Level level, Vec3 origin, double radius) {
        return level.getEntitiesOfClass(LivingEntity.class,
                new net.minecraft.world.phys.AABB(origin, origin).inflate(radius),
                e -> e.isAlive() && e.isAttackable());
    }

    /** 对单个目标结算伤害（可选点燃，用玩家来源，避免触发自身无敌帧刷伤） */
    private static void hurtTarget(Player p, LivingEntity target, float dmg, boolean fire) {
        if (fire) target.setSecondsOnFire(100);
        target.hurt(p.damageSources().playerAttack(p), dmg);
    }

    /**
     * 镰刀冲刺（scythe/dash）：速度 + 前向突进，命中路径 ≤2 格内生物 4.0 伤害（damageAmt 4000），
     * 冲刺音效。原包 dash 还带"撞 impenetrable 停"与 portal 粒子，属演出细节（P11 对照）。
     */
    private static void scytheDash(Player p, Level level) {
        if (!level.isClientSide) {
            p.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                    net.minecraft.world.effect.MobEffects.MOVEMENT_SPEED, 20, 1, true, false));
            Vec3 look = p.getLookAngle();
            p.setDeltaMovement(p.getDeltaMovement().add(look.multiply(0.9, 0, 0.9).add(0, 0.18, 0)));
            p.hurtMarked = true;
            p.resetFallDistance();
            // 冲刺路径伤害（射线近似：前方 7 格、横向 2 格内）
            Vec3 eye = p.getEyePosition();
            for (LivingEntity e : livingAround(level, p.position(), 7.0)) {
                if (e == p) continue;
                Vec3 to = e.position().subtract(eye).multiply(1, 0, 1);
                Vec3 lookXZ = new Vec3(look.x, 0, look.z).normalize();
                double lateral = to.subtract(lookXZ.scale(to.dot(lookXZ))).length();
                double along = to.dot(lookXZ);
                if (along >= 0 && along <= 7.0 && lateral <= 2.0) {
                    e.hurt(p.damageSources().playerAttack(p), 4.0F);
                }
            }
            level.playSound(null, p.getX(), p.getY(), p.getZ(),
                    SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 1F, 0.7F);
        }
    }

    /**
     * 龙鳞刃横扫（wyrmsliver/slash，cd20）：召唤剑气（原 marker 逐 t 前移 50t）。
     * 近似：前方 10 格、横向 1.5 格锥形内目标 14.0 伤害（NIGHTMARE 元素）+ 失明 5s；
     * 自身缓慢 1s + 粒子闪光。
     */
    private static void wyrmsliverSlash(Player p, Level level, int cdTicks, net.minecraft.world.item.Item item) {
        if (!level.isClientSide) {
            p.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                    net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN, 20, 0, true, false));
            Vec3 look = p.getLookAngle();
            Vec3 eye = p.getEyePosition();
            double range = 10.0;
            for (LivingEntity e : livingAround(level, p.position(), range + 1)) {
                if (e == p) continue;
                Vec3 to = e.position().subtract(eye);
                Vec3 dir = to.normalize();
                if (to.length() <= range && dir.dot(look) > 0.45) { // ≈63° 锥形（与挥砍剑气宽度近似）
                    e.hurt(com.yifei.nyctocosm.damage.NyctocosmDamageSources.spellDirect(p,
                            com.yifei.nyctocosm.spell.ElementType.NIGHTMARE), 14.0F);
                    e.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                            net.minecraft.world.effect.MobEffects.BLINDNESS, 5 * 20, 0, true, false));
                }
            }
            level.playSound(null, p.getX(), p.getY(), p.getZ(),
                    SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 1F, 0.9F);
        }
        p.getCooldowns().addCooldown(item, cdTicks);
    }

    /** 末日残影主手烈焰斩（doom/swing，cd30）：周身 2 格内 21.0 伤害 + 点燃 100s + 火焰粒子 */
    private static void doomSwing(Player p, Level level, int cdTicks, net.minecraft.world.item.Item item) {
        if (!level.isClientSide) {
            for (LivingEntity e : livingAround(level, p.position(), 2.0)) {
                if (e == p) continue;
                e.setSecondsOnFire(100);
                e.hurt(p.damageSources().playerAttack(p), 21.0F);
            }
            if (level instanceof net.minecraft.server.level.ServerLevel sl) {
                sl.sendParticles(net.minecraft.core.particles.ParticleTypes.FLAME,
                        p.getX(), p.getY() + 0.9, p.getZ(), 40, 1.5, 0.6, 1.5, 0.02);
                sl.sendParticles(net.minecraft.core.particles.ParticleTypes.SWEEP_ATTACK,
                        p.getX(), p.getY() + 0.9, p.getZ(), 1, 0, 0, 0, 0);
            }
            level.playSound(null, p.getX(), p.getY(), p.getZ(),
                    SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 1F, 1F);
        }
        p.getCooldowns().addCooldown(item, cdTicks);
    }

    /** 末日残影副手火球（doom/shoot，cd30）：≤30 格射线首目标 19.0 + 点燃 + 火球粒子轨迹 */
    private static void doomShoot(Player p, Level level, int cdTicks, net.minecraft.world.item.Item item) {
        if (!level.isClientSide) {
            Vec3 eye = p.getEyePosition();
            Vec3 look = p.getLookAngle();
            LivingEntity best = null;
            double bestDist = 30.0;
            for (LivingEntity e : livingAround(level, eye, 30.0)) {
                if (e == p) continue;
                Vec3 to = e.getBoundingBox().clip(eye, eye.add(look.scale(30.0))).orElse(null);
                if (to == null) continue;
                double d = eye.distanceToSqr(to);
                if (d < bestDist * bestDist) { bestDist = Math.sqrt(d); best = e; }
            }
            if (best != null) {
                best.setSecondsOnFire(100);
                best.hurt(p.damageSources().playerAttack(p), 19.0F);
            }
            if (level instanceof net.minecraft.server.level.ServerLevel sl) {
                Vec3 end = best != null ? best.getEyePosition() : eye.add(look.scale(30.0));
                for (int i = 0; i <= 10; i++) {
                    Vec3 step = eye.lerp(end, i / 10.0);
                    sl.sendParticles(net.minecraft.core.particles.ParticleTypes.FLAME,
                            step.x, step.y, step.z, 2, 0.1, 0.1, 0.1, 0.01);
                }
                sl.sendParticles(net.minecraft.core.particles.ParticleTypes.SMOKE,
                        end.x, end.y, end.z, 12, 0.2, 0.2, 0.2, 0.02);
            }
            level.playSound(null, p.getX(), p.getY(), p.getZ(),
                    SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 1F, 1.3F);
        }
        p.getCooldowns().addCooldown(item, cdTicks);
    }

    private NyctocosmEquipmentItems() {}

    public static void init(IEventBus modBus) {
        ITEMS.register(modBus);
    }

    /**
     * 通用武器 Item：继承 SwordItem 获得近战/攻击属性。
     *
     * @param tagInitializer 构造后 NBT 标记初始化回调（注入 scythe:1 等）
     */
    static class NyctocosmWeaponItem extends SwordItem {

        protected final String nameKey;
        protected final Consumer<ItemStack> tagInit;

        NyctocosmWeaponItem(Tier tier, int attackDamage, float attackSpeed,
                            Item.Properties props, String nameKey,
                            Consumer<ItemStack> tagInitializer) {
            super(tier, attackDamage, attackSpeed, props);
            this.nameKey = nameKey;
            this.tagInit = tagInitializer;
        }

        /** /give 或创造栏拖出后进入玩家物品栏时自动打 NBT。 */
        @Override
        public void onCraftedBy(@Nonnull ItemStack stack, @Nonnull Level level, @Nonnull Player player) {
            if (tagInit != null) tagInit.accept(stack);
        }

        /**
         * 右键使用：
         *  - 镰刀/龙鳞刃：播放挥砍音效，给视觉反馈（等效于原 click_scythe 触发）。
         *  - 末日残影：已在子类 override 为 dash。
         */
        @Nonnull
        @Override
        public InteractionResultHolder<ItemStack> use(@Nonnull Level level, Player player,
                                                       @Nonnull InteractionHand hand) {
            ItemStack stack = player.getItemInHand(hand);
            if (tagInit != null) tagInit.accept(stack); // 防御：对从老版本升级的物品补 NBT
            if (!level.isClientSide) {
                level.playSound(null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 1F, 0.8F);
                // 挥砍冷却 0.4 秒
                player.getCooldowns().addCooldown(this, 8);
            }
            player.startUsingItem(hand);
            return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
        }

        @Override
        public UseAnim getUseAnimation(@Nonnull ItemStack stack) { return UseAnim.SPEAR; }

        @Override
        public int getUseDuration(@Nonnull ItemStack stack) { return 12; }

        @Override
        public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level level,
                                    @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
            tooltip.add(Component.translatable("item.nyctocosm." + nameKey + ".lore")
                    .withStyle(ChatFormatting.DARK_GRAY));
            tooltip.add(Component.translatable("item.nyctocosm." + nameKey + ".lore1",
                    Component.translatable("item.nyctocosm." + nameKey + ".lore2"))
                    .withStyle(ChatFormatting.DARK_GRAY));
        }
    }
}
