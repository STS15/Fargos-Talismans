package com.sts15.fargos.items.souls;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.init.Config;
import com.sts15.fargos.items.TalismanItem;
import com.sts15.fargos.items.providers.Soul_of_World_Shaper_Provider;
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
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Soul_of_World_Shaper extends TalismanItem implements ICurioItem, Soul_of_World_Shaper_Provider {

    public static final String talismanName = "soul_of_world_shaper";
    private static final ResourceLocation REACH_ID = ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "world_shaper_reach");
    private static final ResourceLocation MINING_SPEED_ID = ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "world_shaper_mining_speed");

    public Soul_of_World_Shaper() {
        super(new Item.Properties().rarity(Rarity.EPIC));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("item.fargostalismans.tooltip." + talismanName)
                .setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));

        double reach = Config.SOUL_OF_WORLD_SHAPER_REACH_ADDITION.get();
        double mining = Config.SOUL_OF_WORLD_SHAPER_MINING_SPEED_MULTIPLIER.get() * 100;

        tooltip.add(Component.literal(""));
        tooltip.add(Component.literal("When worn as talisman:").withStyle(ChatFormatting.GOLD));
        tooltip.add(Component.literal(String.format("+%.0f Block Place Range", reach)).withStyle(ChatFormatting.BLUE)
                .append(Component.literal(String.format(" [%.0f]", reach)).withStyle(ChatFormatting.GRAY)));
        tooltip.add(Component.literal(String.format("+%.0f%% Mining Speed", mining)).withStyle(ChatFormatting.BLUE)
                .append(Component.literal(String.format(" [%.0f%%]", mining)).withStyle(ChatFormatting.GRAY)));
        tooltip.add(Component.literal(String.format("+1 Prevents Tool Durability Loss")).withStyle(ChatFormatting.BLUE)
                .append(Component.literal(String.format(" [1]")).withStyle(ChatFormatting.GRAY)));

        if (!checkConfigEnabledStatus()) {
            tooltip.add(Component.translatable("config.fargostalismans.tooltip.disabled")
                    .setStyle(Style.EMPTY.withColor(ChatFormatting.RED)));
        }

        super.appendHoverText(stack, context, tooltip, flag);
    }

    public static boolean checkConfigEnabledStatus() {
        try {
            String fieldName = talismanName.toUpperCase() + "_TOGGLE";
            Field toggleField = Config.class.getField(fieldName);
            return ((ModConfigSpec.BooleanValue) toggleField.get(null)).get();
        } catch (Exception e) {
            return true;
        }
    }

    public static void applyBonuses(Player player) {
        if (player == null) return;

        double reach = Config.SOUL_OF_WORLD_SHAPER_REACH_ADDITION.get();
        double mining = Config.SOUL_OF_WORLD_SHAPER_MINING_SPEED_MULTIPLIER.get();

        AttributeInstance reachAttr = player.getAttribute(Attributes.BLOCK_INTERACTION_RANGE);
        if (reachAttr != null && reachAttr.getModifier(REACH_ID) == null) {
            reachAttr.addTransientModifier(new AttributeModifier(REACH_ID, reach, AttributeModifier.Operation.ADD_VALUE));
        }

        AttributeInstance miningAttr = player.getAttribute(Attributes.BLOCK_BREAK_SPEED);
        if (miningAttr != null && miningAttr.getModifier(MINING_SPEED_ID) == null) {
            miningAttr.addTransientModifier(new AttributeModifier(MINING_SPEED_ID, mining, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        }
    }

    public static void removeBonuses(Player player) {
        if (player == null) return;

        AttributeInstance reachAttr = player.getAttribute(Attributes.BLOCK_INTERACTION_RANGE);
        if (reachAttr != null && reachAttr.getModifier(REACH_ID) != null) {
            reachAttr.removeModifier(REACH_ID);
        }

        AttributeInstance miningAttr = player.getAttribute(Attributes.BLOCK_BREAK_SPEED);
        if (miningAttr != null && miningAttr.getModifier(MINING_SPEED_ID) != null) {
            miningAttr.removeModifier(MINING_SPEED_ID);
        }
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack newStack, ItemStack oldStack) {
        if (oldStack.getItem() == newStack.getItem()) return;
        Player player = (Player) slotContext.entity();
        if (TalismanUtil.isTalismanEnabled(player, talismanName)) {
            applyBonuses(player);
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack oldStack) {
        if (oldStack.getItem() == newStack.getItem()) return;
        Player player = (Player) slotContext.entity();
        removeBonuses(player);
    }

    @EventBusSubscriber(modid = Fargos.MODID)
    public static class Events {

        private static final int CHECK_INTERVAL = 5;
        private static int tickCounter = 0;

        private static final HashMap<UUID, int[]> durabilityCache = new HashMap<>();

        @SubscribeEvent
        public static void onPlayerTick(PlayerTickEvent.Pre event) {
            if (!(event.getEntity() instanceof ServerPlayer player)) return;

            if (++tickCounter < CHECK_INTERVAL) return;
            tickCounter = 0;

            UUID playerId = player.getUUID();
            boolean hasCurio = CuriosApi.getCuriosHelper()
                    .findEquippedCurio(stack -> stack.getItem() instanceof Soul_of_World_Shaper_Provider, player)
                    .isPresent();
            boolean isEnabled = checkConfigEnabledStatus() && TalismanUtil.isTalismanEnabled(player, talismanName);

            if (hasCurio && isEnabled) {
                applyBonuses(player);

                ItemStack main = player.getMainHandItem();
                ItemStack off = player.getOffhandItem();
                int[] cached = durabilityCache.getOrDefault(playerId, new int[]{main.getDamageValue(), off.getDamageValue()});

                if (main.isDamageableItem()) {
                    if (main.getDamageValue() > cached[0]) {
                        main.setDamageValue(cached[0]);
                    } else {
                        cached[0] = main.getDamageValue();
                    }
                }
                if (off.isDamageableItem()) {
                    if (off.getDamageValue() > cached[1]) {
                        off.setDamageValue(cached[1]);
                    } else {
                        cached[1] = off.getDamageValue();
                    }
                }
                durabilityCache.put(playerId, cached);
            } else {
                removeBonuses(player);
                durabilityCache.remove(playerId);
            }
        }
    }

}
