package com.sts15.fargos.items.forces;

import com.sts15.fargos.init.Config;
import com.sts15.fargos.items.TalismanItem;
import com.sts15.fargos.items.providers.*;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class Force_of_Negative extends TalismanItem implements
        Fired_Talisman_Provider, Poisoned_Talisman_Provider, Withered_Talisman_Provider, Blinded_Talisman_Provider, Fatigued_Talisman_Provider,
        Slowed_Talisman_Provider, Nauseated_Talisman_Provider, Weakened_Talisman_Provider
{

    public Force_of_Negative() {
        super(new Properties().rarity(Rarity.RARE));
    }
    
    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        int configValue1 = (int) (Config.FIRED_TALISMAN_INCREASED_DAMAGE.getAsDouble() * 100);
        int configValue2 = (int) (Config.POISONED_TALISMAN_INCREASED_DAMAGE.getAsDouble() * 100);
        int configValue3 = (int) (Config.WITHERED_TALISMAN_INCREASED_DAMAGE.getAsDouble() * 100);
        int configValue4 = (int) (Config.BLINDED_TALISMAN_DAMAGE_FACTOR.getAsDouble() * 100);
        int configValue5 = (int) (Config.FATIGUED_TALISMAN_INCREASED_DAMAGE.getAsDouble() * 100);
        int configValue6 = (int) (Config.SLOWED_TALISMAN_INCREASED_DAMAGE.getAsDouble() * 100);
        int configValue7 = (int) (Config.NAUSEATED_TALISMAN_INCREASED_DAMAGE.getAsDouble() * 100);
        int configValue8 = (int) (Config.WEAKENED_TALISMAN_INCREASED_DAMAGE.getAsDouble() * 100);
        pTooltipComponents.add(Component.translatable("item.fargostalismans.tooltip.force_of_negative")
        		.setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
        pTooltipComponents.add(Component.literal("- ")
        		.append(Component.translatable("item.fargostalismans.tooltip.fired_talisman.title"))
                .append(Component.literal(": "))
                .append(Component.translatable("item.fargostalismans.tooltip.fired_talisman", configValue1))
                .withStyle(ChatFormatting.DARK_RED)
        );
        pTooltipComponents.add(Component.literal("- ")
        		.append(Component.translatable("item.fargostalismans.tooltip.poisoned_talisman.title"))
                .append(Component.literal(": "))
                .append(Component.translatable("item.fargostalismans.tooltip.poisoned_talisman",configValue2))
                .withStyle(ChatFormatting.DARK_GREEN)
        );
        pTooltipComponents.add(Component.literal("- ")
        		.append(Component.translatable("item.fargostalismans.tooltip.withered_talisman.title"))
                .append(Component.literal(": "))
                .append(Component.translatable("item.fargostalismans.tooltip.withered_talisman",configValue3))
                .withStyle(ChatFormatting.DARK_GRAY)
        );
        pTooltipComponents.add(Component.literal("- ")
        		.append(Component.translatable("item.fargostalismans.tooltip.blinded_talisman.title"))
                .append(Component.literal(": "))
                .append(Component.translatable("item.fargostalismans.tooltip.blinded_talisman",configValue4))
                .withStyle(ChatFormatting.BLACK)
        );
        pTooltipComponents.add(Component.literal("- ")
        		.append(Component.translatable("item.fargostalismans.tooltip.fatigued_talisman.title"))
                .append(Component.literal(": "))
                .append(Component.translatable("item.fargostalismans.tooltip.fatigued_talisman",configValue5))
                .withStyle(ChatFormatting.GREEN)
        );
        pTooltipComponents.add(Component.literal("- ")
                .append(Component.translatable("item.fargostalismans.tooltip.slowed_talisman.title"))
                .append(Component.literal(": "))
                .append(Component.translatable("item.fargostalismans.tooltip.slowed_talisman",configValue6))
                .withStyle(ChatFormatting.GRAY)
        );
        pTooltipComponents.add(Component.literal("- ")
                .append(Component.translatable("item.fargostalismans.tooltip.nauseated_talisman.title"))
                .append(Component.literal(": "))
                .append(Component.translatable("item.fargostalismans.tooltip.nauseated_talisman",configValue7))
                .withStyle(ChatFormatting.YELLOW)
        );
        pTooltipComponents.add(Component.literal("- ")
                .append(Component.translatable("item.fargostalismans.tooltip.weakened_talisman.title"))
                .append(Component.literal(": "))
                .append(Component.translatable("item.fargostalismans.tooltip.weakened_talisman", configValue8))
                .withStyle(ChatFormatting.DARK_AQUA)
        );
        
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    }
}
