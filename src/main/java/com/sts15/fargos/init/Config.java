package com.sts15.fargos.init;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    public static final ModConfigSpec.Builder CLIENT_BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec.Builder COMMON_BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec.Builder SERVER_BUILDER = new ModConfigSpec.Builder();

    public static final String CATEGORY_TALISMANS = "talismans";
    public static final String CATEGORY_DESC_TALISMANS = "Is talisman functionality enabled on server?";
    public static ModConfigSpec.BooleanValue AIR_TALISMAN_TOGGLE;
    public static ModConfigSpec.BooleanValue AMETHYST_TALISMAN_TOGGLE;
    public static ModConfigSpec.BooleanValue APPLE_TALISMAN_TOGGLE;
    public static ModConfigSpec.BooleanValue ARCHITECT_TALISMAN_TOGGLE;
    public static ModConfigSpec.BooleanValue ARCTIC_TALISMAN_TOGGLE;
    public static ModConfigSpec.BooleanValue BATTLE_TALISMAN_TOGGLE;
    public static ModConfigSpec.BooleanValue BLAZE_TALISMAN_TOGGLE;
    public static ModConfigSpec.BooleanValue BLINDED_TALISMAN_TOGGLE;
    public static ModConfigSpec.BooleanValue CACTUS_TALISMAN_TOGGLE;
    public static ModConfigSpec.BooleanValue COPPER_TALISMAN_TOGGLE;
    public static ModConfigSpec.BooleanValue CREEPER_TALISMAN_TOGGLE;
    public static ModConfigSpec.BooleanValue DAY_TALISMAN_TOGGLE;
    public static ModConfigSpec.BooleanValue DIAMOND_TALISMAN_TOGGLE;
    public static ModConfigSpec.BooleanValue DRAGON_TALISMAN_TOGGLE;
    public static ModConfigSpec.BooleanValue EARTH_TALISMAN_TOGGLE;
    public static ModConfigSpec.BooleanValue EMERALD_TALISMAN_TOGGLE;
    public static ModConfigSpec.BooleanValue ENCHANTING_TALISMAN_TOGGLE;
    public static ModConfigSpec.BooleanValue ENDERMAN_TALISMAN_TOGGLE;
    public static ModConfigSpec.BooleanValue FATIGUED_TALISMAN_TOGGLE;
    public static ModConfigSpec.BooleanValue FIRE_TALISMAN_TOGGLE;
    public static ModConfigSpec.BooleanValue FIRED_TALISMAN_TOGGLE;
    public static ModConfigSpec.BooleanValue FULL_MOON_TALISMAN_TOGGLE;
    public static ModConfigSpec.BooleanValue GHAST_TALISMAN_TOGGLE;
    public static ModConfigSpec.BooleanValue GLOWSTONE_TALISMAN_TOGGLE;
    public static ModConfigSpec.BooleanValue GOLD_TALISMAN_TOGGLE;
    public static ModConfigSpec.BooleanValue IRON_GOLEM_TALISMAN_TOGGLE;
    public static ModConfigSpec.BooleanValue IRON_TALISMAN_TOGGLE;
    public static ModConfigSpec.BooleanValue LAPIS_TALISMAN_TOGGLE;
    public static ModConfigSpec.BooleanValue LIBRARIAN_TALISMAN_TOGGLE;
    public static ModConfigSpec.BooleanValue MOOSHROOM_TALISMAN_TOGGLE;
    public static ModConfigSpec.BooleanValue NAUSEATED_TALISMAN_TOGGLE;
    public static ModConfigSpec.BooleanValue NETHER_STAR_TALISMAN_TOGGLE;
    public static ModConfigSpec.BooleanValue NIGHT_TALISMAN_TOGGLE;
    public static ModConfigSpec.BooleanValue OBSIDIAN_TALISMAN_TOGGLE;
    public static ModConfigSpec.BooleanValue PICKAXE_TALISMAN_TOGGLE;
    public static ModConfigSpec.BooleanValue POISONED_TALISMAN_TOGGLE;
    public static ModConfigSpec.BooleanValue RAIN_TALISMAN_TOGGLE;
    public static ModConfigSpec.BooleanValue REDSTONE_TALISMAN_TOGGLE;
    public static ModConfigSpec.BooleanValue SHULKER_TALISMAN_TOGGLE;
    public static ModConfigSpec.BooleanValue SKELETON_TALISMAN_TOGGLE;
    public static ModConfigSpec.BooleanValue SLOWED_TALISMAN_TOGGLE;
    public static ModConfigSpec.BooleanValue SNOWY_TALISMAN_TOGGLE;
    public static ModConfigSpec.BooleanValue SPECTRAL_TALISMAN_TOGGLE;
    public static ModConfigSpec.BooleanValue STORM_TALISMAN_TOGGLE;
    public static ModConfigSpec.BooleanValue SUN_TALISMAN_TOGGLE;
    public static ModConfigSpec.BooleanValue THORNY_TALISMAN_TOGGLE;
    public static ModConfigSpec.BooleanValue TRUE_SUN_TALISMAN_TOGGLE;
    public static ModConfigSpec.BooleanValue UNDYING_TALISMAN_TOGGLE;
    public static ModConfigSpec.BooleanValue VAMPIRIC_TALISMAN_TOGGLE;
    public static ModConfigSpec.BooleanValue VINDICATOR_TALISMAN_TOGGLE;
    public static ModConfigSpec.BooleanValue VOID_TALISMAN_TOGGLE;
    public static ModConfigSpec.BooleanValue WATER_TALISMAN_TOGGLE;
    public static ModConfigSpec.BooleanValue WEAKENED_TALISMAN_TOGGLE;
    public static ModConfigSpec.BooleanValue WITCH_TALISMAN_TOGGLE;
    public static ModConfigSpec.BooleanValue WITHER_TALISMAN_TOGGLE;
    public static ModConfigSpec.BooleanValue WITHERED_TALISMAN_TOGGLE;
    public static ModConfigSpec.BooleanValue ZOMBIE_TALISMAN_TOGGLE;

    public static final String CATEGORY_SOULS = "souls";
    public static final String CATEGORY_DESC_SOULS = "Is soul functionality enabled on server?";
    public static ModConfigSpec.BooleanValue SOUL_OF_COLOSSUS_TOGGLE;
    public static ModConfigSpec.BooleanValue SOUL_OF_FLIGHT_MASTERY_TOGGLE;
    public static ModConfigSpec.BooleanValue SOUL_OF_SUPERSONIC_TOGGLE;

    public static final String CATEGORY_APPLE_TALISMAN = "apple_talisman";
    public static final String APPLE_TALISMAN_HEAL_FACTOR_DESC = "How much additional health should the player receive?  Value is added to 1f and then multiplied by the heal amount";
    public static final String APPLE_TALISMAN_HEAL_FACTOR_DEFAULT = "Default is 0.25 for a 1.25f or 125% heal factor";
    public static ModConfigSpec.DoubleValue APPLE_TALISMAN_HEAL_FACTOR;

    public static final String CATEGORY_ARCHITECT_TALISMAN = "architect_talisman";
    public static final String ARCHITECT_TALISMAN_REACH_DISTANCE_DESC = "How much additional reach should the player have?  Value is added to base 5";
    public static final String ARCHITECT_TALISMAN_REACH_DISTANCE_DEFAULT = "Default is 59 for a 64 block reach";
    public static ModConfigSpec.IntValue ARCHITECT_TALISMAN_REACH_DISTANCE;

    public static final String CATEGORY_BATTLE_TALISMAN = "battle_talisman";
    public static final String BATTLE_TALISMAN_INVINCIBILITY_TICKS_DESC = "How long should the player be invincible after taking damage in ticks?";
    public static final String BATTLE_TALISMAN_INVINCIBILITY_TICKS_DEFAULT = "Default is 5 for a 5 tick invincibility after taking damage";
    public static ModConfigSpec.IntValue BATTLE_TALISMAN_INVINCIBILITY_TICKS;

    public static final String CATEGORY_BLAZE_TALISMAN = "blaze_talisman";
    public static final String BLAZE_TALISMAN_BURN_RANGE_DESC = "What radius should the talisman ignite hostiles from the player?";
    public static final String BLAZE_TALISMAN_BURN_RANGE_DEFAULT = "Default is 4";
    public static ModConfigSpec.IntValue BLAZE_TALISMAN_BURN_RANGE;
    public static final String BLAZE_TALISMAN_BURN_TIME_DESC = "How long should the hostile entity be ignited for in seconds?";
    public static final String BLAZE_TALISMAN_BURN_TIME_DEFAULT = "Default is 3 for a 60 tick ignition";
    public static ModConfigSpec.IntValue BLAZE_TALISMAN_BURN_TIME;

    public static final String CATEGORY_BLINDED_TALISMAN = "blinded_talisman";
    public static final String BLINDED_TALISMAN_DAMAGE_FACTOR_DESC = "How much additional damage should the player do while blinded?  Value is added to 1f and then multiplied by the damage amount";
    public static final String BLINDED_TALISMAN_DAMAGE_FACTOR_DEFAULT = "Default is 0.25 for a 1.25f or 125% damage increase";
    public static ModConfigSpec.DoubleValue BLINDED_TALISMAN_DAMAGE_FACTOR;

    public static final String CATEGORY_CACTUS_TALISMAN = "cactus_talisman";
    public static final String CACTUS_TALISMAN_REFLECTED_DAMAGE_DESC = "How much damage should be reflected back towards the attacker?  Value is multiplied by the damage received and then applied back to the attacker";
    public static final String CACTUS_TALISMAN_REFLECTED_DAMAGE_DEFAULT = "Default is 0.25 for a 25% damage reflection";
    public static ModConfigSpec.DoubleValue CACTUS_TALISMAN_REFLECTED_DAMAGE;

    public static final String CATEGORY_COPPER_TALISMAN = "copper_talisman";
    public static final String COPPER_TALISMAN_AREA_DESC = "Area in block radius to attract items. Value is the radius around the talisman.";
    public static final String COPPER_TALISMAN_AREA_DEFAULT = "Default is 10.";
    public static final String COPPER_TALISMAN_PLAYER_AREA_DESC = "Area in block radius around the player. Value determines the effective range.";
    public static final String COPPER_TALISMAN_PLAYER_AREA_DEFAULT = "Default is 15.";
    public static final String COPPER_TALISMAN_SPEED_DESC = "Speed multiplier for items moving towards the player based on distance.";
    public static final String COPPER_TALISMAN_SPEED_DEFAULT = "Default is 1.5, which is multiplied by the distance.";
    public static final String COPPER_TALISMAN_DISTANCE_TO_PICKUP_DESC = "Distance at which items are automatically picked up.";
    public static final String COPPER_TALISMAN_DISTANCE_TO_PICKUP_DEFAULT = "Default is 1.5.";
    public static ModConfigSpec.IntValue COPPER_TALISMAN_AREA;
    public static ModConfigSpec.IntValue COPPER_TALISMAN_PLAYER_AREA;
    public static ModConfigSpec.DoubleValue COPPER_TALISMAN_SPEED;
    public static ModConfigSpec.DoubleValue COPPER_TALISMAN_DISTANCE_TO_PICKUP;

    public static final String CATEGORY_CREEPER_TALISMAN = "creeper_talisman";
    public static final String CREEPER_TALISMAN_BLAST_RADIUS_DESC = "Blast radius for the creeper explosion effect.";
    public static final String CREEPER_TALISMAN_BLAST_RADIUS_DEFAULT = "Default is 4.";
    public static ModConfigSpec.IntValue CREEPER_TALISMAN_BLAST_RADIUS;

    public static final String CATEGORY_DAY_TALISMAN = "day_talisman";
    public static final String DAY_TALISMAN_ADDITIONAL_DAMAGE_DESC = "Additional damage multiplier during daytime.";
    public static final String DAY_TALISMAN_ADDITIONAL_DAMAGE_DEFAULT = "Default is 0.05.";
    public static ModConfigSpec.DoubleValue DAY_TALISMAN_ADDITIONAL_DAMAGE;

    public static final String CATEGORY_DIAMOND_TALISMAN = "diamond_talisman";
    public static final String DIAMOND_TALISMAN_DAMAGE_REDUCTION_DESC = "Damage reduction percentage. Value is subtracted from 1f then multiplied by the incoming damage.";
    public static final String DIAMOND_TALISMAN_DAMAGE_REDUCTION_DEFAULT = "Default is 0.2 for 20% damage reduction.";
    public static ModConfigSpec.DoubleValue DIAMOND_TALISMAN_DAMAGE_REDUCTION;

    public static final String CATEGORY_EMERALD_TALISMAN = "emerald_talisman";
    public static final String EMERALD_TALISMAN_INCREASED_ILLAGER_DAMAGE_DESC = "Increased damage to Illagers. Value is added to 1f and then multiplied by the damage amount.";
    public static final String EMERALD_TALISMAN_INCREASED_ILLAGER_DAMAGE_DEFAULT = "Default is 1.0 for 200% damage.";
    public static ModConfigSpec.DoubleValue EMERALD_TALISMAN_INCREASED_ILLAGER_DAMAGE;

    public static final String CATEGORY_FATIGUED_TALISMAN = "fatigued_talisman";
    public static final String FATIGUED_TALISMAN_INCREASED_DAMAGE_DESC = "Increased damage when fatigued. Value is added to 1f and then multiplied by the damage amount.";
    public static final String FATIGUED_TALISMAN_INCREASED_DAMAGE_DEFAULT = "Default is 0.25 for 125% damage.";
    public static ModConfigSpec.DoubleValue FATIGUED_TALISMAN_INCREASED_DAMAGE;

    public static final String CATEGORY_FIRED_TALISMAN = "fired_talisman";
    public static final String FIRED_TALISMAN_INCREASED_DAMAGE_DESC = "Increased damage when on fire. Value is added to 1f and then multiplied by the damage amount.";
    public static final String FIRED_TALISMAN_INCREASED_DAMAGE_DEFAULT = "Default is 0.25 for 125% damage.";
    public static ModConfigSpec.DoubleValue FIRED_TALISMAN_INCREASED_DAMAGE;

    public static final String CATEGORY_FULL_MOON_TALISMAN = "full_moon_talisman";
    public static final String FULL_MOON_TALISMAN_INCREASED_DAMAGE_DESC = "Increased damage during a full moon. Value is added to 1f and then multiplied by the damage amount.";
    public static final String FULL_MOON_TALISMAN_INCREASED_DAMAGE_DEFAULT = "Default is 0.15 for 115% damage.";
    public static ModConfigSpec.DoubleValue FULL_MOON_TALISMAN_INCREASED_DAMAGE;

    public static final String CATEGORY_GHAST_TALISMAN = "ghast_talisman";
    public static final String GHAST_TALISMAN_RADIUS_DESC = "Radius in blocks to apply weakness effect.";
    public static final String GHAST_TALISMAN_RADIUS_DEFAULT = "Default is 3.";
    public static final String GHAST_TALISMAN_EFFECT_DURATION_DESC = "Duration of weakness effect in ticks.";
    public static final String GHAST_TALISMAN_EFFECT_DURATION_DEFAULT = "Default is 40 ticks.";
    public static ModConfigSpec.IntValue GHAST_TALISMAN_RADIUS;
    public static ModConfigSpec.IntValue GHAST_TALISMAN_EFFECT_DURATION;

    public static final String CATEGORY_GLOWSTONE_TALISMAN = "glowstone_talisman";
    public static final String GLOWSTONE_TALISMAN_RADIUS_DESC = "Radius in blocks to apply glowing effect.";
    public static final String GLOWSTONE_TALISMAN_RADIUS_DEFAULT = "Default is 12.";
    public static final String GLOWSTONE_TALISMAN_EFFECT_DURATION_DESC = "Duration of glowing effect in ticks.";
    public static final String GLOWSTONE_TALISMAN_EFFECT_DURATION_DEFAULT = "Default is 2 ticks.";
    public static ModConfigSpec.IntValue GLOWSTONE_TALISMAN_RADIUS;
    public static ModConfigSpec.IntValue GLOWSTONE_TALISMAN_EFFECT_DURATION;

    public static final String CATEGORY_IRON_GOLEM_TALISMAN = "iron_golem_talisman";
    public static final String IRON_GOLEM_TALISMAN_HEALTH_BOOST_MULTIPLIER_DESC = "Health boost multiplier. Value is multiplied by the base health and then added to the base health.";
    public static final String IRON_GOLEM_TALISMAN_HEALTH_BOOST_MULTIPLIER_DEFAULT = "Default is 2.0 for 300% health.";
    public static ModConfigSpec.DoubleValue IRON_GOLEM_TALISMAN_HEALTH_BOOST_MULTIPLIER;

    public static final String CATEGORY_IRON_TALISMAN = "iron_talisman";
    public static final String IRON_TALISMAN_DAMAGE_REDUCTION_DESC = "Damage reduction. Value is subtracted from 1f and multiplied to incoming damage.  This should be less than Diamond Talisman as it's used in that recipe.";
    public static final String IRON_TALISMAN_DAMAGE_REDUCTION_DEFAULT = "Default is 0.05 for 5% damage reduction.";
    public static ModConfigSpec.DoubleValue IRON_TALISMAN_DAMAGE_REDUCTION;

    public static final String CATEGORY_LAPIS_TALISMAN = "lapis_talisman";
    public static final String LAPIS_TALISMAN_SPEED_DESC = "Speed of XP orb pickup. Multiplied by distance from player.";
    public static final String LAPIS_TALISMAN_SPEED_DEFAULT = "Default is 1.0.";
    public static final String LAPIS_TALISMAN_PICKUP_RANGE_DESC = "Range of XP orb pickup radius in blocks.";
    public static final String LAPIS_TALISMAN_PICKUP_RANGE_DEFAULT = "Default is 12.";
    public static ModConfigSpec.DoubleValue LAPIS_TALISMAN_SPEED;
    public static ModConfigSpec.IntValue LAPIS_TALISMAN_PICKUP_RANGE;

    public static final String CATEGORY_MOOSHROOM_TALISMAN = "mooshroom_talisman";
    public static final String MOOSHROOM_TALISMAN_HEAL_FACTOR_DESC = "Heal factor while standing on mycelium. Value is added to 1f and multiplied by the heal amount.";
    public static final String MOOSHROOM_TALISMAN_HEAL_FACTOR_DEFAULT = "Default is 0.02 for 102% heal.";
    public static ModConfigSpec.DoubleValue MOOSHROOM_TALISMAN_HEAL_FACTOR;

    public static final String CATEGORY_NAUSEATED_TALISMAN = "nauseated_talisman";
    public static final String NAUSEATED_TALISMAN_INCREASED_DAMAGE_DESC = "Increased damage when under nausea effect. Value is added to 1f and multiplied by the damage amount.";
    public static final String NAUSEATED_TALISMAN_INCREASED_DAMAGE_DEFAULT = "Default is 0.25 for 125% damage.";
    public static ModConfigSpec.DoubleValue NAUSEATED_TALISMAN_INCREASED_DAMAGE;

    public static final String CATEGORY_NETHER_STAR_TALISMAN = "nether_star_talisman";
    public static final String NETHER_STAR_TALISMAN_CRIT_MULTIPLIER_DESC = "Critical hit multiplier. Value is added to 1f for critical damage.";
    public static final String NETHER_STAR_TALISMAN_CRIT_MULTIPLIER_DEFAULT = "Default is 0.5 for 150% critical damage.";
    public static ModConfigSpec.DoubleValue NETHER_STAR_TALISMAN_CRIT_MULTIPLIER;

    public static final String CATEGORY_NIGHT_TALISMAN = "night_talisman";
    public static final String NIGHT_TALISMAN_INCREASED_ARMOR_DESC = "Increased armor at night. Value multiplied by current armor then added to armor count.";
    public static final String NIGHT_TALISMAN_INCREASED_ARMOR_DEFAULT = "Default is 0.10 for 110% armor.";
    public static ModConfigSpec.DoubleValue NIGHT_TALISMAN_INCREASED_ARMOR;

    public static final String CATEGORY_PICKAXE_TALISMAN = "pickaxe_talisman";
    public static final String PICKAXE_TALISMAN_MINING_SPEED_MULTIPLIER_DESC = "Mining speed multiplier. Value is multiplied with the base mining speed.";
    public static final String PICKAXE_TALISMAN_MINING_SPEED_MULTIPLIER_DEFAULT = "Default is 2.0.";
    public static ModConfigSpec.DoubleValue PICKAXE_TALISMAN_MINING_SPEED_MULTIPLIER;

    public static final String CATEGORY_POISONED_TALISMAN = "poisoned_talisman";
    public static final String POISONED_TALISMAN_INCREASED_DAMAGE_DESC = "Increased damage when poisoned. Value is added to 1f and multiplied by the damage amount.";
    public static final String POISONED_TALISMAN_INCREASED_DAMAGE_DEFAULT = "Default is 0.25 for 125% damage.";
    public static ModConfigSpec.DoubleValue POISONED_TALISMAN_INCREASED_DAMAGE;

    public static final String CATEGORY_RAIN_TALISMAN = "rain_talisman";
    public static final String RAIN_TALISMAN_INCREASED_SPEED_DESC = "Increased movement speed while raining. Value is added to 1f and multiplied by the movement speed.";
    public static final String RAIN_TALISMAN_INCREASED_SPEED_DEFAULT = "Default is 0.5 for 150% speed.";
    public static ModConfigSpec.DoubleValue RAIN_TALISMAN_INCREASED_SPEED;

    public static final String CATEGORY_REDSTONE_TALISMAN = "redstone_talisman";
    public static final String REDSTONE_TALISMAN_ARMOR_REPAIR_DURATION_DESC = "Armor repair duration in milliseconds. Value is added to a random integer between 0 and 2500 milliseconds.";
    public static final String REDSTONE_TALISMAN_ARMOR_REPAIR_DURATION_DEFAULT = "Default is 5000 milliseconds (5 seconds)";
    public static ModConfigSpec.IntValue REDSTONE_TALISMAN_ARMOR_REPAIR_DURATION;

    public static final String CATEGORY_SKELETON_TALISMAN = "skeleton_talisman";
    public static final String SKELETON_TALISMAN_INCREASED_ARROW_DAMAGE_DESC = "Increased arrow damage. Value is added to 1f and multiplied by the damage amount.";
    public static final String SKELETON_TALISMAN_INCREASED_ARROW_DAMAGE_DEFAULT = "Default is 0.5 for 150% damage.";
    public static ModConfigSpec.DoubleValue SKELETON_TALISMAN_INCREASED_ARROW_DAMAGE;

    public static final String CATEGORY_SLOWED_TALISMAN = "slowed_talisman";
    public static final String SLOWED_TALISMAN_INCREASED_DAMAGE_DESC = "Increased damage when slowed. Value is added to 1f and multiplied by the damage amount.";
    public static final String SLOWED_TALISMAN_INCREASED_DAMAGE_DEFAULT = "Default is 0.25 for 125% damage.";
    public static ModConfigSpec.DoubleValue SLOWED_TALISMAN_INCREASED_DAMAGE;

    public static final String CATEGORY_SNOWY_TALISMAN = "snowy_talisman";
    public static final String SNOWY_TALISMAN_INCREASED_DAMAGE_DESC = "Increased damage in snowing biomes. Value is added to 1f and multiplied by the damage amount.";
    public static final String SNOWY_TALISMAN_INCREASED_DAMAGE_DEFAULT = "Default is 0.2 for 120% damage.";
    public static ModConfigSpec.DoubleValue SNOWY_TALISMAN_INCREASED_DAMAGE;

    public static final String CATEGORY_STORM_TALISMAN = "storm_talisman";
    public static final String STORM_TALISMAN_INCREASED_DAMAGE_DESC = "Increased damage during storms. Value is added to 1f and multiplied by the damage amount.";
    public static final String STORM_TALISMAN_INCREASED_DAMAGE_DEFAULT = "Default is 0.15 for 115% damage.";
    public static ModConfigSpec.DoubleValue STORM_TALISMAN_INCREASED_DAMAGE;

    public static final String CATEGORY_SUN_TALISMAN = "sun_talisman";
    public static final String SUN_TALISMAN_INCREASED_DAMAGE_DESC = "Increased damage when sunny. Value is added to 1f and multiplied by the damage amount.";
    public static final String SUN_TALISMAN_INCREASED_DAMAGE_DEFAULT = "Default is 0.05 for 105% damage.";
    public static ModConfigSpec.DoubleValue SUN_TALISMAN_INCREASED_DAMAGE;

    public static final String CATEGORY_TRUE_SUN_TALISMAN = "true_sun_talisman";
    public static final String TRUE_SUN_TALISMAN_INCREASED_DAMAGE_DESC = "Increased damage when sunny and exposed to the sky. Value is added to 1f and multiplied by the damage amount.";
    public static final String TRUE_SUN_TALISMAN_INCREASED_DAMAGE_DEFAULT = "Default is 0.05 for 105% damage.";
    public static ModConfigSpec.DoubleValue TRUE_SUN_TALISMAN_INCREASED_DAMAGE;

    public static final String CATEGORY_UNDYING_TALISMAN = "undying_talisman";
    public static final String UNDYING_TALISMAN_COOLDOWN_DESC = "Undying cooldown in milliseconds.";
    public static final String UNDYING_TALISMAN_COOLDOWN_DEFAULT = "Default is 24000 milliseconds.";
    public static final String UNDYING_TALISMAN_TELEPORT_DISTANCE_DESC = "Distance teleported backwards in blocks.";
    public static final String UNDYING_TALISMAN_TELEPORT_DISTANCE_DEFAULT = "Default is 10 blocks.";
    public static ModConfigSpec.IntValue UNDYING_TALISMAN_COOLDOWN;
    public static ModConfigSpec.IntValue UNDYING_TALISMAN_TELEPORT_DISTANCE;

    public static final String CATEGORY_VAMPIRIC_TALISMAN = "vampiric_talisman";
    public static final String VAMPIRIC_TALISMAN_HEAL_FACTOR_DESC = "Heal factor from damage dealt. Value is multiplied by the damage dealt.";
    public static final String VAMPIRIC_TALISMAN_HEAL_FACTOR_DEFAULT = "Default is 0.2 for 20% healing.";
    public static ModConfigSpec.DoubleValue VAMPIRIC_TALISMAN_HEAL_FACTOR;

    public static final String CATEGORY_VINDICATOR_TALISMAN = "vindicator_talisman";
    public static final String VINDICATOR_TALISMAN_BOOST_COOLDOWN_DESC = "Boost cooldown in ticks.";
    public static final String VINDICATOR_TALISMAN_BOOST_COOLDOWN_DEFAULT = "Default is 600 ticks.";
    public static final String VINDICATOR_TALISMAN_BOOST_DAMAGE_MODIFIER_DESC = "Boost damage modifier. Added to 1f and multiplied by base damage.";
    public static final String VINDICATOR_TALISMAN_BOOST_DAMAGE_MODIFIER_DEFAULT = "Default is 0.5 for 150% damage.";
    public static final String VINDICATOR_TALISMAN_WEAPON_SWAP_TIME_DESC = "Weapon swap time in ticks.";
    public static final String VINDICATOR_TALISMAN_WEAPON_SWAP_TIME_DEFAULT = "Default is 60 ticks.";
    public static ModConfigSpec.IntValue VINDICATOR_TALISMAN_BOOST_COOLDOWN;
    public static ModConfigSpec.DoubleValue VINDICATOR_TALISMAN_BOOST_DAMAGE_MODIFIER;
    public static ModConfigSpec.IntValue VINDICATOR_TALISMAN_WEAPON_SWAP_TIME;

    public static final String CATEGORY_WEAKENED_TALISMAN = "weakened_talisman";
    public static final String WEAKENED_TALISMAN_INCREASED_DAMAGE_DESC = "Increased damage when under weakness effect. Value added to 1f and multiplied by damage.";
    public static final String WEAKENED_TALISMAN_INCREASED_DAMAGE_DEFAULT = "Default is 0.25 for 125% damage.";
    public static ModConfigSpec.DoubleValue WEAKENED_TALISMAN_INCREASED_DAMAGE;

    public static final String CATEGORY_WITHERED_TALISMAN = "withered_talisman";
    public static final String WITHERED_TALISMAN_INCREASED_DAMAGE_DESC = "Increased damage when under wither effect. Value added to 1f and multiplied by damage.";
    public static final String WITHERED_TALISMAN_INCREASED_DAMAGE_DEFAULT = "Default is 0.25 for 125% damage.";
    public static ModConfigSpec.DoubleValue WITHERED_TALISMAN_INCREASED_DAMAGE;

    public static final String CATEGORY_ZOMBIE_TALISMAN = "zombie_talisman";
    public static final String ZOMBIE_TALISMAN_ATTACK_SPEED_BOOST_DESC = "Attack speed boost.";
    public static final String ZOMBIE_TALISMAN_ATTACK_SPEED_BOOST_DEFAULT = "Default is 5.5.";
    public static final String ZOMBIE_TALISMAN_MAX_ATTACKS_DESC = "Max attacks before speed boost wears off.";
    public static final String ZOMBIE_TALISMAN_MAX_ATTACKS_DEFAULT = "Default is 3.";
    public static final String ZOMBIE_TALISMAN_COOLDOWN_PERIOD_DESC = "Cooldown period in ticks.";
    public static final String ZOMBIE_TALISMAN_COOLDOWN_PERIOD_DEFAULT = "Default is 5000 ticks.";
    public static ModConfigSpec.DoubleValue ZOMBIE_TALISMAN_ATTACK_SPEED_BOOST;
    public static ModConfigSpec.IntValue ZOMBIE_TALISMAN_MAX_ATTACKS;
    public static ModConfigSpec.IntValue ZOMBIE_TALISMAN_COOLDOWN_PERIOD;

    public static final String CATEGORY_SOUL_OF_SUPERSONIC = "soul_of_supersonic";
    public static final String SOUL_OF_SUPERSONIC_WALK_SPEED_MULTIPLIER_DESC = "Walk speed multiplier. Multiplied to current walk speed and added to total.";
    public static final String SOUL_OF_SUPERSONIC_WALK_SPEED_MULTIPLIER_DEFAULT = "Default is 1.75.";
    public static final String SOUL_OF_SUPERSONIC_FLY_SPEED_MULTIPLIER_DESC = "Fly speed multiplier. Multiplied to current fly speed and added to total.";
    public static final String SOUL_OF_SUPERSONIC_FLY_SPEED_MULTIPLIER_DEFAULT = "Default is 2.5.";
    public static ModConfigSpec.DoubleValue SOUL_OF_SUPERSONIC_WALK_SPEED_MULTIPLIER;
    public static ModConfigSpec.DoubleValue SOUL_OF_SUPERSONIC_FLY_SPEED_MULTIPLIER;

    public static final String CATEGORY_SOUL_OF_COLOSSUS = "soul_of_colossus";
    public static final String SOUL_OF_COLOSSUS_HEALTH_MULTIPLIER_DESC = "Health multiplier. Multiplied by base health and added to base health.";
    public static final String SOUL_OF_COLOSSUS_HEALTH_MULTIPLIER_DEFAULT = "Default is 4.0.";
    public static final String SOUL_OF_COLOSSUS_REMOVE_NEGATIVE_EFFECTS_DESC = "Remove negative effects.";
    public static final String SOUL_OF_COLOSSUS_REMOVE_NEGATIVE_EFFECTS_DEFAULT = "Default is true.";
    public static ModConfigSpec.DoubleValue SOUL_OF_COLOSSUS_HEALTH_MULTIPLIER;
    public static ModConfigSpec.BooleanValue SOUL_OF_COLOSSUS_REMOVE_NEGATIVE_EFFECTS;


    public static void register(ModContainer container) {
        //registerClientConfigs(container);
        //registerCommonConfigs(container);
        registerServerConfigs(container);
    }
    private static void registerClientConfigs(ModContainer container) {
        container.registerConfig(ModConfig.Type.CLIENT, CLIENT_BUILDER.build());
    }
    private static void registerCommonConfigs(ModContainer container) {
        container.registerConfig(ModConfig.Type.COMMON, COMMON_BUILDER.build());
    }
    private static void registerServerConfigs(ModContainer container) {
        generalTalismansConfig();
        generalSoulsConfig();
        appleConfig();
        architectConfig();
        battleConfig();
        blazeConfig();
        blindedConfig();
        cactusConfig();
        copperConfig();
        creeperConfig();
        dayConfig();
        diamondConfig();
        emeraldConfig();
        fatiguedConfig();
        firedConfig();
        fullMoonConfig();
        ghastConfig();
        glowstoneConfig();
        ironGolemConfig();
        ironConfig();
        lapisConfig();
        mooshroomConfig();
        nauseatedConfig();
        netherStarConfig();
        nightConfig();
        pickaxeConfig();
        poisonedConfig();
        rainConfig();
        redstoneConfig();
        skeletonConfig();
        slowedConfig();
        snowyConfig();
        stormConfig();
        sunConfig();
        trueSunConfig();
        undyingConfig();
        vampiricConfig();
        vindicatorConfig();
        weakenedConfig();
        witheredConfig();
        zombieConfig();
        soulOfSupersonicConfig();
        soulOfColossusConfig();
        container.registerConfig(ModConfig.Type.SERVER, SERVER_BUILDER.build());
    }

    private static void generalTalismansConfig() {
        SERVER_BUILDER.comment(CATEGORY_DESC_TALISMANS).push(CATEGORY_TALISMANS);
        AIR_TALISMAN_TOGGLE = SERVER_BUILDER
                .define("Air_Talisman", true);
        AMETHYST_TALISMAN_TOGGLE = SERVER_BUILDER
                .define("Amethyst_Talisman", true);
        APPLE_TALISMAN_TOGGLE = SERVER_BUILDER
                .define("Apple_Talisman", true);
        ARCHITECT_TALISMAN_TOGGLE = SERVER_BUILDER
                .define("Architect_Talisman", true);
        ARCTIC_TALISMAN_TOGGLE = SERVER_BUILDER
                .define("Arctic_Talisman", true);
        BATTLE_TALISMAN_TOGGLE = SERVER_BUILDER
                .define("Battle_Talisman", true);
        BLAZE_TALISMAN_TOGGLE = SERVER_BUILDER
                .define("Blaze_Talisman", true);
        BLINDED_TALISMAN_TOGGLE = SERVER_BUILDER
                .define("Blinded_Talisman", true);
        CACTUS_TALISMAN_TOGGLE = SERVER_BUILDER
                .define("Cactus_Talisman", true);
        COPPER_TALISMAN_TOGGLE = SERVER_BUILDER
                .define("Copper_Talisman", true);
        CREEPER_TALISMAN_TOGGLE = SERVER_BUILDER
                .define("Creeper_Talisman", true);
        DAY_TALISMAN_TOGGLE = SERVER_BUILDER
                .define("Day_Talisman", true);
        DIAMOND_TALISMAN_TOGGLE = SERVER_BUILDER
                .define("Diamond_Talisman", true);
        DRAGON_TALISMAN_TOGGLE = SERVER_BUILDER
                .define("Dragon_Talisman", true);
        EARTH_TALISMAN_TOGGLE = SERVER_BUILDER
                .define("Earth_Talisman", true);
        EMERALD_TALISMAN_TOGGLE = SERVER_BUILDER
                .define("Emerald_Talisman", true);
        ENCHANTING_TALISMAN_TOGGLE = SERVER_BUILDER
                .define("Enchanting_Talisman", true);
        ENDERMAN_TALISMAN_TOGGLE = SERVER_BUILDER
                .define("Enderman_Talisman", true);
        FATIGUED_TALISMAN_TOGGLE = SERVER_BUILDER
                .define("Fatigued_Talisman", true);
        FIRE_TALISMAN_TOGGLE = SERVER_BUILDER
                .define("Fire_Talisman", true);
        FIRED_TALISMAN_TOGGLE = SERVER_BUILDER
                .define("Fired_Talisman", true);
        FULL_MOON_TALISMAN_TOGGLE = SERVER_BUILDER
                .define("Full_Moon_Talisman", true);
        GHAST_TALISMAN_TOGGLE = SERVER_BUILDER
                .define("Ghast_Talisman", true);
        GLOWSTONE_TALISMAN_TOGGLE = SERVER_BUILDER
                .define("Glowstone_Talisman", true);
        GOLD_TALISMAN_TOGGLE = SERVER_BUILDER
                .define("Gold_Talisman", true);
        IRON_GOLEM_TALISMAN_TOGGLE = SERVER_BUILDER
                .define("Iron_Golem_Talisman", true);
        IRON_TALISMAN_TOGGLE = SERVER_BUILDER
                .define("Iron_Talisman", true);
        LAPIS_TALISMAN_TOGGLE = SERVER_BUILDER
                .define("Lapis_Talisman", true);
        LIBRARIAN_TALISMAN_TOGGLE = SERVER_BUILDER
                .define("Librarian_Talisman", true);
        MOOSHROOM_TALISMAN_TOGGLE = SERVER_BUILDER
                .define("Mooshroom_Talisman", true);
        NAUSEATED_TALISMAN_TOGGLE = SERVER_BUILDER
                .define("Nauseated_Talisman", true);
        NETHER_STAR_TALISMAN_TOGGLE = SERVER_BUILDER
                .define("Nether_Star_Talisman", true);
        NIGHT_TALISMAN_TOGGLE = SERVER_BUILDER
                .define("Night_Talisman", true);
        OBSIDIAN_TALISMAN_TOGGLE = SERVER_BUILDER
                .define("Obsidian_Talisman", true);
        PICKAXE_TALISMAN_TOGGLE = SERVER_BUILDER
                .define("Pickaxe_Talisman", true);
        POISONED_TALISMAN_TOGGLE = SERVER_BUILDER
                .define("Poisoned_Talisman", true);
        RAIN_TALISMAN_TOGGLE = SERVER_BUILDER
                .define("Rain_Talisman", true);
        REDSTONE_TALISMAN_TOGGLE = SERVER_BUILDER
                .define("Redstone_Talisman", true);
        SHULKER_TALISMAN_TOGGLE = SERVER_BUILDER
                .define("Shulker_Talisman", true);
        SKELETON_TALISMAN_TOGGLE = SERVER_BUILDER
                .define("Skeleton_Talisman", true);
        SLOWED_TALISMAN_TOGGLE = SERVER_BUILDER
                .define("Slowed_Talisman", true);
        SNOWY_TALISMAN_TOGGLE = SERVER_BUILDER
                .define("Snowy_Talisman", true);
        SPECTRAL_TALISMAN_TOGGLE = SERVER_BUILDER
                .define("Spectral_Talisman", true);
        STORM_TALISMAN_TOGGLE = SERVER_BUILDER
                .define("Storm_Talisman", true);
        SUN_TALISMAN_TOGGLE = SERVER_BUILDER
                .define("Sun_Talisman", true);
        THORNY_TALISMAN_TOGGLE = SERVER_BUILDER
                .define("Thorny_Talisman", true);
        TRUE_SUN_TALISMAN_TOGGLE = SERVER_BUILDER
                .define("True_Sun_Talisman", true);
        UNDYING_TALISMAN_TOGGLE = SERVER_BUILDER
                .define("Undying_Talisman", true);
        VAMPIRIC_TALISMAN_TOGGLE = SERVER_BUILDER
                .define("Vampiric_Talisman", true);
        VINDICATOR_TALISMAN_TOGGLE = SERVER_BUILDER
                .define("Vindicator_Talisman", true);
        VOID_TALISMAN_TOGGLE = SERVER_BUILDER
                .define("Void_Talisman", true);
        WATER_TALISMAN_TOGGLE = SERVER_BUILDER
                .define("Water_Talisman", true);
        WEAKENED_TALISMAN_TOGGLE = SERVER_BUILDER
                .define("Weakened_Talisman", true);
        WITCH_TALISMAN_TOGGLE = SERVER_BUILDER
                .define("Witch_Talisman", true);
        WITHER_TALISMAN_TOGGLE = SERVER_BUILDER
                .define("Wither_Talisman", true);
        WITHERED_TALISMAN_TOGGLE = SERVER_BUILDER
                .define("Withered_Talisman", true);
        ZOMBIE_TALISMAN_TOGGLE = SERVER_BUILDER
                .define("Zombie_Talisman", true);
        SERVER_BUILDER.pop();
    }
    private static void generalSoulsConfig() {
        SERVER_BUILDER.comment(CATEGORY_DESC_SOULS).push(CATEGORY_SOULS);
        SOUL_OF_COLOSSUS_TOGGLE = SERVER_BUILDER
                .define("Soul_Of_Colossus", true);
        SOUL_OF_FLIGHT_MASTERY_TOGGLE = SERVER_BUILDER
                .define("Soul_Of_Flight_Mastery", true);
        SOUL_OF_SUPERSONIC_TOGGLE = SERVER_BUILDER
                .define("Soul_Of_Supersonic", true);
        SERVER_BUILDER.pop();
    }
    private static void appleConfig() {
        SERVER_BUILDER.push(CATEGORY_APPLE_TALISMAN);
        APPLE_TALISMAN_HEAL_FACTOR = SERVER_BUILDER
                .comment(APPLE_TALISMAN_HEAL_FACTOR_DESC)
                .comment(APPLE_TALISMAN_HEAL_FACTOR_DEFAULT)
                .defineInRange("apple_talisman_heal_factor", 0.25, 0.0, 5.0);
        SERVER_BUILDER.pop();
    }
    private static void architectConfig() {
        SERVER_BUILDER.push(CATEGORY_ARCHITECT_TALISMAN);
        ARCHITECT_TALISMAN_REACH_DISTANCE = SERVER_BUILDER
                .comment(ARCHITECT_TALISMAN_REACH_DISTANCE_DESC)
                .comment(ARCHITECT_TALISMAN_REACH_DISTANCE_DEFAULT)
                .defineInRange("architect_talisman_reach_distance", 59, 0, 59);
        SERVER_BUILDER.pop();
    }
    private static void battleConfig() {
        SERVER_BUILDER.push(CATEGORY_BATTLE_TALISMAN);
        BATTLE_TALISMAN_INVINCIBILITY_TICKS = SERVER_BUILDER
                .comment(BATTLE_TALISMAN_INVINCIBILITY_TICKS_DESC)
                .comment(BATTLE_TALISMAN_INVINCIBILITY_TICKS_DEFAULT)
                .defineInRange("battle_talisman_invincibility_frames", 5, 0, 200);
        SERVER_BUILDER.pop();
    }
    private static void blazeConfig() {
        SERVER_BUILDER.push(CATEGORY_BLAZE_TALISMAN);
        BLAZE_TALISMAN_BURN_RANGE = SERVER_BUILDER
                .comment(BLAZE_TALISMAN_BURN_RANGE_DESC)
                .comment(BLAZE_TALISMAN_BURN_RANGE_DEFAULT)
                .defineInRange("blaze_talisman_burn_range", 4, 0, 16);
        BLAZE_TALISMAN_BURN_TIME = SERVER_BUILDER
                .comment(BLAZE_TALISMAN_BURN_TIME_DESC)
                .comment(BLAZE_TALISMAN_BURN_TIME_DEFAULT)
                .defineInRange("blaze_talisman_burn_duration", 3, 0, 30);
        SERVER_BUILDER.pop();
    }
    private static void blindedConfig() {
        SERVER_BUILDER.push(CATEGORY_BLINDED_TALISMAN);
        BLINDED_TALISMAN_DAMAGE_FACTOR = SERVER_BUILDER
                .comment(BLINDED_TALISMAN_DAMAGE_FACTOR_DESC)
                .comment(BLINDED_TALISMAN_DAMAGE_FACTOR_DEFAULT)
                .defineInRange("blinded_talisman_damage_factor", 0.25, 0.0, 5.0);
        SERVER_BUILDER.pop();
    }
    private static void cactusConfig() {
        SERVER_BUILDER.push(CATEGORY_CACTUS_TALISMAN);
        CACTUS_TALISMAN_REFLECTED_DAMAGE = SERVER_BUILDER
                .comment(CACTUS_TALISMAN_REFLECTED_DAMAGE_DESC)
                .comment(CACTUS_TALISMAN_REFLECTED_DAMAGE_DEFAULT)
                .defineInRange("cactus_talisman_reflected_damage", 0.25, 0.0, 5.0);
        SERVER_BUILDER.pop();
    }
    private static void copperConfig() {
        SERVER_BUILDER.push(CATEGORY_COPPER_TALISMAN);
        COPPER_TALISMAN_AREA = SERVER_BUILDER
                .comment(COPPER_TALISMAN_AREA_DESC)
                .comment(COPPER_TALISMAN_AREA_DEFAULT)
                .defineInRange("copper_talisman_area", 10, 0, 32);
        COPPER_TALISMAN_PLAYER_AREA = SERVER_BUILDER
                .comment(COPPER_TALISMAN_PLAYER_AREA_DESC)
                .comment(COPPER_TALISMAN_PLAYER_AREA_DEFAULT)
                .defineInRange("copper_talisman_player_area", 15, 0, 64);
        COPPER_TALISMAN_SPEED = SERVER_BUILDER
                .comment(COPPER_TALISMAN_SPEED_DESC)
                .comment(COPPER_TALISMAN_SPEED_DEFAULT)
                .defineInRange("copper_talisman_drop_speed", 1.5, 0.1, 3.0);
        COPPER_TALISMAN_DISTANCE_TO_PICKUP = SERVER_BUILDER
                .comment(COPPER_TALISMAN_DISTANCE_TO_PICKUP_DESC)
                .comment(COPPER_TALISMAN_DISTANCE_TO_PICKUP_DEFAULT)
                .defineInRange("copper_talisman_distance_to_pickup", 1.5, 0.5, 5.0);
        SERVER_BUILDER.pop();
    }
    private static void creeperConfig() {
        SERVER_BUILDER.push(CATEGORY_CREEPER_TALISMAN);
        CREEPER_TALISMAN_BLAST_RADIUS = SERVER_BUILDER
                .comment(CREEPER_TALISMAN_BLAST_RADIUS_DESC)
                .comment(CREEPER_TALISMAN_BLAST_RADIUS_DEFAULT)
                .defineInRange("creeper_talisman_blast_radius", 4, 0, 16);
        SERVER_BUILDER.pop();
    }
    private static void dayConfig() {
        SERVER_BUILDER.push(CATEGORY_DAY_TALISMAN);
        DAY_TALISMAN_ADDITIONAL_DAMAGE = SERVER_BUILDER
                .comment(DAY_TALISMAN_ADDITIONAL_DAMAGE_DESC)
                .comment(DAY_TALISMAN_ADDITIONAL_DAMAGE_DEFAULT)
                .defineInRange("day_talisman_additional_damage", 0.05, 0.0, 5.0);
        SERVER_BUILDER.pop();
    }
    private static void diamondConfig() {
        SERVER_BUILDER.push(CATEGORY_DIAMOND_TALISMAN);
        DIAMOND_TALISMAN_DAMAGE_REDUCTION = SERVER_BUILDER
                .comment(DIAMOND_TALISMAN_DAMAGE_REDUCTION_DESC)
                .comment(DIAMOND_TALISMAN_DAMAGE_REDUCTION_DEFAULT)
                .defineInRange("diamond_talisman_damage_reduction", 0.2, 0.0, 1.0);
        SERVER_BUILDER.pop();
    }
    private static void emeraldConfig() {
        SERVER_BUILDER.push(CATEGORY_EMERALD_TALISMAN);
        EMERALD_TALISMAN_INCREASED_ILLAGER_DAMAGE = SERVER_BUILDER
                .comment(EMERALD_TALISMAN_INCREASED_ILLAGER_DAMAGE_DESC)
                .comment(EMERALD_TALISMAN_INCREASED_ILLAGER_DAMAGE_DEFAULT)
                .defineInRange("emerald_talisman_increased_illager_damage", 1.0, 0.0, 10.0);
        SERVER_BUILDER.pop();
    }
    private static void fatiguedConfig() {
        SERVER_BUILDER.push(CATEGORY_FATIGUED_TALISMAN);
        FATIGUED_TALISMAN_INCREASED_DAMAGE = SERVER_BUILDER
                .comment(FATIGUED_TALISMAN_INCREASED_DAMAGE_DESC)
                .comment(FATIGUED_TALISMAN_INCREASED_DAMAGE_DEFAULT)
                .defineInRange("fatigued_talisman_increased_damage", 0.25, 0.0, 5.0);
        SERVER_BUILDER.pop();
    }
    private static void firedConfig() {
        SERVER_BUILDER.push(CATEGORY_FIRED_TALISMAN);
        FIRED_TALISMAN_INCREASED_DAMAGE = SERVER_BUILDER
                .comment(FIRED_TALISMAN_INCREASED_DAMAGE_DESC)
                .comment(FIRED_TALISMAN_INCREASED_DAMAGE_DEFAULT)
                .defineInRange("fired_talisman_increased_damage", 0.25, 0.0, 5.0);
        SERVER_BUILDER.pop();
    }
    private static void fullMoonConfig() {
        SERVER_BUILDER.push(CATEGORY_FULL_MOON_TALISMAN);
        FULL_MOON_TALISMAN_INCREASED_DAMAGE = SERVER_BUILDER
                .comment(FULL_MOON_TALISMAN_INCREASED_DAMAGE_DESC)
                .comment(FULL_MOON_TALISMAN_INCREASED_DAMAGE_DEFAULT)
                .defineInRange("full_moon_talisman_increased_damage", 0.15, 0.0, 5.0);
        SERVER_BUILDER.pop();
    }
    private static void ghastConfig() {
        SERVER_BUILDER.push(CATEGORY_GHAST_TALISMAN);
        GHAST_TALISMAN_RADIUS = SERVER_BUILDER
                .comment(GHAST_TALISMAN_RADIUS_DESC)
                .comment(GHAST_TALISMAN_RADIUS_DEFAULT)
                .defineInRange("ghast_talisman_radius", 3, 0, 15);
        GHAST_TALISMAN_EFFECT_DURATION = SERVER_BUILDER
                .comment(GHAST_TALISMAN_EFFECT_DURATION_DESC)
                .comment(GHAST_TALISMAN_EFFECT_DURATION_DEFAULT)
                .defineInRange("ghast_talisman_effect_duration", 40, 0, 200);
        SERVER_BUILDER.pop();
    }
    private static void glowstoneConfig() {
        SERVER_BUILDER.push(CATEGORY_GLOWSTONE_TALISMAN);
        GLOWSTONE_TALISMAN_RADIUS = SERVER_BUILDER
                .comment(GLOWSTONE_TALISMAN_RADIUS_DESC)
                .comment(GLOWSTONE_TALISMAN_RADIUS_DEFAULT)
                .defineInRange("glowstone_talisman_radius", 12, 0, 16);
        GLOWSTONE_TALISMAN_EFFECT_DURATION = SERVER_BUILDER
                .comment(GLOWSTONE_TALISMAN_EFFECT_DURATION_DESC)
                .comment(GLOWSTONE_TALISMAN_EFFECT_DURATION_DEFAULT)
                .defineInRange("glowstone_talisman_effect_duration", 2, 0, 200);
        SERVER_BUILDER.pop();
    }
    private static void ironGolemConfig() {
        SERVER_BUILDER.push(CATEGORY_IRON_GOLEM_TALISMAN);
        IRON_GOLEM_TALISMAN_HEALTH_BOOST_MULTIPLIER = SERVER_BUILDER
                .comment(IRON_GOLEM_TALISMAN_HEALTH_BOOST_MULTIPLIER_DESC)
                .comment(IRON_GOLEM_TALISMAN_HEALTH_BOOST_MULTIPLIER_DEFAULT)
                .defineInRange("iron_golem_talisman_health_boost_multiplier", 2.0, 0.0, 10.0);
        SERVER_BUILDER.pop();
    }
    private static void ironConfig() {
        SERVER_BUILDER.push(CATEGORY_IRON_TALISMAN);
        IRON_TALISMAN_DAMAGE_REDUCTION = SERVER_BUILDER
                .comment(IRON_TALISMAN_DAMAGE_REDUCTION_DESC)
                .comment(IRON_TALISMAN_DAMAGE_REDUCTION_DEFAULT)
                .defineInRange("iron_talisman_damage_reduction", 0.05, 0.0, 1.0);
        SERVER_BUILDER.pop();
    }
    private static void lapisConfig() {
        SERVER_BUILDER.push(CATEGORY_LAPIS_TALISMAN);
        LAPIS_TALISMAN_SPEED = SERVER_BUILDER
                .comment(LAPIS_TALISMAN_SPEED_DESC)
                .comment(LAPIS_TALISMAN_SPEED_DEFAULT)
                .defineInRange("lapis_talisman_xp_speed", 1.0, 0.1, 3.0);
        LAPIS_TALISMAN_PICKUP_RANGE = SERVER_BUILDER
                .comment(LAPIS_TALISMAN_PICKUP_RANGE_DESC)
                .comment(LAPIS_TALISMAN_PICKUP_RANGE_DEFAULT)
                .defineInRange("lapis_talisman_xp_pickup_range", 12, 0, 16);
        SERVER_BUILDER.pop();
    }
    private static void mooshroomConfig() {
        SERVER_BUILDER.push(CATEGORY_MOOSHROOM_TALISMAN);
        MOOSHROOM_TALISMAN_HEAL_FACTOR = SERVER_BUILDER
                .comment(MOOSHROOM_TALISMAN_HEAL_FACTOR_DESC)
                .comment(MOOSHROOM_TALISMAN_HEAL_FACTOR_DEFAULT)
                .defineInRange("mooshroom_talisman_heal_factor", 0.02, 0.0, 1.0);
        SERVER_BUILDER.pop();
    }
    private static void nauseatedConfig() {
        SERVER_BUILDER.push(CATEGORY_NAUSEATED_TALISMAN);
        NAUSEATED_TALISMAN_INCREASED_DAMAGE = SERVER_BUILDER
                .comment(NAUSEATED_TALISMAN_INCREASED_DAMAGE_DESC)
                .comment(NAUSEATED_TALISMAN_INCREASED_DAMAGE_DEFAULT)
                .defineInRange("nauseated_talisman_increased_damage", 0.25, 0.0, 5.0);
        SERVER_BUILDER.pop();
    }
    private static void netherStarConfig() {
        SERVER_BUILDER.push(CATEGORY_NETHER_STAR_TALISMAN);
        NETHER_STAR_TALISMAN_CRIT_MULTIPLIER = SERVER_BUILDER
                .comment(NETHER_STAR_TALISMAN_CRIT_MULTIPLIER_DESC)
                .comment(NETHER_STAR_TALISMAN_CRIT_MULTIPLIER_DEFAULT)
                .defineInRange("nether_star_talisman_crit_multiplier", 0.5, 0.0, 5.0);
        SERVER_BUILDER.pop();
    }
    private static void nightConfig() {
        SERVER_BUILDER.push(CATEGORY_NIGHT_TALISMAN);
        NIGHT_TALISMAN_INCREASED_ARMOR = SERVER_BUILDER
                .comment(NIGHT_TALISMAN_INCREASED_ARMOR_DESC)
                .comment(NIGHT_TALISMAN_INCREASED_ARMOR_DEFAULT)
                .defineInRange("night_talisman_increased_armor", 0.10, 0.0, 5.0);
        SERVER_BUILDER.pop();
    }
    private static void pickaxeConfig() {
        SERVER_BUILDER.push(CATEGORY_PICKAXE_TALISMAN);
        PICKAXE_TALISMAN_MINING_SPEED_MULTIPLIER = SERVER_BUILDER
                .comment(PICKAXE_TALISMAN_MINING_SPEED_MULTIPLIER_DESC)
                .comment(PICKAXE_TALISMAN_MINING_SPEED_MULTIPLIER_DEFAULT)
                .defineInRange("pickaxe_talisman_mining_speed_multiplier", 2.0, 0.0, 5.0);
        SERVER_BUILDER.pop();
    }
    private static void poisonedConfig() {
        SERVER_BUILDER.push(CATEGORY_POISONED_TALISMAN);
        POISONED_TALISMAN_INCREASED_DAMAGE = SERVER_BUILDER
                .comment(POISONED_TALISMAN_INCREASED_DAMAGE_DESC)
                .comment(POISONED_TALISMAN_INCREASED_DAMAGE_DEFAULT)
                .defineInRange("poisoned_talisman_increased_damage", 0.25, 0.0, 5.0);
        SERVER_BUILDER.pop();
    }
    private static void rainConfig() {
        SERVER_BUILDER.push(CATEGORY_RAIN_TALISMAN);
        RAIN_TALISMAN_INCREASED_SPEED = SERVER_BUILDER
                .comment(RAIN_TALISMAN_INCREASED_SPEED_DESC)
                .comment(RAIN_TALISMAN_INCREASED_SPEED_DEFAULT)
                .defineInRange("rain_talisman_increased_speed", 0.5, 0.0, 5.0);
        SERVER_BUILDER.pop();
    }
    private static void redstoneConfig() {
        SERVER_BUILDER.push(CATEGORY_REDSTONE_TALISMAN);
        REDSTONE_TALISMAN_ARMOR_REPAIR_DURATION = SERVER_BUILDER
                .comment(REDSTONE_TALISMAN_ARMOR_REPAIR_DURATION_DESC)
                .comment(REDSTONE_TALISMAN_ARMOR_REPAIR_DURATION_DEFAULT)
                .defineInRange("redstone_talisman_armor_repair_duration", 5000, 0, 100000);
        SERVER_BUILDER.pop();
    }
    private static void skeletonConfig() {
        SERVER_BUILDER.push(CATEGORY_SKELETON_TALISMAN);
        SKELETON_TALISMAN_INCREASED_ARROW_DAMAGE = SERVER_BUILDER
                .comment(SKELETON_TALISMAN_INCREASED_ARROW_DAMAGE_DESC)
                .comment(SKELETON_TALISMAN_INCREASED_ARROW_DAMAGE_DEFAULT)
                .defineInRange("skeleton_talisman_increased_arrow_damage", 0.5, 0.0, 5.0);
        SERVER_BUILDER.pop();
    }
    private static void slowedConfig() {
        SERVER_BUILDER.push(CATEGORY_SLOWED_TALISMAN);
        SLOWED_TALISMAN_INCREASED_DAMAGE = SERVER_BUILDER
                .comment(SLOWED_TALISMAN_INCREASED_DAMAGE_DESC)
                .comment(SLOWED_TALISMAN_INCREASED_DAMAGE_DEFAULT)
                .defineInRange("slowed_talisman_increased_damage", 0.25, 0.0, 5.0);
        SERVER_BUILDER.pop();
    }
    private static void snowyConfig() {
        SERVER_BUILDER.push(CATEGORY_SNOWY_TALISMAN);
        SNOWY_TALISMAN_INCREASED_DAMAGE = SERVER_BUILDER
                .comment(SNOWY_TALISMAN_INCREASED_DAMAGE_DESC)
                .comment(SNOWY_TALISMAN_INCREASED_DAMAGE_DEFAULT)
                .defineInRange("snowy_talisman_increased_damage", 0.2, 0.0, 5.0);
        SERVER_BUILDER.pop();
    }
    private static void stormConfig() {
        SERVER_BUILDER.push(CATEGORY_STORM_TALISMAN);
        STORM_TALISMAN_INCREASED_DAMAGE = SERVER_BUILDER
                .comment(STORM_TALISMAN_INCREASED_DAMAGE_DESC)
                .comment(STORM_TALISMAN_INCREASED_DAMAGE_DEFAULT)
                .defineInRange("storm_talisman_increased_damage", 0.15, 0.0, 5.0);
        SERVER_BUILDER.pop();
    }
    private static void sunConfig() {
        SERVER_BUILDER.push(CATEGORY_SUN_TALISMAN);
        SUN_TALISMAN_INCREASED_DAMAGE = SERVER_BUILDER
                .comment(SUN_TALISMAN_INCREASED_DAMAGE_DESC)
                .comment(SUN_TALISMAN_INCREASED_DAMAGE_DEFAULT)
                .defineInRange("sun_talisman_increased_damage", 0.05, 0.0, 5.0);
        SERVER_BUILDER.pop();
    }
    private static void trueSunConfig() {
        SERVER_BUILDER.push(CATEGORY_TRUE_SUN_TALISMAN);
        TRUE_SUN_TALISMAN_INCREASED_DAMAGE = SERVER_BUILDER
                .comment(TRUE_SUN_TALISMAN_INCREASED_DAMAGE_DESC)
                .comment(TRUE_SUN_TALISMAN_INCREASED_DAMAGE_DEFAULT)
                .defineInRange("true_sun_talisman_increased_damage", 0.05, 0.0, 5.0);
        SERVER_BUILDER.pop();
    }
    private static void undyingConfig() {
        SERVER_BUILDER.push(CATEGORY_UNDYING_TALISMAN);
        UNDYING_TALISMAN_COOLDOWN = SERVER_BUILDER
                .comment(UNDYING_TALISMAN_COOLDOWN_DESC)
                .comment(UNDYING_TALISMAN_COOLDOWN_DEFAULT)
                .defineInRange("undying_talisman_cooldown", 24000, 0, 100000);
        UNDYING_TALISMAN_TELEPORT_DISTANCE = SERVER_BUILDER
                .comment(UNDYING_TALISMAN_TELEPORT_DISTANCE_DESC)
                .comment(UNDYING_TALISMAN_TELEPORT_DISTANCE_DEFAULT)
                .defineInRange("undying_talisman_teleport_distance", 10, 0, 16);
        SERVER_BUILDER.pop();
    }
    private static void vampiricConfig() {
        SERVER_BUILDER.push(CATEGORY_VAMPIRIC_TALISMAN);
        VAMPIRIC_TALISMAN_HEAL_FACTOR = SERVER_BUILDER
                .comment(VAMPIRIC_TALISMAN_HEAL_FACTOR_DESC)
                .comment(VAMPIRIC_TALISMAN_HEAL_FACTOR_DEFAULT)
                .defineInRange("vampiric_talisman_heal_factor", 0.2, 0.0, 5.0);
        SERVER_BUILDER.pop();
    }
    private static void vindicatorConfig() {
        SERVER_BUILDER.push(CATEGORY_VINDICATOR_TALISMAN);
        VINDICATOR_TALISMAN_BOOST_COOLDOWN = SERVER_BUILDER
                .comment(VINDICATOR_TALISMAN_BOOST_COOLDOWN_DESC)
                .comment(VINDICATOR_TALISMAN_BOOST_COOLDOWN_DEFAULT)
                .defineInRange("vindicator_talisman_boost_cooldown", 600, 0, 5000);
        VINDICATOR_TALISMAN_BOOST_DAMAGE_MODIFIER = SERVER_BUILDER
                .comment(VINDICATOR_TALISMAN_BOOST_DAMAGE_MODIFIER_DESC)
                .comment(VINDICATOR_TALISMAN_BOOST_DAMAGE_MODIFIER_DEFAULT)
                .defineInRange("vindicator_talisman_boost_damage_modifier", 0.5, 0.0, 5.0);
        VINDICATOR_TALISMAN_WEAPON_SWAP_TIME = SERVER_BUILDER
                .comment(VINDICATOR_TALISMAN_WEAPON_SWAP_TIME_DESC)
                .comment(VINDICATOR_TALISMAN_WEAPON_SWAP_TIME_DEFAULT)
                .defineInRange("vindicator_talisman_weapon_swap_time", 60, 0, 5000);
        SERVER_BUILDER.pop();
    }
    private static void weakenedConfig() {
        SERVER_BUILDER.push(CATEGORY_WEAKENED_TALISMAN);
        WEAKENED_TALISMAN_INCREASED_DAMAGE = SERVER_BUILDER
                .comment(WEAKENED_TALISMAN_INCREASED_DAMAGE_DESC)
                .comment(WEAKENED_TALISMAN_INCREASED_DAMAGE_DEFAULT)
                .defineInRange("weakened_talisman_increased_damage", 0.25, 0.0, 5.0);
        SERVER_BUILDER.pop();
    }
    private static void witheredConfig() {
        SERVER_BUILDER.push(CATEGORY_WITHERED_TALISMAN);
        WITHERED_TALISMAN_INCREASED_DAMAGE = SERVER_BUILDER
                .comment(WITHERED_TALISMAN_INCREASED_DAMAGE_DESC)
                .comment(WITHERED_TALISMAN_INCREASED_DAMAGE_DEFAULT)
                .defineInRange("withered_talisman_increased_damage", 0.25, 0.0, 5.0);
        SERVER_BUILDER.pop();
    }
    private static void zombieConfig() {
        SERVER_BUILDER.push(CATEGORY_ZOMBIE_TALISMAN);
        ZOMBIE_TALISMAN_ATTACK_SPEED_BOOST = SERVER_BUILDER
                .comment(ZOMBIE_TALISMAN_ATTACK_SPEED_BOOST_DESC)
                .comment(ZOMBIE_TALISMAN_ATTACK_SPEED_BOOST_DEFAULT)
                .defineInRange("zombie_talisman_attack_speed_boost", 5.5, 0.0, 10.0);
        ZOMBIE_TALISMAN_MAX_ATTACKS = SERVER_BUILDER
                .comment(ZOMBIE_TALISMAN_MAX_ATTACKS_DESC)
                .comment(ZOMBIE_TALISMAN_MAX_ATTACKS_DEFAULT)
                .defineInRange("zombie_talisman_max_attacks", 3, 0, 10);
        ZOMBIE_TALISMAN_COOLDOWN_PERIOD = SERVER_BUILDER
                .comment(ZOMBIE_TALISMAN_COOLDOWN_PERIOD_DESC)
                .comment(ZOMBIE_TALISMAN_COOLDOWN_PERIOD_DEFAULT)
                .defineInRange("zombie_talisman_cooldown_period", 5000, 0, 100000);
        SERVER_BUILDER.pop();
    }
    private static void soulOfSupersonicConfig() {
        SERVER_BUILDER.push(CATEGORY_SOUL_OF_SUPERSONIC);
        SOUL_OF_SUPERSONIC_WALK_SPEED_MULTIPLIER = SERVER_BUILDER
                .comment(SOUL_OF_SUPERSONIC_WALK_SPEED_MULTIPLIER_DESC)
                .comment(SOUL_OF_SUPERSONIC_WALK_SPEED_MULTIPLIER_DEFAULT)
                .defineInRange("soul_of_supersonic_walk_speed_multiplier", 1.75, 0.0, 5.0);
        SOUL_OF_SUPERSONIC_FLY_SPEED_MULTIPLIER = SERVER_BUILDER
                .comment(SOUL_OF_SUPERSONIC_FLY_SPEED_MULTIPLIER_DESC)
                .comment(SOUL_OF_SUPERSONIC_FLY_SPEED_MULTIPLIER_DEFAULT)
                .defineInRange("soul_of_supersonic_fly_speed_multiplier", 2.5, 0.0, 5.0);
        SERVER_BUILDER.pop();
    }
    private static void soulOfColossusConfig() {
        SERVER_BUILDER.push(CATEGORY_SOUL_OF_COLOSSUS);
        SOUL_OF_COLOSSUS_HEALTH_MULTIPLIER = SERVER_BUILDER
                .comment(SOUL_OF_COLOSSUS_HEALTH_MULTIPLIER_DESC)
                .comment(SOUL_OF_COLOSSUS_HEALTH_MULTIPLIER_DEFAULT)
                .defineInRange("soul_of_colossus_health_multiplier", 4.0, 0.0, 10.0);
        SOUL_OF_COLOSSUS_REMOVE_NEGATIVE_EFFECTS = SERVER_BUILDER
                .comment(SOUL_OF_COLOSSUS_REMOVE_NEGATIVE_EFFECTS_DESC)
                .comment(SOUL_OF_COLOSSUS_REMOVE_NEGATIVE_EFFECTS_DEFAULT)
                .define("soul_of_colossus_remove_negative_effects", true);
        SERVER_BUILDER.pop();
    }

}
