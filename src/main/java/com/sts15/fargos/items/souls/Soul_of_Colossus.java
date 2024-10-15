package com.sts15.fargos.items.souls;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.effect.EffectsInit;
import com.sts15.fargos.items.TalismanItem;
import com.sts15.fargos.items.providers.Soul_of_Colossus_Provider;
import com.sts15.fargos.utils.TalismanUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
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
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class Soul_of_Colossus extends TalismanItem implements ICurioItem, Soul_of_Colossus_Provider {

    public static final String talismanName = "soul_of_colossus";
    private static final double HEALTH_MULTIPLIER = 4.0;
    private static final ResourceLocation HEALTH_BOOST_ID = ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "soul_of_colossus_health_boost");
    private static final ResourceLocation HEALTH_DATA_KEY = ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "soul_of_colossus_health");

    public Soul_of_Colossus() {
        super(new Item.Properties().rarity(Rarity.EPIC));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("item.fargostalismans.tooltip." + talismanName)
                .setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    public static void resetHealth(Player player) {
        AttributeInstance healthAttribute = player.getAttribute(Attributes.MAX_HEALTH);
        if (healthAttribute != null && healthAttribute.hasModifier(HEALTH_BOOST_ID)) {
            healthAttribute.removeModifier(HEALTH_BOOST_ID);
        }
    }

    public static void increaseHealth(Player player) {
        AttributeInstance healthAttribute = player.getAttribute(Attributes.MAX_HEALTH);
        if (healthAttribute != null && !healthAttribute.hasModifier(HEALTH_BOOST_ID)) {
            AttributeModifier modifier = new AttributeModifier(HEALTH_BOOST_ID, HEALTH_MULTIPLIER, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
            healthAttribute.addTransientModifier(modifier);
        }
    }

    private static void negateNegativeEffects(Player player) {
        List<MobEffectInstance> negativeEffects = player.getActiveEffects().stream()
                .filter(effectInstance -> !effectInstance.getEffect().value().isBeneficial())
                .toList();
        for (MobEffectInstance effect : negativeEffects) {
            player.removeEffect(effect.getEffect());
        }
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (!(slotContext.entity() instanceof ServerPlayer player))
            return;

        boolean hasEquippedCurio = CuriosApi.getCuriosHelper()
                .findEquippedCurio(equippedStack -> equippedStack.getItem() instanceof Soul_of_Colossus_Provider, player)
                .isPresent();

        if (hasEquippedCurio) {
            if (TalismanUtil.isTalismanEnabled(player, talismanName)) {
                increaseHealth(player);
                negateNegativeEffects(player);
            } else { // Toggle off is only for config toggle disable, not actual removal
                resetHealth(player);
            }
        }
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (stack.getItem() == newStack.getItem())
            return;
        Player entity = (Player) slotContext.entity();
        increaseHealth(entity);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (stack.getItem() == newStack.getItem())
            return;
        Player entity = (Player) slotContext.entity();
        boolean hasSoulOfColossusEffect = entity.hasEffect(EffectsInit.SOUL_OF_COLOSSUS_EFFECT);
        if (!hasSoulOfColossusEffect) {
            resetHealth(entity);
        }
    }

    @EventBusSubscriber(modid = Fargos.MODID)
    public static class Events {

        @SubscribeEvent
        public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
            if (!(event.getEntity() instanceof ServerPlayer player)) return;
            player.getPersistentData().putFloat(HEALTH_DATA_KEY.toString(), player.getHealth());
        }

        @SubscribeEvent
        public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
            if (!(event.getEntity() instanceof ServerPlayer player)) return;
            if (player.getPersistentData().contains(HEALTH_DATA_KEY.toString())) {
                boolean hasEquippedCurio = CuriosApi.getCuriosHelper().findEquippedCurio(equippedStack -> equippedStack.getItem() instanceof Soul_of_Colossus_Provider, player).isPresent();
                boolean hasSoulOfColossusEffect = player.hasEffect(EffectsInit.SOUL_OF_COLOSSUS_EFFECT);
                if (hasSoulOfColossusEffect || hasEquippedCurio) {
                    increaseHealth(player);
                    player.setHealth(player.getPersistentData().getFloat(HEALTH_DATA_KEY.toString()));
                }
                player.getPersistentData().remove(HEALTH_DATA_KEY.toString());
            }
        }
    }
}
