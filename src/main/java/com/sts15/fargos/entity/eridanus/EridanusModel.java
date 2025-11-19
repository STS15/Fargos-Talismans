package com.sts15.fargos.entity.eridanus;

import com.sts15.fargos.Fargos;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class EridanusModel extends GeoModel<EridanusBoss> {
    public static final ResourceLocation TEXTURE_NORMAL = ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "textures/entity/eridanus/eridanus_boss_texture");
    public static final ResourceLocation TEXTURE_ENRAGED = ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "textures/entity/eridanus/eridanus_boss_texture");

    @Override
    public ResourceLocation getModelResource(EridanusBoss animatable) {
        return ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "geo/eridanus_boss.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EridanusBoss animatable) {
//        if (animatable instanceof EridanusBoss boss) {
//            if (boss.isPhase(EridanusBoss.Phases.FinalPhase))
//                return TEXTURE_ENRAGED;
//            else
//                return TEXTURE_NORMAL;
//        }
        return ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "textures/entity/eridanus/eridanus_boss_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EridanusBoss animatable) {
        return ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "animations/eridanus.animation.json");
    }
}
