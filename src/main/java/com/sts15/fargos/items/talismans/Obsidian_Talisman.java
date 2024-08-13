package com.sts15.fargos.items.talismans;

import java.util.List;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.items.TalismanItem;

import com.sts15.fargos.utils.TalismanUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import top.theillusivec4.curios.api.CuriosApi;

public class Obsidian_Talisman extends TalismanItem {

    private static final String talismanName = "obsidian_talisman";

    public Obsidian_Talisman() {
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
            ResourceKey<DamageType> DamageTypeExplosion = DamageTypes.EXPLOSION;
            ResourceKey<DamageType> DamageTypeExplosion_Player = DamageTypes.PLAYER_EXPLOSION;
            ResourceKey<DamageType> DamageTypeFirework = DamageTypes.FIREWORKS;
            ResourceKey<DamageType> DamageTypeBadRespawn = DamageTypes.BAD_RESPAWN_POINT;

            if (source.is(DamageTypeExplosion)
            		||source.is(DamageTypeExplosion_Player)
            		||source.is(DamageTypeFirework)
            		||source.is(DamageTypeBadRespawn)) {
                if (CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof Obsidian_Talisman, player).isPresent()) {
                    if (!TalismanUtil.isTalismanEnabled(player, talismanName))
                        return;
                    event.setCanceled(true);
                }
            }
        }
    }
}
