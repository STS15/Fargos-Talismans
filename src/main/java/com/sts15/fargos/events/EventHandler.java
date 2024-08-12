package com.sts15.fargos.events;

import com.sts15.fargos.Config;
import com.sts15.fargos.network.NetworkHandler;
import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;

@EventBusSubscriber
public class EventHandler {

    @SubscribeEvent
    public static void onPlayerJoin(PlayerLoggedInEvent event) {
        if (event.getEntity().level().isClientSide()) { // Ensure this only runs on the client side
            Minecraft client = Minecraft.getInstance();
            if (client.player != null && client.player.getUUID().equals(event.getEntity().getUUID())) {
                System.out.println("Sending talisman config to server from client.");
                NetworkHandler.sendTalismanConfigToServer(client.player, Config.talismanEnabledStates);
            }
        }
    }
}
