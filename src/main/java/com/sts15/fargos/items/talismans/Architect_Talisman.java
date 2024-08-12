package com.sts15.fargos.items.talismans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.sts15.fargos.Config;
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

public class Architect_Talisman extends TalismanItem implements Architect_Talisman_Provider {

    private static final String talismanName = "architect_talisman";
    
    private static final double REACH_DISTANCE_BOOST = 59.0;
    private static final Map<UUID, AttributeModifier> reachDistanceModifiers = new HashMap<>();
    private static final ResourceLocation ARCHITECT_REACH_DISTANCE_BOOST_ID = ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "architect_reach_distance_boost");

    public Architect_Talisman() {
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

    private static void resetReachDistance(Player player, UUID playerId) {
        AttributeInstance reachDistanceAttribute = player.getAttribute(Attributes.BLOCK_INTERACTION_RANGE);
        if (reachDistanceAttribute != null && reachDistanceModifiers.containsKey(playerId)) {
            reachDistanceAttribute.removeModifier(reachDistanceModifiers.get(playerId));
            reachDistanceModifiers.remove(playerId);
            //System.out.println("Reset reach distance for player " + player.getName().getString() + " (ID: " + playerId + ")");
        }
    }

    private static void increaseReachDistance(Player player, UUID playerId) {
        AttributeInstance reachDistanceAttribute = player.getAttribute(Attributes.BLOCK_INTERACTION_RANGE);
        if (reachDistanceAttribute != null && reachDistanceAttribute.getModifier(ARCHITECT_REACH_DISTANCE_BOOST_ID) == null) {
            AttributeModifier modifier = new AttributeModifier(ARCHITECT_REACH_DISTANCE_BOOST_ID, REACH_DISTANCE_BOOST, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
            reachDistanceAttribute.addTransientModifier(modifier);
            reachDistanceModifiers.put(playerId, modifier);
            //System.out.println("Increased reach distance for player " + player.getName().getString() + " (ID: " + playerId + ")");
        }
    }

    @EventBusSubscriber(modid = Fargos.MODID)
    public static class Events {
        
        @SuppressWarnings({ "removal", "deprecation" })
        @SubscribeEvent
        public static void onPlayerTick(PlayerTickEvent.Pre event) {
            if (!(event.getEntity() instanceof Player player))
                return;

            UUID playerUUID = player.getUUID();

            if (CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof Architect_Talisman_Provider, player).isPresent()) {
                if (Config.isTalismanEnabledOnClientAndServer(talismanName)) {
                    increaseReachDistance(player, playerUUID);
                }
            } else {
                resetReachDistance(player, playerUUID);
            }
        }
    }
}
