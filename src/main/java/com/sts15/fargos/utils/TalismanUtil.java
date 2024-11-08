package com.sts15.fargos.utils;

import com.sts15.fargos.config.PlayerDataHandler;
import com.sts15.fargos.init.Config;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.lang.reflect.Field;

public class TalismanUtil {

    public static boolean isTalismanEnabled(ServerPlayer player, String talismanName) {
        boolean playerTalismanState = PlayerDataHandler.getTalismanState(player, talismanName.toUpperCase());
        boolean configEnabledStatus = checkConfigEnabledStatus(talismanName);
        return playerTalismanState && configEnabledStatus;
    }

    public static boolean isTalismanEnabled(Player player, String talismanName) {
        boolean playerTalismanState = PlayerDataHandler.getTalismanState(player, talismanName.toUpperCase());
        boolean configEnabledStatus = checkConfigEnabledStatus(talismanName);
        return playerTalismanState && configEnabledStatus;
    }

    public static boolean checkConfigEnabledStatus(String talismanName) {
        boolean isEnabled = true;
        try {
            String fieldName = talismanName.toUpperCase() + "_TOGGLE";
            Field toggleField = Config.class.getField(fieldName);
            isEnabled = ((ModConfigSpec.BooleanValue) toggleField.get(null)).get();
        } catch (NoSuchFieldException | IllegalAccessException ignored) {}
        return isEnabled;
    }
}
