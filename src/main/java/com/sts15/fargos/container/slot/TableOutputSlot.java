package com.sts15.fargos.container.slot;

import com.sts15.fargos.container.CrucibleOfTheCosmosMenu;
import com.sts15.fargos.container.inventory.CrucibleCraftingInventory;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;

public class TableOutputSlot extends Slot {
    private final AbstractContainerMenu container;
    private final CrucibleCraftingInventory matrix;

    public TableOutputSlot(AbstractContainerMenu container, CrucibleCraftingInventory matrix, Container inventory, int index, int xPosition, int yPosition) {
        super(inventory, index, xPosition, yPosition);
        this.container = container;
        this.matrix = matrix;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return false; // Prevent placing items into the output slot
    }

//    @Override
//    public void onTake(Player player, ItemStack stack) {
//        NonNullList<ItemStack> remaining;
//
//        var level = player.level();
//        var inventory = this.matrix;
//
//        // Get the remaining items after crafting (e.g., empty buckets)
//        remaining = level.getRecipeManager().getRemainingItemsFor(ModRecipeTypes.CRUCIBLE_CRAFTING, inventory, level);
//
//        for (int k = 0; k < inventory.getHeight(); k++) {
//            for (int l = 0; l < inventory.getWidth(); l++) {
//                int index = l + k * inventory.getWidth();
//                ItemStack slotStack = this.matrix.getItem(index);
//
//                if (!slotStack.isEmpty()) {
//                    this.matrix.removeItem(index, 1);
//                }
//
//                ItemStack remainingStack = remaining.get(l + k * inventory.getWidth());
//                if (!remainingStack.isEmpty()) {
//                    if (slotStack.isEmpty()) {
//                        this.matrix.setItem(index, remainingStack);
//                    } else if (ItemStack.isSameItemSameTag(slotStack, remainingStack)) {
//                        remainingStack.grow(slotStack.getCount());
//                        this.matrix.setItem(index, remainingStack);
//                    } else if (!player.getInventory().add(remainingStack)) {
//                        player.drop(remainingStack, false);
//                    }
//                }
//            }
//        }
//
//        this.container.slotsChanged(this.matrix);
//    }
}
