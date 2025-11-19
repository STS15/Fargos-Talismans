package com.sts15.fargos.datagen.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class FargosTags {
    public static class Items {

        public static final TagKey<Item> CURIO_CHARM =
                makeItemTag(ResourceLocation.fromNamespaceAndPath("curios", "charm"));

        public static final TagKey<Item> FISHING_ORGANIC =
                makeItemTag(ResourceLocation.fromNamespaceAndPath("fargostalismans", "fishing_organic"));

        public static final TagKey<Item> FISHING_ABOMINABLE =
                makeItemTag(ResourceLocation.fromNamespaceAndPath("fargostalismans", "fishing_abominable"));
    }

    public static TagKey<Item> makeItemTag(ResourceLocation id) {
        return TagKey.create(Registries.ITEM, id);
    }
}
