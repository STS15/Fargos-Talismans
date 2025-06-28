package com.sts15.fargos.utils;

import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;

import java.util.*;

public class WardenAttackTracker {
    private static final Map<Warden, Set<UUID>> attackMap = new WeakHashMap<>();

    public static void markAttacked(Warden warden, Player player) {
        attackMap.computeIfAbsent(warden, w -> new HashSet<>()).add(player.getUUID());
    }

    public static boolean hasAttacked(Warden warden, Player player) {
        return attackMap.getOrDefault(warden, Collections.emptySet()).contains(player.getUUID());
    }

    public static void clear(Warden warden) {
        attackMap.remove(warden);
    }
}
