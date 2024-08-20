package com.sts15.fargos.init;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.items.ItemInit;
import com.sts15.fargos.blocks.BlockInit;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CreativeTabRegistry {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Fargos.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> FARGOS_TAB = CREATIVE_MODE_TABS.register("fargos_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("key.categories.fargostalismansmod"))
            //.withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> ItemInit.ABOMINABLE_ENERGY.get().getDefaultInstance())
            .displayItems((parameters, output) -> {

                output.accept(ItemInit.TALISMAN_BASE_NORMAL.get());
                output.accept(ItemInit.TALISMAN_BASE_ADVANCED.get());
                output.accept(ItemInit.TALISMAN_BASE_EPIC.get());
                output.accept(ItemInit.TALISMAN_BASE_LEGENDARY.get());
                output.accept(ItemInit.TALISMAN_BASE_ULTIMATE.get());
                output.accept(ItemInit.TALISMAN_BASE_GODLY.get());
            	
                // Add talismans
                output.accept(ItemInit.AIR_TALISMAN.get());
                output.accept(ItemInit.AMETHYST_TALISMAN.get());
                output.accept(ItemInit.APPLE_TALISMAN.get());
                output.accept(ItemInit.ARCHITECT_TALISMAN.get());
                output.accept(ItemInit.ARCTIC_TALISMAN.get());
                output.accept(ItemInit.BATTLE_TALISMAN.get());
                output.accept(ItemInit.BLAZE_TALISMAN.get());
                output.accept(ItemInit.BLINDED_TALISMAN.get());
                output.accept(ItemInit.CACTUS_TALISMAN.get());
                output.accept(ItemInit.COPPER_TALISMAN.get());
                output.accept(ItemInit.CREEPER_TALISMAN.get());
                output.accept(ItemInit.DIAMOND_TALISMAN.get());
                output.accept(ItemInit.DRAGON_TALISMAN.get());
                output.accept(ItemInit.EARTH_TALISMAN.get());
                output.accept(ItemInit.EMERALD_TALISMAN.get());
                output.accept(ItemInit.ENCHANTING_TALISMAN.get());
                output.accept(ItemInit.ENDERMAN_TALISMAN.get());
                output.accept(ItemInit.FATIGUED_TALISMAN.get());
                output.accept(ItemInit.FIRE_TALISMAN.get());
                output.accept(ItemInit.FIRED_TALISMAN.get());
                output.accept(ItemInit.GHAST_TALISMAN.get());
                output.accept(ItemInit.GLOWSTONE_TALISMAN.get());
                output.accept(ItemInit.GOLD_TALISMAN.get());
                output.accept(ItemInit.IRON_GOLEM_TALISMAN.get());
                output.accept(ItemInit.IRON_TALISMAN.get());
                output.accept(ItemInit.LAPIS_TALISMAN.get());
                output.accept(ItemInit.LIBRARIAN_TALISMAN.get());
                output.accept(ItemInit.MOOSHROOM_TALISMAN.get());
                output.accept(ItemInit.NAUSEATED_TALISMAN.get());
                output.accept(ItemInit.NETHER_STAR_TALISMAN.get());
                output.accept(ItemInit.OBSIDIAN_TALISMAN.get());
                output.accept(ItemInit.PICKAXE_TALISMAN.get());
                output.accept(ItemInit.POISONED_TALISMAN.get());
                output.accept(ItemInit.REDSTONE_TALISMAN.get());
                output.accept(ItemInit.SHULKER_TALISMAN.get());
                output.accept(ItemInit.SKELETON_TALISMAN.get());
                output.accept(ItemInit.SLOWED_TALISMAN.get());
                output.accept(ItemInit.SPECTRAL_TALISMAN.get());
                output.accept(ItemInit.THORNY_TALISMAN.get());
                output.accept(ItemInit.UNDYING_TALISMAN.get());
                output.accept(ItemInit.VAMPIRIC_TALISMAN.get());
                output.accept(ItemInit.VINDICATOR_TALISMAN.get());
                output.accept(ItemInit.VOID_TALISMAN.get());
                output.accept(ItemInit.WATER_TALISMAN.get());
                output.accept(ItemInit.WEAKENED_TALISMAN.get());
                output.accept(ItemInit.WITCH_TALISMAN.get());
                output.accept(ItemInit.WITHER_TALISMAN.get());
                output.accept(ItemInit.WITHERED_TALISMAN.get());
                output.accept(ItemInit.ZOMBIE_TALISMAN.get());

                // Add forces
                output.accept(ItemInit.FORCE_OF_OVERWORLD.get());
                output.accept(ItemInit.FORCE_OF_NATURE.get());
                output.accept(ItemInit.FORCE_OF_REJECTORS.get());
                output.accept(ItemInit.FORCE_OF_EXPLORER.get());
                output.accept(ItemInit.FORCE_OF_MYSTIC.get());
                output.accept(ItemInit.FORCE_OF_WARRIOR.get());
                output.accept(ItemInit.FORCE_OF_NEGATIVE.get());

                // Add souls
                output.accept(ItemInit.SOUL_OF_MINECRAFT.get());
                output.accept(ItemInit.SOUL_OF_COLOSSUS.get());
                output.accept(ItemInit.SOUL_OF_FLIGHT_MASTERY.get());
                output.accept(ItemInit.SOUL_OF_SUPERSONIC.get());
                //output.accept(ItemInit.SOUL_OF_TRAWLER.get());
                output.accept(ItemInit.SOUL_OF_DIMENSIONS.get());

                output.accept(ItemInit.SOUL_OF_COLOSSUS_1.get());
                output.accept(ItemInit.SOUL_OF_COLOSSUS_2.get());
                output.accept(ItemInit.SOUL_OF_FLIGHT_MASTERY_1.get());
                output.accept(ItemInit.SOUL_OF_FLIGHT_MASTERY_2.get());
                output.accept(ItemInit.SOUL_OF_SUPERSONIC_1.get());
                output.accept(ItemInit.SOUL_OF_SUPERSONIC_2.get());

                // Add everything else
                output.accept(ItemInit.ABOMINABLE_ENERGY.get());
                output.accept(ItemInit.AEOLUS_BOOTS.get());
                output.accept(ItemInit.AMBER_HORSESHOE_BALLOON.get());
                output.accept(ItemInit.ANCIENT_HORN.get());
                output.accept(ItemInit.ANCIENT_WINGS_ELYTRA.get());
                output.accept(ItemInit.ANKH_SHIELD.get());
                output.accept(ItemInit.ASTRAL_WINGS_ELYTRA.get());
                output.accept(ItemInit.BEE_CLOAK.get());
                output.accept(ItemInit.BLAZING_WINGS_ELYTRA.get());
                output.accept(ItemInit.BLESSED_APPLE.get());
                output.accept(ItemInit.BRAIN_OF_CONFUSION.get());
                output.accept(ItemInit.BRAIN_SCRAMBLER.get());
                output.accept(ItemInit.BUNDLE_OF_HORSESHOE_BALLOONS.get());
                output.accept(ItemInit.CHARM_OF_MYTHS.get());
                output.accept(ItemInit.DRAGON_WINGS_ELYTRA.get());
                output.accept(ItemInit.DUSTY_WINGS_ELYTRA.get());
                //output.accept(ItemInit.ELEMENTAL_ASSEMBLER.get());
                output.accept(ItemInit.ENCHANTED_WINGS_ELYTRA.get());
                output.accept(ItemInit.ENDER_WINGS_ELYTRA.get());
                output.accept(ItemInit.FLYING_CARPET.get());
                output.accept(ItemInit.FOREST_WINGS_ELYTRA.get());
                output.accept(ItemInit.FROZEN_SHIELD.get());
                output.accept(ItemInit.FROZEN_WINGS_ELYTRA.get());
                output.accept(ItemInit.GHASTLY_WINGS_ELYTRA.get());
                output.accept(ItemInit.HAND_WARMER.get());
                output.accept(ItemInit.HERO_SHIELD.get());
                output.accept(ItemInit.MASTER_NINJA_GEAR.get());
                output.accept(ItemInit.MECHANICAL_CART.get());
                //output.accept(ItemInit.MULTITASK_CENTER.get());
                output.accept(ItemInit.OBSIDIAN_HORSESHOE.get());
                output.accept(ItemInit.OCEANS_FINS_ELYTRA.get());
                output.accept(ItemInit.PHANTOM_WINGS_ELYTRA.get());
                output.accept(ItemInit.POCKET_MIRROR.get());
                output.accept(ItemInit.REINDEER_BELLS.get());
                output.accept(ItemInit.SHIELD_OF_CTHULHU.get());
                output.accept(ItemInit.SHINY_STONE.get());
                output.accept(ItemInit.STAR_VEIL.get());
                output.accept(ItemInit.SWEETHEART_NECKLACE.get());
                output.accept(ItemInit.VOLCANIC_ASH_ELYTRA.get());
                output.accept(ItemInit.WITHER_WINGS_ELYTRA.get());
                output.accept(ItemInit.WORM_SCARF.get());

                
                //output.accept(BlockInit.CRUCIBLE_OF_THE_COSMOS.get());
            }).build());

    public static void register(IEventBus modEventBus) {
        CREATIVE_MODE_TABS.register(modEventBus);
    }
}
