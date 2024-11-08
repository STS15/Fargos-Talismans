package com.sts15.fargos.items.talismans;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.effect.EffectsInit;
import com.sts15.fargos.init.Config;
import com.sts15.fargos.items.TalismanItem;
import com.sts15.fargos.items.providers.Snowy_Talisman_Provider;
import com.sts15.fargos.utils.TalismanUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import top.theillusivec4.curios.api.CuriosApi;

import java.lang.reflect.Field;
import java.util.List;


public class Snowy_Talisman extends TalismanItem implements Snowy_Talisman_Provider {

    private static final String talismanName = "snowy_talisman";

    public Snowy_Talisman() {
        super(new Properties().rarity(Rarity.UNCOMMON));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        double configValue = Config.SNOWY_TALISMAN_INCREASED_DAMAGE.getAsDouble() * 100;
        String formattedReduction;
        if (configValue == (int) configValue) { formattedReduction = String.format("%.0f", configValue);
        } else { formattedReduction = String.format("%.1f", configValue);}
        tooltipComponents.add(Component.translatable("item.fargostalismans.tooltip." + talismanName,formattedReduction)
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

        @SubscribeEvent
        public static void onLivingHurt(LivingIncomingDamageEvent event) {
            Entity source = event.getSource().getEntity();
            if (!(source instanceof ServerPlayer player))
                return;

            Level level = player.level();
            Biome biome = level.getBiome(player.blockPosition()).value();
            boolean isSnowing = level.isRaining() && biome.getPrecipitationAt(player.blockPosition()) == Biome.Precipitation.SNOW && biome.getBaseTemperature() <= 0.15F;

            if (isSnowing) {
                if (player.hasEffect(EffectsInit.SNOWY_TALISMAN_EFFECT) || CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof Snowy_Talisman_Provider, player).isPresent()) {
                    if (!TalismanUtil.isTalismanEnabled(player, talismanName))
                        return;
                    //System.out.println("Pre: " + event.getAmount());
                    event.setAmount(event.getAmount() *  (1f + Config.SNOWY_TALISMAN_INCREASED_DAMAGE.get().floatValue()));
                    //System.out.println("Post: " + event.getAmount());
                }
            }
        }
    }

}
