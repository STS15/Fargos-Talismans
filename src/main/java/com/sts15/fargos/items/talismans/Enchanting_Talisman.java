package com.sts15.fargos.items.talismans;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;
import com.sts15.fargos.Fargos;
import com.sts15.fargos.effect.EffectsInit;
import com.sts15.fargos.init.Config;
import com.sts15.fargos.items.TalismanItem;
import com.sts15.fargos.items.providers.Enchanting_Talisman_Provider;
import com.sts15.fargos.utils.TalismanUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import top.theillusivec4.curios.api.CuriosApi;

public class Enchanting_Talisman extends TalismanItem implements Enchanting_Talisman_Provider {

    private static final String talismanName = "enchanting_talisman";

    public Enchanting_Talisman() {
        super(new Item.Properties().rarity(Rarity.UNCOMMON));
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

    @EventBusSubscriber(modid = Fargos.MODID)
    public static class Events {

        private static int tickCounter = 0;

        @SubscribeEvent
        public static void onPlayerTick(PlayerTickEvent.Pre event) {
            if (event.getEntity() instanceof ServerPlayer player) {
                if (++tickCounter < 10) { return; } tickCounter = 0;

                if (player.hasEffect(EffectsInit.ENCHANTING_TALISMAN_EFFECT) || CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof Enchanting_Talisman_Provider, player).isPresent()) {
                    if (!TalismanUtil.isTalismanEnabled(player, talismanName)) {
                        return;
                    }
                    // Check main and off hand for any curses, then remove the curse
                    removeCurses(player.getMainHandItem(), player);
                    removeCurses(player.getOffhandItem(), player);
                }
            }
        }

        public static void removeCurses(ItemStack itemStack, ServerPlayer player) {
            if (itemStack != null && !itemStack.isEmpty()) {
                ItemEnchantments itemEnchantments = EnchantmentHelper.getEnchantmentsForCrafting(itemStack);
                ItemEnchantments.Mutable mutableEnchantments = new ItemEnchantments.Mutable(itemEnchantments);
                Set<Holder<Enchantment>> enchantments = itemEnchantments.keySet();

                // Filter the curses that are present on the item
                List<Holder<Enchantment>> cursesToRemove = enchantments.stream()
                        .filter(enchantment -> enchantment.is(EnchantmentTags.CURSE))
                        .toList();

                // If the item has any curses, remove them
                if (!cursesToRemove.isEmpty()) {
                    mutableEnchantments.removeIf(enchantment -> enchantment.is(EnchantmentTags.CURSE));
                    ItemEnchantments updatedEnchantments = mutableEnchantments.toImmutable();
                    EnchantmentHelper.setEnchantments(itemStack, updatedEnchantments);

                    // Create a message for each curse removed
                    for (Holder<Enchantment> curse : cursesToRemove) {
                        // Construct the message with the format "[Enchanting Talisman] {curse} removed from {item name}"
                        MutableComponent message = Component.literal("[")
                                .setStyle(Style.EMPTY.withColor(ChatFormatting.GREEN)) // Set green color for the opening bracket
                                .append(Component.translatable("item.fargostalismans.enchanting_talisman")
                                        .setStyle(Style.EMPTY.withColor(ChatFormatting.GREEN))) // Green color for the talisman name
                                .append(Component.literal("] ")
                                        .setStyle(Style.EMPTY.withColor(ChatFormatting.GREEN))) // Green color for the closing bracket
                                .append(Component.translatable(curse.value().description().getString()) // Curse name
                                        .setStyle(Style.EMPTY.withColor(ChatFormatting.RED))) // Red color for the curse name
                                .append(Component.literal(" removed from ")
                                        .setStyle(Style.EMPTY.withColor(ChatFormatting.WHITE))) // White color for the "removed from" text
                                .append(Component.literal(itemStack.getHoverName().getString()) // Item name
                                        .setStyle(Style.EMPTY.withColor(ChatFormatting.WHITE))); // White color for the item name


                        // Send the message to the player
                        player.sendSystemMessage(message);
                    }
                }
            }
        }

    }
}
