package com.yifei.nyctocosm.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.yifei.nyctocosm.dais.InculcateRig;
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
import org.joml.Matrix4f;

/**
 * Renders the fifteen-bone inculcate rig exactly like vanilla item displays would:
 * root yaw (180 deg, as the datapack teleports the root every tick) then the exported
 * bone transform, followed by the item-display 180 degree flip applied by
 * {@link RigTransforms#itemTransform}. Models are drawn through the HEAD item
 * transform, which bakes the authored 4x scale and vertical offset.
 */
public final class InculcateRigRenderer extends EntityRenderer<InculcateRig> {
    InculcateRigRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(InculcateRig entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }

    @Override
    public void render(InculcateRig entity, float yaw, float partial, PoseStack pose,
                       MultiBufferSource buffers, int light) {
        RigLibrary.Rig rig = RigLibrary.get("ajinculcate");
        float time = entity.tickCount + partial;
        pose.pushPose();
        pose.mulPose(RigTransforms.orientation(180f));
        for (RigLibrary.Bone bone : rig.bones()) {
            Matrix4f matrix = RigTransforms.itemTransform(RigLibrary.pose(rig, bone, "inculcate", time, false));
            pose.pushPose();
            pose.mulPoseMatrix(matrix);
            model(bone.model(), pose, buffers, light);
            pose.popPose();
        }
        pose.popPose();
    }

    private static void model(ResourceLocation id, PoseStack pose, MultiBufferSource buffers, int light) {
        Minecraft mc = Minecraft.getInstance();
        mc.getItemRenderer().render(new ItemStack(Items.STICK), ItemDisplayContext.HEAD, false, pose,
            buffers, light, OverlayTexture.NO_OVERLAY, mc.getModelManager().getModel(id));
    }
}
