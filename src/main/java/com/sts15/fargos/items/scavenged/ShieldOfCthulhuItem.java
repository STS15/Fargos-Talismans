package com.sts15.fargos.items.scavenged;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.init.Config;
import com.sts15.fargos.items.TalismanItem;
import com.sts15.fargos.items.providers.Shield_Of_Cthulhu_Provider;
import com.sts15.fargos.utils.TalismanUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import java.lang.reflect.Field;
import java.util.List;

public class ShieldOfCthulhuItem extends TalismanItem implements ICurioItem, Shield_Of_Cthulhu_Provider {

    private static final Logger LOGGER = LogManager.getLogger(Fargos.MODID);
    public static final String charmName = "shield_of_cthulhu";

    public ShieldOfCthulhuItem() {
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
            LOGGER.error("Error checking config status for " + charmName);
            return true;
        }
    }

    @EventBusSubscriber(modid = Fargos.MODID)
    public static class Events {

        @SubscribeEvent
        public static void onPlayerHit(LivingIncomingDamageEvent event) {
            if (!(event.getEntity() instanceof ServerPlayer player)) return;
            if (player.level().isClientSide) return;

            Entity attacker = event.getSource().getEntity();
            if (!(attacker instanceof LivingEntity livingAttacker)) return;

            boolean equipped = CuriosApi.getCuriosHelper().findEquippedCurio(
                    stack -> stack.getItem() instanceof Shield_Of_Cthulhu_Provider, player).isPresent();
            boolean enabled = TalismanUtil.isTalismanEnabled(player, charmName);

            if (!equipped || !enabled || !player.isSprinting()) return;

            double dx = attacker.getX() - player.getX();
            double dz = attacker.getZ() - player.getZ();
            double magnitude = Math.sqrt(dx * dx + dz * dz);
            if (magnitude < 0.01) return;

            dx /= magnitude;
            dz /= magnitude;

            double launchStrength = 1.5;
            double upwardStrength = 0.5;

            attacker.push(dx * launchStrength, upwardStrength, dz * launchStrength);
            attacker.hurtMarked = true;

            float damage = 4.0F; // 2 hearts of damage
            attacker.hurt(player.damageSources().playerAttack(player), damage);
        }
    }
}
