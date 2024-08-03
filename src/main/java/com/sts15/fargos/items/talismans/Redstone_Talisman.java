package com.sts15.fargos.items.talismans;

import java.util.List;
import java.util.Random;

import com.sts15.fargos.items.TalismanItem;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import top.theillusivec4.curios.api.SlotContext;
import net.minecraft.world.entity.player.Player;

public class Redstone_Talisman extends TalismanItem {
	
	private long lastRepairTime = 0;
    private long nextRepairInterval = 5000;
    private final Random random = new Random();

    public Redstone_Talisman() {
        super(new Item.Properties().rarity(Rarity.UNCOMMON));
    }
    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        pTooltipComponents.add(Component.translatable("item.fargostalismans.tooltip.redstone_talisman")
        		.setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    }
    
    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        long currentTime = System.currentTimeMillis();

        repairArmorOverTime(player, currentTime);
    }
    
    private void repairArmorOverTime(Player player, long currentTime) {
        if (currentTime - lastRepairTime > nextRepairInterval) {
            player.getInventory().armor.forEach(this::repairSingleArmorItem);
            lastRepairTime = currentTime;
            nextRepairInterval = 5000 + random.nextInt(2500);
        }
    }
    
    private void repairSingleArmorItem(ItemStack armorItem) {
        if (armorItem.isDamaged()) {
            armorItem.setDamageValue(armorItem.getDamageValue() - 1);
        }
    }
    
}
