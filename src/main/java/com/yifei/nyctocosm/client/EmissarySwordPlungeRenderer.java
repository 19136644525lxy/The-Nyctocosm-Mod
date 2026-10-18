package com.yifei.nyctocosm.client;

import com.yifei.nyctocosm.Content;
import com.yifei.nyctocosm.boss.emissary.EmissarySwordPlunge;
import com.yifei.nyctocosm.client.RigTransforms;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

/** Renders the same custom item model used by the source ItemDisplay, with its tenfold scale. */
public final class EmissarySwordPlungeRenderer extends EntityRenderer<EmissarySwordPlunge> {
    private static final ItemStack SWORD = new ItemStack(Content.EMISSARY_SWORD_VISUAL.get());

    public EmissarySwordPlungeRenderer(EntityRendererProvider.Context context) { super(context); }

    @Override public void render(EmissarySwordPlunge entity, float yaw, float partialTick, PoseStack pose,
                                 MultiBufferSource buffers, int packedLight) {
        pose.pushPose();
        pose.mulPose(Axis.YP.rotationDegrees(180)); // Source ItemDisplay has identity root rotation, including old native saves.
        pose.scale(10, 10, 10);
        Minecraft.getInstance().getItemRenderer().renderStatic(SWORD, ItemDisplayContext.HEAD,
            net.minecraft.client.renderer.LightTexture.pack(0,10), OverlayTexture.NO_OVERLAY, pose, buffers, entity.level(), entity.getId());
        pose.popPose();
        super.render(entity, yaw, partialTick, pose, buffers, packedLight);
    }

    @Override public ResourceLocation getTextureLocation(EmissarySwordPlunge entity) {
        return ResourceLocation.fromNamespaceAndPath("nyctocosm", "textures/emissary/emissarysword.png");
    }
}
