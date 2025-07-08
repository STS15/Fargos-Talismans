package com.sts15.fargos.entity.eridanus;

import com.sts15.fargos.Fargos;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class EridanusRenderer extends GeoEntityRenderer<EridanusBoss> {
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

}

