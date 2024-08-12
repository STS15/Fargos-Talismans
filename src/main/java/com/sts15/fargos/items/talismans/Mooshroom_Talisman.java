package com.sts15.fargos.items.talismans;

import java.util.List;

import com.sts15.fargos.Config;
import com.sts15.fargos.Fargos;
import com.sts15.fargos.items.TalismanItem;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.MyceliumBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;

public class Mooshroom_Talisman extends TalismanItem {

    private static final String talismanName = "mooshroom_talisman";

    public Mooshroom_Talisman() {
        super(new Item.Properties().rarity(Rarity.UNCOMMON));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {

        if (!Config.isTalismanEnabledServer(talismanName)) {
            tooltipComponents.add(Component.translatable("item.fargostalismans.tooltip.disabled_by_server")
                    .setStyle(Style.EMPTY.withColor(ChatFormatting.RED)));
        } else if (!Config.isTalismanEnabledClient(talismanName)) {
            tooltipComponents.add(Component.translatable("item.fargostalismans.tooltip.disabled_by_client")
                    .setStyle(Style.EMPTY.withColor(ChatFormatting.RED)));
        } else {
            tooltipComponents.add(Component.translatable("item.fargostalismans.tooltip."+talismanName)
                    .setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
        }

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
    
    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
      
    }
    
    @EventBusSubscriber(modid = Fargos.MODID)
    public static class Events {
    	
        @SuppressWarnings({ "removal", "deprecation" })
		@SubscribeEvent
        public static void onPlayerTick(PlayerTickEvent.Pre event) {
        	if (!(event.getEntity() instanceof Player player))
                return;

            if (CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof Mooshroom_Talisman, player).isPresent()) {
                if (!Config.isTalismanEnabledOnClientAndServer(talismanName))
                    return;
                BlockPos belowPlayer = player.blockPosition().below();
    	        BlockState blockBelow = player.level().getBlockState(belowPlayer);
    	        if (blockBelow.getBlock() instanceof MyceliumBlock) {
    	            player.heal(0.05F);
    	        }
            }
        }
        
    }
}
