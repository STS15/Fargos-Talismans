package com.sts15.fargos.init;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import com.sts15.fargos.Fargos;

public class SoundRegistry {

    public static DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, Fargos.MODID);

    public static final Supplier<SoundEvent> EQUIP_TALISMAN = SOUNDS.register("equip_talisman", () -> loadSoundEvent("equip_talisman"));
    public static final Supplier<SoundEvent> UNEQUIP_TALISMAN = SOUNDS.register("unequip_talisman", () -> loadSoundEvent("unequip_talisman"));

    private static SoundEvent loadSoundEvent(String name) {
        ResourceLocation location = ResourceLocation.fromNamespaceAndPath(Fargos.MODID, name);
        return SoundEvent.createVariableRangeEvent(location);
    }
}