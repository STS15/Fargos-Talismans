package com.sts15.fargos.items.forces;

import java.util.List;
import com.sts15.fargos.init.Config;
import com.sts15.fargos.items.TalismanItem;
import com.sts15.fargos.items.providers.*;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;

public class Force_of_Warrior extends TalismanItem implements Battle_Talisman_Provider, Iron_Golem_Talisman_Provider, Thorny_Talisman_Provider, Cactus_Talisman_Provider, Void_Talisman_Provider {

    public Force_of_Warrior() {
        super(new Item.Properties().rarity(Rarity.RARE));
    }
    
    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        int configValue1 = (int) (Config.CACTUS_TALISMAN_REFLECTED_DAMAGE.getAsDouble() * 100);
        int configValue2 = (int) (Config.IRON_GOLEM_TALISMAN_HEALTH_BOOST_MULTIPLIER.getAsDouble() * 100);

        pTooltipComponents.add(Component.translatable("item.fargostalismans.tooltip.force_of_warrior")
        		.setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
        pTooltipComponents.add(Component.literal("- ")
        		.append(Component.translatable("item.fargostalismans.tooltip.battle_talisman.title"))
                .append(Component.literal(": "))
                .append(Component.translatable("item.fargostalismans.tooltip.battle_talisman"))
                .withStyle(ChatFormatting.DARK_GREEN)
        );
        pTooltipComponents.add(Component.literal("- ")
        		.append(Component.translatable("item.fargostalismans.tooltip.cactus_talisman.title"))
                .append(Component.literal(": "))
                .append(Component.translatable("item.fargostalismans.tooltip.cactus_talisman",configValue1))
                .withStyle(ChatFormatting.GREEN)
        );
        pTooltipComponents.add(Component.literal("- ")
        		.append(Component.translatable("item.fargostalismans.tooltip.void_talisman.title"))
                .append(Component.literal(": "))
                .append(Component.translatable("item.fargostalismans.tooltip.void_talisman"))
                .withStyle(ChatFormatting.DARK_GRAY)
        );
        pTooltipComponents.add(Component.literal("- ")
        		.append(Component.translatable("item.fargostalismans.tooltip.thorny_talisman.title"))
                .append(Component.literal(": "))
                .append(Component.translatable("item.fargostalismans.tooltip.thorny_talisman"))
                .withStyle(ChatFormatting.RED)
        );
        pTooltipComponents.add(Component.literal("- ")
        		.append(Component.translatable("item.fargostalismans.tooltip.iron_golem_talisman.title"))
                .append(Component.literal(": "))
                .append(Component.translatable("item.fargostalismans.tooltip.iron_golem_talisman",configValue2))
                .withStyle(ChatFormatting.WHITE)
        );
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    }
}
