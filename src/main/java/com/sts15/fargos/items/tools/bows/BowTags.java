package com.sts15.fargos.items.tools.bows;

import com.sts15.fargos.items.tools.BasicBow;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.nbt.CompoundTag;

/** Shared helpers for bow tagging. */
final class BowTags {
    static final String TAG_BOW_KIND = "FargosBowKind";
    static final String KIND_TSUNAMI = "tsunami";
    static final String KIND_AERIAL  = "aerial_bane";
    static final String KIND_DAEDALUS= "daedalus";
    static final String KIND_HELLWING= "hellwing";
    static final String KIND_TENDON  = "tendon";
    static final String KIND_ICE     = "ice";
    static final String KIND_DEMON   = "demon";
    static final String KIND_BLOOD   = "blood_rain";
    static final String KIND_MECH    = "mechanics";
    static final String KIND_VORTEX  = "vortex";
    static final String KIND_EVENTIDE= "eventide";
    private BowTags() {}
    static void tagArrow(AbstractArrow a, String kind) {
        CompoundTag t = a.getPersistentData();
        t.putString(TAG_BOW_KIND, kind);
    }
}