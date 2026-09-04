package com.yifei.nyctocosm.item.curio;

import com.yifei.nyctocosm.NyctocosmMod;
import com.yifei.nyctocosm.curio.NyctocosmCurios;
import com.yifei.nyctocosm.spell.SpellCastingManager;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * 戒指 "平面环" Planar Circlet（SRP：激活态切换 + 灵魂持有 + 触发施法入口）。
 * <p>
 * 对应原数据包 predicates:
 * <ul>
 *   <li>{@code spells:ring/wearing_ring} = 玩家在 ring 槽佩戴本物品（planar_circlet:1）</li>
 *   <li>{@code spells:ring/wearing_active_ring} = 额外 {@code active:1}</li>
 * </ul>
 * 对应原数据包 mcfunction:
 * <ul>
 *   <li>{@code spells:ring/activate.mcfunction} = 潜行+右键未激活 → 消耗 1 魂 → 激活态</li>
 *   <li>{@code spells:ring/deactivate.mcfunction} = 潜行+右键已激活 → 关闭</li>
 *   <li>{@code click_ring=1 + wearing_active_ring} → 尝试 {@link SpellCastingManager#tryStartCast(Player)}</li>
 * </ul>
 * Java 化后的交互更直观：
 * <ul>
 *   <li>潜行+右键（戒指在 hand/饰品）= 激活/停用切换（需 1 魂）</li>
 *   <li>激活态下，再次潜行+右键（停留时间短，即"连按"）→ 调用 tryStartCast</li>
 *   <li>激活态下，按主手攻击（对空气 left click）→ 等价 click_ring：消耗 1 魂 + tryStartCast（由 ArtifactPassiveHandler 监听）</li>
 * </ul>
 */
public class PlanarCircletItem extends Item implements ICurioItem {

    /** NBT：灵魂持有数（与原数据包同名） */
    public static final String TAG_SOULS_HELD = SoulBottleItem.TAG_SOULS_HELD;
    /** NBT：戒指激活态（与原数据包同名 active:1） */
    public static final String TAG_ACTIVE = "active";

    public PlanarCircletItem() {
        super(new Item.Properties().stacksTo(1));
    }

    // ==================== 右键交互 ====================

    @Override
    @Nonnull
    public InteractionResultHolder<ItemStack> use(@Nonnull Level level, Player player, @Nonnull InteractionHand hand) {
        if (level.isClientSide) return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), true);
        if (!player.isShiftKeyDown()) return InteractionResultHolder.pass(player.getItemInHand(hand));

        // 潜行+右键 → 切换激活 / 启动法术（若已激活直接走 tryStartCast）
        boolean ok = onSneakRightClick(player, player.getItemInHand(hand));
        return ok
                ? InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), false)
                : InteractionResultHolder.fail(player.getItemInHand(hand));
    }

    /**
     * 潜行右键触发逻辑，供 {@link com.yifei.nyctocosm.curio.ArtifactPassiveHandler} 在饰品不在手上但在
     * 饰品槽时同样能触发使用（避免玩家必须把戒指拿在手里，提升体验）。
     *
     * @return true 处理成功（有副作用）
     */
    public static boolean onSneakRightClick(@Nonnull Player player, @Nonnull ItemStack circlet) {
        if (!(circlet.getItem() instanceof PlanarCircletItem)) return false;
        Level level = player.level();
        if (level.isClientSide) return false;

        boolean active = circlet.getOrCreateTag().getBoolean(TAG_ACTIVE);

        if (!active) {
            // --- 激活：消耗 1 魂，播放音效 ---
            boolean consumed = SoulBottleItem.tryConsumeSouls(player, 1);
            if (!consumed) {
                player.sendSystemMessage(Component.translatable("item.nyctocosm.planar_coil.no_soul")
                        .withStyle(ChatFormatting.DARK_RED));
                return false;
            }
            circlet.getOrCreateTag().putBoolean(TAG_ACTIVE, true);
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.AMETHYST_BLOCK_RESONATE, SoundSource.PLAYERS, 0.9F, 1.4F);
            player.displayClientMessage(Component.translatable("item.nyctocosm.planar_coil.activated")
                    .withStyle(ChatFormatting.AQUA), true);
            return true;
        }

        // --- 已激活：立即尝试蜡烛阵施法（等价 click_ring）---
        // 先扣 1 魂（与原 spells/ring/activate -> subtract 执行路径一致）
        boolean consumed = SoulBottleItem.tryConsumeSouls(player, 1);
        if (!consumed) {
            // 魂不够：退出激活态
            circlet.getOrCreateTag().putBoolean(TAG_ACTIVE, false);
            player.sendSystemMessage(Component.translatable("item.nyctocosm.planar_coil.no_soul_deactivate")
                    .withStyle(ChatFormatting.DARK_RED));
            return true;
        }
        boolean casted = SpellCastingManager.tryStartCast(player);
        if (!casted) {
            // 施法失败（没摆蜡烛/没材料）→ 关闭激活
            circlet.getOrCreateTag().putBoolean(TAG_ACTIVE, false);
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 0.5F, 0.9F);
        }
        return true;
    }

    // ==================== 佩戴交互 ====================

    @Override
    public boolean canEquip(SlotContext context, ItemStack stack) {
        // 戒指只能在 ring 槽（Curios 5.x 签名：SlotContext, ItemStack —— 顺序与旧版不同）
        return NyctocosmCurios.SLOT_RING.equals(context.identifier());
    }

    @Override
    public void onUnequip(SlotContext context, ItemStack newStack, ItemStack stack) {
        // 取下戒指：关闭激活（等价于未 wearing_ring）
        if (stack.getItem() instanceof PlanarCircletItem) {
            stack.getOrCreateTag().putBoolean(TAG_ACTIVE, false);
        }
    }

    // ==================== Tooltip ====================

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level level,
                                @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
        int souls = stack.getOrCreateTag().getInt(TAG_SOULS_HELD);
        boolean active = stack.getOrCreateTag().getBoolean(TAG_ACTIVE);
        // 原数据包 Name: planar_coil (金) + planar_coil1 (水) 混合色
        tooltip.add(Component.translatable("item.nyctocosm.container.soul_count",
                        Component.literal(String.valueOf(souls)).withStyle(ChatFormatting.DARK_AQUA))
                .withStyle(ChatFormatting.DARK_GRAY));
        Component status = active
                ? Component.translatable("item.nyctocosm.planar_coil.state.active").withStyle(ChatFormatting.GREEN)
                : Component.translatable("item.nyctocosm.planar_coil.state.inactive").withStyle(ChatFormatting.GRAY);
        tooltip.add(Component.translatable("item.nyctocosm.planar_coil.state", status)
                .withStyle(ChatFormatting.DARK_GRAY));
        tooltip.add(Component.translatable("item.nyctocosm.planar_coil.tip")
                .withStyle(ChatFormatting.GRAY));
    }

    // ==================== 业务工具 ====================

    /** 读取玩家戒指中的灵魂数（0 表示未戴/未持有） */
    public static int readSoulsHeld(Player player) {
        return NyctocosmCurios.readCurioNbtInt(player, NyctocosmCurios.SLOT_RING,
                PlanarCircletItem.class, TAG_SOULS_HELD);
    }

    /** 是否戴了激活态戒指（等价于原 predicates wearing_active_ring） */
    public static boolean isWearingActive(Player player) {
        return NyctocosmCurios.hasCurio(player, PlanarCircletItem.class, TAG_ACTIVE);
    }
}
