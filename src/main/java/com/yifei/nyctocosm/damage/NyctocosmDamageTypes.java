package com.yifei.nyctocosm.damage;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

/**
 * Nyctocosm 自定义伤害类型键定义
 * <p>
 * 与数据包 data/<namespace>/damage_type/*.json 一一对应，
 * 1.20.1 Forge 自动加载 JSON 到 Registries.DAMAGE_TYPE，此处仅提供代码引用常量。
 * <p>
 * 命名空间分组：
 * - magic: 6 种元素伤害（dream/radiance/resonance/nightmare/void/dissonance）
 * - damage: 通用伤害（damage:damage）
 * - sorcerer: Boss 伤害变体（attack/no_iframe/no_knockback）
 */
public interface NyctocosmDamageTypes {

    // === magic 命名空间：6 种元素伤害 ===
    ResourceKey<DamageType> DREAM      = create("magic", "dream");
    ResourceKey<DamageType> RADIANCE   = create("magic", "radiance");
    ResourceKey<DamageType> RESONANCE  = create("magic", "resonance");
    ResourceKey<DamageType> NIGHTMARE  = create("magic", "nightmare");
    ResourceKey<DamageType> VOID       = create("magic", "void");
    ResourceKey<DamageType> DISSONANCE = create("magic", "dissonance");

    // === damage 命名空间：通用伤害 ===
    ResourceKey<DamageType> DAMAGE = create("damage", "damage");

    // === sorcerer 命名空间：Boss 伤害变体 ===
    ResourceKey<DamageType> SORCERER_ATTACK       = create("sorcerer", "attack");
    ResourceKey<DamageType> SORCERER_NO_IFRAME    = create("sorcerer", "no_iframe");
    ResourceKey<DamageType> SORCERER_NO_KNOCKBACK = create("sorcerer", "no_knockback");

    /** 构造 ResourceKey 工具方法 */
    private static ResourceKey<DamageType> create(String namespace, String path) {
        return ResourceKey.create(Registries.DAMAGE_TYPE,
                ResourceLocation.tryParse(namespace + ":" + path));
    }
}

