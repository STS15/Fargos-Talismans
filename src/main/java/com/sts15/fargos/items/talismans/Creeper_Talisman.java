package com.sts15.fargos.items.talismans;

import java.util.List;

import com.sts15.fargos.Config;
import com.sts15.fargos.Fargos;
import com.sts15.fargos.items.TalismanItem;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import top.theillusivec4.curios.api.CuriosApi;

public class Creeper_Talisman extends TalismanItem implements Creeper_Talisman_Provider {

    private static final String talismanName = "creeper_talisman";

    public Creeper_Talisman() {
        super(new Item.Properties().rarity(Rarity.UNCOMMON));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {

        if (!Config.isTalismanEnabledServer(talismanName)) {
            tooltipComponents.add(Component.translatable("item.fargostalismans.tooltip.disabled_by_server")
                    .setStyle(Style.EMPTY.withColor(ChatFormatting.RED)));
        } else if (!Config.isTalismanEnabledClient(talismanName)) {
            tooltipComponents.add(Component.translatable("item.fargostalismans.tooltip.disabled_by_client")
                    .setStyle(Style.EMPTY.withColor(ChatFormatting.RED)));
        } else {
            tooltipComponents.add(Component.translatable("item.fargostalismans.tooltip."+talismanName)
                    .setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
        }

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
    
    @EventBusSubscriber(modid = Fargos.MODID)
    public static class Events {
        @SubscribeEvent
        public static void onLivingJump(LivingEvent.LivingJumpEvent event) {
            if (event.getEntity() instanceof Player player) {
                if (CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof Creeper_Talisman_Provider, player).isPresent()) {
                    if (!Config.isTalismanEnabledOnClientAndServer(talismanName))
                        return;
                    Level level = player.level();
                    level.explode(player, player.getX(), player.getY(), player.getZ(), 4.0F, false, Level.ExplosionInteraction.NONE);
                }
            }
        }
    }
}
