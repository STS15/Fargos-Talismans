package com.sts15.fargos.utils;

import com.sts15.fargos.config.PlayerDataHandler;
import com.sts15.fargos.config.ServerConfig;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class TalismanUtil {

    public static boolean isTalismanEnabled(ServerPlayer player, String talismanName) {
        boolean playerTalismanState = PlayerDataHandler.getTalismanState(player, talismanName.toUpperCase());
        boolean serverTalismanState = ServerConfig.isTalismanEnabledServer(talismanName.toLowerCase());
        System.out.println(playerTalismanState);
        System.out.println(serverTalismanState);
        return playerTalismanState && serverTalismanState;
    }
    public static boolean isTalismanEnabled(Player player, String talismanName) {
        boolean playerTalismanState = PlayerDataHandler.getTalismanState(player, talismanName.toUpperCase());
        boolean serverTalismanState = ServerConfig.isTalismanEnabledServer(talismanName.toLowerCase());
        return playerTalismanState && serverTalismanState;
    }
}
