package com.sts15.fargos.network;

public enum TalismanType {
    AIR_TALISMAN,
    AMETHYST_TALISMAN,
    APPLE_TALISMAN,
    ARCHITECT_TALISMAN,
    ARCTIC_TALISMAN,
    BATTLE_TALISMAN,
    BLAZE_TALISMAN,
    //BLINDED_TALISMAN,
    CACTUS_TALISMAN,
    COPPER_TALISMAN,
    CREEPER_TALISMAN,
    //DAY_TALISMAN,
    DIAMOND_TALISMAN,
    DRAGON_TALISMAN,
    EARTH_TALISMAN,
    EMERALD_TALISMAN,
    ENCHANTING_TALISMAN,
    ENDERMAN_TALISMAN,
    //FATIGUED_TALISMAN,
    FIRE_TALISMAN,
    //FIRED_TALISMAN,
    //FULL_MOON_TALISMAN,
    GHAST_TALISMAN,
    GLOWSTONE_TALISMAN,
    GOLD_TALISMAN,
    IRON_GOLEM_TALISMAN,
    IRON_TALISMAN,
    LAPIS_TALISMAN,
    LIBRARIAN_TALISMAN,
    MOOSHROOM_TALISMAN,
    //NAUSEATED_TALISMAN,
    NETHER_STAR_TALISMAN,
    //NIGHT_TALISMAN,
    OBSIDIAN_TALISMAN,
    PICKAXE_TALISMAN,
    //POISONED_TALISMAN,
    //RAIN_TALISMAN,
    REDSTONE_TALISMAN,
    SHULKER_TALISMAN,
    SKELETON_TALISMAN,
    //SLOWED_TALISMAN,
    //SNOWY_TALISMAN,
    SPECTRAL_TALISMAN,
    //STORM_TALISMAN,
    //SUN_TALISMAN,
    THORNY_TALISMAN,
    //TRUE_SUN_TALISMAN,
    UNDYING_TALISMAN,
    VAMPIRIC_TALISMAN,
    VINDICATOR_TALISMAN,
    VOID_TALISMAN,
    WATER_TALISMAN,
    //WEAKENED_TALISMAN,
    WITCH_TALISMAN,
    WITHER_TALISMAN,
    //WITHERED_TALISMAN,
    ZOMBIE_TALISMAN,
    SOUL_OF_COLOSSUS,
    SOUL_OF_FLIGHT_MASTERY,
    SOUL_OF_SUPERSONIC

    ;

    public int getIndex() {
        return this.ordinal();
    }

    public static TalismanType byIndex(int index) {
        return values()[index];
    }
}

