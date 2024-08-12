package com.sts15.fargos.network;

import com.sts15.fargos.config.ServerConfigManager;
import com.sts15.fargos.network.packet.*;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.util.Map;

public class NetworkHandler {

    public static void registerPackets(RegisterPayloadHandlersEvent event) {
        System.out.println("Registering TalismanConfigPacket...");

        PayloadRegistrar registrar = event.registrar("fargostalismans").versioned("1.0").optional();

        registrar.playToServer(TalismanConfigPacket.TYPE, TalismanConfigPacket.STREAM_CODEC, TalismanConfigPacket::handle);
        //registrar.playToServer(SimpleMessagePacket.TYPE, SimpleMessagePacket::STREAM_CODEC, SimpleMessagePacket::handle);
    }

    public static void sendTalismanConfigToServer(LocalPlayer player, Map<String, Boolean> talismanStates) {
        TalismanConfigPacket packet = new TalismanConfigPacket(talismanStates);
        System.out.println("Sending talisman config to server: " + talismanStates);
        PacketDistributor.sendToServer(packet);
    }

    public static void sendSimpleMessageToServer(LocalPlayer player, String message) {
        SimpleMessagePacket packet = new SimpleMessagePacket(message);
        PacketDistributor.sendToServer(packet);
    }

    public static void handleTalismanConfigFromClient(ServerPlayer player, Map<String, Boolean> talismanStates) {
        System.out.println("Received talisman config from client: " + talismanStates);
        ServerConfigManager.updatePlayerConfig(player, talismanStates);
    }
}
