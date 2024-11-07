package com.sts15.fargos.items.talismans;

import java.lang.reflect.Field;
import java.util.List;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.effect.EffectsInit;
import com.sts15.fargos.init.Config;
import com.sts15.fargos.items.TalismanItem;

import com.sts15.fargos.utils.TalismanUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import top.theillusivec4.curios.api.CuriosApi;

public class Iron_Talisman extends TalismanItem {

    private static final String talismanName = "iron_talisman";

    public Iron_Talisman() {
        super(new Item.Properties().rarity(Rarity.UNCOMMON));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        double damageReduction = Config.IRON_TALISMAN_DAMAGE_REDUCTION.getAsDouble() * 100;
        String formattedReduction;
        if (damageReduction == (int) damageReduction) { formattedReduction = String.format("%.0f", damageReduction);
        } else { formattedReduction = String.format("%.1f", damageReduction);}
        tooltipComponents.add(Component.translatable("item.fargostalismans.tooltip." + talismanName, formattedReduction)
                .setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
        if (!checkConfigEnabledStatus()) {
            tooltipComponents.add(Component.translatable("config.fargostalismans.tooltip.disabled")
                    .setStyle(Style.EMPTY.withColor(ChatFormatting.RED)));
        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    public static boolean checkConfigEnabledStatus() {
        boolean isEnabled = true;
        try {
            String fieldName = talismanName.toUpperCase() + "_TOGGLE";
            Field toggleField = Config.class.getField(fieldName);
            isEnabled = ((ModConfigSpec.BooleanValue) toggleField.get(null)).get();
        } catch (NoSuchFieldException | IllegalAccessException e) {}
        return isEnabled;
    }
    
    @EventBusSubscriber(modid = Fargos.MODID)
    public static class Events {
    	
        @SuppressWarnings({ "removal", "deprecation" })
		@SubscribeEvent
        public static void onLivingHurt(LivingIncomingDamageEvent event) {
        	if (!(event.getEntity() instanceof ServerPlayer player))
                return;

            if (player.hasEffect(EffectsInit.IRON_TALISMAN_EFFECT) || CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof Diamond_Talisman, player).isPresent()) {
                if (!TalismanUtil.isTalismanEnabled(player, talismanName))
                    return;
                float reducedDamage = event.getAmount() * (1f - Config.IRON_TALISMAN_DAMAGE_REDUCTION.get().floatValue());
                event.setAmount(reducedDamage);
            }
        }
        
    }
}
