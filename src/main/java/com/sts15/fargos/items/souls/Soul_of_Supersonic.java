package com.sts15.fargos.items.souls;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.items.TalismanItem;
import com.sts15.fargos.items.providers.Soul_of_Supersonic_Provider;
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
import net.minecraft.nbt.CompoundTag;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.List;

public class Soul_of_Supersonic extends TalismanItem implements Soul_of_Supersonic_Provider {

    private static final ResourceLocation WALK_SPEED_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath("fargos", "supersonic_walking_speed_boost");
    private static final double WALK_SPEED_BOOST = 1.75;
    private static final double FLY_SPEED_BOOST = 2.5;

    public Soul_of_Supersonic() {
        super(new Item.Properties().rarity(Rarity.EPIC));
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        pTooltipComponents.add(Component.translatable("item.fargostalismans.tooltip.soul_of_supersonic")
                .setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    }

    private static void applyEffects(Player player, CompoundTag playerData) {
        AttributeInstance speedAttribute = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speedAttribute != null && speedAttribute.getModifier(WALK_SPEED_MODIFIER_ID) == null) {
            AttributeModifier modifier = new AttributeModifier(WALK_SPEED_MODIFIER_ID, WALK_SPEED_BOOST, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
            speedAttribute.addTransientModifier(modifier);
        }

        if (!playerData.getBoolean("SoulOfSupersonicFlyingSpeedBoost")) {
            player.getAbilities().setFlyingSpeed((float) (player.getAbilities().getFlyingSpeed() * FLY_SPEED_BOOST));
            playerData.putBoolean("SoulOfSupersonicFlyingSpeedBoost", true); // Set flag
        }

        playerData.putBoolean("SoulOfSupersonicActive", true);
        player.onUpdateAbilities();

        player.sendSystemMessage(
                Component.literal("[")
                        .setStyle(Style.EMPTY.withColor(ChatFormatting.RED)) // Red color for the opening bracket
                        .append(Component.translatable("item.fargostalismans.soul_of_supersonic")
                                .setStyle(Style.EMPTY.withColor(ChatFormatting.RED))) // Red color for the translatable item name
                        .append(Component.literal("] ")
                                .setStyle(Style.EMPTY.withColor(ChatFormatting.RED))) // Red color for the closing bracket and space
                        .append(Component.translatable("key.categories.fargostalismans.fov")
                                .setStyle(Style.EMPTY.withColor(ChatFormatting.WHITE))) // White color for the translatable message
        );


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
            player.getAbilities().setFlyingSpeed((float) (player.getAbilities().getFlyingSpeed() / FLY_SPEED_BOOST));
            playerData.remove("SoulOfSupersonicFlyingSpeedBoost"); // Clear flag
        }

        playerData.remove("SoulOfSupersonicActive");
        player.onUpdateAbilities();
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

            if (!TalismanUtil.isTalismanEnabled(player, "Soul_of_Supersonic")) {
                removeEffects(player, player.getPersistentData());
            } else if (CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof Soul_of_Supersonic_Provider, player).isPresent()) {
                if (!TalismanUtil.isTalismanEnabled(player, "Soul_of_Supersonic"))
                    return;
                if (!player.getPersistentData().getBoolean("SoulOfSupersonicActive"))
                    applyEffects(player, player.getPersistentData());
            } else {
                if (player.getPersistentData().getBoolean("SoulOfSupersonicActive"))
                    removeEffects(player, player.getPersistentData());
            }
        }
    }
}
