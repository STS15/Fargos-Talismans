package com.sts15.fargos.block.entity.crucibleofthecosmos;

import com.sts15.fargos.block.entity.BlockEntitiesInit;
import com.sts15.fargos.container.CrucibleOfTheCosmosMenu;
import com.sts15.fargos.container.inventory.CrucibleCraftingInventory;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import javax.annotation.Nullable;

public class CrucibleOfTheCosmosBlockEntity extends BlockEntity implements MenuProvider {
    private final CrucibleCraftingInventory inventory;

    public CrucibleOfTheCosmosBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntitiesInit.COTC_BE.get(), pos, state);
        this.inventory = new CrucibleCraftingInventory(null, new ItemStackHandler(16));
    }

    public CrucibleCraftingInventory getInventory() {
        return inventory;
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Crucible Of The Cosmos");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player) {
        return new CrucibleOfTheCosmosMenu(id, playerInventory, this.inventory, this.getBlockPos());
    }

}
