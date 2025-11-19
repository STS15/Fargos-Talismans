package com.sts15.fargos.items.tools;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import java.util.List;
import java.util.Optional;

public class BasicFishingRod extends FishingRodItem {

    private final int fishingPower;
    private final boolean lavaFishing;
    private final Optional<Component> extraNote;

    /**
     * @param props              item props
     * @param fishingPower       your custom stat (0–100 etc.)
     * @param lavaFishing        true if this rod can fish in lava
     * @param extraNote          optional tooltip line (can be null)
     */
    public BasicFishingRod(Item.Properties props, int fishingPower, boolean lavaFishing, Component extraNote) {
        super(props);
        this.fishingPower = fishingPower;
        this.lavaFishing = lavaFishing;
        this.extraNote = Optional.ofNullable(extraNote);
    }

    public int getFishingPower() { return fishingPower; }

    public boolean canFishInLava() { return lavaFishing; }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("item.fargostalismans.fishing_power", fishingPower).setStyle(Style.EMPTY.withColor(ChatFormatting.AQUA)));
        if (lavaFishing) {
            tooltip.add(Component.translatable("item.fargostalismans.lava_fishing").setStyle(Style.EMPTY.withColor(ChatFormatting.GOLD)));
        }
        extraNote.ifPresent(note -> tooltip.add(note.copy().withStyle(ChatFormatting.GRAY)));
        super.appendHoverText(stack, context, tooltip, flag);
    }
}
