package com.sts15.fargos.items;

import com.sts15.fargos.items.forces.Force_Talisman;
import com.sts15.fargos.items.souls.Soul_Talisman;
import com.sts15.fargos.items.talismans.*;

import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.createItems("fargostalismans");

    // Crafting Items
    public static final DeferredHolder<Item, Item> ABOMINABLE_ENERGY = ITEMS.register("abominable_energy", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> AEOLUS_BOOTS = ITEMS.register("aeolus_boots", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> AMBER_HORSESHOE_BALLOON = ITEMS.register("amber_horseshoe_balloon", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> ANCIENT_HORN = ITEMS.register("ancient_horn", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> ANCIENT_WINGS_ELYTRA = ITEMS.register("ancient_wings_elytra", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> ANKH_SHIELD = ITEMS.register("ankh_shield", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> ASTRAL_WINGS_ELYTRA = ITEMS.register("astral_wings_elytra", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> BEE_CLOAK = ITEMS.register("bee_cloak", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> BLAZING_WINGS_ELYTRA = ITEMS.register("blazing_wings_elytra", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> BLESSED_APPLE = ITEMS.register("blessed_apple", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> BRAIN_OF_CONFUSION = ITEMS.register("brain_of_confusion", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> BRAIN_SCRAMBLER = ITEMS.register("brain_scrambler", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> BUNDLE_OF_HORSESHOE_BALLOONS = ITEMS.register("bundle_of_horseshoe_balloons", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> CHARM_OF_MYTHS = ITEMS.register("charm_of_myths", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> DRAGON_WINGS_ELYTRA = ITEMS.register("dragon_wings_elytra", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> DUSTY_WINGS_ELYTRA = ITEMS.register("dusty_wings_elytra", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> ELEMENTAL_ASSEMBLER = ITEMS.register("elemental_assembler", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> ENCHANTED_WINGS_ELYTRA = ITEMS.register("enchanted_wings_elytra", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> ENDER_WINGS_ELYTRA = ITEMS.register("ender_wings_elytra", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> FLYING_CARPET = ITEMS.register("flying_carpet", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> FOREST_WINGS_ELYTRA = ITEMS.register("forest_wings_elytra", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> FROZEN_SHIELD = ITEMS.register("frozen_shield", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> FROZEN_WINGS_ELYTRA = ITEMS.register("frozen_wings_elytra", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> GHASTLY_WINGS_ELYTRA = ITEMS.register("ghastly_wings_elytra", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> HAND_WARMER = ITEMS.register("hand_warmer", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> HERO_SHIELD = ITEMS.register("hero_shield", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> MASTER_NINJA_GEAR = ITEMS.register("master_ninja_gear", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> MECHANICAL_CART = ITEMS.register("mechanical_cart", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> MULTITASK_CENTER = ITEMS.register("multitask_center", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> OBSIDIAN_HORSESHOE = ITEMS.register("obsidian_horseshoe", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> OCEANS_FINS_ELYTRA = ITEMS.register("oceans_fins_elytra", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> PHANTOM_WINGS_ELYTRA = ITEMS.register("phantom_wings_elytra", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> POCKET_MIRROR = ITEMS.register("pocket_mirror", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> REINDEER_BELLS = ITEMS.register("reindeer_bells", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> SHIELD_OF_CTHULHU = ITEMS.register("shield_of_cthulhu", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> SHINY_STONE = ITEMS.register("shiny_stone", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> STAR_VEIL = ITEMS.register("star_veil", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> SWEETHEART_NECKLACE = ITEMS.register("sweetheart_necklace", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> VOLCANIC_ASH_ELYTRA = ITEMS.register("volcanic_ash_elytra", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> WITHER_WINGS_ELYTRA = ITEMS.register("wither_wings_elytra", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> WORM_SCARF = ITEMS.register("worm_scarf", () -> new Item(new Item.Properties()));
    
    // General Items
    public static final DeferredHolder<Item, Item> ABOMINATIONNS_CURSE = ITEMS.register("abominationns_curse", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> ABOMINATIONN_MUSIC_DISC = ITEMS.register("abominationn_music_disc", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> MUTANTS_CURSE = ITEMS.register("mutants_curse", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> MUTANT_MUSIC_DISC = ITEMS.register("mutant_music_disc", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> MUTANT_VOODOO = ITEMS.register("mutant_voodoo", () -> new Item(new Item.Properties()));
    
    // Weapon Items
    public static final DeferredHolder<Item, Item> STYX_GAZER = ITEMS.register("styx_gazer", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> SWORD_OF_THE_FATES = ITEMS.register("sword_of_the_fates", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> DRAGONS_DEMISE = ITEMS.register("dragons_demise", () -> new Item(new Item.Properties()));

    // Talisman Items
    public static final DeferredHolder<Item, TalismanItem> AIR_TALISMAN = ITEMS.register("air_talisman", Air_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> AMETHYST_TALISMAN = ITEMS.register("amethyst_talisman", Amethyst_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> APPLE_TALISMAN = ITEMS.register("apple_talisman", Apple_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> ARCHITECT_TALISMAN = ITEMS.register("architect_talisman", Architect_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> ARCTIC_TALISMAN = ITEMS.register("arctic_talisman", Arctic_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> BATTLE_TALISMAN = ITEMS.register("battle_talisman", Battle_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> BLAZE_TALISMAN = ITEMS.register("blaze_talisman", Blaze_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> CACTUS_TALISMAN = ITEMS.register("cactus_talisman", Cactus_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> COPPER_TALISMAN = ITEMS.register("copper_talisman", Copper_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> CREEPER_TALISMAN = ITEMS.register("creeper_talisman", Creeper_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> DIAMOND_TALISMAN = ITEMS.register("diamond_talisman", Diamond_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> DRAGON_TALISMAN = ITEMS.register("dragon_talisman", Dragon_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> EARTH_TALISMAN = ITEMS.register("earth_talisman", Earth_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> EMERALD_TALISMAN = ITEMS.register("emerald_talisman", Emerald_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> ENCHANTING_TALISMAN = ITEMS.register("enchanting_talisman", Enchanting_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> ENDERMAN_TALISMAN = ITEMS.register("enderman_talisman", Enderman_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> FIRE_TALISMAN = ITEMS.register("fire_talisman", Fire_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> GHAST_TALISMAN = ITEMS.register("ghast_talisman", Ghast_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> GLOWSTONE_TALISMAN = ITEMS.register("glowstone_talisman", Glowstone_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> GOLD_TALISMAN = ITEMS.register("gold_talisman", Gold_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> IRON_GOLEM_TALISMAN = ITEMS.register("iron_golem_talisman", Iron_Golem_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> IRON_TALISMAN = ITEMS.register("iron_talisman", Iron_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> LAPIS_TALISMAN = ITEMS.register("lapis_talisman", Lapis_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> LIBRARIAN_TALISMAN = ITEMS.register("librarian_talisman", Librarian_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> MOOSHROOM_TALISMAN = ITEMS.register("mooshroom_talisman", Mooshroom_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> NETHER_STAR_TALISMAN = ITEMS.register("nether_star_talisman", Nether_Star_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> OBSIDIAN_TALISMAN = ITEMS.register("obsidian_talisman", Obsidian_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> PICKAXE_TALISMAN = ITEMS.register("pickaxe_talisman", Pickaxe_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> REDSTONE_TALISMAN = ITEMS.register("redstone_talisman", Redstone_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> SHULKER_TALISMAN = ITEMS.register("shulker_talisman", Shulker_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> SKELETON_TALISMAN = ITEMS.register("skeleton_talisman", Skeleton_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> SPECTRAL_TALISMAN = ITEMS.register("spectral_talisman", Spectral_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> THORNY_TALISMAN = ITEMS.register("thorny_talisman", Thorny_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> UNDYING_TALISMAN = ITEMS.register("undying_talisman", Undying_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> VAMPIRIC_TALISMAN = ITEMS.register("vampiric_talisman", Vampiric_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> VINDICATOR_TALISMAN = ITEMS.register("vindicator_talisman", Vindicator_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> VOID_TALISMAN = ITEMS.register("void_talisman", Void_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> WATER_TALISMAN = ITEMS.register("water_talisman", Water_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> WITCH_TALISMAN = ITEMS.register("witch_talisman", Witch_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> WITHER_TALISMAN = ITEMS.register("wither_talisman", Wither_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> ZOMBIE_TALISMAN = ITEMS.register("zombie_talisman", Zombie_Talisman::new);
    
    //Forces
    public static final DeferredHolder<Item, TalismanItem> FORCE_OF_EXPLORER = ITEMS.register("force_of_explorer", Force_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> FORCE_OF_MYSTIC = ITEMS.register("force_of_mystic", Force_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> FORCE_OF_NATURE = ITEMS.register("force_of_nature", Force_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> FORCE_OF_OVERWORLD = ITEMS.register("force_of_overworld", Force_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> FORCE_OF_REJECTORS = ITEMS.register("force_of_rejectors", Force_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> FORCE_OF_WARRIOR = ITEMS.register("force_of_warrior", Force_Talisman::new);
    
    //Souls
    public static final DeferredHolder<Item, TalismanItem> SOUL_OF_COLOSSUS = ITEMS.register("soul_of_colossus", Soul_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> SOUL_OF_DIMENSIONS = ITEMS.register("soul_of_dimensions", Soul_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> SOUL_OF_FLIGHT_MASTERY = ITEMS.register("soul_of_flight_mastery", Soul_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> SOUL_OF_MINECRAFT = ITEMS.register("soul_of_minecraft", Soul_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> SOUL_OF_SUPERSONIC = ITEMS.register("soul_of_supersonic", Soul_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> SOUL_OF_TRAWLER = ITEMS.register("soul_of_trawler", Soul_Talisman::new);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
