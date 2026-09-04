package com.yifei.nyctocosm.client;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * 通用元素粒子实现
 * <p>
 * 设计目的：六种元素（dream/radiance/resonance/nightmare/void/dissonance）
 * 共享同一个粒子类，仅靠资源包中的不同贴图区分。具体行为可后续按元素特性
 * 分别定制（颜色、生命周期、缩放等），通过子类化扩展。
 */
@OnlyIn(Dist.CLIENT)
public class ElementParticle extends TextureSheetParticle {

    protected ElementParticle(ClientLevel level, double x, double y, double z,
                               double xSpeed, double ySpeed, double zSpeed,
                               SpriteSet sprites) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);
        // 默认重力 0、生命周期 40 tick、半透明渲染
        this.gravity = 0.0F;
        this.lifetime = 40;
        this.quadSize = 0.15F;
        // 从 sprite 集中随机取一张贴图（对应资源包中的位图字体贴图）
        this.pickSprite(sprites);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    /**
     * 通用 Provider 工厂，由 {@link NyctocosmClient#onRegisterParticleProviders}
     * 注册到所有元素粒子类型上。
     */
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level,
                                       double x, double y, double z,
                                       double xSpeed, double ySpeed, double zSpeed) {
            return new ElementParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, sprites);
        }
    }
}
