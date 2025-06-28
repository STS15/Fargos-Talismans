package com.sts15.fargos.items.looted;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.effect.EffectsInit;
import com.sts15.fargos.init.Config;
import com.sts15.fargos.items.TalismanItem;
import com.sts15.fargos.items.providers.Frozen_Shield_Provider;
import com.sts15.fargos.utils.TalismanUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.theillusivec4.curios.api.CuriosApi;
import java.lang.reflect.Field;
import java.util.List;

public class FrozenShieldItem extends TalismanItem implements Frozen_Shield_Provider {

    private static final Logger LOGGER = LogManager.getLogger(Fargos.MODID);
    private static final String charmName = "frozen_shield";
    private static final int RESISTANCE_AMPLIFIER = 0;
    private static final int RESISTANCE_DURATION = 40;

    public FrozenShieldItem() {
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
            String fieldName = charmName.toUpperCase() + "_TOGGLE"; // => FROZEN_SHIELD_TOGGLE
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

            boolean hasFrozenShieldEquipped = CuriosApi.getCuriosHelper()
                    .findEquippedCurio(stack -> stack.getItem() instanceof Frozen_Shield_Provider, player)
                    .isPresent();
            if ((!hasFrozenShieldEquipped) || !checkConfigEnabledStatus()) {
                removeFrozenShieldResistance(player);
                return;
            }
            if (!TalismanUtil.isTalismanEnabled(player, charmName))
                return;

            float halfHealth = player.getMaxHealth() / 2.0F;
            if (player.getHealth() < halfHealth) {
                applyFrozenShieldResistance(player);
            } else {
                removeFrozenShieldResistance(player);
            }
        }

        @SuppressWarnings({ "removal", "deprecation" })
        @SubscribeEvent
        public static void onLivingHurt(LivingIncomingDamageEvent event) {
            LivingEntity entity = event.getEntity();
            if (!(entity instanceof ServerPlayer player)) {
                return;
            }

            DamageSource source = event.getSource();
            ResourceKey<DamageType> fallDamageType = DamageTypes.FREEZE;

            if (source.is(fallDamageType)) {
                boolean hasFrozenShieldEquipped = CuriosApi.getCuriosHelper()
                        .findEquippedCurio(stack -> stack.getItem() instanceof Frozen_Shield_Provider, player)
                        .isPresent();
                if (!TalismanUtil.isTalismanEnabled(player, charmName))
                    return;

                if (hasFrozenShieldEquipped) {
                    event.setAmount(0.0F);
                    event.setCanceled(true);
                }
            }
        }

        private static void applyFrozenShieldResistance(Player player) {
            MobEffectInstance resistance = new MobEffectInstance(
                    MobEffects.DAMAGE_RESISTANCE,
                    RESISTANCE_DURATION,
                    RESISTANCE_AMPLIFIER,
                    false,
                    false
            );

            MobEffectInstance currentEffect = player.getEffect(MobEffects.DAMAGE_RESISTANCE);
            if (currentEffect == null
                    || currentEffect.getAmplifier() < RESISTANCE_AMPLIFIER
                    || currentEffect.getDuration() < 10) {
                player.addEffect(resistance);
            }
        }

        private static void removeFrozenShieldResistance(Player player) {
            MobEffectInstance currentEffect = player.getEffect(MobEffects.DAMAGE_RESISTANCE);
            if (currentEffect != null) {
                boolean isOurEffect = (currentEffect.getAmplifier() == RESISTANCE_AMPLIFIER
                        && currentEffect.getDuration() <= RESISTANCE_DURATION);
                if (isOurEffect) {
                    player.removeEffect(MobEffects.DAMAGE_RESISTANCE);
                }
            }
        }
    }
}

