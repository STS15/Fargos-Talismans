package com.sts15.fargos.items.talismans;

import java.util.List;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.effect.EffectsInit;
import com.sts15.fargos.items.TalismanItem;

import com.sts15.fargos.items.providers.Earth_Talisman_Provider;
import com.sts15.fargos.utils.TalismanUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import top.theillusivec4.curios.api.CuriosApi;

public class Earth_Talisman extends TalismanItem implements Earth_Talisman_Provider {
    private static final String talismanName = "earth_talisman";

    public Earth_Talisman() {
        super(new Item.Properties().rarity(Rarity.UNCOMMON));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {

        tooltipComponents.add(Component.translatable("item.fargostalismans.tooltip."+talismanName)
                .setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
    
    @EventBusSubscriber(modid = Fargos.MODID)
    public static class Events {
        @SubscribeEvent
        public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
            if (!(event.getEntity() instanceof ServerPlayer player))
                return;
            
            if (player.hasEffect(EffectsInit.EARTH_TALISMAN_EFFECT) || CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof Earth_Talisman_Provider, player).isPresent()) {
                if (!TalismanUtil.isTalismanEnabled(player, talismanName))
                    return;
                if(event.getLevel().getBlockState(event.getPos()).is(BlockTags.DIRT)){
    				if(!event.getLevel().isClientSide()){
    					event.getLevel().setBlock(new BlockPos(event.getPos().getX(), event.getPos().getY(), event.getPos().getZ()), Blocks.GRASS_BLOCK.defaultBlockState(), 3);
    				}
    			}
    	    }
        }
    }
}
