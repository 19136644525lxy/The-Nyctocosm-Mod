package com.yifei.nyctocosm.entity;

import com.yifei.nyctocosm.NyctocosmMod;
import com.yifei.nyctocosm.entity.spell.SpellPlaneEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Nyctocosm 所有自定义实体注册总入口
 * <p>
 * 设计：
 * 1. 使用 DeferredRegister<EntityType<?>>，在 NyctocosmMod 构造函数中 bind 到 mod 事件总线
 * 2. 实体按功能分包：boss/（Boss）、spell/（法术相关）、effect/（视觉特效）
 * 3. 所有实体均实现 software.bernie.geckolib.animatable.GeoEntity 接口，由 GeckoLib 负责渲染
 */
public final class NyctocosmEntities {

    /** 实体类型注册器，挂 mod 事件总线 */
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, NyctocosmMod.MOD_ID);

    // ===============================
    // 法术系统 - Spell / Projectile
    // ===============================

    /** 法术平面（跟随玩家的 ajspellplane rig，7 骨骼，含 idle/cast/fizzle 动画） */
    public static final RegistryObject<EntityType<SpellPlaneEntity>> SPELL_PLANE =
            ENTITY_TYPES.register("spell_plane",
                    () -> EntityType.Builder
                            .<SpellPlaneEntity>of(SpellPlaneEntity::new, MobCategory.MISC)
                            .clientTrackingRange(8)
                            .updateInterval(1)
                            .sized(1.5f, 0.5f)
                            .fireImmune()
                            .noSummon()
                            .build(NyctocosmMod.MOD_ID + ":spell_plane"));

    private NyctocosmEntities() {
        // 工具类，禁止实例化
    }
}
