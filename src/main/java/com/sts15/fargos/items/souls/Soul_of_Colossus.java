package com.sts15.fargos.items.souls;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.effect.EffectsInit;
import com.sts15.fargos.init.Config;
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
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import java.lang.reflect.Field;
import java.util.List;

public class Soul_of_Colossus extends TalismanItem implements ICurioItem, Soul_of_Colossus_Provider {

    public static final String talismanName = "soul_of_colossus";
    private static final ResourceLocation HEALTH_BOOST_ID = ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "soul_of_colossus_health_boost");
    private static final ResourceLocation HEALTH_DATA_KEY = ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "soul_of_colossus_health");

    public Soul_of_Colossus() {
        super(new Item.Properties().rarity(Rarity.EPIC));
    }

    private static double getHealthMultiplier() {
        return Config.SOUL_OF_COLOSSUS_HEALTH_MULTIPLIER.get();
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("item.fargostalismans.tooltip." + talismanName)
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

    public static void resetHealth(Player player) {
        AttributeInstance healthAttribute = player.getAttribute(Attributes.MAX_HEALTH);
        if (healthAttribute != null && healthAttribute.getModifier(HEALTH_BOOST_ID) != null) {
            healthAttribute.removeModifier(HEALTH_BOOST_ID);
        }
    }

    public static void increaseHealth(Player player) {
        AttributeInstance healthAttribute = player.getAttribute(Attributes.MAX_HEALTH);
        if (healthAttribute != null && healthAttribute.getModifier(HEALTH_BOOST_ID) == null) {
            AttributeModifier modifier = new AttributeModifier(HEALTH_BOOST_ID, getHealthMultiplier(), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
            healthAttribute.addTransientModifier(modifier);
        }
    }

    private static void negateNegativeEffects(Player player) {
        if (Config.SOUL_OF_COLOSSUS_REMOVE_NEGATIVE_EFFECTS.get()) {
            List<MobEffectInstance> negativeEffects = player.getActiveEffects().stream().filter(effectInstance -> !effectInstance.getEffect().value().isBeneficial()).toList();
            for (MobEffectInstance effect : negativeEffects) {
                player.removeEffect(effect.getEffect());
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

        private static int tickCounter = 0;
        @SuppressWarnings({ "removal", "deprecation" })
        @SubscribeEvent
        public static void onPlayerTick(PlayerTickEvent.Pre event) {
            if (!(event.getEntity() instanceof ServerPlayer player))
                return;

            if (!TalismanUtil.isTalismanEnabled(player, "Soul_of_Colossus")) {
                resetHealth(player);
            }

            if (player.hasEffect(EffectsInit.SOUL_OF_COLOSSUS_EFFECT) || CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof Soul_of_Colossus_Provider, player).isPresent()) {
                if (!TalismanUtil.isTalismanEnabled(player, talismanName))
                    return;
                negateNegativeEffects(player);
                if (++tickCounter < 10) {return;} tickCounter = 0; // Only try and increase health every 10 ticks
                increaseHealth(player);
            } else {
                if (++tickCounter < 10) {return;} tickCounter = 0; // Only try and decrease health every 10 ticks
                resetHealth(player);
            }
        }

        @SubscribeEvent
        public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
            if (!(event.getEntity() instanceof ServerPlayer player)) return;
            player.getPersistentData().putFloat(HEALTH_DATA_KEY.toString(), player.getHealth());
        }

        @SubscribeEvent
        public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
            if (!(event.getEntity() instanceof ServerPlayer player)) return;
            if (player.getPersistentData().contains(HEALTH_DATA_KEY.toString())) {
                boolean hasEquippedCurio = CuriosApi.getCuriosHelper()
                        .findEquippedCurio(equippedStack -> equippedStack.getItem() instanceof Soul_of_Colossus_Provider, player)
                        .isPresent();
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
