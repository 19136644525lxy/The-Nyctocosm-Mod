package com.yifei.nyctocosm.client;

import com.yifei.nyctocosm.NyctocosmMod;
import com.yifei.nyctocosm.client.geckolib.renderer.SpellPlaneRenderer;
import com.yifei.nyctocosm.entity.NyctocosmEntities;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Nyctocosm 客户端初始化
 * <p>
 * 职责：
 * 1. 注册自定义粒子类型（dream/radiance/resonance/nightmare/void/dissonance）到 Forge 注册表
 * 2. 注册对应的粒子 provider，匹配资源包中的位图字体贴图
 * 3. 仅在客户端物理侧生效
 */
@Mod.EventBusSubscriber(
        modid = NyctocosmMod.MOD_ID,
        bus = Mod.EventBusSubscriber.Bus.MOD,
        value = Dist.CLIENT
)
public class NyctocosmClient {

    /** 粒子类型 DeferredRegister，挂到 mod 事件总线 */
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, NyctocosmMod.MOD_ID);

    public static final RegistryObject<SimpleParticleType> DREAM =
            PARTICLE_TYPES.register("dream", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> RADIANCE =
            PARTICLE_TYPES.register("radiance", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> RESONANCE =
            PARTICLE_TYPES.register("resonance", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> NIGHTMARE =
            PARTICLE_TYPES.register("nightmare", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> VOID =
            PARTICLE_TYPES.register("void", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> DISSONANCE =
            PARTICLE_TYPES.register("dissonance", () -> new SimpleParticleType(false));

    /**
     * 注册粒子 provider（SpriteSet 模式，匹配资源包位图字体贴图）
     */
    @SubscribeEvent
    public static void onRegisterParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(DREAM.get(), ElementParticle.Provider::new);
        event.registerSpriteSet(RADIANCE.get(), ElementParticle.Provider::new);
        event.registerSpriteSet(RESONANCE.get(), ElementParticle.Provider::new);
        event.registerSpriteSet(NIGHTMARE.get(), ElementParticle.Provider::new);
        event.registerSpriteSet(VOID.get(), ElementParticle.Provider::new);
        event.registerSpriteSet(DISSONANCE.get(), ElementParticle.Provider::new);
    }

    /**
     * 注册 GeckoLib 实体渲染器
     * <p>
     * 说明：EntityRenderersEvent.RegisterRenderers 是 Forge 为实体渲染提供的专用注册事件，
     * 优于 clientSetup 中手动 EntityRenderers.register（避免与 Mod 事件时序冲突）。
     * 所有 Boss / 法术 / 特效实体的渲染器均在此处追加。
     */
    @SubscribeEvent
    public static void onRegisterEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(NyctocosmEntities.SPELL_PLANE.get(), SpellPlaneRenderer::new);
    }
}
