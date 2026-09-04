package com.yifei.nyctocosm.curio;

import com.yifei.nyctocosm.NyctocosmMod;
import com.yifei.nyctocosm.item.curio.NyctocosmCurioItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Curios API 集成入口（单一职责）。
 * <p>
 * 职责：
 * <ol>
 *   <li>触发 14 个饰品 Item 的 DeferredRegister 注册（4 种槽位由 datapack JSON 声明，见
 *       {@code resources/data/nyctocosm/curios/slots/}）</li>
 *   <li>注册全局饰品被动事件监听器（{@link ArtifactPassiveHandler}）到 Forge 主线程事件总线</li>
 *   <li>提供面向业务层的静态工具：{@link #getCurioStacks(Player, String)} 等，封装 Curios Capability 查询</li>
 * </ol>
 * <p>
 * 槽位按功能拆分（替代单一 charm 槽）：
 * <ul>
 *   <li>{@link #SLOT_RING}（1 格）—— PlanarCirclet 戒指</li>
 *   <li>{@link #SLOT_SOUL_BOTTLE}（1 格）—— SoulBottle 灵魂瓶</li>
 *   <li>{@link #SLOT_RELIC}（3 格）—— 6 种 Protection 圣物（元素抗性）</li>
 *   <li>{@link #SLOT_ARTIFACT}（3 格）—— 8 种功能圣物（疾走/悬浮/萃取等）</li>
 * </ul>
 */
public final class NyctocosmCurios {

    /** 戒指槽位 ID —— 装 PlanarCirclet 戒指 */
    public static final String SLOT_RING = "ring";

    /** 灵魂瓶槽位 ID —— 装 SoulBottle（1 格，独立槽避免与其他饰品争用） */
    public static final String SLOT_SOUL_BOTTLE = "soul_bottle";

    /** 圣物槽位 ID —— 装 6 种 Protection 圣物（3 格，可同时佩戴多种元素抗性） */
    public static final String SLOT_RELIC = "relic";

    /** 功能圣物槽位 ID —— 装 8 种功能性圣物（3 格，可同时佩戴多种被动） */
    public static final String SLOT_ARTIFACT = "artifact";

    private NyctocosmCurios() {}

    /**
     * 模组加载阶段入口，由 {@link NyctocosmMod} 构造函数调用。
     *
     * @param modBus IEventBus（从 {@link net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext} 获得，
     *               避免调用已 forRemoval 的 FMLJavaModLoadingContext.get()）
     */
    public static void init(IEventBus modBus) {
        // 注册 Curios 饰品 Item：戒指 1 + 灵魂瓶 1 + 12 种圣物 = 14
        NyctocosmCurioItems.ITEMS.register(modBus);

        // 注册 Player 能力附加兼容（占位；Curios 会自动注入 INVENTORY 能力）
        MinecraftForge.EVENT_BUS.register(NyctocosmCurios.class);
        // 注册全局饰品被动 Handler（LivingTick/LivingDamage/LivingDeath/PlayerInteract 驱动）
        MinecraftForge.EVENT_BUS.register(ArtifactPassiveHandler.class);
    }

    // ==================== 工具方法（迪米特法则：业务层直接调这里，不直接依赖 CuriosCapability） ====================

    /**
     * 读取玩家指定 Curios 槽位所有物品（只读快照，避免并发遍历异常）。
     *
     * @param player 玩家（必须 Server 端；Client 端 Curios Capability 不保证同步）
     * @param slotId 槽位 ID，如 {@link #SLOT_RING} / {@link #SLOT_SOUL_BOTTLE} / {@link #SLOT_RELIC} / {@link #SLOT_ARTIFACT}
     * @return 非 null 列表；未找到或 Client 端返回空列表
     */
    @Nonnull
    public static List<ItemStack> getCurioStacks(@Nonnull Player player, @Nonnull String slotId) {
        if (player.level().isClientSide) return Collections.emptyList();
        Optional<ICuriosItemHandler> opt = player.getCapability(CuriosCapability.INVENTORY).resolve();
        if (opt.isEmpty()) return Collections.emptyList();
        ICurioStacksHandler handler = opt.get().getStacksHandler(slotId).orElse(null);
        if (handler == null) return Collections.emptyList();
        List<ItemStack> result = new ArrayList<>(handler.getSlots());
        for (int i = 0; i < handler.getSlots(); i++) {
            ItemStack s = handler.getStacks().getStackInSlot(i);
            if (!s.isEmpty()) result.add(s);
        }
        return result;
    }

    /**
     * 遍历玩家所有饰品槽（ring + soul_bottle + relic + artifact），返回佩戴中具有指定接口/父类的 ItemStack 列表。
     */
    @Nonnull
    public static <T> List<ItemStack> findCuriosByClass(@Nonnull Player player, @Nonnull Class<T> clazz) {
        List<ItemStack> all = new ArrayList<>(getCurioStacks(player, SLOT_RING));
        all.addAll(getCurioStacks(player, SLOT_SOUL_BOTTLE));
        all.addAll(getCurioStacks(player, SLOT_RELIC));
        all.addAll(getCurioStacks(player, SLOT_ARTIFACT));
        all.removeIf(s -> !clazz.isInstance(s.getItem()));
        return all;
    }

    /**
     * 原子性地修改某槽里第一个匹配 itemClass 的 ItemStack 的 NBT int。
     *
     * @return 变更后新值；若未找到 / Client 端 返回 {@link java.util.OptionalInt#empty()}
     */
    @Nonnull
    public static java.util.OptionalInt modifyCurioNbtInt(@Nonnull Player player,
                                                          @Nonnull String slotId,
                                                          @Nonnull Class<?> itemClass,
                                                          @Nonnull String nbtKey,
                                                          int delta, int min, int max) {
        if (player.level().isClientSide) return java.util.OptionalInt.empty();
        Optional<ICuriosItemHandler> opt = player.getCapability(CuriosCapability.INVENTORY).resolve();
        if (opt.isEmpty()) return java.util.OptionalInt.empty();
        ICurioStacksHandler handler = opt.get().getStacksHandler(slotId).orElse(null);
        if (handler == null) return java.util.OptionalInt.empty();
        for (int i = 0; i < handler.getSlots(); i++) {
            ItemStack s = handler.getStacks().getStackInSlot(i);
            if (s.isEmpty() || !itemClass.isInstance(s.getItem())) continue;
            AtomicInteger cur = new AtomicInteger(s.getOrCreateTag().getInt(nbtKey));
            int next = Math.max(min, Math.min(max, cur.addAndGet(delta)));
            s.getOrCreateTag().putInt(nbtKey, next);
            return java.util.OptionalInt.of(next);
        }
        return java.util.OptionalInt.empty();
    }

    /** 读取某槽第一个匹配 itemClass 实例 NBT 中 int 值（未找到 / Client 端返回 0）。 */
    public static int readCurioNbtInt(@Nonnull Player player,
                                      @Nonnull String slotId,
                                      @Nonnull Class<?> itemClass,
                                      @Nonnull String nbtKey) {
        if (player.level().isClientSide) return 0;
        for (ItemStack s : getCurioStacks(player, slotId)) {
            if (itemClass.isInstance(s.getItem())) {
                return s.getOrCreateTag().getInt(nbtKey);
            }
        }
        return 0;
    }

    /**
     * 是否存在某个匹配条件的饰品（等价于原数据包 predicate wearing_ring / wearing_bottle）。
     *
     * @param extraNbtTagTrue 非 null 时，要求 ItemStack NBT 中同名 boolean = true（如 "active"）
     */
    public static boolean hasCurio(@Nonnull Player player, @Nonnull Class<?> itemClass, String extraNbtTagTrue) {
        List<ItemStack> all = new ArrayList<>(getCurioStacks(player, SLOT_RING));
        all.addAll(getCurioStacks(player, SLOT_SOUL_BOTTLE));
        all.addAll(getCurioStacks(player, SLOT_RELIC));
        all.addAll(getCurioStacks(player, SLOT_ARTIFACT));
        for (ItemStack s : all) {
            if (!itemClass.isInstance(s.getItem())) continue;
            if (extraNbtTagTrue == null || s.getOrCreateTag().getBoolean(extraNbtTagTrue)) {
                return true;
            }
        }
        return false;
    }

    // ==================== Player 能力附加兼容（占位；Curios 会自动注入 INVENTORY 能力） ====================

    @SubscribeEvent
    public static void onAttachPlayerCaps(AttachCapabilitiesEvent<net.minecraft.world.entity.Entity> event) {
        // 当前版本 Curios 5.14 已自动处理 INVENTORY 能力挂载。
        // 若未来需要往 Player 附加全局 SoulsHeld 聚合能力等，可在此处附加 ICapabilityProvider。
    }
}
