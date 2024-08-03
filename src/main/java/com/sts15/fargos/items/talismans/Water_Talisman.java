package com.sts15.fargos.items.talismans;

import java.util.List;

import com.sts15.fargos.items.TalismanItem;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingBreatheEvent;
import top.theillusivec4.curios.api.CuriosApi;
import net.minecraft.world.item.Item.TooltipContext;

public class Water_Talisman extends TalismanItem {

    public Water_Talisman() {
        super(new Item.Properties().rarity(Rarity.UNCOMMON));
    }
    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        pTooltipComponents.add(Component.translatable("item.fargostalismans.tooltip.water_talisman")
        		.setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    }
    
    @EventBusSubscriber
    public static class Events {
        @SubscribeEvent
        public static void onLivingBreath(LivingBreatheEvent event) {
        	if (!(event.getEntity() instanceof Player player))
                return;
            
                if (CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof Water_Talisman, player).isPresent()) {
                    event.setConsumeAirAmount(0);
                }
        }
    }
}
