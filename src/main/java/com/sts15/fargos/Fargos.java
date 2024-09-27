package com.sts15.fargos;

import com.sts15.fargos.block.BlocksInit;
import com.sts15.fargos.block.entity.BlockEntitiesInit;
import com.sts15.fargos.block.entity.renderer.PedestalBlockEntityRenderer;
import com.sts15.fargos.client.command.*;
import com.sts15.fargos.effect.EffectsInit;
import com.sts15.fargos.items.ItemInit;
import com.sts15.fargos.init.CreativeTabRegistry;
import com.sts15.fargos.init.SoundRegistry;
import com.sts15.fargos.loot.LootRegistry;
import com.sts15.fargos.network.NetworkHandler;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
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
        BlocksInit.register(modEventBus);
        BlockEntitiesInit.register(modEventBus);
        CreativeTabRegistry.register(modEventBus);
        EffectsInit.register(modEventBus);
        LootRegistry.register(modEventBus);

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

    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

        }

        @SubscribeEvent
        public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(BlockEntitiesInit.PEDESTAL_BE.get(), PedestalBlockEntityRenderer::new);
        }
    }

}
