package com.sts15.fargos;

import com.sts15.fargos.client.command.*;
import com.sts15.fargos.config.ServerConfig;
import com.sts15.fargos.items.ItemInit;
import com.sts15.fargos.init.CreativeTabRegistry;
import com.sts15.fargos.init.MobEffectRegistry;
import com.sts15.fargos.init.SoundRegistry;
import com.sts15.fargos.network.NetworkHandler;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import java.util.Objects;

@Mod(Fargos.MODID)
public class Fargos {
    public static final String MODID = "fargostalismans";

    public Fargos(IEventBus modEventBus, ModContainer modContainer) {

        Objects.requireNonNull(modContainer.getEventBus()).addListener(this::registerPayloads);

        ItemInit.register(modEventBus);
        SoundRegistry.SOUNDS.register(modEventBus);
        CreativeTabRegistry.register(modEventBus);
        MobEffectRegistry.register(modEventBus);

        modContainer.registerConfig(ModConfig.Type.SERVER, ServerConfig.SERVER_SPEC);

        NeoForge.EVENT_BUS.register(this);
    }

    private void registerPayloads(RegisterPayloadHandlersEvent event) {
        NetworkHandler.registerPackets(event);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        TalismanCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterClientCommandsEvent event) {
        TalismanScreenCommand.register(event.getDispatcher());
    }

}
