package com.sts15.fargos.entity.armor;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.items.armor.ExtendedArmorItem;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.GeckoLibCache;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

import java.util.HashMap;
import java.util.Map;

public class GenericArmorModel<T extends ExtendedArmorItem> extends DefaultedItemGeoModel<T> {
    record ModelVariantResult(ResourceLocation location, boolean validated) {
    }

    private final ResourceLocation model;

    private final ResourceLocation texture;

    private static final ResourceLocation ANIMATION = ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "animations/wizard_armor_animation.json");
    private final Map<String, ModelVariantResult> modelVariants;

    public GenericArmorModel(String modid, String name) {
        super(ResourceLocation.fromNamespaceAndPath(Fargos.MODID, ""));
        this.model = ResourceLocation.fromNamespaceAndPath(modid, String.format("geo/%s_armor.geo.json", name));
        this.texture = ResourceLocation.fromNamespaceAndPath(modid, String.format("textures/models/armor/%s.png", name));
        this.modelVariants = new HashMap<>();
    }

    public GenericArmorModel(String name) {
        this(Fargos.MODID, name);
    }

    public GenericArmorModel<T> variants(Map<String, ResourceLocation> modelVariants) {
        modelVariants.forEach((string, location) -> this.modelVariants.put(string, new ModelVariantResult(location, false)));
        return this;
    }

    @Override
    public ResourceLocation getModelResource(T animatable, @Nullable GeoRenderer<T> renderer) {
        return model;
    }

    private boolean validateModelLocation(ModelVariantResult result, String transmogVariant) {
        if (result.validated) {
            return true;
        } else {
            if (GeckoLibCache.getBakedModels().get(result.location) != null) {
                modelVariants.put(transmogVariant, new ModelVariantResult(result.location, true));
                return true;
            } else {
                modelVariants.remove(transmogVariant);
            }
        }
        return false;
    }

    @Override
    public ResourceLocation getTextureResource(T animatable) {
        return texture;
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return ANIMATION;
    }
}