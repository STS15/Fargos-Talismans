package com.sts15.fargos.items.talismans;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.items.TalismanItem;
import com.sts15.fargos.items.providers.Fatigued_Talisman_Provider;
import com.sts15.fargos.utils.TalismanUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.List;


public class Fatigued_Talisman extends TalismanItem implements Fatigued_Talisman_Provider {

    private static final String talismanName = "fatigued_talisman";

    public Fatigued_Talisman() {
        super(new Properties().rarity(Rarity.UNCOMMON));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("item.fargostalismans.tooltip."+talismanName)
                .setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    @EventBusSubscriber(modid = Fargos.MODID)
    public static class Events {

        @SubscribeEvent
        public static void onLivingDamage(LivingIncomingDamageEvent event) {
            Entity source = event.getSource().getDirectEntity();
            if (source instanceof ServerPlayer player) {
                if (player.hasEffect(MobEffects.HUNGER)) {
                    if (CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof Fatigued_Talisman_Provider, player).isPresent()) {
                        if (!TalismanUtil.isTalismanEnabled(player, talismanName))
                            return;
                        event.setAmount(event.getAmount() * 1.25F);
                    }
                }
            }
        }
    }
}
