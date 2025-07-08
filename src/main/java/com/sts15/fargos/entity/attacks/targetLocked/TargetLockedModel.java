package com.sts15.fargos.entity.attacks.targetLocked;

import com.sts15.fargos.Fargos;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TargetLockedModel extends GeoModel<TargetLockedEntity> {
    @Override
    public ResourceLocation getModelResource(TargetLockedEntity object) {
        return ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "geo/target_locked.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TargetLockedEntity object) {
        return ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "textures/entity/target_locked_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(TargetLockedEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "animations/target_locked.animation.json");
    }

}