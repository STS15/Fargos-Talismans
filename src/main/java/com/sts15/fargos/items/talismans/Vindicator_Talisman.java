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

import com.sts15.fargos.items.providers.Vindicator_Talisman_Provider;
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
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import top.theillusivec4.curios.api.CuriosApi;

public class Vindicator_Talisman extends TalismanItem implements Vindicator_Talisman_Provider {

    private static final String talismanName = "vindicator_talisman";
	
	private static final Map<UUID, ItemStack> lastHeldItems = new HashMap<>();
    private static final Map<UUID, Long> lastWeaponSwitchTimes = new HashMap<>();
    private static final Map<UUID, Boolean> canBoostAttack = new HashMap<>();
    private static final Map<UUID, Long> lastBoostTimes = new HashMap<>();

    public Vindicator_Talisman() {
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
    
    private static void trackWeaponSwitch(Player player, UUID playerId) {
        ItemStack currentItem = player.getMainHandItem();
        long currentTime = player.level().getGameTime();

        // Check if the player switched weapons
        if (!lastHeldItems.containsKey(playerId) || !ItemStack.matches(currentItem, lastHeldItems.get(playerId))) {
            lastHeldItems.put(playerId, currentItem.copy());
            lastWeaponSwitchTimes.put(playerId, currentTime);

            if (!lastBoostTimes.containsKey(playerId) || (currentTime - lastBoostTimes.get(playerId) > Config.VINDICATOR_TALISMAN_BOOST_COOLDOWN.getAsInt())) {
                canBoostAttack.put(playerId, true);
                //System.out.println("Weapon switched for player " + player.getName().getString() + ": boost enabled");
            }
        }
    }

    private static void applyVindicatorBoostIfEligible(Player attacker, LivingDamageEvent.Pre event) {
        UUID attackerId = attacker.getUUID();
        long currentTime = attacker.level().getGameTime();

        if (canBoostAttack.getOrDefault(attackerId, false) && isEligibleForVindicatorBoost(attackerId, currentTime)) {
            float boostedDamage = event.getOriginalDamage() * (1f + Config.VINDICATOR_TALISMAN_BOOST_DAMAGE_MODIFIER.get().floatValue());
            event.setNewDamage(boostedDamage);
            canBoostAttack.put(attackerId, false);
            lastBoostTimes.put(attackerId, currentTime);
            //System.out.println("Applied vindicator boost to player " + attacker.getName().getString() + ": new damage = " + boostedDamage);
        }
    }

    private static boolean isEligibleForVindicatorBoost(UUID playerId, long currentTime) {
        Long lastSwitchTime = lastWeaponSwitchTimes.get(playerId);
        boolean isEligible = lastSwitchTime != null && (currentTime - lastSwitchTime <= Config.VINDICATOR_TALISMAN_WEAPON_SWAP_TIME.getAsInt());
        return isEligible;
    }

    @EventBusSubscriber(modid = Fargos.MODID)
    public static class Events {

        @SubscribeEvent
        public static void onPlayerTick(PlayerTickEvent.Pre event) {
            if (!(event.getEntity() instanceof ServerPlayer player))
                return;

            UUID playerUUID = player.getUUID();

            if (player.hasEffect(EffectsInit.VINDICATOR_TALISMAN_EFFECT) || CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof Vindicator_Talisman_Provider, player).isPresent()) {
                if (!TalismanUtil.isTalismanEnabled(player, talismanName))
                    return;
                trackWeaponSwitch(player, playerUUID);
            }
        }

        @SubscribeEvent
        public static void onLivingHurt(LivingDamageEvent.Pre event) {
            if (!(event.getSource().getEntity() instanceof ServerPlayer player))
                return;

            if (player.hasEffect(EffectsInit.VINDICATOR_TALISMAN_EFFECT) || CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof Vindicator_Talisman_Provider, player).isPresent()) {
                if (!TalismanUtil.isTalismanEnabled(player, talismanName))
                    return;
                applyVindicatorBoostIfEligible(player, event);
            }
        }
    }
}