package com.sts15.fargos.items.talismans;

import java.util.List;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.items.TalismanItem;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.TooltipContext;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import top.theillusivec4.curios.api.CuriosApi;

public class Amethyst_Talisman extends TalismanItem {

    public Amethyst_Talisman() {
        super(new Item.Properties().rarity(Rarity.UNCOMMON));
    }
    
    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        pTooltipComponents.add(Component.translatable("item.fargostalismans.tooltip.amethyst_talisman")
        		.setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    }
    
    @EventBusSubscriber(modid = Fargos.MODID)
    public static class Events {
        @SubscribeEvent
        public static void onLivingHurt(LivingIncomingDamageEvent event) {
            if (!(event.getEntity() instanceof Player player))
                return;
            
            DamageSource source = event.getSource();
            ResourceKey<DamageType> DamageTypeArrow = DamageTypes.ARROW;
            ResourceKey<DamageType> DamageTypeProjectile = DamageTypes.MOB_PROJECTILE;
            ResourceKey<DamageType> DamageTypeSkull = DamageTypes.WITHER_SKULL;
            ResourceKey<DamageType> DamageTypeTrident = DamageTypes.TRIDENT;
            ResourceKey<DamageType> DamageTypeUNFireball = DamageTypes.UNATTRIBUTED_FIREBALL;
            ResourceKey<DamageType> DamageTypeFireball = DamageTypes.FIREBALL;
            ResourceKey<DamageType> DamageTypeThrown = DamageTypes.THROWN;
            ResourceKey<DamageType> DamageTypeWindCharge = DamageTypes.WIND_CHARGE;

            if (	  source.is(DamageTypeArrow)
            		||source.is(DamageTypeProjectile)
            		||source.is(DamageTypeSkull)
            		||source.is(DamageTypeTrident)
            		||source.is(DamageTypeUNFireball)
            		||source.is(DamageTypeFireball)
            		||source.is(DamageTypeThrown)
            		||source.is(DamageTypeWindCharge)
               ) {
                if (CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof Amethyst_Talisman, player).isPresent()) {
                    event.setCanceled(true);
                }
            }
        }
    }
}
