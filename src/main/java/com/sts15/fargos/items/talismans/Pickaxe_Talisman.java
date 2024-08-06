package com.sts15.fargos.items.talismans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.items.TalismanItem;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import top.theillusivec4.curios.api.CuriosApi;

public class Pickaxe_Talisman extends TalismanItem {
	
	private static final double MINING_SPEED_BOOST = 2.0;
    private static final Map<UUID, AttributeModifier> miningSpeedModifiers = new HashMap<>();
    private static final ResourceLocation PICKAXE_MINING_SPEED_BOOST_ID = ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "pickaxe_mining_speed_boost");

    public Pickaxe_Talisman() {
        super(new Item.Properties().rarity(Rarity.UNCOMMON));
    }
    
    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        pTooltipComponents.add(Component.translatable("item.fargostalismans.tooltip.pickaxe_talisman")
        		.setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    }
    
    private static void resetMiningSpeed(Player player, UUID playerId) {
        AttributeInstance miningSpeedAttribute = player.getAttribute(Attributes.BLOCK_BREAK_SPEED);
        if (miningSpeedAttribute != null && miningSpeedModifiers.containsKey(playerId)) {
            miningSpeedAttribute.removeModifier(miningSpeedModifiers.get(playerId));
            miningSpeedModifiers.remove(playerId);
            System.out.println("Reset mining speed for player " + player.getName().getString() + " (ID: " + playerId + ")");
        }
    }

    private static void increaseMiningSpeed(Player player, UUID playerId) {
        AttributeInstance miningSpeedAttribute = player.getAttribute(Attributes.BLOCK_BREAK_SPEED);
        if (miningSpeedAttribute != null && !miningSpeedModifiers.containsKey(playerId)) {
            AttributeModifier modifier = new AttributeModifier(PICKAXE_MINING_SPEED_BOOST_ID, MINING_SPEED_BOOST, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
            miningSpeedAttribute.addTransientModifier(modifier);
            miningSpeedModifiers.put(playerId, modifier);
            System.out.println("Increased mining speed for player " + player.getName().getString() + " (ID: " + playerId + ")");
        }
    }
    
    @EventBusSubscriber(modid = Fargos.MODID)
    public static class Events {
        
        @SubscribeEvent
        public static void onPlayerTick(PlayerTickEvent.Pre event) {
            if (!(event.getEntity() instanceof Player player))
                return;
            
            UUID playerUUID = player.getUUID();
            
            if (CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof Pickaxe_Talisman, player).isPresent()) {
                increaseMiningSpeed(player, playerUUID);
            } else {
                resetMiningSpeed(player, playerUUID);
            }
        }
    }
    
}
