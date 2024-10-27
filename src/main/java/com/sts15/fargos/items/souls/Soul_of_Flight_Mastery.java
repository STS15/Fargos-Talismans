package com.sts15.fargos.items.souls;

import java.util.List;
import com.sts15.fargos.Fargos;
import com.sts15.fargos.effect.EffectsInit;
import com.sts15.fargos.items.TalismanItem;
import com.sts15.fargos.items.providers.Soul_of_Colossus_Provider;
import com.sts15.fargos.items.providers.Soul_of_Flight_Mastery_Provider;
import com.sts15.fargos.utils.TalismanUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
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
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;

public class Soul_of_Flight_Mastery extends TalismanItem implements Soul_of_Flight_Mastery_Provider {

    public static final String talismanName = "soul_of_flight_mastery";
    private static final ResourceLocation FLIGHT_ENABLE_ID = ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "soul_of_flight_mastery_enabled");

    public Soul_of_Flight_Mastery() {
        super(new Item.Properties().rarity(Rarity.EPIC));
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @NotNull TooltipContext pContext, List<Component> pTooltipComponents, @NotNull TooltipFlag pTooltipFlag) {
        pTooltipComponents.add(Component.translatable("item.fargostalismans.tooltip.soul_of_flight_mastery")
                .setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    }

    public static void enableFlight(Player player) {
        AttributeInstance flightAttribute = player.getAttribute(NeoForgeMod.CREATIVE_FLIGHT);
        if (flightAttribute != null && flightAttribute.getBaseValue() == 0 && !player.isCreative() && !player.isSpectator()) {
            flightAttribute.setBaseValue(1);
            player.getPersistentData().putBoolean(FLIGHT_ENABLE_ID.toString(), true);
        }
    }

    public static void disableFlight(Player player) {
        AttributeInstance flightAttribute = player.getAttribute(NeoForgeMod.CREATIVE_FLIGHT);
        if (flightAttribute != null && flightAttribute.getBaseValue() == 1 && !player.isCreative() && !player.isSpectator()) {
            flightAttribute.setBaseValue(0);
            player.getAbilities().flying = false;
            player.getAbilities().mayfly = false;
            player.onUpdateAbilities();
            player.getPersistentData().remove(FLIGHT_ENABLE_ID.toString());
        }
    }

    private static boolean isFlightEnabledByMod(Player player) {
        return player.getPersistentData().getBoolean(FLIGHT_ENABLE_ID.toString());
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (!(slotContext.entity() instanceof ServerPlayer player))
            return;

        boolean hasEquippedCurio = CuriosApi.getCuriosHelper()
                .findEquippedCurio(equippedStack -> equippedStack.getItem() instanceof Soul_of_Flight_Mastery_Provider, player)
                .isPresent();

        if (hasEquippedCurio) {
            if (TalismanUtil.isTalismanEnabled(player, talismanName)) {
                enableFlight(player);
            } else if (isFlightEnabledByMod(player)) {
                disableFlight(player);
            }
        }
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (stack.getItem() == newStack.getItem())
            return;
        Player entity = (Player) slotContext.entity();
        if (TalismanUtil.isTalismanEnabled(entity, talismanName)) {
            enableFlight(entity);
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (stack.getItem() == newStack.getItem())
            return;
        Player entity = (Player) slotContext.entity();
        disableFlight(entity);
    }
}
