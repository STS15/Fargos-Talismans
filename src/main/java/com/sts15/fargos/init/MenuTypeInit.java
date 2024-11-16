package com.sts15.fargos.init;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.container.CrucibleOfTheCosmosMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class MenuTypeInit {
    private static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(Registries.MENU, Fargos.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<CrucibleOfTheCosmosMenu>> CRUCIBLE_OF_THE_COSMOS_MENU =
            registerMenuType("crucible_of_the_cosmos_menu", CrucibleOfTheCosmosMenu::new);

    private static <T extends AbstractContainerMenu>DeferredHolder<MenuType<?>, MenuType<T>> registerMenuType(String name, IContainerFactory<T> factory) {
        return CONTAINERS.register(name, () -> IMenuTypeExtension.create(factory));
    }

    public static void register(IEventBus eventBus) {
        CONTAINERS.register(eventBus);
    }
}