package com.bugboy.kiwarrior.entitites.client;

import com.bugboy.kiwarrior.KiWarrior;
import com.bugboy.kiwarrior.entitites.custom.BigBlast;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BigBlastRenderer<BigBlast extends AbstractHurtingProjectile> extends EntityRenderer<BigBlast> {
    BigBlastModel bigBlastModel;
    public BigBlastRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
        ModelPart modelPart = p_174008_.bakeLayer(ModModelLayers.BIG_BLAST_LAYER);
        bigBlastModel = new BigBlastModel(modelPart);
    }

    @Override
    public ResourceLocation getTextureLocation(BigBlast pEntity) {
        return new ResourceLocation(KiWarrior.MODID,"textures/entity/bigblast.png");
    }

    @Override
    public void render(BigBlast entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        super.render(entity, yaw, partialTicks, poseStack, bufferSource, packedLight);
        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entitySolid(this.getTextureLocation(entity)));
        bigBlastModel.renderToBuffer(poseStack,vertexConsumer,packedLight, OverlayTexture.NO_OVERLAY,1.0F,1.0F,1.0F,1.0F);
    }
}
