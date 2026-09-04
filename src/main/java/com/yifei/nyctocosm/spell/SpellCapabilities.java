package com.yifei.nyctocosm.spell;

import com.yifei.nyctocosm.NyctocosmMod;
import com.yifei.nyctocosm.spell.data.ISpellPlayerData;
import com.yifei.nyctocosm.spell.data.SpellPlayerData;
import com.yifei.nyctocosm.spell.data.SpellPlayerProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;

/**
 * 法术系统 Capability 注册与挂载点。
 * <p>
 * 设计：
 * <ul>
 *   <li>构造阶段（FMLConstructModEvent / RegisterCapabilitiesEvent）通过 CapabilityManager 注册 {@link ISpellPlayerData} Capability。</li>
 *   <li>AttachCapabilitiesEvent{@literal <Entity>} 给每个 Player 实体附加 {@link SpellPlayerProvider}。</li>
 *   <li>PlayerEvent.Clone 在玩家死亡重生/返回主世界时复制数据。</li>
 * </ul>
 */
@Mod.EventBusSubscriber(modid = NyctocosmMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class SpellCapabilities {

    /** Capability 单例（在 {@link #onRegisterCapabilities} 时由 Forge 注入真实实例；
     *  默认为空壳方便编译期引用，实际比较时与 Forge 返回的一致） */
    public static Capability<ISpellPlayerData> SPELL_PLAYER;

    /** Provider 挂载用的资源名（与 Nyctocosm 命名空间一致） */
    private static final ResourceLocation PROVIDER_KEY =
            ResourceLocation.fromNamespaceAndPath(NyctocosmMod.MOD_ID, "spell_player_data");

    private SpellCapabilities() {}

    /**
     * 注册自定义 Capability（MOD 总线）。
     * <p>
     * Forge 1.20.1 使用 {@link CapabilityManager#get(Class)} 在首次访问时惰性注册，
     * 配合 {@link net.minecraftforge.common.capabilities.CapabilityToken} 解析接口类型。
     * 这里显式调用一次 get() 提前注册，并赋值给静态常量方便 getCapability 比较。
     */
    @SubscribeEvent
    public static void onRegisterCapabilities(
            net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent event) {
        SPELL_PLAYER = CapabilityManager.get(SpellPlayerProvider.TOKEN);
        NyctocosmMod.LOGGER.info("[SpellCapabilities] 已注册 ISpellPlayerData Capability");
    }

    /**
     * MOD 总线 FMLConstructModEvent：同时初始化法术注册中心（保证在所有事件之前完成）。
     * 放这里比放主类构造函数晚，但注册顺序更可靠。
     */
    @SubscribeEvent
    public static void onConstruct(FMLConstructModEvent event) {
        SpellRegistry.init();
        NyctocosmMod.LOGGER.info("[SpellRegistry] 已注册 {} 个法术", SpellRegistry.size());
    }

    // =======================
    // Forge 总线（挂载 Provider / 死亡复制）
    // =======================

    @Mod.EventBusSubscriber(modid = NyctocosmMod.MOD_ID)
    public static final class ForgeBusHandlers {

        /** 为 Player 实体挂载 {@link SpellPlayerProvider} */
        @SubscribeEvent
        public static void onAttachCaps(AttachCapabilitiesEvent<Entity> event) {
            if (!(event.getObject() instanceof Player player)) return;
            event.addCapability(PROVIDER_KEY, new SpellPlayerProvider());
        }

        /**
         * 玩家死亡重生 / 从末地返回主世界：复制旧数据。
         * <p>
         * 原：isWasDeath == true 是死亡重生（必须保留持久化数据如 kills/damage）；
         *     false 是末地返回主世界（复制一切）。
         * 无论哪种都执行 copyFrom，吟唱状态由 PlayerClone 后 SpellCastingManager 的监听统一取消。
         */
        @SubscribeEvent
        public static void onPlayerClone(PlayerEvent.Clone event) {
            Player oldP = event.getOriginal();
            Player newP = event.getEntity();
            // 先反序列化原始玩家的 Capability（Forge 有时在克隆前就 invalidate）
            oldP.reviveCaps();
            oldP.getCapability(SPELL_PLAYER).ifPresent(oldData ->
                    newP.getCapability(SPELL_PLAYER).ifPresent(newData -> {
                        // deep copy 持久化字段（吟唱/平面 UUID 由 SpellCastingManager 再清理）
                        if (newData instanceof SpellPlayerData nd && oldData instanceof SpellPlayerData od) {
                            // 手动复制：保留统计/属性/activeSpells，丢弃吟唱状态（避免跨 tick 残留）
                            nd.setTraceChanceBase(od.getTraceChanceBase());
                            nd.setSpellPower(od.getSpellPower());
                            System.arraycopy(od.getDamageDealt(), 0, nd.getDamageDealt(), 0, 6);
                            System.arraycopy(od.getKills(), 0, nd.getKills(), 0, 6);
                            System.arraycopy(od.getSpellsCast(), 0, nd.getSpellsCast(), 0, 6);
                            nd.getActiveSpells().clear();
                            nd.getActiveSpells().putAll(od.getActiveSpells());
                            // SpellPlaneUUID 不复制（新实体未绑定）
                        } else {
                            // 兜底：接口定义的通用 copyFrom
                            newData.copyFrom(oldData);
                        }
                    }));
            oldP.invalidateCaps();
        }
    }
}
