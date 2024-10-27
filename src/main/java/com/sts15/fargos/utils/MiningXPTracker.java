package com.sts15.fargos.utils;

import net.minecraft.world.entity.player.Player;

public class MiningXPTracker {
    public static final ThreadLocal<Player> CURRENT_MINING_PLAYER = new ThreadLocal<>();

    public static void setMiningPlayer(Player player) {
        CURRENT_MINING_PLAYER.set(player);
    }

    public static void clearMiningPlayer() {
        CURRENT_MINING_PLAYER.remove();
    }
}
