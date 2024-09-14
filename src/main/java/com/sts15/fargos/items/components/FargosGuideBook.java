package com.sts15.fargos.items.components;

import com.sts15.fargos.compat.PatchouliHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public abstract class FargosGuideBook extends Item {
    public FargosGuideBook(Properties properties) {
        super(properties.stacksTo(1));
    }
    
    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        pTooltipComponents.add(Component.literal("Guide Book")
        		.setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    }

    @NotNull
    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack stack = playerIn.getItemInHand(handIn);
        if (playerIn instanceof ServerPlayer player) {
            PatchouliHandler.openBookGUI(player);
        }
        return new InteractionResultHolder<>(InteractionResult.CONSUME, stack);
    }

}
