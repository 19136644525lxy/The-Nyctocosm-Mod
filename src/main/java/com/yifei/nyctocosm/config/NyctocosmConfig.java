package com.yifei.nyctocosm.config;

import net.minecraftforge.common.ForgeConfigSpec;

/**
 * Nyctocosm 模组通用配置
 * <p>
 * 设计原则：配置项集中在 SPEC 内构建，运行期读取值缓存到静态字段，
 * 通过 {@link ReloadListener} 在配置重载时同步刷新。
 */
public class NyctocosmConfig {

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    // 维度相关
    public static final ForgeConfigSpec.BooleanValue ENABLE_NYCTOCOSM_DIMENSION = BUILDER
            .comment("是否启用 Nyctocosm 维度入口与生成")
            .define("dimension.enable_nyctocosm", true);

    public static final ForgeConfigSpec.BooleanValue ENABLE_DUSK_PLANE = BUILDER
            .comment("是否启用 Dusk Plane 子维度（Boss 战专用）")
            .define("dimension.enable_dusk_plane", true);

    public static final ForgeConfigSpec.BooleanValue ENABLE_EMISSARY_DOMAIN = BUILDER
            .comment("是否启用 Emissary Domain 子维度（Boss 战专用）")
            .define("dimension.enable_emissary_domain", true);

    // 世界生成
    public static final ForgeConfigSpec.BooleanValue ENABLE_MONOLITH = BUILDER
            .comment("是否生成 Monolith 巨石结构")
            .define("worldgen.monolith", true);

    public static final ForgeConfigSpec.BooleanValue ENABLE_OUTPOST = BUILDER
            .comment("是否生成 Outpost 前哨结构")
            .define("worldgen.outpost", true);

    public static final ForgeConfigSpec.BooleanValue ENABLE_SLATEHENGE = BUILDER
            .comment("是否生成 Slatehenge 板岩巨阵结构")
            .define("worldgen.slatehenge", true);

    // Boss 相关
    public static final ForgeConfigSpec.BooleanValue ENABLE_SORCERER_BOSS = BUILDER
            .comment("是否允许召唤 Sorcerer 巫师 Boss")
            .define("boss.sorcerer", true);

    public static final ForgeConfigSpec.BooleanValue ENABLE_EMISSARY_BOSS = BUILDER
            .comment("是否允许召唤 Emissary 使者 Boss")
            .define("boss.emissary", true);

    public static final ForgeConfigSpec.BooleanValue ENABLE_SERPENT_BOSS = BUILDER
            .comment("是否允许召唤 Serpent 巨蛇 Boss")
            .define("boss.serpent", true);

    // 魔法系统
    public static final ForgeConfigSpec.BooleanValue ENABLE_CUSTOM_DAMAGE_TYPES = BUILDER
            .comment("是否启用自定义元素伤害类型（dream/radiance/resonance/nightmare/void/dissonance）")
            .define("magic.custom_damage_types", true);

    // 调试
    public static final ForgeConfigSpec.BooleanValue LOG_DEBUG = BUILDER
            .comment("是否输出调试日志")
            .define("debug.log", false);

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    // ===== 运行期缓存值（在 ReloadListener 中刷新）=====

    public static boolean enableNyctocosmDimension;
    public static boolean enableDuskPlane;
    public static boolean enableEmissaryDomain;
    public static boolean enableMonolith;
    public static boolean enableOutpost;
    public static boolean enableSlatehenge;
    public static boolean enableSorcererBoss;
    public static boolean enableEmissaryBoss;
    public static boolean enableSerpentBoss;
    public static boolean enableCustomDamageTypes;
    public static boolean logDebug;

    /** 刷新所有运行期缓存值，在 ModConfigEvent 加载/重载时调用 */
    public static void refresh() {
        enableNyctocosmDimension = ENABLE_NYCTOCOSM_DIMENSION.get();
        enableDuskPlane = ENABLE_DUSK_PLANE.get();
        enableEmissaryDomain = ENABLE_EMISSARY_DOMAIN.get();
        enableMonolith = ENABLE_MONOLITH.get();
        enableOutpost = ENABLE_OUTPOST.get();
        enableSlatehenge = ENABLE_SLATEHENGE.get();
        enableSorcererBoss = ENABLE_SORCERER_BOSS.get();
        enableEmissaryBoss = ENABLE_EMISSARY_BOSS.get();
        enableSerpentBoss = ENABLE_SERPENT_BOSS.get();
        enableCustomDamageTypes = ENABLE_CUSTOM_DAMAGE_TYPES.get();
        logDebug = LOG_DEBUG.get();
    }
}
