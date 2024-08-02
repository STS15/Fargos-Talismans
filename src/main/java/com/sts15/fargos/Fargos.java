package com.sts15.fargos;

import com.mojang.logging.LogUtils;

import com.sts15.fargos.items.ItemInit;
import com.sts15.fargos.blocks.BlockInit;
//import com.sts15.fargos.events.EventHandler;
import com.sts15.fargos.init.CreativeTabRegistry;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

@Mod(Fargos.MODID)
public class Fargos {
    public static final String MODID = "fargostalismans";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Fargos(IEventBus modEventBus, ModContainer modContainer) {
        BlockInit.register(modEventBus);
        ItemInit.register(modEventBus);
        CreativeTabRegistry.register(modEventBus);
        //modEventBus.register(EventHandler.class);
        NeoForge.EVENT_BUS.register(this);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) { }

}
