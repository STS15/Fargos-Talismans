package com.sts15.fargos.container;

import com.sts15.fargos.block.BlocksInit;
import com.sts15.fargos.container.inventory.CrucibleCraftingInventory;
import com.sts15.fargos.container.slot.TableOutputSlot;
import com.sts15.fargos.init.MenuTypeInit;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Containers;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.items.ItemStackHandler;

public class CrucibleOfTheCosmosMenu extends AbstractContainerMenu {
    private final Level level;
    private final Container result;
    private final CrucibleCraftingInventory matrix;
    private final BlockPos pos;

    public CrucibleOfTheCosmosMenu(int id, Inventory playerInventory, CrucibleCraftingInventory inventory, BlockPos pos) {
        super(MenuTypeInit.CRUCIBLE_OF_THE_COSMOS_MENU.get(), id);
        this.level = playerInventory.player.level();
        this.result = new ResultContainer();
        this.matrix = inventory;
        this.pos = pos;
        this.addSlot(new TableOutputSlot(this, this.matrix, this.result, 0, 137, 52));
        for (int row = 0; row < 4; ++row) {
            for (int col = 0; col < 4; ++col) {
                int slotSizeWithGap = 18 + 1;
                int x = 24 + col * slotSizeWithGap;
                int y = 23 + row * slotSizeWithGap;
                int slotIndex = col + row * 4; // Indices 0 to 15
                this.addSlot(new Slot(this.matrix, slotIndex, x, y));
            }
        }

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
        this.slotsChanged(this.matrix);
    }

    public CrucibleOfTheCosmosMenu(int id, Inventory playerInventory, FriendlyByteBuf data) {
        this(id, playerInventory, new CrucibleCraftingInventory(null, new ItemStackHandler(16)), data.readBlockPos());
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, pos), player, BlocksInit.CRUCIBLE_OF_THE_COSMOS.get());
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        if (!player.level().isClientSide()) {
            Containers.dropContents(player.level(), player.blockPosition(), this.matrix);
        }
    }


    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 104 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 104+58));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack stackInSlot = slot.getItem();
            itemstack = stackInSlot.copy();

            if (index == 0) { // Result slot
                if (!this.moveItemStackTo(stackInSlot, 17, 53, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(stackInSlot, itemstack);
                // No need to call consumeCraftingItems() here; TableOutputSlot handles it
            } else if (index >= 1 && index <= 16) { // Crafting grid slots
                if (!this.moveItemStackTo(stackInSlot, 17, 53, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= 17 && index < 53) { // Player inventory and hotbar slots
                if (!this.moveItemStackTo(stackInSlot, 1, 17, false)) { // Try to move to crafting grid
                    return ItemStack.EMPTY;
                }
            } else {
                return ItemStack.EMPTY;
            }

            if (stackInSlot.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (stackInSlot.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, stackInSlot);
        }

        return itemstack;
    }


    private void consumeCraftingItems() {
        for (int i = 0; i < 16; i++) {
            ItemStack itemInSlot = this.matrix.getItem(i);
            if (!itemInSlot.isEmpty()) {
                itemInSlot.shrink(1);
                if (itemInSlot.getCount() == 0) {
                    this.matrix.setItem(i, ItemStack.EMPTY);
                }
            }
        }
    }

    @Override
    public void slotsChanged(Container inventory) {
        super.slotsChanged(inventory);
        if (inventory == this.matrix) {
            updateCraftingResult();
        }
    }

    private void updateCraftingResult() {
//        if (!this.level.isClientSide) {
//            Optional<ICrucibleRecipe> optional = this.level.getRecipeManager()
//                    .getRecipeFor(ModRecipeTypes.CRUCIBLE_CRAFTING, this.matrix, this.level);
//
//            if (optional.isPresent()) {
//                ItemStack resultItem = optional.get().assemble(this.matrix, this.level.registryAccess());
//                this.result.setItem(0, resultItem);
//            } else {
//                this.result.setItem(0, ItemStack.EMPTY);
//            }
//            this.broadcastChanges();
//        }
    }

}
