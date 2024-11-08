package com.sts15.fargos.items.talismans;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.effect.EffectsInit;
import com.sts15.fargos.init.Config;
import com.sts15.fargos.items.TalismanItem;

import com.sts15.fargos.items.providers.Architect_Talisman_Provider;
import com.sts15.fargos.utils.TalismanUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
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
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import top.theillusivec4.curios.api.CuriosApi;

public class Architect_Talisman extends TalismanItem implements Architect_Talisman_Provider {

    private static final String talismanName = "architect_talisman";
    private static final boolean isDebug = false;
    private static final Map<UUID, AttributeModifier> reachDistanceModifiers = new HashMap<>();
    private static final ResourceLocation ARCHITECT_REACH_DISTANCE_BOOST_ID = ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "architect_reach_distance_boost");

    public Architect_Talisman() {
        super(new Item.Properties().rarity(Rarity.UNCOMMON));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("item.fargostalismans.tooltip." + talismanName, Config.ARCHITECT_TALISMAN_REACH_DISTANCE.getAsInt())
                .setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
        if (!checkConfigEnabledStatus()) {
            tooltipComponents.add(Component.translatable("config.fargostalismans.tooltip.disabled")
                    .setStyle(Style.EMPTY.withColor(ChatFormatting.RED)));
        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    public static boolean checkConfigEnabledStatus() {
        boolean isEnabled = true;
        try {
            String fieldName = talismanName.toUpperCase() + "_TOGGLE";
            Field toggleField = Config.class.getField(fieldName);
            isEnabled = ((ModConfigSpec.BooleanValue) toggleField.get(null)).get();
        } catch (NoSuchFieldException | IllegalAccessException e) {}
        return isEnabled;
    }

    private static int getReachDistance() {
        return Config.ARCHITECT_TALISMAN_REACH_DISTANCE.get().intValue();
    }

    private static void resetReachDistance(Player player, UUID playerId) {
        AttributeInstance reachDistanceAttribute = player.getAttribute(Attributes.BLOCK_INTERACTION_RANGE);
        if (reachDistanceAttribute != null && reachDistanceModifiers.containsKey(playerId)) {
            reachDistanceAttribute.removeModifier(reachDistanceModifiers.get(playerId));
            reachDistanceModifiers.remove(playerId);
            if (isDebug) System.out.println("Reset reach distance for player " + player.getName().getString() + " (ID: " + playerId + ")");
        }
    }

    private static void increaseReachDistance(Player player, UUID playerId) {
        AttributeInstance reachDistanceAttribute = player.getAttribute(Attributes.BLOCK_INTERACTION_RANGE);
        if (reachDistanceAttribute != null && reachDistanceAttribute.getModifier(ARCHITECT_REACH_DISTANCE_BOOST_ID) == null) {
            AttributeModifier modifier = new AttributeModifier(ARCHITECT_REACH_DISTANCE_BOOST_ID, getReachDistance(), AttributeModifier.Operation.ADD_VALUE);
            reachDistanceAttribute.addTransientModifier(modifier);
            reachDistanceModifiers.put(playerId, modifier);
            if (isDebug) System.out.println("Increased reach distance for player " + player.getName().getString() + " (ID: " + playerId + ")"+ " to "+getReachDistance()+"\n"+modifier);
        }
    }

    @EventBusSubscriber(modid = Fargos.MODID)
    public static class Events {

        private static int tickCounter = 0;
        
        @SuppressWarnings({ "removal", "deprecation" })
        @SubscribeEvent
        public static void onPlayerTick(PlayerTickEvent.Pre event) {
            if (!(event.getEntity() instanceof ServerPlayer player))
                return;
            if (++tickCounter < 10) { return; } tickCounter = 0;

            UUID playerUUID = player.getUUID();

            if (!TalismanUtil.isTalismanEnabled(player, talismanName)) {
                resetReachDistance(player, playerUUID);
            } else if (player.hasEffect(EffectsInit.ARCHITECT_TALISMAN_EFFECT) || CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof Architect_Talisman_Provider, player).isPresent()) {
                if (TalismanUtil.isTalismanEnabled(player, talismanName)) {
                    increaseReachDistance(player, playerUUID);
                }
            } else {
                resetReachDistance(player, playerUUID);
            }
        }
    }
}
