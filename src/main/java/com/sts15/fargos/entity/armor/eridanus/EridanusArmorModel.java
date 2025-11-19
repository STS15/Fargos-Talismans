package com.sts15.fargos.entity.armor.eridanus;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.items.armor.EridanusArmorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class EridanusArmorModel extends GeoModel<EridanusArmorItem> {

    public EridanusArmorModel() {
        super();

    }

    @Override
    public ResourceLocation getModelResource(EridanusArmorItem object) {
        return ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "geo/eridanus_armor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EridanusArmorItem object) {
        return ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "textures/models/armor/eridanus_armor_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EridanusArmorItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "animations/armor_animation.animation.json");
    }
}