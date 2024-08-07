package com.sts15.fargos.items.talismans;

import java.util.List;
import java.util.UUID;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.items.TalismanItem;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import top.theillusivec4.curios.api.CuriosApi;

public class Void_Talisman extends TalismanItem implements Void_Talisman_Provider {

    public Void_Talisman() {
        super(new Item.Properties().rarity(Rarity.UNCOMMON));
    }
    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        pTooltipComponents.add(Component.translatable("item.fargostalismans.tooltip.void_talisman")
        		.setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    }
    
    @EventBusSubscriber(modid = Fargos.MODID)
    public static class Events {
        
        @SuppressWarnings({ "removal", "deprecation" })
        @SubscribeEvent
        public static void onPlayerTick(PlayerTickEvent.Pre event) {
            if (!(event.getEntity() instanceof Player player))
                return;

            if (CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof Void_Talisman_Provider, player).isPresent()) {
            	int minY = player.level().dimensionType().minY();
                int maxY = player.level().dimensionType().logicalHeight();
                if (player.getY() < (minY - 40)) {
                    player.teleportTo(player.getX(), maxY, player.getZ());
                }
            }
        }
    }
}
