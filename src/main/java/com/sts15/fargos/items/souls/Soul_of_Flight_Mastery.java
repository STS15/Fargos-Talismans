package com.sts15.fargos.items.souls;

import java.util.List;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.effect.EffectsInit;
import com.sts15.fargos.items.TalismanItem;
import com.sts15.fargos.items.providers.Air_Talisman_Provider;
import com.sts15.fargos.items.providers.Soul_of_Flight_Mastery_Provider;
import com.sts15.fargos.utils.TalismanUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
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
        }
    }

    private static void disableFlight(Player player) {
        AttributeInstance flightAttribute = player.getAttribute(NeoForgeMod.CREATIVE_FLIGHT);
        if (flightAttribute != null && flightAttribute.getBaseValue() == 1 && !player.isCreative() && !player.isSpectator()) {
            flightAttribute.setBaseValue(0);
            player.getAbilities().flying = false;
            player.fallDistance = 0;
        }
    }

    @EventBusSubscriber(modid = Fargos.MODID)
    public static class Events {

        private static int tickCounter = 0;
        private static boolean lastFlightState = false;

        @SuppressWarnings({"removal", "deprecation"})
        @SubscribeEvent
        public static void onPlayerTick(PlayerTickEvent.Pre event) {
            Player player = event.getEntity();
            if (++tickCounter < 10) {
                return;
            }
            tickCounter = 0;
            boolean isTalismanEnabled = TalismanUtil.isTalismanEnabled(player, "Soul_of_Flight_Mastery");
            if (!isTalismanEnabled) {
                if (lastFlightState) {
                    disableFlight(player);
                    lastFlightState = false;
                }
                return;
            }
            boolean hasTalisman = player.hasEffect(EffectsInit.SOUL_OF_FLIGHT_MASTERY_EFFECT) || CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof Soul_of_Flight_Mastery_Provider, player).isPresent();

            AttributeInstance flightAttribute = player.getAttribute(NeoForgeMod.CREATIVE_FLIGHT);
            if (flightAttribute == null) return;
            if (hasTalisman && !lastFlightState) {
                enableFlight(player);
                lastFlightState = true;
            } else if (!hasTalisman && lastFlightState) {
                disableFlight(player);
                lastFlightState = false;
            }
        }

        // Player receives fall damage when shifting to go down, this should negate that, may add air talisman to recipe since it's the same event handler
        @SubscribeEvent
        public static void onLivingHurt(LivingIncomingDamageEvent event) {
            if (!(event.getEntity() instanceof ServerPlayer player))
                return;
            DamageSource source = event.getSource();
            ResourceKey<DamageType> fallDamageType = DamageTypes.FALL;

            if (source.is(fallDamageType)) {
                if (CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof Soul_of_Flight_Mastery_Provider, player).isPresent()) {
                    if (!TalismanUtil.isTalismanEnabled(player, "Soul_of_Flight_Mastery"))
                        return;
                    event.setCanceled(true);
                }
            }
        }
    }
}
