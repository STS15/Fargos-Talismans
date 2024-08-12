package com.sts15.fargos.items.talismans;

import java.util.List;

import com.sts15.fargos.Config;
import com.sts15.fargos.Fargos;
import com.sts15.fargos.items.TalismanItem;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.item.Item.TooltipContext;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import top.theillusivec4.curios.api.CuriosApi;

public class Earth_Talisman extends TalismanItem implements Earth_Talisman_Provider {
    private static final String talismanName = "earth_talisman";

    public Earth_Talisman() {
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
    
    @EventBusSubscriber(modid = Fargos.MODID)
    public static class Events {
        @SubscribeEvent
        public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
            if (!(event.getEntity() instanceof Player player))
                return;
            
            if (CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof Earth_Talisman_Provider, player).isPresent()) {
                if (!Config.isTalismanEnabledOnClientAndServer(talismanName))
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
