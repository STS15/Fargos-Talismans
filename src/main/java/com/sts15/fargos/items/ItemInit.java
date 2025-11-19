package com.sts15.fargos.items;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.items.armor.EridanusArmorItem;
import com.sts15.fargos.items.armor.MutantArmorItem;
import com.sts15.fargos.items.armor.StyxArmorItem;
import com.sts15.fargos.items.components.*;
import com.sts15.fargos.items.crafted.BasicElytraItem;
import com.sts15.fargos.items.fishingrods.*;
import com.sts15.fargos.items.tools.BasicBow;
import com.sts15.fargos.items.tools.BasicFishingRod;
import com.sts15.fargos.items.forces.*;
import com.sts15.fargos.items.looted.*;
import com.sts15.fargos.items.scavenged.*;
import com.sts15.fargos.items.souls.*;
import com.sts15.fargos.items.talismans.*;
import com.sts15.fargos.items.tools.bows.TsunamiBow;
import com.sts15.fargos.utils.ItemPropertiesHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.createItems("fargostalismans");

    // Crafting Item
    public static final DeferredHolder<Item, AbominableEnergyItem> ABOMINABLE_ENERGY = ITEMS.register("abominable_energy", () -> new AbominableEnergyItem(new Item.Properties()) {});

    // Soul of Flight Mastery
    public static final DeferredHolder<Item, BasicElytraItem> ANCIENT_WINGS_ELYTRA = ITEMS.register("ancient_wings_elytra", () -> new BasicElytraItem(new Item.Properties().stacksTo(1), ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "textures/entity/elytra/ancient_wings_elytra.png")));
    public static final DeferredHolder<Item, BasicElytraItem> ASTRAL_WINGS_ELYTRA = ITEMS.register("astral_wings_elytra", () -> new BasicElytraItem(new Item.Properties().stacksTo(1), ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "textures/entity/elytra/astral_wings_elytra.png")));
    public static final DeferredHolder<Item, BasicElytraItem> BLAZING_WINGS_ELYTRA = ITEMS.register("blazing_wings_elytra", () -> new BasicElytraItem(new Item.Properties().stacksTo(1), ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "textures/entity/elytra/blazing_wings_elytra.png")));
    public static final DeferredHolder<Item, BasicElytraItem> DRAGON_WINGS_ELYTRA = ITEMS.register("dragon_wings_elytra", () -> new BasicElytraItem(new Item.Properties().stacksTo(1), ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "textures/entity/elytra/dragon_wings_elytra.png")));
    public static final DeferredHolder<Item, BasicElytraItem> DUSTY_WINGS_ELYTRA = ITEMS.register("dusty_wings_elytra", () -> new BasicElytraItem(new Item.Properties().stacksTo(1), ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "textures/entity/elytra/dusty_wings_elytra.png")));
    public static final DeferredHolder<Item, BasicElytraItem> ENCHANTED_WINGS_ELYTRA = ITEMS.register("enchanted_wings_elytra", () -> new BasicElytraItem(new Item.Properties().stacksTo(1), ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "textures/entity/elytra/enchanted_wings_elytra.png")));
    public static final DeferredHolder<Item, BasicElytraItem> ENDER_WINGS_ELYTRA = ITEMS.register("ender_wings_elytra", () -> new BasicElytraItem(new Item.Properties().stacksTo(1), ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "textures/entity/elytra/ender_wings_elytra.png")));
    public static final DeferredHolder<Item, BasicElytraItem> FOREST_WINGS_ELYTRA = ITEMS.register("forest_wings_elytra", () -> new BasicElytraItem(new Item.Properties().stacksTo(1), ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "textures/entity/elytra/forest_wings_elytra.png")));
    public static final DeferredHolder<Item, BasicElytraItem> FROZEN_WINGS_ELYTRA = ITEMS.register("frozen_wings_elytra", () -> new BasicElytraItem(new Item.Properties().stacksTo(1), ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "textures/entity/elytra/frozen_wings_elytra.png")));
    public static final DeferredHolder<Item, BasicElytraItem> GHASTLY_WINGS_ELYTRA = ITEMS.register("ghastly_wings_elytra", () -> new BasicElytraItem(new Item.Properties().stacksTo(1), ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "textures/entity/elytra/ghastly_wings_elytra.png")));
    public static final DeferredHolder<Item, BasicElytraItem> OCEANS_FINS_ELYTRA = ITEMS.register("oceans_fins_elytra", () -> new BasicElytraItem(new Item.Properties().stacksTo(1), ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "textures/entity/elytra/oceans_fins_elytra.png")));
    public static final DeferredHolder<Item, BasicElytraItem> PHANTOM_WINGS_ELYTRA = ITEMS.register("phantom_wings_elytra", () -> new BasicElytraItem(new Item.Properties().stacksTo(1), ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "textures/entity/elytra/phantom_wings_elytra.png")));
    public static final DeferredHolder<Item, BasicElytraItem> VOLCANIC_ASH_ELYTRA = ITEMS.register("volcanic_ash_elytra", () -> new BasicElytraItem(new Item.Properties().stacksTo(1), ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "textures/entity/elytra/volcanic_ash_elytra.png")));
    public static final DeferredHolder<Item, BasicElytraItem> WITHER_WINGS_ELYTRA = ITEMS.register("wither_wings_elytra", () -> new BasicElytraItem(new Item.Properties().stacksTo(1), ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "textures/entity/elytra/wither_wings_elytra.png")));

    // Soul of Colossus
    public static final DeferredHolder<Item, TalismanItem> ANKH_SHIELD = ITEMS.register("ankh_shield", AnkhShieldItem::new);
    public static final DeferredHolder<Item, TalismanItem> SHINY_STONE = ITEMS.register("shiny_stone", ShinyStoneItem::new);
    public static final DeferredHolder<Item, TalismanItem> HAND_WARMER = ITEMS.register("hand_warmer", HandWarmerItem::new);
    public static final DeferredHolder<Item, TalismanItem> POCKET_MIRROR = ITEMS.register("pocket_mirror", PocketMirrorItem::new);
    public static final DeferredHolder<Item, TalismanItem> FROZEN_SHIELD = ITEMS.register("frozen_shield", FrozenShieldItem::new);
    public static final DeferredHolder<Item, TalismanItem> WORM_SCARF = ITEMS.register("worm_scarf", WormScarfItem::new);
    public static final DeferredHolder<Item, TalismanItem> CHARM_OF_MYTHS = ITEMS.register("charm_of_myths", CharmOfMythsItem::new);
    public static final DeferredHolder<Item, TalismanItem> BRAIN_OF_CONFUSION = ITEMS.register("brain_of_confusion", BrainOfConfusionItem::new);
    public static final DeferredHolder<Item, TalismanItem> BEE_CLOAK = ITEMS.register("bee_cloak", BeeCloakItem::new);
    public static final DeferredHolder<Item, TalismanItem> HERO_SHIELD = ITEMS.register("hero_shield", HeroShieldItem::new);
    public static final DeferredHolder<Item, TalismanItem> OBSIDIAN_HORSESHOE = ITEMS.register("obsidian_horseshoe", ObsidianHorseshoeItem::new);
    public static final DeferredHolder<Item, TalismanItem> STAR_VEIL = ITEMS.register("star_veil", StarVeilItem::new);

    // Soul of Supersonic
    public static final DeferredHolder<Item, TalismanItem> AEOLUS_BOOTS = ITEMS.register("aeolus_boots", AeolusBootsItem::new);
    public static final DeferredHolder<Item, TalismanItem> AMBER_HORSESHOE_BALLOON = ITEMS.register("amber_horseshoe_balloon", AmberHorseshoeBalloonItem::new);
    public static final DeferredHolder<Item, TalismanItem> ANCIENT_HORN = ITEMS.register("ancient_horn", AncientHornItem::new);
    public static final DeferredHolder<Item, TalismanItem> BLESSED_APPLE = ITEMS.register("blessed_apple", BlessedAppleItem::new);
    public static final DeferredHolder<Item, TalismanItem> BRAIN_SCRAMBLER = ITEMS.register("brain_scrambler", BrainScramblerItem::new);
    public static final DeferredHolder<Item, TalismanItem> BUNDLE_OF_HORSESHOE_BALLOONS = ITEMS.register("bundle_of_horseshoe_balloons", BundleOfHorseshoeBalloonsItem::new);
    public static final DeferredHolder<Item, TalismanItem> FLYING_CARPET = ITEMS.register("flying_carpet", FlyingCarpetItem::new);
    public static final DeferredHolder<Item, TalismanItem> MASTER_NINJA_GEAR = ITEMS.register("master_ninja_gear", MasterNinjaGearItem::new);
    public static final DeferredHolder<Item, TalismanItem> MECHANICAL_CART = ITEMS.register("mechanical_cart", MechanicalCartItem::new);
    public static final DeferredHolder<Item, TalismanItem> REINDEER_BELLS = ITEMS.register("reindeer_bells", ReindeerBellsItem::new);
    public static final DeferredHolder<Item, TalismanItem> SHIELD_OF_CTHULHU = ITEMS.register("shield_of_cthulhu", ShieldOfCthulhuItem::new);
    public static final DeferredHolder<Item, TalismanItem> SWEETHEART_NECKLACE = ITEMS.register("sweetheart_necklace", SweetheartNecklaceItem::new);

    // Force of Negative Vials
    public static final DeferredHolder<Item, CraftingItem> FATIGUED_VIAL = ITEMS.register("fatigued_vial", () -> new CraftingItem(new Item.Properties()) {});
    public static final DeferredHolder<Item, CraftingItem> WITHERED_VIAL = ITEMS.register("withered_vial", () -> new CraftingItem(new Item.Properties()) {});
    public static final DeferredHolder<Item, CraftingItem> NAUSEATED_VIAL = ITEMS.register("nauseated_vial", () -> new CraftingItem(new Item.Properties()) {});
    public static final DeferredHolder<Item, CraftingItem> POISONED_VIAL = ITEMS.register("poisoned_vial", () -> new CraftingItem(new Item.Properties()) {});
    public static final DeferredHolder<Item, CraftingItem> WEAKENED_VIAL = ITEMS.register("weakened_vial", () -> new CraftingItem(new Item.Properties()) {});
    public static final DeferredHolder<Item, CraftingItem> FIRED_VIAL = ITEMS.register("fired_vial", () -> new CraftingItem(new Item.Properties()) {});
    public static final DeferredHolder<Item, CraftingItem> SLOWED_VIAL = ITEMS.register("slowed_vial", () -> new CraftingItem(new Item.Properties()) {});
    public static final DeferredHolder<Item, CraftingItem> BLINDED_VIAL = ITEMS.register("blinded_vial", () -> new CraftingItem(new Item.Properties()) {});

    // Soul Crafting Parts
    public static final DeferredHolder<Item, CraftingItem> ESSENCE_OF_THE_TITAN = ITEMS.register("essence_of_the_titan", () -> new CraftingItem(new Item.Properties()) {});
    public static final DeferredHolder<Item, CraftingItem> ESSENCE_OF_THE_JUGGERNAUT = ITEMS.register("essence_of_the_juggernaut", () -> new CraftingItem(new Item.Properties()) {});
    public static final DeferredHolder<Item, CraftingItem> ESSENCE_OF_THE_BEHEMOTH = ITEMS.register("essence_of_the_behemoth", () -> new CraftingItem(new Item.Properties()) {});
    public static final DeferredHolder<Item, CraftingItem> ESSENCE_OF_THE_SENTINEL = ITEMS.register("essence_of_the_sentinel", () -> new CraftingItem(new Item.Properties()) {});
    public static final DeferredHolder<Item, CraftingItem> ESSENCE_OF_THE_ZEPHYR = ITEMS.register("essence_of_the_zephyr", () -> new CraftingItem(new Item.Properties()) {});
    public static final DeferredHolder<Item, CraftingItem> ESSENCE_OF_THE_SKYRUNNER = ITEMS.register("essence_of_the_skyrunner", () -> new CraftingItem(new Item.Properties()) {});
    public static final DeferredHolder<Item, CraftingItem> ESSENCE_OF_THE_HIGHWIND = ITEMS.register("essence_of_the_highwind", () -> new CraftingItem(new Item.Properties()) {});
    public static final DeferredHolder<Item, CraftingItem> ESSENCE_OF_THE_SOARER = ITEMS.register("essence_of_the_soarer", () -> new CraftingItem(new Item.Properties()) {});
    public static final DeferredHolder<Item, CraftingItem> ESSENCE_OF_THE_COMET = ITEMS.register("essence_of_the_comet", () -> new CraftingItem(new Item.Properties()) {});
    public static final DeferredHolder<Item, CraftingItem> ESSENCE_OF_THE_VELOCITY = ITEMS.register("essence_of_the_velocity", () -> new CraftingItem(new Item.Properties()) {});
    public static final DeferredHolder<Item, CraftingItem> ESSENCE_OF_THE_KINETIC = ITEMS.register("essence_of_the_kinetic", () -> new CraftingItem(new Item.Properties()) {});
    public static final DeferredHolder<Item, CraftingItem> ESSENCE_OF_THE_WHIPLASH = ITEMS.register("essence_of_the_whiplash", () -> new CraftingItem(new Item.Properties()) {});
    public static final DeferredHolder<Item, CraftingItem> ESSENCE_OF_THE_ARCANA = ITEMS.register("essence_of_the_arcana", () -> new CraftingItem(new Item.Properties()) {});
    public static final DeferredHolder<Item, CraftingItem> ESSENCE_OF_THE_ELDRITCH = ITEMS.register("essence_of_the_eldritch", () -> new CraftingItem(new Item.Properties()) {});
    public static final DeferredHolder<Item, CraftingItem> ESSENCE_OF_THE_MAGE = ITEMS.register("essence_of_the_mage", () -> new CraftingItem(new Item.Properties()) {});
    public static final DeferredHolder<Item, CraftingItem> ESSENCE_OF_THE_THAUMATURGE = ITEMS.register("essence_of_the_thaumaturge", () -> new CraftingItem(new Item.Properties()) {});
    public static final DeferredHolder<Item, CraftingItem> ESSENCE_OF_THE_RAVAGER = ITEMS.register("essence_of_the_ravager", () -> new CraftingItem(new Item.Properties()) {});
    public static final DeferredHolder<Item, CraftingItem> ESSENCE_OF_THE_WARLORD = ITEMS.register("essence_of_the_warlord", () -> new CraftingItem(new Item.Properties()) {});
    public static final DeferredHolder<Item, CraftingItem> ESSENCE_OF_THE_SAVAGE = ITEMS.register("essence_of_the_savage", () -> new CraftingItem(new Item.Properties()) {});
    public static final DeferredHolder<Item, CraftingItem> ESSENCE_OF_THE_WARMONGER = ITEMS.register("essence_of_the_warmonger", () -> new CraftingItem(new Item.Properties()) {});
    public static final DeferredHolder<Item, CraftingItem> ESSENCE_OF_THE_TOTEM = ITEMS.register("essence_of_the_totem", () -> new CraftingItem(new Item.Properties()) {});
    public static final DeferredHolder<Item, CraftingItem> ESSENCE_OF_THE_OCCULT = ITEMS.register("essence_of_the_occult", () -> new CraftingItem(new Item.Properties()) {});
    public static final DeferredHolder<Item, CraftingItem> ESSENCE_OF_THE_PHANTOM = ITEMS.register("essence_of_the_phantom", () -> new CraftingItem(new Item.Properties()) {});
    public static final DeferredHolder<Item, CraftingItem> ESSENCE_OF_THE_HEX = ITEMS.register("essence_of_the_hex", () -> new CraftingItem(new Item.Properties()) {});
    public static final DeferredHolder<Item, CraftingItem> ESSENCE_OF_THE_LONGSHOT = ITEMS.register("essence_of_the_longshot", () -> new CraftingItem(new Item.Properties()) {});
    public static final DeferredHolder<Item, CraftingItem> ESSENCE_OF_THE_DEADEYE = ITEMS.register("essence_of_the_deadeye", () -> new CraftingItem(new Item.Properties()) {});
    public static final DeferredHolder<Item, CraftingItem> ESSENCE_OF_THE_HAWK = ITEMS.register("essence_of_the_hawk", () -> new CraftingItem(new Item.Properties()) {});
    public static final DeferredHolder<Item, CraftingItem> ESSENCE_OF_THE_MARKSMAN = ITEMS.register("essence_of_the_marksman", () -> new CraftingItem(new Item.Properties()) {});
    public static final DeferredHolder<Item, CraftingItem> ESSENCE_OF_THE_LEVIATHAN = ITEMS.register("essence_of_the_leviathan", () -> new CraftingItem(new Item.Properties()) {});
    public static final DeferredHolder<Item, CraftingItem> ESSENCE_OF_THE_ANGLER = ITEMS.register("essence_of_the_angler", () -> new CraftingItem(new Item.Properties()) {});
    public static final DeferredHolder<Item, CraftingItem> ESSENCE_OF_THE_ABYSS = ITEMS.register("essence_of_the_abyss", () -> new CraftingItem(new Item.Properties()) {});
    public static final DeferredHolder<Item, CraftingItem> ESSENCE_OF_THE_BRINE = ITEMS.register("essence_of_the_brine", () -> new CraftingItem(new Item.Properties()) {});
    public static final DeferredHolder<Item, CraftingItem> ESSENCE_OF_THE_ARTISAN = ITEMS.register("essence_of_the_artisan", () -> new CraftingItem(new Item.Properties()) {});
    public static final DeferredHolder<Item, CraftingItem> ESSENCE_OF_THE_MASON = ITEMS.register("essence_of_the_mason", () -> new CraftingItem(new Item.Properties()) {});
    public static final DeferredHolder<Item, CraftingItem> ESSENCE_OF_THE_TERRAFORMER = ITEMS.register("essence_of_the_terraformer", () -> new CraftingItem(new Item.Properties()) {});
    public static final DeferredHolder<Item, CraftingItem> ESSENCE_OF_THE_SCULPTOR = ITEMS.register("essence_of_the_sculptor", () -> new CraftingItem(new Item.Properties()) {});

    // Talisman Bases
    public static final DeferredHolder<Item, CraftingItem> TALISMAN_BASE_NORMAL = ITEMS.register("talisman_base_normal", () -> new CraftingItem(new Item.Properties()) {});
    public static final DeferredHolder<Item, CraftingItem> TALISMAN_BASE_ADVANCED = ITEMS.register("talisman_base_advanced", () -> new CraftingItem(new Item.Properties()) {});
    public static final DeferredHolder<Item, CraftingItem> TALISMAN_BASE_EPIC = ITEMS.register("talisman_base_epic", () -> new CraftingItem(new Item.Properties()) {});
    public static final DeferredHolder<Item, CraftingItem> TALISMAN_BASE_LEGENDARY = ITEMS.register("talisman_base_legendary", () -> new CraftingItem(new Item.Properties()) {});
    public static final DeferredHolder<Item, CraftingItem> TALISMAN_BASE_ULTIMATE = ITEMS.register("talisman_base_ultimate", () -> new CraftingItem(new Item.Properties()) {});
    public static final DeferredHolder<Item, CraftingItem> TALISMAN_BASE_GODLY = ITEMS.register("talisman_base_godly", () -> new CraftingItem(new Item.Properties()) {});
    public static final DeferredHolder<Item, CraftingItem> TALISMAN_BASE_ANCIENT = ITEMS.register("talisman_base_ancient", () -> new CraftingItem(new Item.Properties()) {});

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
    public static final DeferredHolder<Item, TalismanItem> FIRED_TALISMAN = ITEMS.register("fired_talisman", Fired_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> POISONED_TALISMAN = ITEMS.register("poisoned_talisman", Poisoned_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> WITHERED_TALISMAN = ITEMS.register("withered_talisman", Withered_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> BLINDED_TALISMAN = ITEMS.register("blinded_talisman", Blinded_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> FATIGUED_TALISMAN = ITEMS.register("fatigued_talisman", Fatigued_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> SLOWED_TALISMAN = ITEMS.register("slowed_talisman", Slowed_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> NAUSEATED_TALISMAN = ITEMS.register("nauseated_talisman", Nauseated_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> WEAKENED_TALISMAN = ITEMS.register("weakened_talisman", Weakened_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> SUN_TALISMAN = ITEMS.register("sun_talisman", Sun_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> TRUE_SUN_TALISMAN = ITEMS.register("true_sun_talisman", True_Sun_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> STORM_TALISMAN = ITEMS.register("storm_talisman", Storm_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> RAIN_TALISMAN = ITEMS.register("rain_talisman", Rain_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> NIGHT_TALISMAN = ITEMS.register("night_talisman", Night_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> DAY_TALISMAN = ITEMS.register("day_talisman", Day_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> FULL_MOON_TALISMAN = ITEMS.register("full_moon_talisman", Full_Moon_Talisman::new);
    public static final DeferredHolder<Item, TalismanItem> SNOWY_TALISMAN = ITEMS.register("snowy_talisman", Snowy_Talisman::new);
    
    //Forces
    public static final DeferredHolder<Item, TalismanItem> FORCE_OF_EXPLORER = ITEMS.register("force_of_explorer", Force_of_Explorer::new);
    public static final DeferredHolder<Item, TalismanItem> FORCE_OF_MYSTIC = ITEMS.register("force_of_mystic", Force_of_Mystic::new);
    public static final DeferredHolder<Item, TalismanItem> FORCE_OF_NATURE = ITEMS.register("force_of_nature", Force_of_Nature::new);
    public static final DeferredHolder<Item, TalismanItem> FORCE_OF_OVERWORLD = ITEMS.register("force_of_overworld", Force_of_Overworld::new);
    public static final DeferredHolder<Item, TalismanItem> FORCE_OF_REJECTORS = ITEMS.register("force_of_rejectors", Force_of_Rejectors::new);
    public static final DeferredHolder<Item, TalismanItem> FORCE_OF_WARRIOR = ITEMS.register("force_of_warrior", Force_of_Warrior::new);
    public static final DeferredHolder<Item, TalismanItem> FORCE_OF_NEGATIVE = ITEMS.register("force_of_negative", Force_of_Negative::new);
    public static final DeferredHolder<Item, TalismanItem> FORCE_OF_ENVIRONMENT = ITEMS.register("force_of_environment", Force_of_Environment::new);
    
    //Souls
    public static final DeferredHolder<Item, TalismanItem> SOUL_OF_COLOSSUS = ITEMS.register("soul_of_colossus", Soul_of_Colossus::new);
    public static final DeferredHolder<Item, TalismanItem> SOUL_OF_DIMENSIONS = ITEMS.register("soul_of_dimensions", Soul_of_Dimensions::new);
    public static final DeferredHolder<Item, BasicElytraItem> SOUL_OF_FLIGHT_MASTERY = ITEMS.register("soul_of_flight_mastery", Soul_of_Flight_Mastery::new);
    public static final DeferredHolder<Item, TalismanItem> SOUL_OF_MINECRAFT = ITEMS.register("soul_of_minecraft", Soul_of_Minecraft::new);
    public static final DeferredHolder<Item, TalismanItem> SOUL_OF_SUPERSONIC = ITEMS.register("soul_of_supersonic", Soul_of_Supersonic::new);
    public static final DeferredHolder<Item, TalismanItem> SOUL_OF_ARCH_WIZARD = ITEMS.register("soul_of_arch_wizard", Soul_of_Arch_Wizard::new);
    public static final DeferredHolder<Item, TalismanItem> SOUL_OF_BERSERKER = ITEMS.register("soul_of_berserker", Soul_of_Berserker::new);
    public static final DeferredHolder<Item, TalismanItem> SOUL_OF_CONJURIST = ITEMS.register("soul_of_conjurist", Soul_of_Conjurist::new);
    public static final DeferredHolder<Item, TalismanItem> SOUL_OF_SNIPER = ITEMS.register("soul_of_sniper", Soul_of_Sniper::new);
    public static final DeferredHolder<Item, TalismanItem> SOUL_OF_TRAWLER = ITEMS.register("soul_of_trawler", Soul_of_Trawler::new);
    public static final DeferredHolder<Item, TalismanItem> SOUL_OF_WORLD_SHAPER = ITEMS.register("soul_of_world_shaper", Soul_of_World_Shaper::new);
    public static final DeferredHolder<Item, TalismanItem> SOUL_OF_THE_UNIVERSE = ITEMS.register("soul_of_the_universe", Soul_of_the_Universe::new);
    public static final DeferredHolder<Item, TalismanItem> SOUL_OF_ETERNITY = ITEMS.register("soul_of_eternity", Soul_of_Eternity::new);

    // Armors
    public static final DeferredHolder<Item, Item> ERIDANUS_HELMET = ITEMS.register("eridanus_helmet", () -> new EridanusArmorItem(ArmorItem.Type.HELMET, ItemPropertiesHelper.equipment(1)));
    public static final DeferredHolder<Item, Item> ERIDANUS_CHESTPLATE = ITEMS.register("eridanus_chestplate", () -> new EridanusArmorItem(ArmorItem.Type.CHESTPLATE, ItemPropertiesHelper.equipment(1)));
    public static final DeferredHolder<Item, Item> ERIDANUS_LEGGINGS = ITEMS.register("eridanus_leggings", () -> new EridanusArmorItem(ArmorItem.Type.LEGGINGS, ItemPropertiesHelper.equipment(1)));
    public static final DeferredHolder<Item, Item> ERIDANUS_BOOTS = ITEMS.register("eridanus_boots", () -> new EridanusArmorItem(ArmorItem.Type.BOOTS, ItemPropertiesHelper.equipment(1)));
    public static final DeferredHolder<Item, Item> STYX_HELMET = ITEMS.register("styx_helmet", () -> new StyxArmorItem(ArmorItem.Type.HELMET, ItemPropertiesHelper.equipment(1)));
    public static final DeferredHolder<Item, Item> STYX_CHESTPLATE = ITEMS.register("styx_chestplate", () -> new StyxArmorItem(ArmorItem.Type.CHESTPLATE, ItemPropertiesHelper.equipment(1)));
    public static final DeferredHolder<Item, Item> STYX_LEGGINGS = ITEMS.register("styx_leggings", () -> new StyxArmorItem(ArmorItem.Type.LEGGINGS, ItemPropertiesHelper.equipment(1)));
    public static final DeferredHolder<Item, Item> STYX_BOOTS = ITEMS.register("styx_boots", () -> new StyxArmorItem(ArmorItem.Type.BOOTS, ItemPropertiesHelper.equipment(1)));
    public static final DeferredHolder<Item, Item> MUTANT_HELMET = ITEMS.register("mutant_helmet", () -> new MutantArmorItem(ArmorItem.Type.HELMET, ItemPropertiesHelper.equipment(1)));
    public static final DeferredHolder<Item, Item> MUTANT_CHESTPLATE = ITEMS.register("mutant_chestplate", () -> new MutantArmorItem(ArmorItem.Type.CHESTPLATE, ItemPropertiesHelper.equipment(1)));
    public static final DeferredHolder<Item, Item> MUTANT_LEGGINGS = ITEMS.register("mutant_leggings", () -> new MutantArmorItem(ArmorItem.Type.LEGGINGS, ItemPropertiesHelper.equipment(1)));
    public static final DeferredHolder<Item, Item> MUTANT_BOOTS = ITEMS.register("mutant_boots", () -> new MutantArmorItem(ArmorItem.Type.BOOTS, ItemPropertiesHelper.equipment(1)));

    // Fishing Rods
    public static final DeferredHolder<Item, SimpleFishingRod> SIMPLE_FISHING_ROD = rod("simple_fishing_rod", Rarity.COMMON, 200, SimpleFishingRod::new);
    public static final DeferredHolder<Item, FiberglassFishingRod> FIBERGLASS_FISHING_ROD = rod("fiberglass_fishing_rod", Rarity.UNCOMMON, 250, FiberglassFishingRod::new);
    public static final DeferredHolder<Item, FleshcatcherFishingRod> FLESHCATCHER_FISHING_ROD = rod("fleshcatcher_fishing_rod", Rarity.UNCOMMON, 260, FleshcatcherFishingRod::new);
    public static final DeferredHolder<Item, FisherOfSoulsFishingRod> FISHER_OF_SOULS_FISHING_ROD = rod("fisher_of_souls_fishing_rod", Rarity.UNCOMMON, 260, FisherOfSoulsFishingRod::new);
    public static final DeferredHolder<Item, ReinforcedFishingRod> REINFORCED_FISHING_ROD = rod("reinforced_fishing_rod", Rarity.COMMON, 240, ReinforcedFishingRod::new);
    public static final DeferredHolder<Item, ChumCasterFishingRod> CHUM_CASTER_FISHING_ROD = rod("chum_caster_fishing_rod", Rarity.UNCOMMON, 280, ChumCasterFishingRod::new);
    public static final DeferredHolder<Item, ScarabFishingRod> SCARAB_FISHING_ROD = rod("scarab_fishing_rod", Rarity.UNCOMMON, 260, ScarabFishingRod::new);
    public static final DeferredHolder<Item, MechanicsFishingRod> MECHANICS_FISHING_ROD = rod("mechanics_fishing_rod", Rarity.UNCOMMON, 300, MechanicsFishingRod::new);
    public static final DeferredHolder<Item, HotlineFishingRod> HOTLINE_FISHING_ROD = rod("hotline_fishing_rod", Rarity.RARE, 350, HotlineFishingRod::new);
    public static final DeferredHolder<Item, GoldenFishingRod> GOLDEN_FISHING_ROD = rod("golden_fishing_rod", Rarity.RARE, 400, GoldenFishingRod::new);
    public static final DeferredHolder<Item, SittingDucksFishingRod> SITTING_DUCKS_FISHING_ROD = rod("sitting_ducks_fishing_rod", Rarity.UNCOMMON, 320, SittingDucksFishingRod::new);
    public static final DeferredHolder<Item, StyxCasterFishingRod> STYX_CASTER_FISHING_ROD = rod("styx_caster_fishing_rod", Rarity.EPIC, 400, StyxCasterFishingRod::new);

    public static final DeferredHolder<Item, BasicBow> EVENTIDE_BOW        = bow("eventide_bow",        1.70f, 19f, Rarity.EPIC,     500);
    public static final DeferredHolder<Item, BasicBow> DAEDALUS_STORMBOW   = bow("daedalus_stormbow",   1.55f, 16f, Rarity.EPIC,     400);
    public static final DeferredHolder<Item, BasicBow> HELLWING_BOW        = bow("hellwing_bow",        1.40f, 14f, Rarity.RARE,     325);
    public static final DeferredHolder<Item, BasicBow> TENDON_BOW          = bow("tendon_bow",          1.30f, 13f, Rarity.UNCOMMON, 300);
    public static final DeferredHolder<Item, BasicBow> ICE_BOW             = bow("ice_bow",             1.25f, 12.5f, Rarity.UNCOMMON, 250);
    public static final DeferredHolder<Item, BasicBow> DEMON_BOW           = bow("demon_bow",           1.35f, 13.5f, Rarity.RARE,   300);
    public static final DeferredHolder<Item, BasicBow> SIMPLE_BOW          = bow("simple_bow",          1.20f, 12f, Rarity.COMMON,   200);
    public static final DeferredHolder<Item, BasicBow> BLOOD_RAIN_BOW      = bow("blood_rain_bow",      1.50f, 15f, Rarity.RARE,     375);
    public static final DeferredHolder<Item, BasicBow> MECHANICS_BOW       = bow("mechanics_bow",       1.45f, 14.5f, Rarity.RARE,   350);
    public static final DeferredHolder<Item, TsunamiBow> TSUNAMI_BOW = ITEMS.register("tsunami_bow", () -> new TsunamiBow(1.60f, 17f, Rarity.EPIC, 450));
    public static final DeferredHolder<Item, BasicBow> VORTEX_BOW          = bow("vortex_bow",          1.65f, 18f, Rarity.EPIC,     475);
    public static final DeferredHolder<Item, BasicBow> AERIAL_BANE_BOW     = bow("aerial_bane_bow",     1.55f, 16f, Rarity.EPIC,     400);

    public static final DeferredHolder<Item, FargosGuideBook> GUIDE_BOOK = ITEMS.register("guide_book",
            () -> new FargosGuideBook(new Item.Properties().stacksTo(1)) {});

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    private static Item.Properties rodProps(Rarity rarity, int durability) {
        return new Item.Properties().stacksTo(1).rarity(rarity).durability(durability);
    }

    private static <T extends BasicFishingRod> DeferredHolder<Item, T> rod(String name, Rarity rarity, int durability, Function<Item.Properties, T> constructor) {
        return ITEMS.register(name, () -> constructor.apply(rodProps(rarity, durability)));
    }

    private static DeferredHolder<Item, BasicBow> bow(String name, float drawSpeed, float range, Rarity rarity, int durability) {
        return ITEMS.register(name, () -> new BasicBow(drawSpeed, range, rarity, durability) {
            @Override
            public void onArrowFired(Level level, LivingEntity shooter, AbstractArrow baseArrow, float power) {}
        });
    }

}
