package com.yifei.nyctocosm.item.curio;

import com.yifei.nyctocosm.NyctocosmMod;
import com.yifei.nyctocosm.curio.NyctocosmCurios;
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
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * 灵魂瓶饰品物品（SRP：只负责 souls_held 的读写 + 右键交互）。
 * <p>
 * 对应原数据包 {@code spells/bottle/give.mcfunction}（carrot_on_a_stick + CMD=1 + souls_held:1 + soul_bottle:1）。
 * <p>
 * [修复 #7] 右键语义已对齐原包（此前 Java 映射与数据包相反）：
 * <ul>
 *   <li>主手右键（未潜行）：等价于 items:playertick 的 click_bottle → {@code spells/bottle/subtract.mcfunction}，
 *       souls_held <b>-1</b>（扣至 0）。原数据包扣至 ≤0 会把物品替换回 glass_bottle；Java 物品是独立自定义
 *       物品，不转回玻璃瓶，仅扣至 0。</li>
 *   <li>主手 Shift+右键：原数据包<b>无</b>对应交互（Java 先前虚构的"潜行 -50"映射已移除，改仅提示）。</li>
 *   <li>灵魂<b>收集 +1</b> 不由右键完成：原包是击杀/灵魂标记（items:tick 对 soul marker）自动触发
 *       {@code spells/bottle/add.mcfunction}，Java 侧自动收集接入待「判定兼容改造（#8 范围）」统一处理。</li>
 * </ul>
 * 与 PlanarCirclet 共享的灵魂池：施法优先消耗 PlanarCirclet，不足时再向 SoulBottle 借。
 */
public class SoulBottleItem extends Item implements ICurioItem {

    /** NBT 标签名 —— 与原数据包完全一致（兼容性考虑） */
    public static final String TAG_SOULS_HELD = "souls_held";
    public static final int MAX_SOULS = 999;

    public SoulBottleItem() {
        // 堆叠数 1：灵魂数量是单物品实例独立属性
        super(new Item.Properties().stacksTo(1));
    }

    // ==================== 右键交互（充能/消耗） ====================

    @Override
    @Nonnull
    public InteractionResultHolder<ItemStack> use(@Nonnull Level level, Player player, @Nonnull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (level.isClientSide) return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);

        int cur = stack.getOrCreateTag().getInt(TAG_SOULS_HELD);
        if (player.isShiftKeyDown()) {
            // 原数据包无"潜行 -50"交互（Java 先前为虚构映射，[修复 #7] 移除）；仅提示收集方式
            player.displayClientMessage(Component.translatableWithFallback(
                    "item.nyctocosm.soul_bottle.tip_shift",
                    "灵魂瓶：击杀怪物或拾取灵魂标记会自动 +1 灵魂"), true);
        } else {
            // 对齐原包 click_bottle → spells:bottle/subtract：右键消耗 1 魂（扣至 0）
            if (cur <= 0) {
                player.displayClientMessage(Component.translatableWithFallback(
                        "item.nyctocosm.soul_bottle.empty", "灵魂瓶已空"), true);
                return InteractionResultHolder.fail(stack);
            }
            stack.getOrCreateTag().putInt(TAG_SOULS_HELD, cur - 1);
            player.playNotifySound(SoundEvents.BOTTLE_EMPTY, SoundSource.PLAYERS, 0.6F, 1.0F);
        }
        return InteractionResultHolder.sidedSuccess(stack, false);
    }

    // ==================== Tooltip：动态显示灵魂数量 ====================

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level level,
                                @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
        int count = stack.getOrCreateTag().getInt(TAG_SOULS_HELD);
        tooltip.add(Component.translatable("item.nyctocosm.container.soul_count",
                        Component.literal(String.valueOf(count)).withStyle(ChatFormatting.DARK_AQUA))
                .withStyle(ChatFormatting.DARK_GRAY));
        tooltip.add(Component.translatable("item.nyctocosm.soul_bottle.tip")
                .withStyle(ChatFormatting.GRAY));
    }

    // ==================== Curios 事件：被穿戴/取下时无需特殊动作，仅保留扩展点 ====================

    @Override
    public boolean canEquip(top.theillusivec4.curios.api.SlotContext context, ItemStack stack) {
        return NyctocosmCurios.SLOT_SOUL_BOTTLE.equals(context.identifier());
    }

    // ==================== 业务工具：读取/修改任意玩家的 SoulBottle 灵魂数 ====================

    /** 读取玩家佩戴中的所有 SoulBottle 灵魂数总和（若多个 Charm 放多瓶，则求和）。 */
    public static int getTotalSoulsHeld(Player player) {
        if (player == null || player.level().isClientSide) return 0;
        int total = 0;
        for (ItemStack s : NyctocosmCurios.getCurioStacks(player, NyctocosmCurios.SLOT_SOUL_BOTTLE)) {
            if (s.getItem() instanceof SoulBottleItem) {
                total += s.getOrCreateTag().getInt(TAG_SOULS_HELD);
            }
        }
        return total;
    }

    /**
     * 消耗若干灵魂（优先消耗 PlanarCirclet 中的，不够再向 SoulBottle 扣）。
     *
     * @param player 玩家
     * @param amount 需要消耗的量
     * @return true 全部扣完；false 灵魂不足（此时任何扣减都不发生）
     */
    public static boolean tryConsumeSouls(Player player, int amount) {
        if (player == null || player.level().isClientSide || amount <= 0) return false;
        int total = PlanarCircletItem.readSoulsHeld(player) + getTotalSoulsHeld(player);
        if (total < amount) return false;

        // 1) 先扣戒指中的灵魂
        int left = amount;
        left -= consumeCurioSouls(player, NyctocosmCurios.SLOT_RING, PlanarCircletItem.class, left);
        // 2) 剩余再扣 SoulBottle 槽
        //noinspection StatementWithEmptyBody
        while (left > 0 && consumeCurioSouls(player, NyctocosmCurios.SLOT_SOUL_BOTTLE, SoulBottleItem.class, 1) > 0) {
            left--;
        }
        return left == 0;
    }

    /**
     * 从指定槽第一个匹配实例中最多扣除 maxDrain 个灵魂，返回实际扣减数量。
     */
    private static int consumeCurioSouls(Player player, String slot, Class<?> itemCls, int maxDrain) {
        var opt = player.getCapability(top.theillusivec4.curios.api.CuriosCapability.INVENTORY).resolve();
        if (opt.isEmpty()) return 0;
        var stacksHandler = opt.get().getStacksHandler(slot).orElse(null);
        if (stacksHandler == null) return 0;
        for (int i = 0; i < stacksHandler.getSlots(); i++) {
            ItemStack s = stacksHandler.getStacks().getStackInSlot(i);
            if (s.isEmpty() || !itemCls.isInstance(s.getItem())) continue;
            int cur = s.getOrCreateTag().getInt(TAG_SOULS_HELD);
            if (cur <= 0) continue;
            int consumed = Math.min(maxDrain, cur);
            s.getOrCreateTag().putInt(TAG_SOULS_HELD, cur - consumed);
            return consumed;
        }
        return 0;
    }
}
