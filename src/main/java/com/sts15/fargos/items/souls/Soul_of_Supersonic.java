package com.sts15.fargos.items.souls;

import com.sts15.fargos.items.providers.Soul_of_Supersonic_Provider;
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
import net.minecraft.nbt.CompoundTag;
import top.theillusivec4.curios.api.CuriosApi;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.items.TalismanItem;

import java.util.List;

public class Soul_of_Supersonic extends TalismanItem implements Soul_of_Supersonic_Provider { // Works but weird fov changes, prolly fine

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

    @EventBusSubscriber(modid = Fargos.MODID)
    public static class Events {

        @SuppressWarnings({ "deprecation", "removal" })
		@SubscribeEvent
        public static void onPlayerTick(PlayerTickEvent.Pre event) {
            Player player = event.getEntity();
            if (CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof Soul_of_Supersonic_Provider, player).isPresent()) {
                applyEffects(player);
            } else {
                removeEffects(player);
            }
        }

        private static void applyEffects(Player player) {
            CompoundTag playerData = player.getPersistentData();

            // Walking speed boost
                AttributeInstance speedAttribute = player.getAttribute(Attributes.MOVEMENT_SPEED);
                if (speedAttribute != null && speedAttribute.getModifier(WALK_SPEED_MODIFIER_ID) == null) {
                    AttributeModifier modifier = new AttributeModifier(WALK_SPEED_MODIFIER_ID, WALK_SPEED_BOOST, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
                    speedAttribute.addTransientModifier(modifier);
                }
            

            // Flying speed boost
                if (!playerData.getBoolean("SoulOfSupersonicFlyingSpeedBoost")) {
                    player.getAbilities().setFlyingSpeed((float) (player.getAbilities().getFlyingSpeed() * FLY_SPEED_BOOST));
                    playerData.putBoolean("SoulOfSupersonicFlyingSpeedBoost", true); // Set flag
                }


            player.onUpdateAbilities();
        }

        private static void removeEffects(Player player) {
            CompoundTag playerData = player.getPersistentData();

            // Remove walking speed boost
            AttributeInstance speedAttribute = player.getAttribute(Attributes.MOVEMENT_SPEED);
            if (speedAttribute != null) {
                AttributeModifier modifier = speedAttribute.getModifier(WALK_SPEED_MODIFIER_ID);
                if (modifier != null) {
                    speedAttribute.removeModifier(modifier);
                }
            }

            // Remove flying speed boost if it was applied
            if (playerData.getBoolean("SoulOfSupersonicFlyingSpeedBoost")) {
                player.getAbilities().setFlyingSpeed((float) (player.getAbilities().getFlyingSpeed() / FLY_SPEED_BOOST));
                playerData.remove("SoulOfSupersonicFlyingSpeedBoost"); // Clear flag
            }

            player.onUpdateAbilities();
        }
    }
}
