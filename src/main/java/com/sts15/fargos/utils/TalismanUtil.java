package com.sts15.fargos.utils;

import com.sts15.fargos.config.PlayerDataHandler;
import com.sts15.fargos.config.ServerDataHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class TalismanUtil {

    public static boolean isTalismanEnabled(ServerPlayer player, String talismanName) {
        boolean playerTalismanState = PlayerDataHandler.getTalismanState(player, talismanName.toUpperCase());
        //boolean serverTalismanState = ServerDataHandler.isTalismanEnabledOnServer(talismanName.toLowerCase(), Minecraft.getInstance().player);
        return playerTalismanState /*&& serverTalismanState*/;
    }
    public static boolean isTalismanEnabled(Player player, String talismanName) {
        boolean playerTalismanState = PlayerDataHandler.getTalismanState(player, talismanName.toUpperCase());
        //boolean serverTalismanState = ServerDataHandler.isTalismanEnabledOnServer(talismanName.toLowerCase(), Minecraft.getInstance().player);
        return playerTalismanState /*&& serverTalismanState*/;
    }
}
