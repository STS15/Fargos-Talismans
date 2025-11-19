package com.sts15.fargos.entity.eridanus;

import com.sts15.fargos.Fargos;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BeaconRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.minecraft.client.renderer.RenderType;
import com.mojang.math.Axis;
import net.minecraft.util.FastColor;
import net.minecraft.client.Minecraft;


public class EridanusRenderer extends GeoEntityRenderer<EridanusBoss> {

    private static final ResourceLocation BEAM_TEXTURE = ResourceLocation.fromNamespaceAndPath("minecraft","textures/entity/beacon_beam.png");

    public EridanusRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new EridanusModel());
        this.shadowRadius = 1.0f;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull EridanusBoss entity) {
        return ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "textures/entity/eridanus/eridanus_boss_texture.png");
    }

    @Override
    public EridanusBoss getAnimatable() {
        return this.animatable;
    }

    @Override
    public boolean shouldRender(@NotNull EridanusBoss entity, @NotNull Frustum frustum, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    public void render(EridanusBoss entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
    }

}

