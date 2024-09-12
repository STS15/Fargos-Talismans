package com.sts15.fargos.items.talismans;

import java.util.List;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.effect.EffectsInit;
import com.sts15.fargos.items.TalismanItem;

import com.sts15.fargos.items.providers.Arctic_Talisman_Provider;
import com.sts15.fargos.utils.TalismanUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import top.theillusivec4.curios.api.CuriosApi;

public class Arctic_Talisman extends TalismanItem implements Arctic_Talisman_Provider {

    private static final String talismanName = "arctic_talisman";

    public Arctic_Talisman() {
        super(new Item.Properties().rarity(Rarity.UNCOMMON));
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
        public static void onLivingHurt(LivingIncomingDamageEvent event) {
            if (!(event.getEntity() instanceof ServerPlayer player))
                return;
            
            DamageSource source = event.getSource();
            ResourceKey<DamageType> fallDamageType = DamageTypes.FREEZE;

            if (source.is(fallDamageType)) {
                if (player.hasEffect(EffectsInit.ARCTIC_TALISMAN_EFFECT) || CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof Arctic_Talisman_Provider, player).isPresent()) {
                    if (TalismanUtil.isTalismanEnabled(player, talismanName)) {
                        event.setCanceled(true);
                    }
                }
            }
        }
    }
}
