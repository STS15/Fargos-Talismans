package com.sts15.fargos.items.looted;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.init.Config;
import com.sts15.fargos.items.TalismanItem;
import com.sts15.fargos.items.providers.Charm_of_Myths_Provider;
import com.sts15.fargos.utils.TalismanUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.theillusivec4.curios.api.CuriosApi;

import java.lang.reflect.Field;
import java.util.List;

public class CharmOfMythsItem extends TalismanItem implements Charm_of_Myths_Provider {

    private static final Logger LOGGER = LogManager.getLogger(Fargos.MODID);
    private static final String charmName = "charm_of_myths";
    private static final int REGEN_AMPLIFIER = 0;
    private static final int REGEN_DURATION = 40;

    public CharmOfMythsItem() {
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
        } catch (NoSuchFieldException | IllegalAccessException e) {
            LOGGER.error("Error checking config status for " + charmName, e);
        }
        return isEnabled;
    }

    @EventBusSubscriber(modid = Fargos.MODID)
    public static class Events {

        @SuppressWarnings({ "removal", "deprecation" })
        @SubscribeEvent
        public static void onPlayerTick(PlayerTickEvent.Pre event) {
            if (!(event.getEntity() instanceof ServerPlayer player)) {
                return;
            }

            boolean hasCharmEquipped = CuriosApi.getCuriosHelper()
                    .findEquippedCurio(stack -> stack.getItem() instanceof Charm_of_Myths_Provider, player)
                    .isPresent();

            if (!hasCharmEquipped || !checkConfigEnabledStatus()) {
                removeCharmRegen(player);
                return;
            }
            if (!TalismanUtil.isTalismanEnabled(player, charmName))
                return;

            float currentHealth = player.getHealth();
            float maxHealth = player.getMaxHealth();
            FoodData foodData = player.getFoodData();
            int foodLevel = foodData.getFoodLevel();

            if (currentHealth < maxHealth && foodLevel >= 18) {
                applyCharmRegen(player);
            }
        }

        private static void applyCharmRegen(Player player) {
            MobEffectInstance newRegen = new MobEffectInstance(
                    MobEffects.REGENERATION,
                    REGEN_DURATION,
                    REGEN_AMPLIFIER,
                    false,
                    false
            );

            MobEffectInstance current = player.getEffect(MobEffects.REGENERATION);
            if (current == null
                    || current.getAmplifier() < REGEN_AMPLIFIER
                    || current.getDuration() < 10) {
                player.addEffect(newRegen);
            }
        }

        private static void removeCharmRegen(Player player) {
            MobEffectInstance current = player.getEffect(MobEffects.REGENERATION);
            if (current != null) {
                boolean isOurEffect = (current.getAmplifier() == REGEN_AMPLIFIER
                        && current.getDuration() <= REGEN_DURATION);
                if (isOurEffect) {
                    player.removeEffect(MobEffects.REGENERATION);
                }
            }
        }
    }
}
