package com.yifei.nyctocosm.client.geckolib.renderer;

import com.yifei.nyctocosm.client.geckolib.model.SpellPlaneModel;
import com.yifei.nyctocosm.entity.spell.SpellPlaneEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

/**
 * SpellPlane（法术环）实体渲染器
 * <p>
 * GeckoLib 会自动通过 GeoModel 的路径查找 geo / texture / animation 文件，
 * 此处仅需 super(context, new SpellPlaneModel()) 即可完成渲染链路装配。
 * <p>
 * 后续若需发光层、半透明、自定义阴影等扩展，在此类 addRenderLayer(...)。
 */
public class SpellPlaneRenderer extends GeoEntityRenderer<SpellPlaneEntity> {

    public SpellPlaneRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new SpellPlaneModel());
        // 法术环不投射阴影（对应原 AJ item_display 无 shadow 特性）
        this.shadowRadius = 0.0f;
    }
}
