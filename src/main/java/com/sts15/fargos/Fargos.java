package com.sts15.fargos;

import com.sts15.fargos.block.BlocksInit;
import com.sts15.fargos.block.entity.BlockEntitiesInit;
import com.sts15.fargos.block.entity.renderer.PedestalBlockEntityRenderer;
import com.sts15.fargos.client.command.*;
import com.sts15.fargos.client.elytra.MyElytraLayer;
import com.sts15.fargos.client.hud.FireUIRenderer;
import com.sts15.fargos.effect.EffectsInit;
import com.sts15.fargos.entity.attacks.death_sickle.DeathSickleRenderer;
import com.sts15.fargos.entity.attacks.targetLocked.TargetLockedRenderer;
import com.sts15.fargos.entity.eridanus.*;
import com.sts15.fargos.init.*;
import com.sts15.fargos.items.ItemInit;
import com.sts15.fargos.loot.LootRegistry;
import com.sts15.fargos.network.NetworkHandler;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import org.jetbrains.annotations.NotNull;

import javax.swing.text.html.parser.Entity;
import java.util.Objects;

@Mod(Fargos.MODID)
public class Fargos {
    public static final String MODID = "fargostalismans";

    public Fargos(IEventBus modEventBus, ModContainer modContainer) {
        Config.register(modContainer);
        Objects.requireNonNull(modContainer.getEventBus()).addListener(this::registerPayloads);

        ItemInit.register(modEventBus);
        SoundRegistry.SOUNDS.register(modEventBus);
        BlocksInit.register(modEventBus);
        BlockEntitiesInit.register(modEventBus);
        CreativeTabRegistry.register(modEventBus);
        EffectsInit.register(modEventBus);
        LootRegistry.register(modEventBus);
        ArmorMaterialRegistry.register(modEventBus);
        EntityRegistry.register(modEventBus);

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
        public static void registerScreens(RegisterMenuScreensEvent event) {
        }

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            NeoForge.EVENT_BUS.register(FireUIRenderer.class);
        }

        @SubscribeEvent
        public static void registerRenderers(EntityRenderersEvent.AddLayers event) {
            PlayerRenderer defaultRenderer = (PlayerRenderer) event.getSkin(PlayerSkin.Model.WIDE);
            PlayerRenderer slimRenderer = (PlayerRenderer) event.getSkin(PlayerSkin.Model.SLIM);

            if (defaultRenderer != null) {
                defaultRenderer.addLayer(new MyElytraLayer(defaultRenderer));
            }
            if (slimRenderer != null) {
                slimRenderer.addLayer(new MyElytraLayer(slimRenderer));
            }
        }

        @SubscribeEvent
        public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(EntityRegistry.ERIDANUS_BOSS.get(), EridanusRenderer::new);

            event.registerEntityRenderer(EntityRegistry.TARGET_LOCKED.get(), TargetLockedRenderer::new);
            event.registerEntityRenderer(EntityRegistry.DEATH_SICKLE.get(), DeathSickleRenderer::new);
        }


        @SubscribeEvent
        public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(BlockEntitiesInit.PEDESTAL_BE.get(), PedestalBlockEntityRenderer::new);
        }
    }

    @EventBusSubscriber(modid = Fargos.MODID, bus = EventBusSubscriber.Bus.MOD)
    public class CommonSetup {

        @SubscribeEvent
        public static void onAttributeCreate(EntityAttributeCreationEvent event) {
            event.put(EntityRegistry.ERIDANUS_BOSS.get(), EridanusBoss.prepareAttributes().build());

            event.put(EntityRegistry.TARGET_LOCKED.get(), EridanusBoss.prepareAttributes().build());
            event.put(EntityRegistry.DEATH_SICKLE.get(), EridanusBoss.prepareAttributes().build());
        }

    }

    public static ResourceLocation id(@NotNull String path) {
        return ResourceLocation.fromNamespaceAndPath(Fargos.MODID, path);
    }
}
