package com.sts15.fargos.config;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class PlayerDataHandler {

    private static final String TALISMAN_DATA_KEY = "TalismanData";

    public static void setTalismanState(ServerPlayer player, String talismanName, boolean isEnabled) {
        //System.out.println("Setting talisman state for player: " + player.getName().getString() + ", Talisman: " + talismanName + ", Enabled: " + isEnabled);

        CompoundTag data = player.getPersistentData().getCompound(Player.PERSISTED_NBT_TAG);
        CompoundTag talismanData = data.getCompound(TALISMAN_DATA_KEY);
        talismanData.putBoolean(talismanName, isEnabled);
        data.put(TALISMAN_DATA_KEY, talismanData);
        player.getPersistentData().put(Player.PERSISTED_NBT_TAG, data);

        //System.out.println("Talisman state saved. NBT data: " + getTalismanState(player, talismanName));
    }

    public static void setTalismanState(LocalPlayer player, String talismanName, boolean isEnabled) {
        //System.out.println("Setting talisman state for player: " + player.getName().getString() + ", Talisman: " + talismanName + ", Enabled: " + isEnabled);

        CompoundTag data = player.getPersistentData().getCompound(Player.PERSISTED_NBT_TAG);
        CompoundTag talismanData = data.getCompound(TALISMAN_DATA_KEY);
        talismanData.putBoolean(talismanName, isEnabled);
        data.put(TALISMAN_DATA_KEY, talismanData);
        player.getPersistentData().put(Player.PERSISTED_NBT_TAG, data);

        //System.out.println("Talisman state saved. NBT data: " + getTalismanState(player, talismanName));
    }

    public static boolean getTalismanState(ServerPlayer player, String talismanName) {
        //System.out.println("Retrieving talisman state for player: " + player.getName().getString() + ", Talisman: " + talismanName);

        CompoundTag data = player.getPersistentData().getCompound(Player.PERSISTED_NBT_TAG);
        CompoundTag talismanData = data.getCompound(TALISMAN_DATA_KEY);

        if (talismanData.contains(talismanName)) {
            boolean state = talismanData.getBoolean(talismanName);
            //System.out.println("Talisman state found: " + state);
            return state;
        }

        //System.out.println("Talisman state not found, defaulting to enabled.");
        return true;
    }

    public static boolean getTalismanState(Player player, String talismanName) {
        //System.out.println("Checking talisman state for player: " + player.getName().getString() + ", Talisman: " + talismanName);

        if (player instanceof ServerPlayer serverPlayer) {
            //System.out.println("Player is a ServerPlayer, proceeding to retrieve state.");
            return getTalismanState(serverPlayer, talismanName);
        } else if (player instanceof LocalPlayer localPlayer) {
            //System.out.println("Player is a LocalPlayer, attempting to retrieve corresponding ServerPlayer.");
            ServerPlayer serverPlayer = getServerPlayerFromLocal(localPlayer);
            if (serverPlayer != null) {
                return getTalismanState(serverPlayer, talismanName);
            } else {
                //System.out.println("Failed to retrieve ServerPlayer for LocalPlayer: " + player.getName().getString());
            }
        }

        //System.out.println("Defaulting to enabled as no talisman state was found.");
        return true; // Default to true if no state is found
    }

    public static ServerPlayer getServerPlayerFromLocal(LocalPlayer localPlayer) {
        //System.out.println("Attempting to retrieve ServerPlayer from LocalPlayer: " + localPlayer.getName().getString());

        Minecraft mc = Minecraft.getInstance();
        if (mc.getSingleplayerServer() != null) {
            ServerPlayer serverPlayer = mc.getSingleplayerServer().getPlayerList().getPlayer(localPlayer.getUUID());
            if (serverPlayer != null) {
                //System.out.println("Found ServerPlayer: " + serverPlayer.getName().getString());
            } else {
                //System.out.println("ServerPlayer not found for LocalPlayer: " + localPlayer.getName().getString());
            }
            return serverPlayer;
        }

        //System.out.println("No integrated server available, cannot retrieve ServerPlayer.");
        return null;
    }
}
