package com.sts15.fargos.items.souls;

import java.util.List;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.items.TalismanItem;
import com.sts15.fargos.items.providers.Soul_of_Flight_Mastery_Provider;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import top.theillusivec4.curios.api.CuriosApi;

public class Soul_of_Flight_Mastery extends TalismanItem implements Soul_of_Flight_Mastery_Provider {

    public Soul_of_Flight_Mastery() {
        super(new Item.Properties().rarity(Rarity.EPIC));
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        pTooltipComponents.add(Component.translatable("item.fargostalismans.tooltip.soul_of_flight_mastery")
                .setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));

        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    }

    private static void enableFlight(Player player) {
        AttributeInstance flightAttribute = player.getAttribute(NeoForgeMod.CREATIVE_FLIGHT);
        if (flightAttribute != null && flightAttribute.getBaseValue() == 0 && !player.isCreative() && !player.isSpectator()) {
            flightAttribute.setBaseValue(1);
            //System.out.println("Flight enabled for player: " + player.getName().getString());
        }
    }

    private static void disableFlight(Player player) {
        AttributeInstance flightAttribute = player.getAttribute(NeoForgeMod.CREATIVE_FLIGHT);
        if (flightAttribute != null && flightAttribute.getBaseValue() == 1 && !player.isCreative() && !player.isSpectator()) {
            flightAttribute.setBaseValue(0);
            //System.out.println("Flight disabled for player: " + player.getName().getString());
        }
    }

    @EventBusSubscriber(modid = Fargos.MODID)
    public static class Events {

        @SuppressWarnings({"removal", "deprecation"})
        @SubscribeEvent
        public static void onPlayerTick(PlayerTickEvent.Pre event) {
            Player player = event.getEntity();
            boolean hasTalisman = CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof Soul_of_Flight_Mastery_Provider, player).isPresent();

            AttributeInstance flightAttribute = player.getAttribute(NeoForgeMod.CREATIVE_FLIGHT);
            if (flightAttribute == null) return;

            if (hasTalisman && flightAttribute.getBaseValue() == 0 && !player.isCreative() && !player.isSpectator()) {
                enableFlight(player);
            } else if (!hasTalisman && flightAttribute.getBaseValue() == 1 && !player.isCreative() && !player.isSpectator()) {
                disableFlight(player);
            }
        }
    }
}