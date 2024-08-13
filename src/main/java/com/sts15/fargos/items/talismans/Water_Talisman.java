package com.sts15.fargos.items.talismans;

import java.util.List;

import com.sts15.fargos.items.TalismanItem;

import com.sts15.fargos.network.packet.SyncAirStatusPacket;
import com.sts15.fargos.network.NetworkHandler;
import com.sts15.fargos.utils.TalismanUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingBreatheEvent;
import top.theillusivec4.curios.api.CuriosApi;

public class Water_Talisman extends TalismanItem implements Water_Talisman_Provider {

    private static final String talismanName = "water_talisman";

    public Water_Talisman() {
        super(new Item.Properties().rarity(Rarity.UNCOMMON));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {

        tooltipComponents.add(Component.translatable("item.fargostalismans.tooltip."+talismanName)
                .setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
    
    @EventBusSubscriber
    public static class Events {
        @SubscribeEvent
        public static void onLivingBreath(LivingBreatheEvent event) {
        	if ((event.getEntity() instanceof ServerPlayer player))
                if (CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof Water_Talisman_Provider, player).isPresent()) {
                    if (!TalismanUtil.isTalismanEnabled(player, talismanName))
                        return;
                    NetworkHandler.sendSyncAirStatusToClient(player, player.getAirSupply());
                    event.setConsumeAirAmount(0);
                }
        }
    }
}