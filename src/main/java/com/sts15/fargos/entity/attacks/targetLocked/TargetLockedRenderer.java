package com.sts15.fargos.entity.attacks.targetLocked;

import com.sts15.fargos.Fargos;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TargetLockedRenderer extends GeoEntityRenderer<TargetLockedEntity> {
    public TargetLockedRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new TargetLockedModel());
        this.shadowRadius = 0.0f;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull TargetLockedEntity instance) {
        return ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "textures/entity/target_locked_texture.png");
    }

    @Override
    public boolean shouldRender(@NotNull TargetLockedEntity entity, @NotNull Frustum frustum, double camX, double camY, double camZ) {
        return true;
    }
}
