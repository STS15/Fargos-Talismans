package com.sts15.fargos.compat;

import com.sts15.fargos.Fargos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import vazkii.patchouli.api.PatchouliAPI;

public class PatchouliHandler {

    public static void openBookGUI(ServerPlayer player) {
        PatchouliAPI.get().openBookGUI(player, ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "book"));
    }

}
