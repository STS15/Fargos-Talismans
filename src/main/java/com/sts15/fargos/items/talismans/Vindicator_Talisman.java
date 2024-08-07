package com.sts15.fargos.items.talismans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.items.TalismanItem;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import top.theillusivec4.curios.api.CuriosApi;

public class Vindicator_Talisman extends TalismanItem implements Vindicator_Talisman_Provider {
	
	private static final Map<UUID, ItemStack> lastHeldItems = new HashMap<>();
    private static final Map<UUID, Long> lastWeaponSwitchTimes = new HashMap<>();
    private static final Map<UUID, Boolean> canBoostAttack = new HashMap<>();
    private static final Map<UUID, Long> lastBoostTimes = new HashMap<>();
    private static final long BOOST_COOLDOWN = 600;

    public Vindicator_Talisman() {
        super(new Item.Properties().rarity(Rarity.UNCOMMON));
    }
    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        pTooltipComponents.add(Component.translatable("item.fargostalismans.tooltip.vindicator_talisman")
        		.setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    }
    
    private static void trackWeaponSwitch(Player player, UUID playerId) {
        ItemStack currentItem = player.getMainHandItem();
        long currentTime = player.level().getGameTime();

        // Check if the player switched weapons
        if (!lastHeldItems.containsKey(playerId) || !ItemStack.matches(currentItem, lastHeldItems.get(playerId))) {
            lastHeldItems.put(playerId, currentItem.copy());
            lastWeaponSwitchTimes.put(playerId, currentTime);

            if (!lastBoostTimes.containsKey(playerId) || (currentTime - lastBoostTimes.get(playerId) > BOOST_COOLDOWN)) {
                canBoostAttack.put(playerId, true);
                //System.out.println("Weapon switched for player " + player.getName().getString() + ": boost enabled");
            }
        }
    }

    private static void applyVindicatorBoostIfEligible(Player attacker, LivingDamageEvent.Pre event) {
        UUID attackerId = attacker.getUUID();
        long currentTime = attacker.level().getGameTime();

        if (canBoostAttack.getOrDefault(attackerId, false) && isEligibleForVindicatorBoost(attackerId, currentTime)) {
            float boostedDamage = event.getOriginalDamage() * 1.5F;
            event.setNewDamage(boostedDamage);
            canBoostAttack.put(attackerId, false);
            lastBoostTimes.put(attackerId, currentTime);
            //System.out.println("Applied vindicator boost to player " + attacker.getName().getString() + ": new damage = " + boostedDamage);
        }
    }

    private static boolean isEligibleForVindicatorBoost(UUID playerId, long currentTime) {
        Long lastSwitchTime = lastWeaponSwitchTimes.get(playerId);
        boolean isEligible = lastSwitchTime != null && (currentTime - lastSwitchTime <= 60);
        return isEligible;
    }

    @EventBusSubscriber(modid = Fargos.MODID)
    public static class Events {

        @SubscribeEvent
        public static void onPlayerTick(PlayerTickEvent.Pre event) {
            if (!(event.getEntity() instanceof Player player))
                return;

            UUID playerUUID = player.getUUID();

            if (CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof Vindicator_Talisman_Provider, player).isPresent()) {
                trackWeaponSwitch(player, playerUUID);
            }
        }

        @SubscribeEvent
        public static void onLivingHurt(LivingDamageEvent.Pre event) {
            if (!(event.getSource().getEntity() instanceof Player player))
                return;

            if (CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof Vindicator_Talisman_Provider, player).isPresent()) {
                applyVindicatorBoostIfEligible(player, event);
            }
        }
    }
}