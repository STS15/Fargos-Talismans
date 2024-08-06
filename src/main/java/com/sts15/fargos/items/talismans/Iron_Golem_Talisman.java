package com.sts15.fargos.items.talismans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

public class Iron_Golem_Talisman extends TalismanItem {

    private static final double HEALTH_BOOST_MULTIPLIER = 2.0;
    private static final Map<UUID, AttributeModifier> healthModifiers = new HashMap<>();

    private static final ResourceLocation IRON_GOLEM_HEALTH_BOOST = ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "iron_golem_health_boost");

    public Iron_Golem_Talisman() {
        super(new Item.Properties().rarity(Rarity.UNCOMMON));
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        pTooltipComponents.add(Component.translatable("item.fargostalismans.tooltip.iron_golem_talisman")
        		.setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    }

    private static void resetHealth(Player player, UUID playerId) {
        AttributeInstance healthAttribute = player.getAttribute(Attributes.MAX_HEALTH);
        if (healthAttribute != null && healthModifiers.containsKey(playerId)) {
            healthAttribute.removeModifier(healthModifiers.get(playerId));
            healthModifiers.remove(playerId);
        }
    }

    private static void increaseHealth(Player player, UUID playerId) {
        AttributeInstance healthAttribute = player.getAttribute(Attributes.MAX_HEALTH);
        if (healthAttribute != null && !healthModifiers.containsKey(playerId)) {
            AttributeModifier modifier = new AttributeModifier(IRON_GOLEM_HEALTH_BOOST, HEALTH_BOOST_MULTIPLIER, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
            healthAttribute.addTransientModifier(modifier);
            healthModifiers.put(playerId, modifier);
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

            if (CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof Iron_Golem_Talisman, player).isPresent()) {
                increaseHealth(player, playerUUID);
            } else {
                resetHealth(player, playerUUID);
            }
        }
    }
}
