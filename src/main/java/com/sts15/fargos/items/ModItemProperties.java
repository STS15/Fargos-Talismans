package com.sts15.fargos.items;

import com.sts15.fargos.items.tools.BasicBow;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;

public final class ModItemProperties {

    private ModItemProperties() {}

    public static void register() {
        // Register for all your bows here (RegistryObject/DeferredHolder style)
        List<DeferredHolder<Item, ? extends Item>> BOWS = List.of(
                ItemInit.EVENTIDE_BOW,
                ItemInit.DAEDALUS_STORMBOW,
                ItemInit.HELLWING_BOW,
                ItemInit.TENDON_BOW,
                ItemInit.ICE_BOW,
                ItemInit.DEMON_BOW,
                ItemInit.SIMPLE_BOW,
                ItemInit.BLOOD_RAIN_BOW,
                ItemInit.MECHANICS_BOW,
                ItemInit.TSUNAMI_BOW,
                ItemInit.VORTEX_BOW,
                ItemInit.AERIAL_BANE_BOW
        );

        for (var holder : BOWS) {
            registerBowPredicates(holder.get());
        }
    }

    private static void registerBowPredicates(Item bow) {
        // "pull" predicate: returns 0..1 based on use time and bow draw speed
        ItemProperties.register(
                bow,
                ResourceLocation.withDefaultNamespace("pull"),
                (stack, level, entity, seed) -> {
                    if (entity == null || entity.getUseItem() != stack) return 0.0F;

                    float drawSpeed = 1.0F;
                    if (stack.getItem() instanceof BasicBow bb) {
                        drawSpeed = bb.getDrawSpeed();
                    }

                    int used = stack.getUseDuration(entity) - entity.getUseItemRemainingTicks();
                    float timeToFull = 20.0F / drawSpeed; // vanilla full-draw (20t) scaled by drawSpeed
                    return Math.min(1.0F, used / timeToFull);
                }
        );

        // "pulling" predicate: 1 while using, else 0
        ItemProperties.register(
                bow,
                ResourceLocation.withDefaultNamespace("pulling"),
                (stack, level, entity, seed) ->
                        (entity != null && entity.isUsingItem() && entity.getUseItem() == stack) ? 1.0F : 0.0F
        );
    }
}
