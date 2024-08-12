package com.sts15.fargos;

import com.sts15.fargos.client.command.*;
import com.sts15.fargos.items.ItemInit;
import com.sts15.fargos.blocks.BlockInit;
import com.sts15.fargos.init.CreativeTabRegistry;
import com.sts15.fargos.init.MobEffectRegistry;
import com.sts15.fargos.init.SoundRegistry;
import com.sts15.fargos.network.NetworkHandler;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import java.util.Objects;

@Mod(Fargos.MODID)
public class Fargos {
    public static final String MODID = "fargostalismans";

    public Fargos(IEventBus modEventBus, ModContainer modContainer) {

        // Register payloads for network communication
        Objects.requireNonNull(modContainer.getEventBus()).addListener(this::registerPayloads);

        // Register other mod elements
        ItemInit.register(modEventBus);
        BlockInit.register(modEventBus);
        SoundRegistry.SOUNDS.register(modEventBus);
        CreativeTabRegistry.register(modEventBus);
        MobEffectRegistry.register(modEventBus);

        // Register the client configuration
        modContainer.registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_SPEC);

        // Register this mod with the global NeoForge event bus
        NeoForge.EVENT_BUS.register(this);
    }

    // Method to register network payload handlers
    private void registerPayloads(RegisterPayloadHandlersEvent event) {
        NetworkHandler.registerPackets(event);
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        TalismanCommand.register(event.getDispatcher());
        SimpleMessageCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Perform any necessary setup when the server starts
    }
}
