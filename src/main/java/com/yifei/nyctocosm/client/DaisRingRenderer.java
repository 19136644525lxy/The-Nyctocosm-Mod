package com.yifei.nyctocosm.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.yifei.nyctocosm.dais.DaisRing;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

/**
 * Renders the four dais rings the same way the original marker armour stands did: the
 * armour-stand head chain, then the item HEAD transform that bakes the authored -5/16
 * offset and 1.6x/4x scale. Yaw and head pitch are derived from the entity clock so no
 * per-tick packets are needed.
 */
public final class DaisRingRenderer extends EntityRenderer<DaisRing> {
    private static final ResourceLocation RING = ResourceLocation.fromNamespaceAndPath("nyctocosm", "magic/dais/dais_ring");
    private static final ResourceLocation GLYPH = ResourceLocation.fromNamespaceAndPath("nyctocosm", "magic/dais/dais_ring_glyph");

    DaisRingRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(DaisRing entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }

    @Override
    public void render(DaisRing entity, float yaw, float partial, PoseStack pose,
                       MultiBufferSource buffers, int light) {
        float t = entity.tickCount + partial;
        // plain glyph ring: +1 deg/tick, no head pitch (crafting:__generated__/block/18)
        layer(GLYPH, t, 0f, pose, buffers, light);
        // pos: +1 deg/tick, up: +1.5 deg/tick, neg: -1 deg/tick (block/14 + block/15)
        layer(RING, t, pitch((t * 20f) % 3600f), pose, buffers, light);
        layer(RING, 1.5f * t, pitch((900f + t * 30f) % 3600f), pose, buffers, light);
        layer(RING, -t, pitch(3600f - ((t * 20f) % 3600f)), pose, buffers, light);
    }

    private static float pitch(float timer) {
        return timer * 0.1f;
    }

    private static void layer(ResourceLocation id, float yaw, float pitch, PoseStack pose,
                              MultiBufferSource buffers, int light) {
        pose.pushPose();
        pose.mulPoseMatrix(RigTransforms.armorStandHead(yaw, pitch, .0001f, .0001f));
        Minecraft mc = Minecraft.getInstance();
        mc.getItemRenderer().render(new ItemStack(Items.QUARTZ), ItemDisplayContext.HEAD, false, pose,
            buffers, light, OverlayTexture.NO_OVERLAY, mc.getModelManager().getModel(id));
        pose.popPose();
    }
}
