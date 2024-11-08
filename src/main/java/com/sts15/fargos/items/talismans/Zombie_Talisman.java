package com.sts15.fargos.items.talismans;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.effect.EffectsInit;
import com.sts15.fargos.init.Config;
import com.sts15.fargos.items.TalismanItem;

import com.sts15.fargos.items.providers.Zombie_Talisman_Provider;
import com.sts15.fargos.utils.TalismanUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
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
import top.theillusivec4.curios.api.CuriosApi;

public class Zombie_Talisman extends TalismanItem implements Zombie_Talisman_Provider {

    private static final String talismanName = "zombie_talisman";

    private static final long INCREASE_ATTACK_SPEED_THRESHOLD = 100;

    private static final Map<UUID, Long> lastAttackTimes = new HashMap<>();
    private static final Map<UUID, Integer> attackCounts = new HashMap<>();
    private static final Map<UUID, Long> cooldownEndTimes = new HashMap<>();

    private static final ResourceLocation ZOMBIE_ATTACK_SPEED_BOOST_ID = ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "zombie_attack_speed_boost");

    public Zombie_Talisman() {
        super(new Item.Properties().rarity(Rarity.UNCOMMON));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("item.fargostalismans.tooltip." + talismanName)
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

    private static void resetAttackSpeed(Player player, UUID playerId) {
        AttributeInstance attackSpeedAttribute = player.getAttribute(Attributes.ATTACK_SPEED);
        if (attackSpeedAttribute != null && attackSpeedAttribute.hasModifier(ZOMBIE_ATTACK_SPEED_BOOST_ID)) {
            attackSpeedAttribute.removeModifier(ZOMBIE_ATTACK_SPEED_BOOST_ID);
            attackCounts.remove(playerId);
            long cooldownEndTime = player.level().getGameTime() + Config.ZOMBIE_TALISMAN_COOLDOWN_PERIOD.getAsInt();
            cooldownEndTimes.put(playerId, cooldownEndTime);
        }
    }

    private static void increaseAttackSpeed(Player player, UUID playerId) {
        if (cooldownEndTimes.getOrDefault(playerId, 0L) > player.level().getGameTime()) {
            return;
        }

        AttributeInstance attackSpeedAttribute = player.getAttribute(Attributes.ATTACK_SPEED);
        if (attackSpeedAttribute != null && !attackSpeedAttribute.hasModifier(ZOMBIE_ATTACK_SPEED_BOOST_ID)) {
            AttributeModifier modifier = new AttributeModifier(ZOMBIE_ATTACK_SPEED_BOOST_ID, Config.ZOMBIE_TALISMAN_ATTACK_SPEED_BOOST.getAsDouble(), AttributeModifier.Operation.ADD_VALUE);
            attackSpeedAttribute.addTransientModifier(modifier);
            attackCounts.put(playerId, 0);
        }
    }

    @EventBusSubscriber(modid = Fargos.MODID)
    public static class Events {

        @SuppressWarnings({ "removal", "deprecation" })
		@SubscribeEvent
        public static void onPlayerTick(PlayerTickEvent.Pre event) {
            if (!(event.getEntity() instanceof ServerPlayer player))
                return;

            UUID playerUUID = player.getUUID();
            long currentTime = player.level().getGameTime();

            if (player.hasEffect(EffectsInit.ZOMBIE_TALISMAN_EFFECT) || CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof Zombie_Talisman_Provider, player).isPresent()) {
                if (!TalismanUtil.isTalismanEnabled(player, talismanName))
                    return;
                Long lastAttackTime = lastAttackTimes.getOrDefault(playerUUID, 0L);
                if (currentTime - lastAttackTime >= INCREASE_ATTACK_SPEED_THRESHOLD) {
                    increaseAttackSpeed(player, playerUUID);
                }
            } else {
                resetAttackSpeed(player, playerUUID);
            }
        }

        @SuppressWarnings({ "removal", "deprecation" })
		@SubscribeEvent
        public static void onLivingHurt(LivingIncomingDamageEvent event) {
            if (!(event.getSource().getEntity() instanceof ServerPlayer player))
                return;

            UUID playerUUID = player.getUUID();

            if (player.hasEffect(EffectsInit.ZOMBIE_TALISMAN_EFFECT) || CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof Zombie_Talisman_Provider, player).isPresent()) {
                if (!TalismanUtil.isTalismanEnabled(player, talismanName))
                    return;
                int attackCount = attackCounts.getOrDefault(playerUUID, 0);
                attackCount++;
                attackCounts.put(playerUUID, attackCount);

                if (attackCount >= Config.ZOMBIE_TALISMAN_MAX_ATTACKS.getAsInt()) {
                    resetAttackSpeed(player, playerUUID);
                } else {
                    lastAttackTimes.put(playerUUID, player.level().getGameTime());
                }
            }
        }
    }
}
