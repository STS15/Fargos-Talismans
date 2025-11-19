package com.sts15.fargos.entity.armor.mutant;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.items.armor.MutantArmorItem;
import com.sts15.fargos.items.armor.StyxArmorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class MutantArmorModel extends GeoModel<MutantArmorItem> {

    public MutantArmorModel() {
        super();

    }

    @Override
    public ResourceLocation getModelResource(MutantArmorItem object) {
        return ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "geo/mutant_armor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MutantArmorItem object) {
        return ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "textures/models/armor/mutant_armor.png");
    }

    @Override
    public ResourceLocation getAnimationResource(MutantArmorItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "animations/armor_animation.animation.json");
    }
}