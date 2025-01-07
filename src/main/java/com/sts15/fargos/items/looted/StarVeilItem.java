package com.sts15.fargos.items.looted;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.init.Config;
import com.sts15.fargos.items.TalismanItem;
import com.sts15.fargos.items.providers.Star_Veil_Provider;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.theillusivec4.curios.api.CuriosApi;
import java.lang.reflect.Field;
import java.util.List;

public class StarVeilItem extends TalismanItem implements Star_Veil_Provider {

    private static final Logger LOGGER = LogManager.getLogger(Fargos.MODID);
    private static final String charmName = "star_veil";

    public StarVeilItem() {
        super(new Item.Properties().rarity(Rarity.RARE));
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
        boolean isEnabled = true;
        try {
            String fieldName = charmName.toUpperCase() + "_TOGGLE";
            Field toggleField = Config.class.getField(fieldName);
            isEnabled = ((ModConfigSpec.BooleanValue) toggleField.get(null)).get();
        } catch (Exception e) {
            LOGGER.error("Error checking config status for " + charmName, e);
        }
        return isEnabled;
    }

    @EventBusSubscriber(modid = Fargos.MODID)
    public static class Events {

        @SuppressWarnings("removal")
        @SubscribeEvent
        public static void onPlayerHurt(LivingIncomingDamageEvent event) {
            if (!(event.getEntity() instanceof ServerPlayer player)) return;

            boolean hasStarVeilEquipped = CuriosApi.getCuriosHelper()
                    .findEquippedCurio(stack -> stack.getItem() instanceof Star_Veil_Provider, player)
                    .isPresent();

            if (!hasStarVeilEquipped || !checkConfigEnabledStatus()) return;

            // Increase i-frames: e.g., set hurtResistantTime to a higher value
            // Vanilla default is about 10 ticks (0.5s). Let's add +10 as an example
            // so you have ~1 second of immunity. Adjust as you see fit.

            player.hurtMarked = true;
            player.invulnerableTime += 10;

            // Summon a star projectile above the attacker, or above the player
//            Entity attacker = event.getSource().getEntity();
//            if (attacker != null) {
//                Projectile starProjectile = new ThrownExperienceBottle(player.level());
//                starProjectile.setPos(attacker.getX(), attacker.getY() + 6.0, attacker.getZ());
//                starProjectile.setDeltaMovement(0, -1, 0); // Straight down
//                player.level().addFreshEntity(starProjectile);
//            }
        }
    }
}
