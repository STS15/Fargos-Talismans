package com.sts15.fargos.datagen;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.datagen.providers.FargosLootModifierProvider;
import com.sts15.fargos.datagen.providers.FargosLootTableProvider;
import com.sts15.fargos.datagen.providers.FargosRecipeProvider;
import com.sts15.fargos.datagen.tags.FargosItemTagProvider;
import net.minecraft.Util;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.registries.VanillaRegistries;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = Fargos.MODID, bus = EventBusSubscriber.Bus.MOD)
public class FargosDataGenerator {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {

        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = CompletableFuture.supplyAsync(VanillaRegistries::createLookup, Util.backgroundExecutor());
        FargosDataProvider provider = new FargosDataProvider();

        provider.addSubProvider(event.includeServer(), new FargosItemTagProvider(packOutput, lookupProvider, fileHelper));
        provider.addSubProvider(event.includeServer(), new FargosLootModifierProvider(packOutput, lookupProvider));
        provider.addSubProvider(event.includeServer(), new FargosLootTableProvider(packOutput, lookupProvider));
        provider.addSubProvider(event.includeServer(), new FargosRecipeProvider(packOutput, lookupProvider, fileHelper));

        generator.addProvider(true, provider);
    }
}