package com.yifei.nyctocosm;

import com.mojang.logging.LogUtils;
import com.yifei.nyctocosm.client.NyctocosmClient;
import com.yifei.nyctocosm.config.NyctocosmConfig;
import com.yifei.nyctocosm.curio.NyctocosmCurios;
import com.yifei.nyctocosm.entity.NyctocosmEntities;
import com.yifei.nyctocosm.item.NyctocosmItems;
import com.yifei.nyctocosm.item.book.NyctocosmBookItems;
import com.yifei.nyctocosm.item.equipment.NyctocosmEquipmentItems;
import com.yifei.nyctocosm.item.material.NyctocosmMaterialItems;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

/**
 * Nyctocosm 模组入口类
 * 原数据包作者：Flubberschnub
 * 1.20.1 Forge 移植：Yifei
 * <p>
 * 设计原则：本类仅负责事件总线协调与配置注册，具体业务下沉到子包。
 */
@Mod(NyctocosmMod.MOD_ID)
public class NyctocosmMod {

    /** 模组唯一标识，与 mods.toml 中 modId 一致 */
    public static final String MOD_ID = "nyctocosm";

    /** 全局日志器 */
    public static final Logger LOGGER = LogUtils.getLogger();

    public NyctocosmMod(FMLJavaModLoadingContext context) {
        IEventBus modBus = context.getModEventBus();

        // 注册粒子类型（客户端渲染 provider 在 NyctocosmClient 中订阅）
        NyctocosmClient.PARTICLE_TYPES.register(modBus);

        // 注册创造模式物品栏
        NyctocosmItems.CREATIVE_TABS.register(modBus);

        // [P4+] 独立模组物品注册（替代原 NBT 包装 vanilla 物品方案）
        NyctocosmEquipmentItems.init(modBus);  // 4 武器
        NyctocosmBookItems.init(modBus);        // 5 卷册
        NyctocosmMaterialItems.init(modBus);     // 5 材料

        // [P4] Curios API 初始化：注册饰品槽（ring/charm）+ 14 个 Curio 物品 + 全局被动事件
        NyctocosmCurios.init(modBus);

        // 注册所有自定义实体类型（DeferredRegister，后续 boss/spell/effect 实体统一在 NyctocosmEntities 中追加）
        NyctocosmEntities.ENTITY_TYPES.register(modBus);

        // 注册配置规范（Forge 自动加载与持久化）
        context.registerConfig(ModConfig.Type.COMMON, NyctocosmConfig.SPEC, "nyctocosm.toml");

        // 配置加载/重载完成后刷新运行期缓存（修复：此前 refresh() 无调用者，配置开关实际不生效）
        modBus.addListener(NyctocosmConfig::onModConfigEvent);

        // 注册 Forge 主事件总线（用于 ServerStarting 等游戏事件）
        MinecraftForge.EVENT_BUS.register(this);
    }
}
