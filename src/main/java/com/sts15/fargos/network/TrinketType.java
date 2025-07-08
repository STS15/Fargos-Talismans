package com.sts15.fargos.network;

public enum TrinketType {
    ANKH_SHIELD,
    BEE_CLOAK,
    BRAIN_OF_CONFUSION,
    CHARM_OF_MYTHS,
    FROZEN_SHIELD,
    HAND_WARMER,
    HERO_SHIELD,
    OBSIDIAN_HORSESHOE,
    POCKET_MIRROR,
    SHINY_STONE,
    STAR_VEIL,
    WORM_SCARF,
    AEOLUS_BOOTS,
    BRAIN_SCRAMBLER,
    REINDEER_BELLS,
    ANCIENT_HORN,
    MECHANICAL_CART,
    BLESSED_APPLE,
    MASTER_NINJA_GEAR,
    SHIELD_OF_CTHULHU,
    BUNDLE_OF_HORSESHOE_BALLOONS,
    AMBER_HORSESHOE_BALLOON,
    SWEETHEART_NECKLACE,
    FLYING_CARPET
    ;

    public int getIndex() {
        return this.ordinal();
    }

    public static TrinketType byIndex(int index) {
        return values()[index];
    }
}

