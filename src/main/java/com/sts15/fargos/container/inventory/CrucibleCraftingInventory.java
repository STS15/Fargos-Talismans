package com.sts15.fargos.container.inventory;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.TransientCraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;

public class CrucibleCraftingInventory extends TransientCraftingContainer {
    private final AbstractContainerMenu container;
    private final ItemStackHandler inventory;

    public CrucibleCraftingInventory(AbstractContainerMenu container, ItemStackHandler inventory) {
        super(container, 4, 4);
        this.container = container;
        this.inventory = inventory;
    }

    @Override
    public int getContainerSize() {
        return this.inventory.getSlots();
    }

    @Override
    public ItemStack getItem(int slot) {
        return this.inventory.getStackInSlot(slot);
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < this.getContainerSize(); i++) {
            if (!this.inventory.getStackInSlot(i).isEmpty())
                return false;
        }
        return true;
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        this.inventory.setStackInSlot(slot, stack);
        if (this.container != null) {
            this.container.slotsChanged(this);
        }
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        var stack = this.inventory.extractItem(slot, amount, false);
        if (this.container != null) {
            this.container.slotsChanged(this);
        }
        return stack;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        var stack = this.inventory.getStackInSlot(slot);
        this.inventory.setStackInSlot(slot, ItemStack.EMPTY);
        return stack;
    }

    @Override
    public void clearContent() {
        for (int i = 0; i < this.getContainerSize(); i++) {
            this.inventory.setStackInSlot(i, ItemStack.EMPTY);
        }
    }
}

