package com.sts15.fargos.utils;

import com.sts15.fargos.config.PlayerDataHandler;
import com.sts15.fargos.config.ServerConfig;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class TalismanUtil {

    /**
     * Checks if a talisman is enabled for the given player based on both the player's data and the server configuration.
     *
     * @param player The player to check the talisman state for.
     * @param talismanName The name of the talisman.
     * @return true if the talisman is enabled in both the player data and server config; false otherwise.
     */
    public static boolean isTalismanEnabled(ServerPlayer player, String talismanName) {
        boolean playerTalismanState = PlayerDataHandler.getTalismanState(player, talismanName.toUpperCase());
        boolean serverTalismanState = ServerConfig.isTalismanEnabledServer(talismanName.toLowerCase());
        return playerTalismanState && serverTalismanState;
    }
    public static boolean isTalismanEnabled(Player player, String talismanName) {
        boolean playerTalismanState = PlayerDataHandler.getTalismanState(player, talismanName.toUpperCase());
        boolean serverTalismanState = ServerConfig.isTalismanEnabledServer(talismanName.toLowerCase());
        return playerTalismanState && serverTalismanState;
    }
}
