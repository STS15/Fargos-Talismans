package com.sts15.fargos.config;

import net.minecraft.server.level.ServerPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ServerConfigManager {
    private static final Map<UUID, Map<String, Boolean>> playerConfigs = new HashMap<>();

    public static void updatePlayerConfig(ServerPlayer player, Map<String, Boolean> talismanConfig) {
        System.out.println("Updating config for player: " + player.getName().getString() + " with config: " + talismanConfig);
        playerConfigs.put(player.getUUID(), talismanConfig);
    }

    public static Map<String, Boolean> getPlayerConfig(ServerPlayer player) {
        return playerConfigs.getOrDefault(player.getUUID(), new HashMap<>());
    }

    public static void removePlayerConfig(ServerPlayer player) {
        playerConfigs.remove(player.getUUID());
    }
}
