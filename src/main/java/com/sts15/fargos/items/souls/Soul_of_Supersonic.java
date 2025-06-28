package com.sts15.fargos.items.souls;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.effect.EffectsInit;
import com.sts15.fargos.init.Config;
import com.sts15.fargos.items.TalismanItem;
import com.sts15.fargos.items.providers.*;
import com.sts15.fargos.utils.TalismanUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
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
import net.minecraft.nbt.CompoundTag;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;

import java.lang.reflect.Field;
import java.util.List;

public class Soul_of_Supersonic extends TalismanItem implements Soul_of_Supersonic_Provider,
        Aeolus_Boots_Provider, Brain_Scrambler_Provider, Reindeer_Bells_Provider, Ancient_Horn_Provider, Mechanical_Cart_Provider, Blessed_Apple_Provider, Master_Ninja_Gear_Provider,
        Shield_Of_Cthulhu_Provider, Bundle_Of_Horseshoe_Balloons_Provider, Amber_Horseshoe_Balloon_Provider, Sweetheart_Necklace_Provider, Flying_Carpet_Provider {

    private static final ResourceLocation WALK_SPEED_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath("fargos", "supersonic_walking_speed_boost");
    public static final String talismanName = "soul_of_supersonic";

    public Soul_of_Supersonic() {
        super(new Item.Properties().rarity(Rarity.EPIC));
    }

    private static double getWalkSpeedBoost() {
        return Config.SOUL_OF_SUPERSONIC_WALK_SPEED_MULTIPLIER.get();
    }
    private static double getFlySpeedBoost() {
        return Config.SOUL_OF_SUPERSONIC_FLY_SPEED_MULTIPLIER.get();
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("item.fargostalismans.tooltip." + talismanName)
                .setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));

        if (!checkConfigEnabledStatus()) {
            tooltipComponents.add(Component.translatable("config.fargostalismans.tooltip.disabled")
                    .setStyle(Style.EMPTY.withColor(ChatFormatting.RED)));
        }

        if (Screen.hasShiftDown()) {
            if (Screen.hasShiftDown()) {
                addTrinketTooltip(tooltipComponents, "aeolus_boots", 0x66CCFF);
                addTrinketTooltip(tooltipComponents, "brain_scrambler", 0xAA33CC);
                addTrinketTooltip(tooltipComponents, "reindeer_bells", 0xD46F00);
                addTrinketTooltip(tooltipComponents, "ancient_horn", 0xA0652A);
                addTrinketTooltip(tooltipComponents, "mechanical_cart", 0x999999);
                addTrinketTooltip(tooltipComponents, "blessed_apple", 0xFFCC00);
                addTrinketTooltip(tooltipComponents, "master_ninja_gear", 0x3A3A3A);
                addTrinketTooltip(tooltipComponents, "shield_of_cthulhu", 0x990000);
                addTrinketTooltip(tooltipComponents, "bundle_of_horseshoe_balloons", 0x0099CC);
                addTrinketTooltip(tooltipComponents, "amber_horseshoe_balloon", 0xFFAA00);
                addTrinketTooltip(tooltipComponents, "sweetheart_necklace", 0xFF6699);
                addTrinketTooltip(tooltipComponents, "flying_carpet", 0xB97A57);
            }
        } else {
            tooltipComponents.add(Component.literal("Hold ")
                    .append(Component.literal("[LShift]").withStyle(ChatFormatting.GRAY))
                    .append(" to see each trinket effect")
                    .withStyle(ChatFormatting.DARK_GRAY));
        }

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    private void addTrinketTooltip(List<Component> tooltip, String key, int hexColor) {
        Component name = Component.translatable("item.fargostalismans." + key)
                .setStyle(Style.EMPTY.withColor(hexColor));
        Component effect = Component.translatable("item.fargostalismans.tooltip." + key)
                .setStyle(Style.EMPTY.withColor(0xAAAAAA)); // soft gray
        tooltip.add(Component.literal(" - ").append(name).append(": ").append(effect));
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

    private static void applyEffects(Player player, CompoundTag playerData) {
        AttributeInstance speedAttribute = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speedAttribute != null && speedAttribute.getModifier(WALK_SPEED_MODIFIER_ID) == null) {
            AttributeModifier modifier = new AttributeModifier(WALK_SPEED_MODIFIER_ID, getWalkSpeedBoost(), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
            speedAttribute.addTransientModifier(modifier);
        }
        if (!playerData.getBoolean("SoulOfSupersonicFlyingSpeedBoost")) {
            player.getAbilities().setFlyingSpeed((float) (player.getAbilities().getFlyingSpeed() * getFlySpeedBoost()));
            playerData.putBoolean("SoulOfSupersonicFlyingSpeedBoost", true);
        }
        playerData.putBoolean("SoulOfSupersonicActive", true);
        player.onUpdateAbilities();
    }

    private static void removeEffects(Player player, CompoundTag playerData) {
        AttributeInstance speedAttribute = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speedAttribute != null) {
            AttributeModifier modifier = speedAttribute.getModifier(WALK_SPEED_MODIFIER_ID);
            if (modifier != null) {
                speedAttribute.removeModifier(modifier);
            }
        }
        if (playerData.getBoolean("SoulOfSupersonicFlyingSpeedBoost")) {
            double flySpeedBoost = getFlySpeedBoost();
            player.getAbilities().setFlyingSpeed((float) (player.getAbilities().getFlyingSpeed() / flySpeedBoost));
            playerData.remove("SoulOfSupersonicFlyingSpeedBoost"); // Clear flag
        }
        playerData.remove("SoulOfSupersonicActive");
        player.onUpdateAbilities();
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (stack.getItem() == newStack.getItem())
            return;
        Player entity = (Player) slotContext.entity();
        if (TalismanUtil.isTalismanEnabled(entity, talismanName)) {
            applyEffects(entity, entity.getPersistentData());
            entity.sendSystemMessage(
                    Component.literal("[")
                            .setStyle(Style.EMPTY.withColor(ChatFormatting.RED))
                            .append(Component.translatable("item.fargostalismans.soul_of_supersonic")
                                    .setStyle(Style.EMPTY.withColor(ChatFormatting.RED)))
                            .append(Component.literal("] ")
                                    .setStyle(Style.EMPTY.withColor(ChatFormatting.RED)))
                            .append(Component.translatable("key.categories.fargostalismans.fov")
                                    .setStyle(Style.EMPTY.withColor(ChatFormatting.WHITE)))
            );
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (stack.getItem() == newStack.getItem())
            return;
        Player entity = (Player) slotContext.entity();
        removeEffects(entity, entity.getPersistentData());
    }

    @EventBusSubscriber(modid = Fargos.MODID)
    public static class Events {

        private static int tickCounter = 0;

        @SuppressWarnings({ "removal", "deprecation" })
        @SubscribeEvent
        public static void onPlayerTick(PlayerTickEvent.Pre event) {
            if (!(event.getEntity() instanceof ServerPlayer player))
                return;

            CompoundTag playerData = player.getPersistentData();

            // Use per-player tick counter stored in PersistentData
            int tickCounter = playerData.getInt("SoulOfSupersonicTickCounter");
            if (++tickCounter < 10) {
                playerData.putInt("SoulOfSupersonicTickCounter", tickCounter);
                return;
            }
            playerData.putInt("SoulOfSupersonicTickCounter", 0);

            boolean isEnabledInConfig = TalismanUtil.isTalismanEnabled(player, Soul_of_Supersonic.talismanName);
            boolean isEquipped = CuriosApi.getCuriosHelper()
                    .findEquippedCurio(stack -> stack.getItem() instanceof Soul_of_Supersonic_Provider, player)
                    .isPresent();
            boolean hasEffect = player.hasEffect(EffectsInit.SOUL_OF_SUPERSONIC_EFFECT);
            boolean shouldBeActive = isEnabledInConfig && (isEquipped || hasEffect);
            boolean isCurrentlyActive = playerData.getBoolean("SoulOfSupersonicActive");

            if (shouldBeActive && !isCurrentlyActive) {
                applyEffects(player, playerData);
            } else if (!shouldBeActive && isCurrentlyActive) {
                removeEffects(player, playerData);
            }
        }
    }
}
