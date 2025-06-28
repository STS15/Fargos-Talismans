package com.sts15.fargos.config;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class PlayerDataHandler {

    private static final String TALISMAN_DATA_KEY = "TalismanData";

    public static void setTalismanState(ServerPlayer player, String talismanName, boolean isEnabled) {
        CompoundTag data = player.getPersistentData().getCompound(Player.PERSISTED_NBT_TAG);
        CompoundTag talismanData = data.getCompound(TALISMAN_DATA_KEY);
        talismanData.putBoolean(talismanName, isEnabled);
        data.put(TALISMAN_DATA_KEY, talismanData);
        player.getPersistentData().put(Player.PERSISTED_NBT_TAG, data);
    }

    public static void setTalismanState(LocalPlayer player, String talismanName, boolean isEnabled) {
        CompoundTag data = player.getPersistentData().getCompound(Player.PERSISTED_NBT_TAG);
        CompoundTag talismanData = data.getCompound(TALISMAN_DATA_KEY);
        talismanData.putBoolean(talismanName, isEnabled);
        data.put(TALISMAN_DATA_KEY, talismanData);
        player.getPersistentData().put(Player.PERSISTED_NBT_TAG, data);
    }

    public static boolean getTalismanState(ServerPlayer player, String talismanName) {
        CompoundTag data = player.getPersistentData().getCompound(Player.PERSISTED_NBT_TAG);
        CompoundTag talismanData = data.getCompound(TALISMAN_DATA_KEY);
        if (talismanData.contains(talismanName)) {
            boolean state = talismanData.getBoolean(talismanName);
            return state;
        }
        return true;
    }

    public static boolean getTalismanState(Player player, String talismanName) {
        if (player instanceof ServerPlayer serverPlayer) {
            return getTalismanState(serverPlayer, talismanName);
        } else if (player instanceof LocalPlayer localPlayer) {
            ServerPlayer serverPlayer = getServerPlayerFromLocal(localPlayer);
            if (serverPlayer != null) {
                return getTalismanState(serverPlayer, talismanName);
            }
        }
        return true;
    }

    public static ServerPlayer getServerPlayerFromLocal(LocalPlayer localPlayer) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.getSingleplayerServer() != null) {
            ServerPlayer serverPlayer = mc.getSingleplayerServer().getPlayerList().getPlayer(localPlayer.getUUID());
            if (serverPlayer != null) {} else {}
            return serverPlayer;
        }
        return null;
    }
}
