package com.sts15.fargos.items.scavenged;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.init.Config;
import com.sts15.fargos.items.TalismanItem;
import com.sts15.fargos.items.providers.Master_Ninja_Gear_Provider;
import com.sts15.fargos.utils.WardenAttackTracker;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import java.lang.reflect.Field;
import java.util.*;

public class MasterNinjaGearItem extends TalismanItem implements ICurioItem, Master_Ninja_Gear_Provider {

    public static final String charmName = "master_ninja_gear";

    public MasterNinjaGearItem() {
        super(new Properties().rarity(Rarity.RARE));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("item.fargostalismans.tooltip." + charmName)
                .setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
        if (!checkConfigEnabledStatus()) {
            tooltipComponents.add(Component.translatable("config.fargostalismans.tooltip.disabled")
                    .setStyle(Style.EMPTY.withColor(ChatFormatting.RED)));
        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    public static boolean checkConfigEnabledStatus() {
        try {
            String fieldName = charmName.toUpperCase() + "_TOGGLE";
            Field toggleField = Config.class.getField(fieldName);
            return ((ModConfigSpec.BooleanValue) toggleField.get(null)).get();
        } catch (Exception e) {
            return true;
        }
    }

    @EventBusSubscriber(modid = Fargos.MODID)
    public static class Events {

        @SubscribeEvent
        public static void onWardenDamaged(LivingIncomingDamageEvent event) {
            if (event.getEntity() instanceof Warden warden && event.getSource().getEntity() instanceof Player player) {
                WardenAttackTracker.markAttacked(warden, player);
            }
        }

        @SubscribeEvent
        public static void onWardenDeath(LivingDeathEvent event) {
            if (event.getEntity() instanceof Warden warden) {
                WardenAttackTracker.clear(warden);
            }
        }
    }

}
