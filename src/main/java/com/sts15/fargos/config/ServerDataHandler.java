package com.sts15.fargos.config;

import com.sts15.fargos.network.NetworkHandler;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;

public class ServerDataHandler {

    private static final String SERVER_TALISMAN_DATA_KEY = "ServerTalismanData";
    private static final Map<String, Boolean> serverTalismanStates = new HashMap<>();
    private static boolean requestSent = false;  // Track if a request has already been sent

    // Check if the server data is empty, and if so, send a network request to the server to get the data
    public static boolean isTalismanEnabledOnServer(String talismanName, LocalPlayer localPlayer) {
        // Check if the data is present
        if (serverTalismanStates.isEmpty() && !requestSent) {
            // Send the request to the server since we don't have the data
            System.out.println("Server data is empty, requesting server talisman states...");
            requestSent = true;  // Mark that the request has been sent
            NetworkHandler.sendServerTalismanConfigRequestToServer(localPlayer);  // Pass the local player to identify the requestor
            return true; // Return default true while waiting for the server response
        }

        // If we have data, return the state of the specific talisman
        return serverTalismanStates.getOrDefault(talismanName, true); // Default to true if not found
    }

    // Save the received talisman states on the client side
    public static void setServerTalismanStates(Map<String, Boolean> talismanStates) {
        serverTalismanStates.clear();
        serverTalismanStates.putAll(talismanStates);
        System.out.println("Server talisman states updated on client: " + serverTalismanStates);
        requestSent = false;  // Reset the request flag once we have data
    }

    // Helper function to save the server talisman states into the player's persistent data
    public static void saveServerTalismanStatesToPlayer(LocalPlayer player) {
        CompoundTag data = player.getPersistentData().getCompound(Player.PERSISTED_NBT_TAG);
        CompoundTag serverTalismanData = new CompoundTag();

        for (Map.Entry<String, Boolean> entry : serverTalismanStates.entrySet()) {
            serverTalismanData.putBoolean(entry.getKey(), entry.getValue());
        }

        data.put(SERVER_TALISMAN_DATA_KEY, serverTalismanData);
        player.getPersistentData().put(Player.PERSISTED_NBT_TAG, data);
    }

    // Helper function to load the server talisman states from the player's persistent data
    public static void loadServerTalismanStatesFromPlayer(LocalPlayer player) {
        CompoundTag data = player.getPersistentData().getCompound(Player.PERSISTED_NBT_TAG);
        CompoundTag serverTalismanData = data.getCompound(SERVER_TALISMAN_DATA_KEY);

        serverTalismanStates.clear();
        for (String key : serverTalismanData.getAllKeys()) {
            serverTalismanStates.put(key, serverTalismanData.getBoolean(key));
        }

        System.out.println("Loaded server talisman states from player NBT: " + serverTalismanStates);
    }
}
