package com.sts15.fargos.items.souls;

import java.util.List;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.effect.EffectsInit;
import com.sts15.fargos.items.TalismanItem;
import com.sts15.fargos.items.providers.Soul_of_Flight_Mastery_Provider;
import com.sts15.fargos.utils.TalismanUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.theillusivec4.curios.api.CuriosApi;

public class Soul_of_Flight_Mastery extends TalismanItem implements Soul_of_Flight_Mastery_Provider {

    private static final ResourceLocation FLIGHT_ENABLE_ID = ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "soul_of_flight_mastery_enabled");
    private static final Logger LOGGER = LogManager.getLogger();

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
            player.getPersistentData().putBoolean(FLIGHT_ENABLE_ID.toString(), true);
            //LOGGER.info("Enabled flight for player: " + player.getName().getString());
        } else {
            //LOGGER.warn("Failed to enable flight for player: " + player.getName().getString() + ". Conditions not met.");
        }
    }

    private static void disableFlight(Player player) {
        AttributeInstance flightAttribute = player.getAttribute(NeoForgeMod.CREATIVE_FLIGHT);
        if (flightAttribute != null && flightAttribute.getBaseValue() == 1 && !player.isCreative() && !player.isSpectator()) {
            flightAttribute.setBaseValue(0);
            player.getAbilities().flying = false;
            player.getAbilities().mayfly = false;
            player.onUpdateAbilities();
            player.getPersistentData().remove(FLIGHT_ENABLE_ID.toString());
            //LOGGER.info("Disabled flight for player: " + player.getName().getString());
        } else {
            //LOGGER.warn("Failed to disable flight for player: " + player.getName().getString() + ". Conditions not met.");
        }
    }

    private static boolean isFlightEnabledByMod(Player player) {
        boolean flightEnabled = player.getPersistentData().getBoolean(FLIGHT_ENABLE_ID.toString());
        //LOGGER.debug("Flight enabled by mod for player {}: {}", player.getName().getString(), flightEnabled);
        return flightEnabled;
    }

    @EventBusSubscriber(modid = Fargos.MODID)
    public static class Events {

        private static int tickCounter = 0;

        @SuppressWarnings({"removal", "deprecation"})
        @SubscribeEvent
        public static void onPlayerTick(PlayerTickEvent.Pre event) {
            Player player = event.getEntity();

            // Only check every 10 ticks
            if (++tickCounter < 10) { return; } tickCounter = 0;

            // Check if the talisman is enabled
            if (!TalismanUtil.isTalismanEnabled(player, "Soul_of_Flight_Mastery")) {
                if (isFlightEnabledByMod(player)) {
                    disableFlight(player);
                }
                return;
            }

            boolean hasTalisman = CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof Soul_of_Flight_Mastery_Provider, player).isPresent();
            boolean hasEffect = player.hasEffect(EffectsInit.SOUL_OF_FLIGHT_MASTERY_EFFECT);

            AttributeInstance flightAttribute = player.getAttribute(NeoForgeMod.CREATIVE_FLIGHT);
            if (flightAttribute == null) {
                //LOGGER.warn("Flight attribute is null for player: " + player.getName().getString());
                return;
            }

            // Enable flight if the talisman or effect is active, and flight was not already enabled by the mod
            if ((hasTalisman || hasEffect) && !player.isCreative() && !player.isSpectator()) {
                //LOGGER.info("Attempting to enable flight for player: " + player.getName().getString());
                enableFlight(player);
            } else if (!hasTalisman && !hasEffect && isFlightEnabledByMod(player)) {
                // Disable flight if talisman/effect is not active and flight was enabled by the mod
                //LOGGER.info("Attempting to disable flight for player: " + player.getName().getString());
                disableFlight(player);
            }
        }
    }
}
