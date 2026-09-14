package com.yifei.nyctocosm.damage;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;

import javax.annotation.Nullable;

/**
 * Nyctocosm 自定义伤害源构建工具
 * <p>
 * 提供 Java 代码中类型安全地构建元素伤害 DamageSource 的方法，
 * 与数据包中 {@code damage @s ... magic:dream} 命令等价。
 * <p>
 * 用法示例：
 * <pre>
 * DamageSource src = NyctocosmDamageSources.dream(level.registryAccess(), attacker);
 * target.hurt(src, 10.0F);
 * </pre>
 */
public final class NyctocosmDamageSources {

    private NyctocosmDamageSources() {
    }

    /** 从注册表获取 DamageType Holder */
    private static Holder<DamageType> holder(RegistryAccess access, ResourceKey<DamageType> key) {
        Registry<DamageType> registry = access.registryOrThrow(Registries.DAMAGE_TYPE);
        return registry.getHolderOrThrow(key);
    }

    // === 通用构建方法 ===

    /** 构建无来源实体的伤害源 */
    public static DamageSource source(RegistryAccess access, ResourceKey<DamageType> key) {
        return new DamageSource(holder(access, key));
    }

    /** 构建有直接来源实体的伤害源 */
    public static DamageSource source(RegistryAccess access, ResourceKey<DamageType> key, @Nullable Entity entity) {
        return new DamageSource(holder(access, key), entity);
    }

    /** 构建区分直接/间接来源实体的伤害源 */
    public static DamageSource source(RegistryAccess access, ResourceKey<DamageType> key,
                                      @Nullable Entity direct, @Nullable Entity causing) {
        return new DamageSource(holder(access, key), direct, causing);
    }

    // === 6 元素便捷方法（无来源实体）===

    public static DamageSource dream(RegistryAccess a) {
        return source(a, NyctocosmDamageTypes.DREAM);
    }

    public static DamageSource radiance(RegistryAccess a) {
        return source(a, NyctocosmDamageTypes.RADIANCE);
    }

    public static DamageSource resonance(RegistryAccess a) {
        return source(a, NyctocosmDamageTypes.RESONANCE);
    }

    public static DamageSource nightmare(RegistryAccess a) {
        return source(a, NyctocosmDamageTypes.NIGHTMARE);
    }

    /** void 是 Java 关键字，方法名加 Damage 后缀 */
    public static DamageSource voidDamage(RegistryAccess a) {
        return source(a, NyctocosmDamageTypes.VOID);
    }

    public static DamageSource dissonance(RegistryAccess a) {
        return source(a, NyctocosmDamageTypes.DISSONANCE);
    }

    // === 6 元素便捷方法（有来源实体）===

    public static DamageSource dream(RegistryAccess a, Entity entity) {
        return source(a, NyctocosmDamageTypes.DREAM, entity);
    }

    public static DamageSource radiance(RegistryAccess a, Entity entity) {
        return source(a, NyctocosmDamageTypes.RADIANCE, entity);
    }

    public static DamageSource resonance(RegistryAccess a, Entity entity) {
        return source(a, NyctocosmDamageTypes.RESONANCE, entity);
    }

    public static DamageSource nightmare(RegistryAccess a, Entity entity) {
        return source(a, NyctocosmDamageTypes.NIGHTMARE, entity);
    }

    public static DamageSource voidDamage(RegistryAccess a, Entity entity) {
        return source(a, NyctocosmDamageTypes.VOID, entity);
    }

    public static DamageSource dissonance(RegistryAccess a, Entity entity) {
        return source(a, NyctocosmDamageTypes.DISSONANCE, entity);
    }

    // === sorcerer Boss 伤害便捷方法 ===

    public static DamageSource sorcererAttack(RegistryAccess a, Entity entity) {
        return source(a, NyctocosmDamageTypes.SORCERER_ATTACK, entity);
    }

    public static DamageSource sorcererNoIframe(RegistryAccess a, Entity entity) {
        return source(a, NyctocosmDamageTypes.SORCERER_NO_IFRAME, entity);
    }

    public static DamageSource sorcererNoKnockback(RegistryAccess a, Entity entity) {
        return source(a, NyctocosmDamageTypes.SORCERER_NO_KNOCKBACK, entity);
    }

    // === 法术系统便捷方法（SpellHelpers 调用，按元素自动映射 DamageType）===

    /** 玩家作为施法者的直接法术伤害（causing = player，direct = null，同原 magic:attack calcstatsspell） */
    public static DamageSource spellDirect(Entity casterEntity,
                                           com.yifei.nyctocosm.spell.ElementType element) {
        RegistryAccess access = casterEntity.level().registryAccess();
        return source(access, element.damageTypeKey, null, casterEntity);
    }

    /** 无施法者的环境/法术实体伤害（例如法术平面自身造成的伤害） */
    public static DamageSource spellDirect(net.minecraft.world.level.Level level,
                                           com.yifei.nyctocosm.spell.ElementType element) {
        RegistryAccess access = level.registryAccess();
        return source(access, element.damageTypeKey);
    }

    /**
     * 反向查找：若给定 DamageSource 属于 nyctocosm 6 元素之一则返回对应 ElementType，否则 null。
     * 用于 ArtifactPassiveHandler 实现 Protection 圣物"元素抗性"（按元素减免伤害）。
     * <p>
     * 修复：此前只比较 location.path（如 "void"），任意模组同名 damage_type（如别的 mod 的
     * "void"）会被误判成本模组元素、误触发 Protection 减免；现改为比较完整 ResourceLocation
     * （namespace + path，即只有 magic:dream 等本模组 6 元素伤害类型可命中）。
     */
    @Nullable
    public static com.yifei.nyctocosm.spell.ElementType resolveElement(DamageSource src) {
        if (src == null) return null;
        net.minecraft.resources.ResourceLocation loc = src.typeHolder().unwrapKey()
                .map(k -> k.location())
                .orElse(null);
        if (loc == null) return null;
        for (com.yifei.nyctocosm.spell.ElementType t : com.yifei.nyctocosm.spell.ElementType.values()) {
            if (t.damageTypeKey != null && t.damageTypeKey.location().equals(loc)) {
                return t;
            }
        }
        return null;
    }
}
