package com.yifei.nyctocosm.client.geckolib;

import com.yifei.nyctocosm.NyctocosmMod;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

import java.util.Objects;

/**
 * Nyctocosm GeckoLib GeoModel 抽象基类（DRY：复用资源路径）
 * <p>
 * 约定：
 * - 模型路径：assets/nyctocosm/geo/{name}.geo.json
 * - 纹理路径：assets/nyctocosm/textures/entity/{category}/{name}.png
 * - 动画路径：assets/nyctocosm/animations/{category}/{name}.animation.json
 * <p>
 * 泛型边界 &lt;T extends GeoAnimatable&gt; 由 GeckoLib GeoModel 父类要求，不能省略。
 * 使用 {@link ResourceLocation#tryBuild} 替代已标记为 [removal] 的双参构造器。
 *
 * @param <T> 可动画对象泛型（GeoEntity / GeoItem / GeoBlockEntity）
 */
public abstract class NyctocosmGeoModel<T extends GeoAnimatable> extends GeoModel<T> {

    protected final ResourceLocation model;
    protected final ResourceLocation texture;
    protected final ResourceLocation animations;

    /**
     * @param name     资源基础名（如 spellplane, emissary）
     * @param category 子目录分类（如 spell, boss, effect），用于 textures/animations
     */
    protected NyctocosmGeoModel(String name, String category) {
        this.model = rl("geo/" + name + ".geo.json");
        this.texture = rl("textures/entity/" + category + "/" + name + ".png");
        this.animations = rl("animations/" + category + "/" + name + ".animation.json");
    }

    /** 本地工具：使用 tryBuild，路径确定合法时必然非 null */
    private static ResourceLocation rl(String path) {
        return Objects.requireNonNull(
                ResourceLocation.tryBuild(NyctocosmMod.MOD_ID, path),
                "Invalid resource path: " + NyctocosmMod.MOD_ID + ":" + path);
    }

    @Override
    public ResourceLocation getModelResource(T animatable) {
        return this.model;
    }

    @Override
    public ResourceLocation getTextureResource(T animatable) {
        return this.texture;
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return this.animations;
    }
}
