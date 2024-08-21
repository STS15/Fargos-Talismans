package com.sts15.fargos.items.talismans;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.items.TalismanItem;
import com.sts15.fargos.items.providers.Night_Talisman_Provider;
import com.sts15.fargos.utils.TalismanUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Night_Talisman extends TalismanItem implements Night_Talisman_Provider {

    private static final String talismanName = "night_talisman";
    private static final ResourceLocation NIGHT_ARMOR_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "night_talisman_armor_modifier");

    private static final double ARMOR_BONUS = 0.10;
    private static final Map<UUID, Double> lastArmorValues = new HashMap<>();

    public Night_Talisman() {
        super(new Properties().rarity(Rarity.UNCOMMON));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("item.fargostalismans.tooltip." + talismanName)
                .setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    @EventBusSubscriber(modid = Fargos.MODID)
    public static class Events {

        @SubscribeEvent
        public static void onPlayerTick(PlayerTickEvent.Pre event) {
            if (!(event.getEntity() instanceof ServerPlayer player))
                return;

            Level level = player.level();
            boolean isDaytime = level.isDay();
            UUID playerUUID = player.getUUID();

            AttributeInstance armorAttribute = player.getAttribute(Attributes.ARMOR);
            if (armorAttribute == null) {
                //System.out.println("Player " + player.getName().getString() + " does not have an armor attribute!");
                return;
            }

            boolean hasTalismanEquipped = CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof Night_Talisman_Provider, player).isPresent();

            if (!isDaytime && hasTalismanEquipped) {
                if (!TalismanUtil.isTalismanEnabled(player, talismanName))
                    return;

                double currentArmor = armorAttribute.getValue();
                if (!armorAttribute.hasModifier(NIGHT_ARMOR_MODIFIER_ID) || !lastArmorValues.containsKey(playerUUID) || lastArmorValues.get(playerUUID) != currentArmor) {
                    if (armorAttribute.hasModifier(NIGHT_ARMOR_MODIFIER_ID)) {
                        //System.out.println("Updating Night Armor Bonus for player " + player.getName().getString() + ". Old Armor: " + lastArmorValues.get(playerUUID) + ", New Armor: " + currentArmor);
                        armorAttribute.removeModifier(NIGHT_ARMOR_MODIFIER_ID);
                    }

                    AttributeModifier armorModifier = new AttributeModifier(NIGHT_ARMOR_MODIFIER_ID, ARMOR_BONUS, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
                    armorAttribute.addTransientModifier(armorModifier);

                    //System.out.println("Night Armor Bonus applied. Modified Armor: " + armorAttribute.getValue());
                    lastArmorValues.put(playerUUID, currentArmor);
                }
            } else {
                if (armorAttribute.hasModifier(NIGHT_ARMOR_MODIFIER_ID)) {
                    //System.out.println("Removing Night Armor Bonus for player " + player.getName().getString());
                    armorAttribute.removeModifier(NIGHT_ARMOR_MODIFIER_ID);
                }
                lastArmorValues.remove(playerUUID);
            }
        }
    }
}
