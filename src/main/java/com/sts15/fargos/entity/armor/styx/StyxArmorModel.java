package com.sts15.fargos.entity.armor.styx;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.items.armor.StyxArmorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class StyxArmorModel extends GeoModel<StyxArmorItem> {

    public StyxArmorModel() {
        super();

    }

    @Override
    public ResourceLocation getModelResource(StyxArmorItem object) {
        return ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "geo/styx_armor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(StyxArmorItem object) {
        return ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "textures/models/armor/styx_armor.png");
    }

    @Override
    public ResourceLocation getAnimationResource(StyxArmorItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "animations/styx_armor.animation.json");
    }
}