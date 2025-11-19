package com.sts15.fargos.datagen;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class FargosDataProvider implements DataProvider {

    private final List<DataProvider> subProviders = new ArrayList<>();

    public void addSubProvider(boolean include, DataProvider provider) {
        if (include) subProviders.add(provider);
    }

    @Override
    @NotNull
    public CompletableFuture<?> run(@NotNull CachedOutput cachedOutput) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        for (DataProvider provider : subProviders) {
            tasks.add(provider.run(cachedOutput));
        }
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    @NotNull
    public String getName() {
        return "Fargo's Talismans Data Provider";
    }
}
