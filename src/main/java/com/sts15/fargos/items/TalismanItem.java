package com.sts15.fargos.items;

import com.sts15.fargos.init.SoundRegistry;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public abstract class TalismanItem extends Item  implements ICurioItem {
    public TalismanItem(Item.Properties properties) {
        super(properties.stacksTo(1).fireResistant());
    }
    
    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return true;
    }
    
    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        playEquipSound(slotContext, stack);
    }
    
    @Override
    public void onUnequip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        playUnequipSound(slotContext, stack);
    }

    private void playEquipSound(SlotContext slotContext, ItemStack stack) {
        Level level = slotContext.entity().level();
        if (!level.isClientSide) {
            Player player = (Player) slotContext.entity();
            level.playSound(null, player.blockPosition(), SoundRegistry.EQUIP_TALISMAN.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
        }
    }
    
    private void playUnequipSound(SlotContext slotContext, ItemStack stack) {
        Level level = slotContext.entity().level();
        if (!level.isClientSide) {
            Player player = (Player) slotContext.entity();
            level.playSound(null, player.blockPosition(), SoundRegistry.UNEQUIP_TALISMAN.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
        }
    }

}
