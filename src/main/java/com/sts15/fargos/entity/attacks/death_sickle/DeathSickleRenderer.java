package com.sts15.fargos.entity.attacks.death_sickle;

import com.sts15.fargos.Fargos;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import javax.annotation.Nullable;

public class DeathSickleRenderer extends GeoEntityRenderer<DeathSickleEntity> {
    public DeathSickleRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DeathSickleModel());
        this.shadowRadius = 0.0f;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull DeathSickleEntity instance) {
        return ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "textures/entity/death_sickle_texture.png");
    }

    @Override
    public boolean shouldRender(@NotNull DeathSickleEntity entity, @NotNull Frustum frustum, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    public RenderType getRenderType(DeathSickleEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.itemEntityTranslucentCull(texture);
    }

}
