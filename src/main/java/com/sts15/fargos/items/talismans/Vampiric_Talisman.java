package com.sts15.fargos.items.talismans;

import java.util.List;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.effect.EffectsInit;
import com.sts15.fargos.items.TalismanItem;

import com.sts15.fargos.items.providers.Vampiric_Talisman_Provider;
import com.sts15.fargos.utils.TalismanUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import top.theillusivec4.curios.api.CuriosApi;

public class Vampiric_Talisman extends TalismanItem implements Vampiric_Talisman_Provider {

    private static final String talismanName = "vampiric_talisman";

    public Vampiric_Talisman() {
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
        public static void onLivingDamage(LivingIncomingDamageEvent event) {
            // Get the entity causing the damage
            Entity source = event.getSource().getEntity();

            // Check if the entity causing the damage is a player
            if (source instanceof ServerPlayer player) {
                // Find the equipped Vampiric Talisman
                if (player.hasEffect(EffectsInit.VAMPIRIC_TALISMAN_EFFECT) || CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof Vampiric_Talisman_Provider, player).isPresent()) {
                    if (!TalismanUtil.isTalismanEnabled(player, talismanName))
                        return;
                    // Heal the player for 20% of the damage dealt
                    float damageDealt = event.getAmount();
                    float healthToHeal = damageDealt * 0.20F;
                    player.heal(healthToHeal);
                }
            }
        }
    }
}
