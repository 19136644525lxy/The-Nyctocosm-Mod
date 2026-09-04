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
                        // 法杖右键等价于戒指的"法术触发"
                        boolean ok = SpellCastingManager.tryStartCast(player);
                        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                                ok ? SoundEvents.ILLUSIONER_CAST_SPELL : SoundEvents.VILLAGER_NO,
                                SoundSource.PLAYERS, 1F, 1F);
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

    // 末日残影（使者剑：高伤害 + 右键前向 dash）
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

                /** 右键前向 dash 5 格（末日残影的基础机动）。 */
                @Nonnull
                @Override
                public InteractionResultHolder<ItemStack> use(@Nonnull Level level, Player player,
                                                               @Nonnull InteractionHand hand) {
                    ItemStack stack = player.getItemInHand(hand);
                    if (!level.isClientSide) {
                        Vec3 look = player.getLookAngle().multiply(2.4, 0, 2.4).add(0, 0.35, 0);
                        player.setDeltaMovement(player.getDeltaMovement().add(look));
                        player.hurtMarked = true;
                        player.resetFallDistance();
                        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                                SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 0.8F, 0.95F);
                        player.getCooldowns().addCooldown(this, 20 * 2); // 4 秒冷却
                    }
                    return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
                }
            });

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
        private final Consumer<ItemStack> tagInit;

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
