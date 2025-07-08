package com.sts15.fargos.entity.attacks.death_sickle;

import com.sts15.fargos.Fargos;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class DeathSickleModel extends GeoModel<DeathSickleEntity> {
    @Override
    public ResourceLocation getModelResource(DeathSickleEntity object) {
        return ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "geo/death_sickle.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(DeathSickleEntity object) {
        return ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "textures/entity/death_sickle_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(DeathSickleEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "animations/death_sickle.animation.json");
    }

}