package com.sts15.fargos.datagen.providers;

import com.google.gson.JsonObject;
import com.hollingsworth.arsnouveau.api.registry.GlyphRegistry;
import com.hollingsworth.arsnouveau.common.items.Glyph;
import com.hollingsworth.arsnouveau.setup.registry.ItemsRegistry;
import com.sts15.fargos.Fargos;
import com.sts15.fargos.items.ItemInit;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.crafting.CraftingHelper;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class FargosRecipeProvider extends RecipeProvider {

    public FargosRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper fileHelper) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput out) {
        buildTalismans(out);
        buildWings(out);
        buildEssences(out);
        buildForces(out);
        buildItems(out);
        buildSouls(out);
    }

    protected void buildTalismans(RecipeOutput out) {
        shaped(out, "air_talisman", ItemInit.AIR_TALISMAN.get(), b -> {
            b.pattern("ACA");
            b.pattern("B#B");
            b.pattern("ADA");
            b.define('#', ItemInit.TALISMAN_BASE_ADVANCED.get());
            b.define('A', Items.FEATHER);
            b.define('B', Items.PHANTOM_MEMBRANE);
            b.define('C', Items.ELYTRA);
            b.define('D', Items.SLIME_BLOCK);
        });

        shaped(out, "amethyst_talisman", ItemInit.AMETHYST_TALISMAN.get(), b -> {
            b.pattern("ACA");
            b.pattern("B#B");
            b.pattern("ADA");
            b.define('#', ItemInit.TALISMAN_BASE_ADVANCED.get());
            b.define('A', Items.AMETHYST_BLOCK);
            b.define('B', Items.AMETHYST_SHARD);
            b.define('C', Items.SHIELD);
            b.define('D', Items.SHIELD);
        });

        shaped(out, "apple_talisman", ItemInit.APPLE_TALISMAN.get(), b -> {
            b.pattern("ACA");
            b.pattern("B#B");
            b.pattern("ADA");
            b.define('#', ItemInit.TALISMAN_BASE_ADVANCED.get());
            b.define('A', Items.APPLE);
            b.define('B', Items.NETHER_STAR);
            b.define('C', Items.COOKIE);
            b.define('D', Items.HONEYCOMB);
        });

        shaped(out, "architect_talisman", ItemInit.ARCHITECT_TALISMAN.get(), b -> {
            b.pattern("ABC");
            b.pattern("DEF");
            b.pattern("GHI");
            b.define('A', Items.ENDER_CHEST);
            b.define('B', Items.ENDER_EYE);
            b.define('C', Items.BEACON);
            b.define('D', Items.ENDER_PEARL);
            b.define('E', ItemInit.TALISMAN_BASE_NORMAL.get());
            b.define('F', Items.ENDER_PEARL);
            b.define('G', Items.BEACON);
            b.define('H', Items.ENDER_EYE);
            b.define('I', Items.ENDER_CHEST);
        });

        shaped(out, "arctic_talisman", ItemInit.ARCTIC_TALISMAN.get(), b -> {
            b.pattern("ACA");
            b.pattern("B#B");
            b.pattern("ADA");
            b.define('#', ItemInit.TALISMAN_BASE_ADVANCED.get());
            b.define('A', Items.POWDER_SNOW_BUCKET);
            b.define('B', Items.SNOW_BLOCK);
            b.define('C', Items.LEATHER_CHESTPLATE);
            b.define('D', Items.LEATHER_LEGGINGS);
        });

        shaped(out, "battle_talisman", ItemInit.BATTLE_TALISMAN.get(), b -> {
            b.pattern("ABC");
            b.pattern("D#D");
            b.pattern("FGH");
            b.define('#', ItemInit.TALISMAN_BASE_ADVANCED.get());
            b.define('A', Items.COOKED_BEEF);
            b.define('B', Items.PUMPKIN_PIE);
            b.define('C', Items.COOKED_RABBIT);
            b.define('D', Items.ENCHANTED_GOLDEN_APPLE);
            b.define('F', Items.COOKED_CHICKEN);
            b.define('G', Items.CAKE);
            b.define('H', Items.COOKED_MUTTON);
        });

        shaped(out, "blaze_talisman", ItemInit.BLAZE_TALISMAN.get(), b -> {
            b.pattern("ACA");
            b.pattern("B#B");
            b.pattern("ADA");
            b.define('#', ItemInit.TALISMAN_BASE_ADVANCED.get());
            b.define('A', Items.BLAZE_ROD);
            b.define('B', Items.FIRE_CHARGE);
            b.define('C', Items.FLINT_AND_STEEL);
            b.define('D', Items.COAL_BLOCK);
        });

        shaped(out, "blinded_talisman", ItemInit.BLINDED_TALISMAN.get(), b -> {
            b.pattern("IBI");
            b.pattern("OFO");
            b.pattern("IBI");
            b.define('I', ItemInit.BLINDED_VIAL.get());
            b.define('B', Items.INK_SAC);
            b.define('F', ItemInit.TALISMAN_BASE_EPIC.get());
            b.define('O', Items.BLACK_DYE);
        });

        shaped(out, "cactus_talisman", ItemInit.CACTUS_TALISMAN.get(), b -> {
            b.pattern("ACA");
            b.pattern("B#B");
            b.pattern("ADA");
            b.define('#', ItemInit.TALISMAN_BASE_ADVANCED.get());
            b.define('A', Items.CACTUS);
            b.define('B', Items.POINTED_DRIPSTONE);
            b.define('C', Items.WITHER_ROSE);
            b.define('D', Items.SAND);
        });

        shaped(out, "copper_talisman", ItemInit.COPPER_TALISMAN.get(), b -> {
            b.pattern("ABC");
            b.pattern("BEB");
            b.pattern("DBF");
            b.define('E', ItemInit.TALISMAN_BASE_NORMAL.get());
            b.define('A', Items.COPPER_BLOCK);
            b.define('B', Items.COPPER_INGOT);
            b.define('D', Items.EXPOSED_COPPER);
            b.define('C', Items.WEATHERED_COPPER);
            b.define('F', Items.OXIDIZED_COPPER);
        });

        shaped(out, "creeper_talisman", ItemInit.CREEPER_TALISMAN.get(), b -> {
            b.pattern("ABA");
            b.pattern("B#B");
            b.pattern("ABA");
            b.define('#', ItemInit.TALISMAN_BASE_NORMAL.get());
            b.define('A', Items.CREEPER_HEAD);
            b.define('B', Items.TNT);
        });

        shaped(out, "day_talisman", ItemInit.DAY_TALISMAN.get(), b -> {
            b.pattern("ABA");
            b.pattern("C#C");
            b.pattern("AEA");
            b.define('A', Items.SUNFLOWER);
            b.define('B', Items.DAYLIGHT_DETECTOR);
            b.define('C', Items.GOLD_INGOT);
            b.define('E', Items.GLOWSTONE);
            b.define('#', ItemInit.TALISMAN_BASE_EPIC.get());
        });

        shaped(out, "diamond_talisman", ItemInit.DIAMOND_TALISMAN.get(), b -> {
            b.pattern("ACA");
            b.pattern("B#B");
            b.pattern("ACA");
            b.define('#', ItemInit.TALISMAN_BASE_ADVANCED.get());
            b.define('A', Items.DIAMOND_BLOCK);
            b.define('B', ItemInit.IRON_TALISMAN.get());
            b.define('C', Items.NETHER_STAR);
        });

        shaped(out, "dragon_talisman", ItemInit.DRAGON_TALISMAN.get(), b -> {
            b.pattern("ACA");
            b.pattern("B#B");
            b.pattern("ADA");
            b.define('#', ItemInit.TALISMAN_BASE_ADVANCED.get());
            b.define('A', Items.END_ROD);
            b.define('B', Items.DRAGON_BREATH);
            b.define('C', Items.DRAGON_HEAD);
            b.define('D', Items.DRAGON_EGG);
        });

        shaped(out, "earth_talisman", ItemInit.EARTH_TALISMAN.get(), b -> {
            b.pattern("ADC");
            b.pattern("BEB");
            b.pattern("CDA");
            b.define('E', ItemInit.TALISMAN_BASE_NORMAL.get());
            b.define('A', Items.RAW_IRON);
            b.define('B', Items.RAW_GOLD);
            b.define('C', Items.DIAMOND);
            b.define('D', Items.GRASS_BLOCK);
        });

        shaped(out, "emerald_talisman", ItemInit.EMERALD_TALISMAN.get(), b -> {
            b.pattern("ADC");
            b.pattern("B#E");
            b.pattern("CDA");
            b.define('#', ItemInit.TALISMAN_BASE_NORMAL.get());
            b.define('A', Items.EMERALD_BLOCK);
            b.define('B', Items.CROSSBOW);
            b.define('C', Items.EMERALD);
            b.define('D', Items.BONE);
            b.define('E', Items.STONE_AXE);
        });

        shaped(out, "enchanting_talisman", ItemInit.ENCHANTING_TALISMAN.get(), b -> {
            b.pattern("ABA");
            b.pattern("B#B");
            b.pattern("ABA");
            b.define('#', ItemInit.TALISMAN_BASE_EPIC.get());
            b.define('A', Items.ENCHANTING_TABLE);
            b.define('B', Items.ENCHANTED_GOLDEN_APPLE);
        });

        shaped(out, "enderman_talisman", ItemInit.ENDERMAN_TALISMAN.get(), b -> {
            b.pattern("ADA");
            b.pattern("B#B");
            b.pattern("ADA");
            b.define('#', ItemInit.TALISMAN_BASE_NORMAL.get());
            b.define('A', Items.CARVED_PUMPKIN);
            b.define('B', Items.ENDER_EYE);
            b.define('D', Items.ENDER_PEARL);
        });

        shaped(out, "fatigued_talisman", ItemInit.FATIGUED_TALISMAN.get(), b -> {
            b.pattern("VSV");
            b.pattern("PFT");
            b.pattern("VSV");
            b.define('S', Items.SLIME_BALL);
            b.define('F', ItemInit.TALISMAN_BASE_EPIC.get());
            b.define('T', Items.TURTLE_SCUTE);
            b.define('P', Items.PUFFERFISH);
            b.define('V', ItemInit.FATIGUED_VIAL.get());
        });

        shaped(out, "fired_talisman", ItemInit.FIRED_TALISMAN.get(), b -> {
            b.pattern("BAB");
            b.pattern("MFM");
            b.pattern("BLB");
            b.define('B', ItemInit.FIRED_VIAL.get());
            b.define('M', Items.MAGMA_BLOCK);
            b.define('F', ItemInit.TALISMAN_BASE_EPIC.get());
            b.define('L', Items.LAVA_BUCKET);
            b.define('A', Items.BLAZE_POWDER);
        });

        shaped(out, "fire_talisman", ItemInit.FIRE_TALISMAN.get(), b -> {
            b.pattern("ABC");
            b.pattern("D#E");
            b.pattern("FGH");
            b.define('#', ItemInit.TALISMAN_BASE_ADVANCED.get());
            b.define('A', Items.OAK_PLANKS);
            b.define('B', Items.FIRE_CHARGE);
            b.define('C', Items.SPRUCE_PLANKS);
            b.define('D', Items.CAMPFIRE);
            b.define('E', Items.SOUL_CAMPFIRE);
            b.define('F', Items.BIRCH_PLANKS);
            b.define('G', Items.FLINT_AND_STEEL);
            b.define('H', Items.JUNGLE_PLANKS);
        });

        shaped(out, "full_moon_talisman", ItemInit.FULL_MOON_TALISMAN.get(), b -> {
            b.pattern("ABA");
            b.pattern("C#D");
            b.pattern("AEA");
            b.define('A', Items.ENDER_PEARL);
            b.define('B', Items.LODESTONE);
            b.define('C', Items.SLIME_BALL);
            b.define('D', Items.RABBIT_FOOT);
            b.define('E', Items.AMETHYST_SHARD);
            b.define('#', ItemInit.TALISMAN_BASE_EPIC.get());
        });

        shaped(out, "ghast_talisman", ItemInit.GHAST_TALISMAN.get(), b -> {
            b.pattern("ACA");
            b.pattern("B#B");
            b.pattern("ADA");
            b.define('#', ItemInit.TALISMAN_BASE_NORMAL.get());
            b.define('A', Items.GHAST_TEAR);
            b.define('B', Items.GLOWSTONE);
            b.define('C', Items.WITHER_SKELETON_SKULL);
            b.define('D', Items.WITHER_ROSE);
        });

        shaped(out, "glowstone_talisman", ItemInit.GLOWSTONE_TALISMAN.get(), b -> {
            b.pattern("ADB");
            b.pattern("C#C");
            b.pattern("BDA");
            b.define('#', ItemInit.TALISMAN_BASE_NORMAL.get());
            b.define('A', Items.SOUL_LANTERN);
            b.define('B', Items.LANTERN);
            b.define('C', Items.SOUL_TORCH);
            b.define('D', Items.TORCH);
        });

        shaped(out, "gold_talisman", ItemInit.GOLD_TALISMAN.get(), b -> {
            b.pattern("ADA");
            b.pattern("B#B");
            b.pattern("ACA");
            b.define('#', ItemInit.TALISMAN_BASE_NORMAL.get());
            b.define('A', Items.GOLD_BLOCK);
            b.define('B', Items.GOLD_INGOT);
            b.define('C', Items.SHIELD);
            b.define('D', Items.CROSSBOW);
        });

        shaped(out, "iron_golem_talisman", ItemInit.IRON_GOLEM_TALISMAN.get(), b -> {
            b.pattern("ACA");
            b.pattern("B#B");
            b.pattern("ADA");
            b.define('#', ItemInit.TALISMAN_BASE_ADVANCED.get());
            b.define('A', Items.IRON_BLOCK);
            b.define('B', Items.PISTON);
            b.define('C', Items.CARVED_PUMPKIN);
            b.define('D', Items.ROSE_BUSH);
        });

        shaped(out, "iron_talisman", ItemInit.IRON_TALISMAN.get(), b -> {
            b.pattern("AGC");
            b.pattern("E#F");
            b.pattern("BHD");
            b.define('#', ItemInit.TALISMAN_BASE_NORMAL.get());
            b.define('A', Items.IRON_HELMET);
            b.define('B', Items.IRON_CHESTPLATE);
            b.define('C', Items.IRON_LEGGINGS);
            b.define('D', Items.IRON_BOOTS);
            b.define('E', Items.IRON_SWORD);
            b.define('F', Items.IRON_AXE);
            b.define('G', Items.SHIELD);
            b.define('H', Items.IRON_HORSE_ARMOR);
        });

        shaped(out, "lapis_talisman", ItemInit.LAPIS_TALISMAN.get(), b -> {
            b.pattern("ACA");
            b.pattern("B#B");
            b.pattern("ADA");
            b.define('#', ItemInit.TALISMAN_BASE_ADVANCED.get());
            b.define('A', Items.LAPIS_BLOCK);
            b.define('B', Items.EXPERIENCE_BOTTLE);
            b.define('C', Items.BOOK);
            b.define('D', Items.ENCHANTING_TABLE);
        });

        shaped(out, "librarian_talisman", ItemInit.LIBRARIAN_TALISMAN.get(), b -> {
            b.pattern("ACA");
            b.pattern("B#B");
            b.pattern("ABA");
            b.define('#', ItemInit.TALISMAN_BASE_EPIC.get());
            b.define('A', Items.BOOKSHELF);
            b.define('B', Items.BOOK);
            b.define('C', Items.WRITABLE_BOOK);
        });

        shaped(out, "mooshroom_talisman", ItemInit.MOOSHROOM_TALISMAN.get(), b -> {
            b.pattern("ABC");
            b.pattern("D#E");
            b.pattern("FGH");
            b.define('#', ItemInit.TALISMAN_BASE_ADVANCED.get());
            b.define('A', Items.BROWN_MUSHROOM_BLOCK);
            b.define('B', Items.NETHER_STAR);
            b.define('C', Items.RED_MUSHROOM_BLOCK);
            b.define('D', Items.RED_MUSHROOM);
            b.define('E', Items.BROWN_MUSHROOM);
            b.define('F', Items.MUSHROOM_STEM);
            b.define('G', Items.NETHER_STAR);
            b.define('H', Items.MUSHROOM_STEM);
        });

        shaped(out, "nauseated_talisman", ItemInit.NAUSEATED_TALISMAN.get(), b -> {
            b.pattern("VFV");
            b.pattern("PBR");
            b.pattern("VMV");
            b.define('R', Items.ROTTEN_FLESH);
            b.define('F', Items.FERMENTED_SPIDER_EYE);
            b.define('P', Items.PUFFERFISH);
            b.define('M', Items.BROWN_MUSHROOM);
            b.define('B', ItemInit.TALISMAN_BASE_EPIC.get());
            b.define('V', ItemInit.NAUSEATED_VIAL.get());
        });

        shaped(out, "nether_star_talisman", ItemInit.NETHER_STAR_TALISMAN.get(), b -> {
            b.pattern("ADA");
            b.pattern("B#C");
            b.pattern("AEA");
            b.define('#', ItemInit.TALISMAN_BASE_EPIC.get());
            b.define('A', Items.NETHER_STAR);
            b.define('B', Items.NETHERITE_SWORD);
            b.define('C', Items.NETHERITE_AXE);
            b.define('D', Items.MACE);
            b.define('E', Items.TRIDENT);
        });

        shaped(out, "night_talisman", ItemInit.NIGHT_TALISMAN.get(), b -> {
            b.pattern("ADA");
            b.pattern("C#B");
            b.pattern("AEA");
            b.define('A', Items.END_STONE);
            b.define('B', Items.BLACKSTONE);
            b.define('C', Items.OBSIDIAN);
            b.define('D', Items.DIAMOND_CHESTPLATE);
            b.define('E', Items.CRYING_OBSIDIAN);
            b.define('#', ItemInit.TALISMAN_BASE_EPIC.get());
        });

        shaped(out, "obsidian_talisman", ItemInit.OBSIDIAN_TALISMAN.get(), b -> {
            b.pattern("ACE");
            b.pattern("B#B");
            b.pattern("EDA");
            b.define('#', ItemInit.TALISMAN_BASE_ADVANCED.get());
            b.define('A', Items.OBSIDIAN);
            b.define('B', Items.TNT);
            b.define('C', Items.NETHERITE_CHESTPLATE);
            b.define('D', Items.NETHER_STAR);
            b.define('E', Items.CRYING_OBSIDIAN);
        });

        shaped(out, "pickaxe_talisman", ItemInit.PICKAXE_TALISMAN.get(), b -> {
            b.pattern("AGC");
            b.pattern("E#F");
            b.pattern("BHD");
            b.define('#', ItemInit.TALISMAN_BASE_NORMAL.get());
            b.define('A', Items.IRON_PICKAXE);
            b.define('B', Items.GOLDEN_PICKAXE);
            b.define('C', Items.DIAMOND_PICKAXE);
            b.define('D', Items.NETHERITE_PICKAXE);
            b.define('E', Items.RAW_IRON_BLOCK);
            b.define('F', Items.RAW_GOLD_BLOCK);
            b.define('G', Items.DIAMOND_BLOCK);
            b.define('H', Items.RAW_COPPER_BLOCK);
        });

        shaped(out, "poisoned_talisman", ItemInit.POISONED_TALISMAN.get(), b -> {
            b.pattern("VSV");
            b.pattern("FBP");
            b.pattern("VCV");
            b.define('S', Items.SPIDER_EYE);
            b.define('F', Items.FERMENTED_SPIDER_EYE);
            b.define('P', Items.POISONOUS_POTATO);
            b.define('C', Items.CACTUS);
            b.define('B', ItemInit.TALISMAN_BASE_EPIC.get());
            b.define('V', ItemInit.POISONED_VIAL.get());
        });

        shaped(out, "rain_talisman", ItemInit.RAIN_TALISMAN.get(), b -> {
            b.pattern("ABA");
            b.pattern("C#D");
            b.pattern("AEA");
            b.define('A', Items.LAPIS_LAZULI);
            b.define('B', Items.WATER_BUCKET);
            b.define('C', Items.TURTLE_SCUTE);
            b.define('D', Items.PRISMARINE_SHARD);
            b.define('E', Items.SEAGRASS);
            b.define('#', ItemInit.TALISMAN_BASE_EPIC.get());
        });

        shaped(out, "redstone_talisman", ItemInit.REDSTONE_TALISMAN.get(), b -> {
            b.pattern("ACA");
            b.pattern("B#E");
            b.pattern("ADA");
            b.define('#', ItemInit.TALISMAN_BASE_ADVANCED.get());
            b.define('A', Items.REDSTONE_BLOCK);
            b.define('B', Items.REPEATER);
            b.define('E', Items.COMPARATOR);
            b.define('C', Items.REDSTONE_LAMP);
            b.define('D', Items.NETHER_STAR);
        });

        shaped(out, "shulker_talisman", ItemInit.SHULKER_TALISMAN.get(), b -> {
            b.pattern("ABC");
            b.pattern("D#D");
            b.pattern("CEA");
            b.define('#', ItemInit.TALISMAN_BASE_NORMAL.get());
            b.define('A', Items.ELYTRA);
            b.define('B', Items.FEATHER);
            b.define('C', Items.NETHER_STAR);
            b.define('D', Items.SLIME_BALL);
            b.define('E', Items.SLIME_BLOCK);
        });

        shaped(out, "skeleton_talisman", ItemInit.SKELETON_TALISMAN.get(), b -> {
            b.pattern("ACA");
            b.pattern("E#E");
            b.pattern("ACA");
            b.define('#', ItemInit.TALISMAN_BASE_NORMAL.get());
            b.define('A', Items.ARROW);
            b.define('C', Items.BONE);
            b.define('E', Items.BOW);
        });

        shaped(out, "slowed_talisman", ItemInit.SLOWED_TALISMAN.get(), b -> {
            b.pattern("VCV");
            b.pattern("IFS");
            b.pattern("VBV");
            b.define('I', Items.BLUE_ICE);
            b.define('B', Items.SNOW_BLOCK);
            b.define('F', ItemInit.TALISMAN_BASE_EPIC.get());
            b.define('C', Items.COBWEB);
            b.define('V', ItemInit.SLOWED_VIAL.get());
            b.define('S', Items.ICE);
        });

        shaped(out, "snowy_talisman", ItemInit.SNOWY_TALISMAN.get(), b -> {
            b.pattern("ABA");
            b.pattern("C#D");
            b.pattern("AEA");
            b.define('A', Items.SNOWBALL);
            b.define('B', Items.BLUE_ICE);
            b.define('C', Items.IRON_INGOT);
            b.define('D', Items.WHITE_WOOL);
            b.define('E', Items.SNOW_BLOCK);
            b.define('#', ItemInit.TALISMAN_BASE_EPIC.get());
        });

        shaped(out, "spectral_talisman", ItemInit.SPECTRAL_TALISMAN.get(), b -> {
            b.pattern("ADA");
            b.pattern("B#B");
            b.pattern("ABA");
            b.define('#', ItemInit.TALISMAN_BASE_NORMAL.get());
            b.define('A', Items.PHANTOM_MEMBRANE);
            b.define('B', Items.SPECTRAL_ARROW);
            b.define('D', Items.TURTLE_HELMET);
        });

        shaped(out, "storm_talisman", ItemInit.STORM_TALISMAN.get(), b -> {
            b.pattern("ABA");
            b.pattern("C#D");
            b.pattern("AEA");
            b.define('A', Items.PHANTOM_MEMBRANE);
            b.define('B', Items.TRIDENT);
            b.define('C', Items.NETHER_STAR);
            b.define('D', Items.LIGHTNING_ROD);
            b.define('E', Items.IRON_BLOCK);
            b.define('#', ItemInit.TALISMAN_BASE_EPIC.get());
        });

        shaped(out, "sun_talisman", ItemInit.SUN_TALISMAN.get(), b -> {
            b.pattern("ABA");
            b.pattern("C#D");
            b.pattern("AEA");
            b.define('A', Items.BLAZE_POWDER);
            b.define('B', Items.SUNFLOWER);
            b.define('C', Items.GOLD_INGOT);
            b.define('D', Items.DAYLIGHT_DETECTOR);
            b.define('E', Items.GOLD_BLOCK);
            b.define('#', ItemInit.TALISMAN_BASE_EPIC.get());
        });

        shaped(out, "talisman_base_advanced", ItemInit.TALISMAN_BASE_ADVANCED.get(), b -> {
            b.pattern("IAI");
            b.pattern("#N#");
            b.pattern("IAI");
            b.define('#', ItemInit.TALISMAN_BASE_NORMAL.get());
            b.define('N', Items.NETHERITE_INGOT);
            b.define('I', Items.IRON_BLOCK);
            b.define('A', ItemInit.ABOMINABLE_ENERGY.get());
        });

        shaped(out, "talisman_base_ancient", ItemInit.TALISMAN_BASE_ANCIENT.get(), b -> {
            b.pattern("A#A");
            b.pattern("#N#");
            b.pattern("A#A");
            b.define('#', ItemInit.TALISMAN_BASE_GODLY.get());
            b.define('N', Items.AMETHYST_BLOCK);
            b.define('A', ItemInit.ABOMINABLE_ENERGY.get());
        });

        shaped(out, "talisman_base_epic", ItemInit.TALISMAN_BASE_EPIC.get(), b -> {
            b.pattern("IAI");
            b.pattern("#N#");
            b.pattern("IAI");
            b.define('#', ItemInit.TALISMAN_BASE_ADVANCED.get());
            b.define('N', Items.NETHERITE_BLOCK);
            b.define('I', Items.GOLD_BLOCK);
            b.define('A', ItemInit.ABOMINABLE_ENERGY.get());
        });

        shaped(out, "talisman_base_godly", ItemInit.TALISMAN_BASE_GODLY.get(), b -> {
            b.pattern("AAA");
            b.pattern("#N#");
            b.pattern("AAA");
            b.define('#', ItemInit.TALISMAN_BASE_ULTIMATE.get());
            b.define('N', Items.OBSIDIAN);
            b.define('A', ItemInit.ABOMINABLE_ENERGY.get());
        });

        shaped(out, "talisman_base_legendary", ItemInit.TALISMAN_BASE_LEGENDARY.get(), b -> {
            b.pattern("NAN");
            b.pattern("#I#");
            b.pattern("NAN");
            b.define('#', ItemInit.TALISMAN_BASE_EPIC.get());
            b.define('N', Items.NETHERITE_BLOCK);
            b.define('I', Items.DIAMOND_BLOCK);
            b.define('A', ItemInit.ABOMINABLE_ENERGY.get());
        });

        shaped(out, "talisman_base_normal", ItemInit.TALISMAN_BASE_NORMAL.get(), b -> {
            b.pattern("ABD");
            b.pattern("N#N");
            b.pattern("DHA");
            b.define('#', Items.TOTEM_OF_UNDYING);
            b.define('A', Items.GOLDEN_APPLE);
            b.define('B', Items.GLOW_BERRIES);
            b.define('D', Items.GOLDEN_CARROT);
            b.define('H', Items.HONEY_BOTTLE);
            b.define('N', Items.GOLD_NUGGET);
        });

        shaped(out, "talisman_base_ultimate", ItemInit.TALISMAN_BASE_ULTIMATE.get(), b -> {
            b.pattern("AEA");
            b.pattern("#N#");
            b.pattern("AEA");
            b.define('#', ItemInit.TALISMAN_BASE_LEGENDARY.get());
            b.define('N', Items.NETHERITE_BLOCK);
            b.define('E', Items.END_CRYSTAL);
            b.define('A', ItemInit.ABOMINABLE_ENERGY.get());
        });

        shaped(out, "thorny_talisman", ItemInit.THORNY_TALISMAN.get(), b -> {
            b.pattern("ABC");
            b.pattern("BEB");
            b.pattern("DBF");
            b.define('E', ItemInit.TALISMAN_BASE_NORMAL.get());
            b.define('A', Items.OAK_LEAVES);
            b.define('B', Items.SWEET_BERRIES);
            b.define('D', Items.SPRUCE_LEAVES);
            b.define('C', Items.BIRCH_LEAVES);
            b.define('F', Items.JUNGLE_LEAVES);
        });

        shaped(out, "true_sun_talisman", ItemInit.TRUE_SUN_TALISMAN.get(), b -> {
            b.pattern("ABA");
            b.pattern("C#D");
            b.pattern("AEA");
            b.define('A', Items.GLASS);
            b.define('B', Items.GHAST_TEAR);
            b.define('C', Items.QUARTZ);
            b.define('D', Items.FEATHER);
            b.define('E', ItemInit.SUN_TALISMAN.get());
            b.define('#', ItemInit.TALISMAN_BASE_EPIC.get());
        });

        shaped(out, "undying_talisman", ItemInit.UNDYING_TALISMAN.get(), b -> {
            b.pattern("ADA");
            b.pattern("E#B");
            b.pattern("ACA");
            b.define('#', ItemInit.TALISMAN_BASE_ADVANCED.get());
            b.define('A', Items.TOTEM_OF_UNDYING);
            b.define('B', Items.CROSSBOW);
            b.define('C', Items.EMERALD_BLOCK);
            b.define('D', Items.BLACK_BANNER);
            b.define('E', Items.ENDER_EYE);
        });

        shaped(out, "vampiric_talisman", ItemInit.VAMPIRIC_TALISMAN.get(), b -> {
            b.pattern("ACA");
            b.pattern("B#E");
            b.pattern("ADA");
            b.define('#', ItemInit.TALISMAN_BASE_ADVANCED.get());
            b.define('A', Items.REDSTONE_BLOCK);
            b.define('B', Items.CRYING_OBSIDIAN);
            b.define('C', Items.GHAST_TEAR);
            b.define('D', Items.OBSERVER);
            b.define('E', Items.OBSIDIAN);
        });

        shaped(out, "vindicator_talisman", ItemInit.VINDICATOR_TALISMAN.get(), b -> {
            b.pattern("AGB");
            b.pattern("E#E");
            b.pattern("BGA");
            b.define('#', ItemInit.TALISMAN_BASE_NORMAL.get());
            b.define('A', Items.DARK_OAK_LOG);
            b.define('B', Items.EMERALD_BLOCK);
            b.define('E', Items.NETHERITE_AXE);
            b.define('G', Items.DIAMOND_AXE);
        });

        shaped(out, "void_talisman", ItemInit.VOID_TALISMAN.get(), b -> {
            b.pattern("ABC");
            b.pattern("E#E");
            b.pattern("DBF");
            b.define('#', ItemInit.TALISMAN_BASE_NORMAL.get());
            b.define('A', Items.SCULK);
            b.define('B', Items.ECHO_SHARD);
            b.define('D', Items.SCULK_CATALYST);
            b.define('C', Items.SCULK_SHRIEKER);
            b.define('E', Items.ELYTRA);
            b.define('F', Items.SCULK_SENSOR);
        });

        shaped(out, "water_talisman", ItemInit.WATER_TALISMAN.get(), b -> {
            b.pattern("ABC");
            b.pattern("D#E");
            b.pattern("FGH");
            b.define('#', ItemInit.TALISMAN_BASE_ADVANCED.get());
            b.define('A', Items.GRAVEL);
            b.define('B', Items.HEART_OF_THE_SEA);
            b.define('C', Items.CLAY);
            b.define('D', Items.PRISMARINE_SHARD);
            b.define('E', Items.LILY_PAD);
            b.define('F', Items.SAND);
            b.define('G', Items.NAUTILUS_SHELL);
            b.define('H', Items.GRAVEL);
        });

        shaped(out, "weakened_talisman", ItemInit.WEAKENED_TALISMAN.get(), b -> {
            b.pattern("VPV");
            b.pattern("GFG");
            b.pattern("VBV");
            b.define('P', Items.PHANTOM_MEMBRANE);
            b.define('F', ItemInit.TALISMAN_BASE_EPIC.get());
            b.define('V', ItemInit.WEAKENED_VIAL.get());
            b.define('G', Items.GHAST_TEAR);
            b.define('B', Items.BONE);
        });

        shaped(out, "witch_talisman", ItemInit.WITCH_TALISMAN.get(), b -> {
            b.pattern("ACA");
            b.pattern("B#E");
            b.pattern("ADA");
            b.define('#', ItemInit.TALISMAN_BASE_EPIC.get());
            b.define('A', Items.FERMENTED_SPIDER_EYE);
            b.define('B', Items.GLOWSTONE_DUST);
            b.define('C', Items.EXPERIENCE_BOTTLE);
            b.define('D', Items.POISONOUS_POTATO);
            b.define('E', Items.GUNPOWDER);
        });

        shaped(out, "withered_talisman", ItemInit.WITHERED_TALISMAN.get(), b -> {
            b.pattern("VSV");
            b.pattern("WFW");
            b.pattern("VRV");
            b.define('W', Items.WITHER_SKELETON_SKULL);
            b.define('F', ItemInit.TALISMAN_BASE_EPIC.get());
            b.define('V', ItemInit.WITHERED_VIAL.get());
            b.define('S', Items.SOUL_SAND);
            b.define('R', Items.WITHER_ROSE);
        });

        shaped(out, "wither_talisman", ItemInit.WITHER_TALISMAN.get(), b -> {
            b.pattern("ACA");
            b.pattern("B#B");
            b.pattern("ADA");
            b.define('#', ItemInit.TALISMAN_BASE_ADVANCED.get());
            b.define('A', Items.WITHER_SKELETON_SKULL);
            b.define('B', Items.NETHER_STAR);
            b.define('C', Items.WITHER_ROSE);
            b.define('D', Items.WITHER_ROSE);
        });

        shaped(out, "zombie_talisman", ItemInit.ZOMBIE_TALISMAN.get(), b -> {
            b.pattern("ADA");
            b.pattern("E#B");
            b.pattern("ACA");
            b.define('#', ItemInit.TALISMAN_BASE_NORMAL.get());
            b.define('A', Items.ZOMBIE_HEAD);
            b.define('B', Items.POTATO);
            b.define('C', Items.ROTTEN_FLESH);
            b.define('D', Items.IRON_INGOT);
            b.define('E', Items.CARROT);
        });

    }
    protected void buildWings(RecipeOutput out) {
        shaped(out, "ancient_wings_elytra", ItemInit.ANCIENT_WINGS_ELYTRA.get(), b -> {
            b.pattern("GDG");
            b.pattern("DED");
            b.pattern("GDG");
            b.define('G', Items.ECHO_SHARD);
            b.define('D', Items.SCULK_VEIN);
            b.define('E', Items.ELYTRA);
        });

        shaped(out, "astral_wings_elytra", ItemInit.ASTRAL_WINGS_ELYTRA.get(), b -> {
                    b.pattern("SMS");
                    b.pattern("MEM");
                    b.pattern("SMS");
                    b.define('S', Items.SPORE_BLOSSOM);
                    b.define('M', Items.NETHER_STAR);
                    b.define('E', Items.ELYTRA);
        });

        shaped(out, "blazing_wings_elytra", ItemInit.BLAZING_WINGS_ELYTRA.get(), b -> {
            b.pattern("MRM");
            b.pattern("RER");
            b.pattern("MRM");
            b.define('M', Items.MAGMA_CREAM);
            b.define('R', Items.BLAZE_ROD);
            b.define('E', Items.ELYTRA);
        });

            shaped(out, "dragon_wings_elytra", ItemInit.DRAGON_WINGS_ELYTRA.get(), b -> {
                b.pattern("DBD");
                b.pattern("BEB");
                b.pattern("DBD");
                b.define('D', Items.DRAGON_EGG);
                b.define('B', Items.BLAZE_POWDER);
                b.define('E', Items.ELYTRA);
            });

            shaped(out, "dusty_wings_elytra", ItemInit.DUSTY_WINGS_ELYTRA.get(), b -> {
                b.pattern("RRR");
                b.pattern("RER");
                b.pattern("RRR");
                b.define('R', Items.REDSTONE_BLOCK);
                b.define('E', Items.ELYTRA);
            });

            shaped(out, "enchanted_wings_elytra", ItemInit.ENCHANTED_WINGS_ELYTRA.get(), b -> {
                b.pattern("DED");
                b.pattern("ESE");
                b.pattern("DED");
                b.define('D', Items.ENCHANTING_TABLE);
                b.define('E', Items.BOOKSHELF);
                b.define('S', Items.ELYTRA);
            });

            shaped(out, "ender_wings_elytra", ItemInit.ENDER_WINGS_ELYTRA.get(), b -> {
                b.pattern("OCO");
                b.pattern("CEC");
                b.pattern("OCO");
                b.define('O', Items.OBSIDIAN);
                b.define('C', Items.CHORUS_FRUIT);
                b.define('E', Items.ELYTRA);
            });

            shaped(out, "forest_wings_elytra", ItemInit.FOREST_WINGS_ELYTRA.get(), b -> {
                b.pattern("OFO");
                b.pattern("FEF");
                b.pattern("OFO");
                b.define('O', Items.OAK_LEAVES);
                b.define('F', Items.POPPY);
                b.define('E', Items.ELYTRA);
            });

            shaped(out, "frozen_wings_elytra", ItemInit.FROZEN_WINGS_ELYTRA.get(), b -> {
                b.pattern("SIS");
                b.pattern("IEI");
                b.pattern("SIS");
                b.define('S', Items.SNOW_BLOCK);
                b.define('I', Items.ICE);
                b.define('E', Items.ELYTRA);
            });

            shaped(out, "ghastly_wings_elytra", ItemInit.GHASTLY_WINGS_ELYTRA.get(), b -> {
                b.pattern("GRG");
                b.pattern("RER");
                b.pattern("GRG");
                b.define('G', Items.GHAST_TEAR);
                b.define('R', Items.QUARTZ);
                b.define('E', Items.ELYTRA);
            });

            shaped(out, "oceans_fins_elytra", ItemInit.OCEANS_FINS_ELYTRA.get(), b -> {
                b.pattern("PLP");
                b.pattern("LEL");
                b.pattern("PLP");
                b.define('P', Items.PRISMARINE_SHARD);
                b.define('L', Items.SEA_LANTERN);
                b.define('E', Items.ELYTRA);
            });

            shaped(out, "phantom_wings_elytra", ItemInit.PHANTOM_WINGS_ELYTRA.get(), b -> {
                b.pattern("PBP");
                b.pattern("BEB");
                b.pattern("PBP");
                b.define('P', Items.PHANTOM_MEMBRANE);
                b.define('B', Items.FEATHER);
                b.define('E', Items.ELYTRA);
            });

            shaped(out, "volcanic_ash_elytra", ItemInit.VOLCANIC_ASH_ELYTRA.get(), b -> {
                b.pattern("MLM");
                b.pattern("LEL");
                b.pattern("MLM");
                b.define('M', Items.NETHERRACK);
                b.define('L', Items.FIRE_CHARGE);
                b.define('E', Items.ELYTRA);
            });

            shaped(out, "wither_wings_elytra", ItemInit.WITHER_WINGS_ELYTRA.get(), b -> {
                b.pattern("NWN");
                b.pattern("WEW");
                b.pattern("NWN");
                b.define('N', Items.NETHER_STAR);
                b.define('W', Items.WITHER_SKELETON_SKULL);
                b.define('E', Items.ELYTRA);
            });
    }
    protected void buildEssences(RecipeOutput out) {
        shaped(out, "essence_of_the_arcana", ItemInit.ESSENCE_OF_THE_ARCANA.get(), b -> {
            b.pattern("A =");
            b.pattern(" C ");
            b.pattern(" DB");
            b.define('=', ItemInit.ABOMINABLE_ENERGY.get());
            b.define('A', ItemsRegistry.FIRE_ESSENCE);
            b.define('B', ItemsRegistry.ABJURATION_ESSENCE);
            b.define('C', glyph("ars_nouveau:glyph_hex"));
            b.define('D', ItemsRegistry.AMULET_OF_MANA_BOOST);
        });

        shaped(out, "essence_of_the_behemoth", ItemInit.ESSENCE_OF_THE_BEHEMOTH.get(), b -> {
            b.pattern("  A");
            b.pattern(" =B");
            b.pattern("C  ");
            b.define('=', ItemInit.ABOMINABLE_ENERGY.get());
            b.define('A', ItemInit.FROZEN_SHIELD.get());
            b.define('B', ItemInit.HERO_SHIELD.get());
            b.define('C', ItemInit.OBSIDIAN_HORSESHOE.get());
        });

        shaped(out, "essence_of_the_comet", ItemInit.ESSENCE_OF_THE_COMET.get(), b -> {
            b.pattern("A= ");
            b.pattern(" B ");
            b.pattern("  C");
            b.define('=', ItemInit.ABOMINABLE_ENERGY.get());
            b.define('A', ItemInit.BLESSED_APPLE.get());
            b.define('B', ItemInit.BRAIN_SCRAMBLER.get());
            b.define('C', ItemInit.MECHANICAL_CART.get());
        });

        shaped(out, "essence_of_the_eldritch", ItemInit.ESSENCE_OF_THE_ELDRITCH.get(), b -> {
            b.pattern("AD ");
            b.pattern("=C ");
            b.pattern("  B");
            b.define('=', ItemInit.ABOMINABLE_ENERGY.get());
            b.define('A', ItemsRegistry.AIR_ESSENCE);
            b.define('B', ItemsRegistry.EARTH_ESSENCE);
            b.define('C', glyph("ars_nouveau:glyph_wither"));
            b.define('D', ItemsRegistry.RING_OF_GREATER_DISCOUNT);
        });

        shaped(out, "essence_of_the_hex", ItemInit.ESSENCE_OF_THE_HEX.get(), b -> {
            b.pattern(" E ");
            b.pattern("=D=");
            b.pattern("BAC");
            b.define('=', ItemInit.ABOMINABLE_ENERGY.get());
            b.define('A', ItemRegistry.INK_LEGENDARY.get());
            b.define('B', ItemRegistry.PROTECTION_UPGRADE_ORB.get());
            b.define('C', ItemRegistry.COOLDOWN_UPGRADE_ORB.get());
            b.define('D', ItemRegistry.MANA_UPGRADE_ORB.get());
            b.define('E', ItemRegistry.DRAGONSKIN_SPELL_BOOK.get());
        });

        shaped(out, "essence_of_the_highwind", ItemInit.ESSENCE_OF_THE_HIGHWIND.get(), b -> {
            b.pattern(" =A");
            b.pattern("B  ");
            b.pattern(" C ");
            b.define('=', ItemInit.ABOMINABLE_ENERGY.get());
            b.define('A', ItemInit.ENCHANTED_WINGS_ELYTRA.get());
            b.define('B', ItemInit.PHANTOM_WINGS_ELYTRA.get());
            b.define('C', ItemInit.OCEANS_FINS_ELYTRA.get());
        });

        shaped(out, "essence_of_the_juggernaut", ItemInit.ESSENCE_OF_THE_JUGGERNAUT.get(), b -> {
            b.pattern("= A");
            b.pattern(" B ");
            b.pattern("C =");
            b.define('=', ItemInit.ABOMINABLE_ENERGY.get());
            b.define('A', ItemInit.BEE_CLOAK.get());
            b.define('B', ItemInit.STAR_VEIL.get());
            b.define('C', ItemInit.POCKET_MIRROR.get());
        });

        shaped(out, "essence_of_the_kinetic", ItemInit.ESSENCE_OF_THE_KINETIC.get(), b -> {
            b.pattern("A  ");
            b.pattern("= B");
            b.pattern("  C");
            b.define('=', ItemInit.ABOMINABLE_ENERGY.get());
            b.define('A', ItemInit.AEOLUS_BOOTS.get());
            b.define('B', ItemInit.REINDEER_BELLS.get());
            b.define('C', ItemInit.ANCIENT_HORN.get());
        });

        shaped(out, "essence_of_the_mage", ItemInit.ESSENCE_OF_THE_MAGE.get(), b -> {
            b.pattern("A  ");
            b.pattern(" C ");
            b.pattern("= B");
            b.define('=', ItemInit.ABOMINABLE_ENERGY.get());
            b.define('A', ItemsRegistry.CONJURATION_ESSENCE);
            b.define('B', ItemsRegistry.MANIPULATION_ESSENCE);
            b.define('C', glyph("ars_nouveau:glyph_sense_magic"));
        });

        shaped(out, "essence_of_the_occult", ItemInit.ESSENCE_OF_THE_OCCULT.get(), b -> {
            b.pattern("EBC");
            b.pattern("=D ");
            b.pattern("AGF");
            b.define('=', ItemInit.ABOMINABLE_ENERGY.get());
            b.define('A', ItemRegistry.FIRE_UPGRADE_ORB.get());
            b.define('B', ItemRegistry.INK_LEGENDARY.get());
            b.define('C', ItemRegistry.ICE_UPGRADE_ORB.get());
            b.define('D', ItemRegistry.POISONWARD_RING.get());
            b.define('E', ItemRegistry.FROSTWARD_RING.get());
            b.define('F', ItemRegistry.FIREWARD_RING.get());
            b.define('G', ItemRegistry.DRUIDIC_SPELL_BOOK.get());
        });

        shaped(out, "essence_of_the_phantom", ItemInit.ESSENCE_OF_THE_PHANTOM.get(), b -> {
            b.pattern("BC ");
            b.pattern("GAD");
            b.pattern("EF=");
            b.define('=', ItemInit.ABOMINABLE_ENERGY.get());
            b.define('A', ItemRegistry.INK_LEGENDARY.get());
            b.define('B', ItemRegistry.HOLY_UPGRADE_ORB.get());
            b.define('C', ItemRegistry.ENDER_UPGRADE_ORB.get());
            b.define('D', ItemRegistry.BLOOD_UPGRADE_ORB.get());
            b.define('E', ItemRegistry.NATURE_UPGRADE_ORB.get());
            b.define('F', ItemRegistry.EVOCATION_UPGRADE_ORB.get());
            b.define('G', ItemRegistry.CURSED_DOLL_SPELLBOOK.get());
        });

        shaped(out, "essence_of_the_sentinel", ItemInit.ESSENCE_OF_THE_SENTINEL.get(), b -> {
            b.pattern("A  ");
            b.pattern("=B ");
            b.pattern(" C ");
            b.define('=', ItemInit.ABOMINABLE_ENERGY.get());
            b.define('A', ItemInit.BRAIN_OF_CONFUSION.get());
            b.define('B', ItemInit.CHARM_OF_MYTHS.get());
            b.define('C', ItemInit.HAND_WARMER.get());
        });

        shaped(out, "essence_of_the_skyrunner", ItemInit.ESSENCE_OF_THE_SKYRUNNER.get(), b -> {
            b.pattern("  A");
            b.pattern(" =B");
            b.pattern("C  ");
            b.define('=', ItemInit.ABOMINABLE_ENERGY.get());
            b.define('A', ItemInit.GHASTLY_WINGS_ELYTRA.get());
            b.define('B', ItemInit.FROZEN_WINGS_ELYTRA.get());
            b.define('C', ItemInit.ASTRAL_WINGS_ELYTRA.get());
        });

        shaped(out, "essence_of_the_soarer", ItemInit.ESSENCE_OF_THE_SOARER.get(), b -> {
            b.pattern("A  ");
            b.pattern("= B");
            b.pattern("  C");
            b.define('=', ItemInit.ABOMINABLE_ENERGY.get());
            b.define('A', ItemInit.BLAZING_WINGS_ELYTRA.get());
            b.define('B', ItemInit.ENDER_WINGS_ELYTRA.get());
            b.define('C', ItemInit.DRAGON_WINGS_ELYTRA.get());
        });

        shaped(out, "essence_of_the_thaumaturge", ItemInit.ESSENCE_OF_THE_THAUMATURGE.get(), b -> {
            b.pattern("A  ");
            b.pattern(" C ");
            b.pattern(" =D");
            b.define('=', ItemInit.ABOMINABLE_ENERGY.get());
            b.define('A', ItemsRegistry.WATER_ESSENCE);
            b.define('C', glyph("ars_nouveau:glyph_linger"));
            b.define('D', ItemsRegistry.AMULET_OF_MANA_REGEN);
        });

        shaped(out, "essence_of_the_titan", ItemInit.ESSENCE_OF_THE_TITAN.get(), b -> {
            b.pattern("A =");
            b.pattern(" B ");
            b.pattern("= C");
            b.define('=', ItemInit.ABOMINABLE_ENERGY.get());
            b.define('A', ItemInit.ANKH_SHIELD.get());
            b.define('B', ItemInit.WORM_SCARF.get());
            b.define('C', ItemInit.SHINY_STONE.get());
        });

        shaped(out, "essence_of_the_totem", ItemInit.ESSENCE_OF_THE_TOTEM.get(), b -> {
            b.pattern("BC=");
            b.pattern("DAF");
            b.pattern(" EG");
            b.define('=', ItemInit.ABOMINABLE_ENERGY.get());
            b.define('A', ItemRegistry.LIGHTNING_UPGRADE_ORB.get());
            b.define('B', ItemRegistry.INK_LEGENDARY.get());
            b.define('C', ItemRegistry.MANA_RING.get());
            b.define('D', ItemRegistry.COOLDOWN_RING.get());
            b.define('E', ItemRegistry.CAST_TIME_RING.get());
            b.define('F', ItemRegistry.AMETHYST_RESONANCE_NECKLACE.get());
            b.define('G', ItemRegistry.ICE_SPELL_BOOK.get());
        });

        shaped(out, "essence_of_the_velocity", ItemInit.ESSENCE_OF_THE_VELOCITY.get(), b -> {
            b.pattern("= A");
            b.pattern(" B ");
            b.pattern("C =");
            b.define('=', ItemInit.ABOMINABLE_ENERGY.get());
            b.define('A', ItemInit.MASTER_NINJA_GEAR.get());
            b.define('B', ItemInit.BUNDLE_OF_HORSESHOE_BALLOONS.get());
            b.define('C', ItemInit.FLYING_CARPET.get());
        });

        shaped(out, "essence_of_the_whiplash", ItemInit.ESSENCE_OF_THE_WHIPLASH.get(), b -> {
            b.pattern("  A");
            b.pattern(" =B");
            b.pattern("C  ");
            b.define('=', ItemInit.ABOMINABLE_ENERGY.get());
            b.define('A', ItemInit.AMBER_HORSESHOE_BALLOON.get());
            b.define('B', ItemInit.SWEETHEART_NECKLACE.get());
            b.define('C', ItemInit.SHIELD_OF_CTHULHU.get());
        });

        shaped(out, "essence_of_the_zephyr", ItemInit.ESSENCE_OF_THE_ZEPHYR.get(), b -> {
            b.pattern("A= ");
            b.pattern(" B ");
            b.pattern("  C");
            b.define('=', ItemInit.ABOMINABLE_ENERGY.get());
            b.define('A', ItemInit.ANCIENT_WINGS_ELYTRA.get());
            b.define('B', ItemInit.VOLCANIC_ASH_ELYTRA.get());
            b.define('C', ItemInit.DUSTY_WINGS_ELYTRA.get());
        });

        shaped(out, "essence_of_the_longshot", ItemInit.ESSENCE_OF_THE_LONGSHOT.get(), b -> {
            b.pattern("A= ");
            b.pattern(" B ");
            b.pattern("  C");
            b.define('=', ItemInit.ABOMINABLE_ENERGY.get());
            b.define('A', ItemInit.SIMPLE_BOW.get());
            b.define('B', ItemInit.DAEDALUS_STORMBOW.get());
            b.define('C', ItemInit.EVENTIDE_BOW.get());
        });

        shaped(out, "essence_of_the_deadeye", ItemInit.ESSENCE_OF_THE_DEADEYE.get(), b -> {
            b.pattern(" A ");
            b.pattern(" = ");
            b.pattern("B C");
            b.define('=', ItemInit.ABOMINABLE_ENERGY.get());
            b.define('A', ItemInit.TENDON_BOW.get());
            b.define('B', ItemInit.HELLWING_BOW.get());
            b.define('C', ItemInit.ICE_BOW.get());
        });

        shaped(out, "essence_of_the_hawk", ItemInit.ESSENCE_OF_THE_HAWK.get(), b -> {
            b.pattern(" =A");
            b.pattern("B  ");
            b.pattern("C  ");
            b.define('=', ItemInit.ABOMINABLE_ENERGY.get());
            b.define('A', ItemInit.MECHANICS_BOW.get());
            b.define('B', ItemInit.BLOOD_RAIN_BOW.get());
            b.define('C', ItemInit.DEMON_BOW.get());
        });

        shaped(out, "essence_of_the_marksman", ItemInit.ESSENCE_OF_THE_MARKSMAN.get(), b -> {
            b.pattern("A  ");
            b.pattern("= B");
            b.pattern("  C");
            b.define('=', ItemInit.ABOMINABLE_ENERGY.get());
            b.define('A', ItemInit.TSUNAMI_BOW.get());
            b.define('B', ItemInit.VORTEX_BOW.get());
            b.define('C', ItemInit.AERIAL_BANE_BOW.get());
        });

        shaped(out, "essence_of_the_leviathan", ItemInit.ESSENCE_OF_THE_LEVIATHAN.get(), b -> {
            b.pattern("A= ");
            b.pattern(" B ");
            b.pattern("  C");
            b.define('=', ItemInit.ABOMINABLE_ENERGY.get());
            b.define('A', ItemInit.SIMPLE_FISHING_ROD.get());
            b.define('B', ItemInit.FIBERGLASS_FISHING_ROD.get());
            b.define('C', ItemInit.FLESHCATCHER_FISHING_ROD.get());
        });

        shaped(out, "essence_of_the_angler", ItemInit.ESSENCE_OF_THE_ANGLER.get(), b -> {
            b.pattern("  A");
            b.pattern(" =B");
            b.pattern("C  ");
            b.define('=', ItemInit.ABOMINABLE_ENERGY.get());
            b.define('A', ItemInit.CHUM_CASTER_FISHING_ROD.get());
            b.define('B', ItemInit.REINFORCED_FISHING_ROD.get());
            b.define('C', ItemInit.FISHER_OF_SOULS_FISHING_ROD.get());
        });

        shaped(out, "essence_of_the_abyss", ItemInit.ESSENCE_OF_THE_ABYSS.get(), b -> {
            b.pattern(" =A");
            b.pattern("B  ");
            b.pattern(" C ");
            b.define('=', ItemInit.ABOMINABLE_ENERGY.get());
            b.define('A', ItemInit.MECHANICS_FISHING_ROD.get());
            b.define('B', ItemInit.SCARAB_FISHING_ROD.get());
            b.define('C', ItemInit.HOTLINE_FISHING_ROD.get());
        });

        shaped(out, "essence_of_the_brine", ItemInit.ESSENCE_OF_THE_BRINE.get(), b -> {
            b.pattern("A  ");
            b.pattern("= B");
            b.pattern("  C");
            b.define('=', ItemInit.ABOMINABLE_ENERGY.get());
            b.define('A', ItemInit.GOLDEN_FISHING_ROD.get());
            b.define('B', ItemInit.STYX_CASTER_FISHING_ROD.get());
            b.define('C', ItemInit.SITTING_DUCKS_FISHING_ROD.get());
        });

        shaped(out, "essence_of_the_artisan", ItemInit.ESSENCE_OF_THE_ARTISAN.get(), b -> {
            b.pattern("PBA");
            b.pattern("H*S");
            b.pattern("=N=");
            b.define('=', ItemInit.ABOMINABLE_ENERGY.get());
            b.define('*', ItemInit.ARCHITECT_TALISMAN.get());
            b.define('A', Items.DIAMOND_AXE);
            b.define('B', Items.PISTON);
            b.define('P', Items.DIAMOND_PICKAXE);
            b.define('H', Items.DIAMOND_HOE);
            b.define('S', Items.DIAMOND_SHOVEL);
            b.define('N', Items.NETHERITE_INGOT);
        });

        shaped(out, "essence_of_the_mason", ItemInit.ESSENCE_OF_THE_MASON.get(), b -> {
            b.pattern("PBA");
            b.pattern("H*S");
            b.pattern("=N=");
            b.define('=', ItemInit.ABOMINABLE_ENERGY.get());
            b.define('*', ItemInit.ARCHITECT_TALISMAN.get());
            b.define('A', Items.DIAMOND_AXE);
            b.define('B', Items.BEACON);
            b.define('P', Items.DIAMOND_PICKAXE);
            b.define('H', Items.DIAMOND_HOE);
            b.define('S', Items.DIAMOND_SHOVEL);
            b.define('N', Items.NETHERITE_INGOT);
        });

        shaped(out, "essence_of_the_terraformer", ItemInit.ESSENCE_OF_THE_TERRAFORMER.get(), b -> {
            b.pattern("PBA");
            b.pattern("H*S");
            b.pattern("=N=");
            b.define('=', ItemInit.ABOMINABLE_ENERGY.get());
            b.define('*', ItemInit.ARCHITECT_TALISMAN.get());
            b.define('A', Items.DIAMOND_AXE);
            b.define('B', Items.STICKY_PISTON);
            b.define('P', Items.DIAMOND_PICKAXE);
            b.define('H', Items.DIAMOND_HOE);
            b.define('S', Items.DIAMOND_SHOVEL);
            b.define('N', Items.NETHERITE_INGOT);
        });

        shaped(out, "essence_of_the_sculptor", ItemInit.ESSENCE_OF_THE_SCULPTOR.get(), b -> {
            b.pattern("PBA");
            b.pattern("H*S");
            b.pattern("=N=");
            b.define('=', ItemInit.ABOMINABLE_ENERGY.get());
            b.define('*', ItemInit.ARCHITECT_TALISMAN.get());
            b.define('A', Items.DIAMOND_AXE);
            b.define('B', Items.SLIME_BLOCK);
            b.define('P', Items.DIAMOND_PICKAXE);
            b.define('H', Items.DIAMOND_HOE);
            b.define('S', Items.DIAMOND_SHOVEL);
            b.define('N', Items.NETHERITE_INGOT);
        });

//        shaped(out, "essence_of_the_", ItemInit.ESSENCE_OF_THE_.get(), b -> {
//            b.pattern("");
//            b.pattern("");
//            b.pattern("");
//            b.define('=', ItemInit.ABOMINABLE_ENERGY.get());
//            b.define('A', ItemInit.);
//            b.define('B', ItemInit.);
//            b.define('C', ItemInit.);
//            b.define('D', ItemInit.);
//        });

    }
    protected void buildForces(RecipeOutput out) {
        shaped(out, "force_of_environment", ItemInit.FORCE_OF_ENVIRONMENT.get(), b -> {
                    b.pattern("ABC");
                    b.pattern("D#E");
                    b.pattern("FGH");
                    b.define('#', ItemInit.TALISMAN_BASE_LEGENDARY.get());
                    b.define('A', ItemInit.STORM_TALISMAN.get());
                    b.define('B', ItemInit.SUN_TALISMAN.get());
                    b.define('C', ItemInit.SNOWY_TALISMAN.get());
                    b.define('D', ItemInit.NIGHT_TALISMAN.get());
                    b.define('E', ItemInit.RAIN_TALISMAN.get());
                    b.define('F', ItemInit.FULL_MOON_TALISMAN.get());
                    b.define('G', ItemInit.DAY_TALISMAN.get());
                    b.define('H', ItemInit.TRUE_SUN_TALISMAN.get());
                });

        shaped(out, "force_of_explorer", ItemInit.FORCE_OF_EXPLORER.get(), b -> {
            b.pattern("B C");
            b.pattern("D#E");
            b.pattern("AFA");
            b.define('#', ItemInit.TALISMAN_BASE_EPIC.get());
            b.define('A', ItemInit.ABOMINABLE_ENERGY.get());
            b.define('B', ItemInit.ENDERMAN_TALISMAN.get());
            b.define('C', ItemInit.SPECTRAL_TALISMAN.get());
            b.define('D', ItemInit.ARCTIC_TALISMAN.get());
            b.define('E', ItemInit.ARCHITECT_TALISMAN.get());
            b.define('F', ItemInit.GLOWSTONE_TALISMAN.get());
        });

            shaped(out, "force_of_mystic", ItemInit.FORCE_OF_MYSTIC.get(), b -> {
                b.pattern("ACD");
                b.pattern("B#E");
                b.pattern("=F=");
                b.define('#', ItemInit.TALISMAN_BASE_LEGENDARY.get());
                b.define('=', ItemInit.ABOMINABLE_ENERGY.get());
                b.define('A', ItemInit.WITCH_TALISMAN.get());
                b.define('B', ItemInit.ENCHANTING_TALISMAN.get());
                b.define('C', ItemInit.VAMPIRIC_TALISMAN.get());
                b.define('D', ItemInit.LIBRARIAN_TALISMAN.get());
                b.define('E', ItemInit.UNDYING_TALISMAN.get());
                b.define('F', ItemInit.SHULKER_TALISMAN.get());
            });

            shaped(out, "force_of_nature", ItemInit.FORCE_OF_NATURE.get(), b -> {
                b.pattern("AB ");
                b.pattern("C#D");
                b.pattern(" EA");
                b.define('#', ItemInit.TALISMAN_BASE_EPIC.get());
                b.define('A', ItemInit.ABOMINABLE_ENERGY.get());
                b.define('B', ItemInit.AIR_TALISMAN.get());
                b.define('C', ItemInit.WATER_TALISMAN.get());
                b.define('D', ItemInit.FIRE_TALISMAN.get());
                b.define('E', ItemInit.EARTH_TALISMAN.get());
            });

            shaped(out, "force_of_negative", ItemInit.FORCE_OF_NEGATIVE.get(), b -> {
                b.pattern("ABC");
                b.pattern("D#E");
                b.pattern("FGH");
                b.define('#', ItemInit.TALISMAN_BASE_LEGENDARY.get());
                b.define('A', ItemInit.FATIGUED_TALISMAN.get());
                b.define('B', ItemInit.WITHERED_TALISMAN.get());
                b.define('C', ItemInit.NAUSEATED_TALISMAN.get());
                b.define('D', ItemInit.POISONED_TALISMAN.get());
                b.define('E', ItemInit.WEAKENED_TALISMAN.get());
                b.define('F', ItemInit.FIRED_TALISMAN.get());
                b.define('G', ItemInit.SLOWED_TALISMAN.get());
                b.define('H', ItemInit.BLINDED_TALISMAN.get());
            });

            shaped(out, "force_of_overworld", ItemInit.FORCE_OF_OVERWORLD.get(), b -> {
                b.pattern("BAE");
                b.pattern("C#F");
                b.pattern("DAG");
                b.define('#', ItemInit.TALISMAN_BASE_EPIC.get());
                b.define('A', ItemInit.ABOMINABLE_ENERGY.get());
                b.define('B', ItemInit.LAPIS_TALISMAN.get());
                b.define('C', ItemInit.REDSTONE_TALISMAN.get());
                b.define('D', ItemInit.AMETHYST_TALISMAN.get());
                b.define('E', ItemInit.EMERALD_TALISMAN.get());
                b.define('F', ItemInit.GOLD_TALISMAN.get());
                b.define('G', ItemInit.DIAMOND_TALISMAN.get());
            });

            shaped(out, "force_of_rejectors", ItemInit.FORCE_OF_REJECTORS.get(), b -> {
                b.pattern("ABC");
                b.pattern("DGE");
                b.pattern("F#H");
                b.define('#', ItemInit.TALISMAN_BASE_LEGENDARY.get());
                b.define('A', ItemInit.VINDICATOR_TALISMAN.get());
                b.define('B', ItemInit.DRAGON_TALISMAN.get());
                b.define('C', ItemInit.WITHER_TALISMAN.get());
                b.define('D', ItemInit.SKELETON_TALISMAN.get());
                b.define('E', ItemInit.ZOMBIE_TALISMAN.get());
                b.define('F', ItemInit.CREEPER_TALISMAN.get());
                b.define('G', ItemInit.BLAZE_TALISMAN.get());
                b.define('H', ItemInit.GHAST_TALISMAN.get());
            });

            shaped(out, "force_of_warrior", ItemInit.FORCE_OF_WARRIOR.get(), b -> {
                b.pattern(" CD");
                b.pattern("B#=");
                b.pattern("A=E");
                b.define('#', ItemInit.TALISMAN_BASE_LEGENDARY.get());
                b.define('=', ItemInit.ABOMINABLE_ENERGY.get());
                b.define('A', ItemInit.BATTLE_TALISMAN.get());
                b.define('B', ItemInit.VOID_TALISMAN.get());
                b.define('C', ItemInit.THORNY_TALISMAN.get());
                b.define('D', ItemInit.CACTUS_TALISMAN.get());
                b.define('E', ItemInit.IRON_GOLEM_TALISMAN.get());
            });
    }
    protected void buildItems(RecipeOutput out) {
        shapeless(out, "blinded_vial", ItemInit.BLINDED_VIAL.get(), b -> {
            b.requires(Items.GLASS_BOTTLE);
            b.requires(Items.INK_SAC);
            b.requires(Items.BLACK_DYE);
        });

        shapeless(out, "fatigued_vial", ItemInit.FATIGUED_VIAL.get(), b -> {
            b.requires(Items.GLASS_BOTTLE);
            b.requires(Items.ROTTEN_FLESH);
            b.requires(Items.COAL);
        });

        shapeless(out, "fired_vial", ItemInit.FIRED_VIAL.get(), b -> {
                    b.requires(Items.GLASS_BOTTLE);
                    b.requires(Items.BLAZE_POWDER);
                    b.requires(Items.MAGMA_BLOCK);
                });
        
        shapeless(out, "guide_book", ItemInit.GUIDE_BOOK.get(), b -> {
            b.requires(Items.BOOK);
            b.requires(Items.SOUL_LANTERN);
        });

        shapeless(out, "nauseated_vial", ItemInit.NAUSEATED_VIAL.get(), b -> {
                b.requires(Items.GLASS_BOTTLE);
                b.requires(Items.PUFFERFISH);
                b.requires(Items.SPIDER_EYE);
            });

        shapeless(out, "poisoned_vial", ItemInit.POISONED_VIAL.get(), b -> {
                b.requires(Items.GLASS_BOTTLE);
                b.requires(Items.SPIDER_EYE);
                b.requires(Items.FERMENTED_SPIDER_EYE);
            });

        shapeless(out, "slowed_vial", ItemInit.SLOWED_VIAL.get(), b -> {
                b.requires(Items.GLASS_BOTTLE);
                b.requires(Items.SNOWBALL);
                b.requires(Items.SOUL_SAND);
            });

        shapeless(out, "weakened_vial", ItemInit.WEAKENED_VIAL.get(), b -> {
                b.requires(Items.GLASS_BOTTLE);
                b.requires(Items.FERMENTED_SPIDER_EYE);
                b.requires(Items.IRON_NUGGET);
            });

        shapeless(out, "withered_vial", ItemInit.WITHERED_VIAL.get(), b -> {
                b.requires(Items.GLASS_BOTTLE);
                b.requires(Items.WITHER_ROSE);
                b.requires(Items.BONE);
            });

        shaped(out, "simple_fishing_rod", ItemInit.SIMPLE_FISHING_ROD.get(), b -> {
            b.pattern("  A");
            b.pattern(" RS");
            b.pattern("B S");
            b.define('R', Items.FISHING_ROD);
            b.define('A', Items.STICK);
            b.define('B', Items.STICK);
            b.define('S', Items.STRING);
        });

        shaped(out, "fiberglass_fishing_rod", ItemInit.FIBERGLASS_FISHING_ROD.get(), b -> {
            b.pattern("  A");
            b.pattern(" RS");
            b.pattern("B S");
            b.define('R', Items.FISHING_ROD);
            b.define('A', Items.TRIPWIRE_HOOK);
            b.define('B', Items.BREEZE_ROD);
            b.define('S', Items.STRING);
        });

        shaped(out, "fleshcatcher_fishing_rod", ItemInit.FLESHCATCHER_FISHING_ROD.get(), b -> {
            b.pattern("  A");
            b.pattern(" RS");
            b.pattern("B S");
            b.define('R', Items.FISHING_ROD);
            b.define('A', Items.LEATHER);
            b.define('B', Items.BLAZE_ROD);
            b.define('S', Items.STRING);
        });

        shaped(out, "fisher_of_souls_fishing_rod", ItemInit.FISHER_OF_SOULS_FISHING_ROD.get(), b -> {
            b.pattern("  A");
            b.pattern(" RS");
            b.pattern("B S");
            b.define('R', Items.FISHING_ROD);
            b.define('A', Items.FERMENTED_SPIDER_EYE);
            b.define('B', Items.STICK);
            b.define('S', Items.STRING);
        });

        shaped(out, "reinforced_fishing_rod", ItemInit.REINFORCED_FISHING_ROD.get(), b -> {
            b.pattern("  A");
            b.pattern(" RS");
            b.pattern("B S");
            b.define('R', Items.FISHING_ROD);
            b.define('A', Items.IRON_INGOT);
            b.define('B', Items.IRON_INGOT);
            b.define('S', Items.STRING);
        });

        shaped(out, "chum_caster_fishing_rod", ItemInit.CHUM_CASTER_FISHING_ROD.get(), b -> {
            b.pattern("  A");
            b.pattern(" RS");
            b.pattern("B S");
            b.define('R', Items.FISHING_ROD);
            b.define('A', Items.FERMENTED_SPIDER_EYE);
            b.define('B', Items.BLAZE_ROD);
            b.define('S', Items.STRING);
        });

        shaped(out, "scarab_fishing_rod", ItemInit.SCARAB_FISHING_ROD.get(), b -> {
            b.pattern("  A");
            b.pattern(" RS");
            b.pattern("B S");
            b.define('R', Items.FISHING_ROD);
            b.define('A', Items.CHORUS_FLOWER);
            b.define('B', Items.CHORUS_PLANT);
            b.define('S', Items.STRING);
        });

        shaped(out, "mechanics_fishing_rod", ItemInit.MECHANICS_FISHING_ROD.get(), b -> {
            b.pattern("  A");
            b.pattern(" RS");
            b.pattern("B S");
            b.define('R', Items.FISHING_ROD);
            b.define('A', Items.LIGHTNING_ROD);
            b.define('B', Items.IRON_INGOT);
            b.define('S', Items.LEAD);
        });

        shaped(out, "hotline_fishing_rod", ItemInit.HOTLINE_FISHING_ROD.get(), b -> {
            b.pattern("  A");
            b.pattern(" RS");
            b.pattern("B S");
            b.define('R', Items.FISHING_ROD);
            b.define('A', Items.FIRE_CHARGE);
            b.define('B', Items.BLAZE_ROD);
            b.define('S', Items.STRING);
        });

        shaped(out, "sitting_ducks_fishing_rod", ItemInit.SITTING_DUCKS_FISHING_ROD.get(), b -> {
            b.pattern("  A");
            b.pattern(" RS");
            b.pattern("B S");
            b.define('R', Items.FISHING_ROD);
            b.define('A', Items.RABBIT_HIDE);
            b.define('B', Items.FLINT);
            b.define('S', Items.STRING);
        });

        shaped(out, "styx_caster_fishing_rod", ItemInit.STYX_CASTER_FISHING_ROD.get(), b -> {
            b.pattern("  A");
            b.pattern(" RS");
            b.pattern("B S");
            b.define('R', Items.FISHING_ROD);
            b.define('A', Items.BLAZE_ROD);
            b.define('B', Items.NETHERITE_INGOT);
            b.define('S', ItemInit.ABOMINABLE_ENERGY.get());
        });

        shaped(out, "golden_fishing_rod", ItemInit.GOLDEN_FISHING_ROD.get(), b -> {
            b.pattern("  A");
            b.pattern(" RS");
            b.pattern("B S");
            b.define('R', Items.FISHING_ROD);
            b.define('A', Items.GOLD_INGOT);
            b.define('B', Items.GOLD_INGOT);
            b.define('S', Items.STRING);
        });

        shaped(out, "simple_bow", ItemInit.SIMPLE_BOW.get(), b -> {
            b.pattern(" RS");
            b.pattern("FBS");
            b.pattern(" RS");
            b.define('B', Items.BOW);
            b.define('R', Items.STICK);
            b.define('F', Items.STICK);
            b.define('S', Items.STRING);
        });

        shaped(out, "eventide_bow", ItemInit.EVENTIDE_BOW.get(), b -> {
            b.pattern("ARM");
            b.pattern("FBS");
            b.pattern("ARM");
            b.define('B', Items.BOW);
            b.define('A', Items.GOLD_INGOT);
            b.define('R', Items.BREEZE_ROD);
            b.define('F', Items.BREEZE_ROD);
            b.define('M', ItemInit.ABOMINABLE_ENERGY.get());
            b.define('S', Items.STRING);
        });

        shaped(out, "daedalus_stormbow", ItemInit.DAEDALUS_STORMBOW.get(), b -> {
            b.pattern(" RM");
            b.pattern("FBS");
            b.pattern(" RM");
            b.define('B', Items.BOW);
            b.define('R', Items.IRON_INGOT);
            b.define('F', Items.BREEZE_ROD);
            b.define('M', ItemInit.ABOMINABLE_ENERGY.get());
            b.define('S', Items.STRING);
        });

        shaped(out, "hellwing_bow", ItemInit.HELLWING_BOW.get(), b -> {
            b.pattern(" RS");
            b.pattern("FBS");
            b.pattern(" RS");
            b.define('B', Items.BOW);
            b.define('R', Items.STICK);
            b.define('F', Items.STICK);
            b.define('S', Items.BLAZE_POWDER);
        });

        shaped(out, "tsunami_bow", ItemInit.TSUNAMI_BOW.get(), b -> {
            b.pattern(" RS");
            b.pattern("FBS");
            b.pattern(" RS");
            b.define('B', Items.BOW);
            b.define('R', Items.TROPICAL_FISH);
            b.define('F', Items.PUFFERFISH);
            b.define('S', Items.STRING);
        });

        shaped(out, "vortex_bow", ItemInit.VORTEX_BOW.get(), b -> {
            b.pattern("ARS");
            b.pattern("FBS");
            b.pattern("ARS");
            b.define('B', Items.BOW);
            b.define('A', Items.WARPED_FUNGUS);
            b.define('R', Items.STICK);
            b.define('F', Items.BREEZE_ROD);
            b.define('S', Items.WARPED_ROOTS);
        });

        shaped(out, "tendon_bow", ItemInit.TENDON_BOW.get(), b -> {
            b.pattern("ARS");
            b.pattern("FBS");
            b.pattern("ARS");
            b.define('B', Items.BOW);
            b.define('A', Items.IRON_INGOT);
            b.define('R', Items.STICK);
            b.define('F', Items.STICK);
            b.define('S', Items.ROTTEN_FLESH);
        });

        shaped(out, "ice_bow", ItemInit.ICE_BOW.get(), b -> {
            b.pattern(" RS");
            b.pattern("FBS");
            b.pattern(" RS");
            b.define('B', Items.BOW);
            b.define('R', Items.ICE);
            b.define('F', Items.ICE);
            b.define('S', Items.STRING);
        });

        shaped(out, "demon_bow", ItemInit.DEMON_BOW.get(), b -> {
            b.pattern(" RS");
            b.pattern("FBS");
            b.pattern(" RS");
            b.define('B', Items.BOW);
            b.define('R', Items.BREEZE_ROD);
            b.define('F', Items.NETHERITE_INGOT);
            b.define('S', Items.STRING);
        });

        shaped(out, "blood_rain_bow", ItemInit.BLOOD_RAIN_BOW.get(), b -> {
            b.pattern(" RM");
            b.pattern("FBS");
            b.pattern(" RM");
            b.define('B', Items.BOW);
            b.define('R', Items.BLAZE_ROD);
            b.define('F', Items.BLAZE_POWDER);
            b.define('M', Items.FERMENTED_SPIDER_EYE);
            b.define('S', Items.STRING);
        });

        shaped(out, "mechanics_bow", ItemInit.MECHANICS_BOW.get(), b -> {
            b.pattern("ARS");
            b.pattern("FBS");
            b.pattern("ARS");
            b.define('B', Items.BOW);
            b.define('A', Items.REDSTONE);
            b.define('R', Items.GOLD_INGOT);
            b.define('F', Items.BLAZE_ROD);
            b.define('S', Items.STRING);
        });

        shaped(out, "aerial_bane_bow", ItemInit.AERIAL_BANE_BOW.get(), b -> {
            b.pattern("ARS");
            b.pattern("FBS");
            b.pattern("ARS");
            b.define('B', Items.BOW);
            b.define('A', Items.CHORUS_FRUIT);
            b.define('R', Items.BLAZE_ROD);
            b.define('F', Items.BLAZE_ROD);
            b.define('S', ItemInit.ABOMINABLE_ENERGY.get());
        });

    }
    protected void buildSouls(RecipeOutput out) {
        shaped(out, "soul_of_arch_wizard", ItemInit.SOUL_OF_ARCH_WIZARD.get(), b -> {
            b.pattern(" 1 ");
            b.pattern("2#3");
            b.pattern(" 4 ");
            b.define('1', ItemInit.ESSENCE_OF_THE_ARCANA.get());
            b.define('2', ItemInit.ESSENCE_OF_THE_ELDRITCH.get());
            b.define('3', ItemInit.ESSENCE_OF_THE_MAGE.get());
            b.define('4', ItemInit.ESSENCE_OF_THE_THAUMATURGE.get());
            b.define('#', ItemInit.TALISMAN_BASE_ULTIMATE.get());
        });

        shaped(out, "soul_of_berserker", ItemInit.SOUL_OF_BERSERKER.get(), b -> {
            b.pattern(" 1 ");
            b.pattern("2#3");
            b.pattern(" 4 ");
            b.define('1', ItemInit.ESSENCE_OF_THE_RAVAGER.get());
            b.define('2', ItemInit.ESSENCE_OF_THE_WARLORD.get());
            b.define('3', ItemInit.ESSENCE_OF_THE_SAVAGE.get());
            b.define('4', ItemInit.ESSENCE_OF_THE_WARMONGER.get());
            b.define('#', ItemInit.TALISMAN_BASE_ULTIMATE.get());
        });

        shaped(out, "soul_of_colossus", ItemInit.SOUL_OF_COLOSSUS.get(), b -> {
            b.pattern(" 1 ");
            b.pattern("2#3");
            b.pattern(" 4 ");
            b.define('1', ItemInit.ESSENCE_OF_THE_TITAN.get());
            b.define('2', ItemInit.ESSENCE_OF_THE_JUGGERNAUT.get());
            b.define('3', ItemInit.ESSENCE_OF_THE_BEHEMOTH.get());
            b.define('4', ItemInit.ESSENCE_OF_THE_SENTINEL.get());
            b.define('#', ItemInit.TALISMAN_BASE_ULTIMATE.get());
        });

        shaped(out, "soul_of_conjurist", ItemInit.SOUL_OF_CONJURIST.get(), b -> {
            b.pattern(" 1 ");
            b.pattern("2#3");
            b.pattern(" 4 ");
            b.define('1', ItemInit.ESSENCE_OF_THE_TOTEM.get());
            b.define('2', ItemInit.ESSENCE_OF_THE_OCCULT.get());
            b.define('3', ItemInit.ESSENCE_OF_THE_PHANTOM.get());
            b.define('4', ItemInit.ESSENCE_OF_THE_HEX.get());
            b.define('#', ItemInit.TALISMAN_BASE_ULTIMATE.get());
        });

        shaped(out, "soul_of_dimensions", ItemInit.SOUL_OF_DIMENSIONS.get(), b -> {
            b.pattern("=A=");
            b.pattern("D#B");
            b.pattern("=C=");
            b.define('#', ItemInit.TALISMAN_BASE_GODLY.get());
            b.define('=', ItemInit.ABOMINABLE_ENERGY.get());
            b.define('A', ItemInit.SOUL_OF_MINECRAFT.get());
            b.define('B', ItemInit.SOUL_OF_SUPERSONIC.get());
            b.define('C', ItemInit.SOUL_OF_COLOSSUS.get());
            b.define('D', ItemInit.SOUL_OF_FLIGHT_MASTERY.get());
        });

        shaped(out, "soul_of_eternity", ItemInit.SOUL_OF_ETERNITY.get(), b -> {
            b.pattern("===");
            b.pattern("A#B");
            b.pattern("===");
            b.define('#', ItemInit.TALISMAN_BASE_ANCIENT.get());
            b.define('=', ItemInit.ABOMINABLE_ENERGY.get());
            b.define('A', ItemInit.SOUL_OF_DIMENSIONS.get());
            b.define('B', ItemInit.SOUL_OF_THE_UNIVERSE.get());
        });

        shaped(out, "soul_of_flight_mastery", ItemInit.SOUL_OF_FLIGHT_MASTERY.get(), b -> {
            b.pattern(" 1 ");
            b.pattern("2#3");
            b.pattern(" 4 ");
            b.define('1', ItemInit.ESSENCE_OF_THE_ZEPHYR.get());
            b.define('2', ItemInit.ESSENCE_OF_THE_SKYRUNNER.get());
            b.define('3', ItemInit.ESSENCE_OF_THE_HIGHWIND.get());
            b.define('4', ItemInit.ESSENCE_OF_THE_SOARER.get());
            b.define('#', ItemInit.TALISMAN_BASE_ULTIMATE.get());
        });

        shaped(out, "soul_of_minecraft", ItemInit.SOUL_OF_MINECRAFT.get(), b -> {
            b.pattern("AGD");
            b.pattern("B#E");
            b.pattern("CHF");
            b.define('#', ItemInit.TALISMAN_BASE_ULTIMATE.get());
            b.define('G', ItemInit.FORCE_OF_ENVIRONMENT.get());
            b.define('H', ItemInit.FORCE_OF_NEGATIVE.get());
            b.define('A', ItemInit.FORCE_OF_OVERWORLD.get());
            b.define('B', ItemInit.FORCE_OF_NATURE.get());
            b.define('C', ItemInit.FORCE_OF_REJECTORS.get());
            b.define('D', ItemInit.FORCE_OF_EXPLORER.get());
            b.define('E', ItemInit.FORCE_OF_MYSTIC.get());
            b.define('F', ItemInit.FORCE_OF_WARRIOR.get());
        });

        shaped(out, "soul_of_sniper", ItemInit.SOUL_OF_SNIPER.get(), b -> {
            b.pattern(" 1 ");
            b.pattern("2#3");
            b.pattern(" 4 ");
            b.define('1', ItemInit.ESSENCE_OF_THE_LONGSHOT.get());
            b.define('2', ItemInit.ESSENCE_OF_THE_DEADEYE.get());
            b.define('3', ItemInit.ESSENCE_OF_THE_HAWK.get());
            b.define('4', ItemInit.ESSENCE_OF_THE_MARKSMAN.get());
            b.define('#', ItemInit.TALISMAN_BASE_ULTIMATE.get());
        });

        shaped(out, "soul_of_supersonic", ItemInit.SOUL_OF_SUPERSONIC.get(), b -> {
            b.pattern(" 1 ");
            b.pattern("2#3");
            b.pattern(" 4 ");
            b.define('1', ItemInit.ESSENCE_OF_THE_VELOCITY.get());
            b.define('2', ItemInit.ESSENCE_OF_THE_KINETIC.get());
            b.define('3', ItemInit.ESSENCE_OF_THE_COMET.get());
            b.define('4', ItemInit.ESSENCE_OF_THE_WHIPLASH.get());
            b.define('#', ItemInit.TALISMAN_BASE_ULTIMATE.get());
        });

        shaped(out, "soul_of_the_universe", ItemInit.SOUL_OF_THE_UNIVERSE.get(), b -> {
            b.pattern("AB=");
            b.pattern("D#E");
            b.pattern("=CF");
            b.define('#', ItemInit.TALISMAN_BASE_GODLY.get());
            b.define('=', ItemInit.ABOMINABLE_ENERGY.get());
            b.define('A', ItemInit.SOUL_OF_ARCH_WIZARD.get());
            b.define('B', ItemInit.SOUL_OF_SNIPER.get());
            b.define('C', ItemInit.SOUL_OF_BERSERKER.get());
            b.define('D', ItemInit.SOUL_OF_CONJURIST.get());
            b.define('E', ItemInit.SOUL_OF_WORLD_SHAPER.get());
            b.define('F', ItemInit.SOUL_OF_TRAWLER.get());
        });

        shaped(out, "soul_of_trawler", ItemInit.SOUL_OF_TRAWLER.get(), b -> {
            b.pattern(" 1 ");
            b.pattern("2#3");
            b.pattern(" 4 ");
            b.define('1', ItemInit.ESSENCE_OF_THE_LEVIATHAN.get());
            b.define('2', ItemInit.ESSENCE_OF_THE_ANGLER.get());
            b.define('3', ItemInit.ESSENCE_OF_THE_ABYSS.get());
            b.define('4', ItemInit.ESSENCE_OF_THE_BRINE.get());
            b.define('#', ItemInit.TALISMAN_BASE_ULTIMATE.get());
        });

        shaped(out, "soul_of_world_shaper", ItemInit.SOUL_OF_WORLD_SHAPER.get(), b -> {
            b.pattern(" 1 ");
            b.pattern("2#3");
            b.pattern(" 4 ");
            b.define('1', ItemInit.ESSENCE_OF_THE_ARTISAN.get());
            b.define('2', ItemInit.ESSENCE_OF_THE_MASON.get());
            b.define('3', ItemInit.ESSENCE_OF_THE_TERRAFORMER.get());
            b.define('4', ItemInit.ESSENCE_OF_THE_SCULPTOR.get());
            b.define('#', ItemInit.TALISMAN_BASE_ULTIMATE.get());
        });

    }

    private void shaped(RecipeOutput out, String id, Item result, Consumer<ShapedRecipeBuilder> fn) {
        ShapedRecipeBuilder builder = new ShapedRecipeBuilder(RecipeCategory.MISC, result, 1);
        fn.accept(builder);
        builder.unlockedBy("has_" + result.toString(), has(result))
                .save(out, ResourceLocation.fromNamespaceAndPath(Fargos.MODID, id));
    }

    private void shapeless(RecipeOutput out, String id, Item result, Consumer<ShapelessRecipeBuilder> fn) {
        ShapelessRecipeBuilder builder = new ShapelessRecipeBuilder(RecipeCategory.MISC, result, 1);
        fn.accept(builder);
        builder.unlockedBy("has_" + result.toString(), has(result))
                .save(out, ResourceLocation.fromNamespaceAndPath(Fargos.MODID, id));
    }

    private Ingredient glyph(String id) {
        // id is something like "arsnouveau:glyph_hex"
        int i = id.indexOf(':');
        if (i < 0) {
            throw new IllegalArgumentException("Glyph ID must contain a namespace: " + id);
        }

        String namespace = id.substring(0, i);
        String path = id.substring(i + 1);

        // Now safe: BOTH arguments are valid
        ResourceLocation rl = ResourceLocation.fromNamespaceAndPath(namespace, path);

        Supplier<Glyph> glyph = GlyphRegistry.getGlyphItemMap().get(rl);

        if (glyph == null) {
            throw new IllegalArgumentException("Unknown Ars Nouveau glyph: " + rl);
        }

        return Ingredient.of(glyph.get());
    }



}
