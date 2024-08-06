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
import net.minecraft.resources.ResourceLocation;
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
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import top.theillusivec4.curios.api.CuriosApi;

public class Zombie_Talisman extends TalismanItem {
	
	private static final long INCREASE_ATTACK_SPEED_THRESHOLD = 100;
    private static final double ATTACK_SPEED_BOOST = 5.5;
    private static final int MAX_ATTACKS = 3;
    private static final long COOLDOWN_PERIOD = 200;

    private static final Map<UUID, Long> lastAttackTimes = new HashMap<>();
    private static final Map<UUID, Integer> attackCounts = new HashMap<>();
    private static final Map<UUID, AttributeModifier> attackSpeedModifiers = new HashMap<>();
    private static final Map<UUID, Long> cooldownEndTimes = new HashMap<>(); // Cooldown end times

    private static final ResourceLocation ZOMBIE_ATTACK_SPEED_BOOST_ID = ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "zombie_attack_speed_boost");
    
    public Zombie_Talisman() {
        super(new Item.Properties().rarity(Rarity.UNCOMMON));
    }
    
    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        pTooltipComponents.add(Component.translatable("item.fargostalismans.tooltip.zombie_talisman")
        		.setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    }
    
    private static void resetAttackSpeed(Player player, UUID playerId) {
        AttributeInstance attackSpeedAttribute = player.getAttribute(Attributes.ATTACK_SPEED);
        if (attackSpeedAttribute != null && attackSpeedModifiers.containsKey(playerId)) {
            attackSpeedAttribute.removeModifier(attackSpeedModifiers.get(playerId));
            attackSpeedModifiers.remove(playerId);
            attackCounts.remove(playerId);
            long cooldownEndTime = player.level().getGameTime() + COOLDOWN_PERIOD;
            cooldownEndTimes.put(playerId, cooldownEndTime);
            //System.out.println("Reset attack speed for player " + player.getName().getString() + " (ID: " + playerId + ")");
        }
    }

    private static void increaseAttackSpeed(Player player, UUID playerId) {
        if (cooldownEndTimes.getOrDefault(playerId, 0L) > player.level().getGameTime()) {
            return;
        }

        AttributeInstance attackSpeedAttribute = player.getAttribute(Attributes.ATTACK_SPEED);
        if (attackSpeedAttribute != null && !attackSpeedModifiers.containsKey(playerId)) {
            AttributeModifier modifier = new AttributeModifier(ZOMBIE_ATTACK_SPEED_BOOST_ID, ATTACK_SPEED_BOOST, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
            attackSpeedAttribute.addTransientModifier(modifier);
            attackSpeedModifiers.put(playerId, modifier);
            attackCounts.put(playerId, 0);
            //System.out.println("Increased attack speed for player " + player.getName().getString() + " (ID: " + playerId + ")");
        }
    }
    
    @EventBusSubscriber(modid = Fargos.MODID)
    public static class Events {
    	
        @SuppressWarnings({ "removal", "deprecation" })
		@SubscribeEvent
        public static void onPlayerTick(PlayerTickEvent.Pre event) {
        	if (!(event.getEntity() instanceof Player player))
                return;
        	
        	UUID playerUUID = player.getUUID();
    	    long currentTime = player.level().getGameTime();

    	    if (CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof Zombie_Talisman, player).isPresent()) {
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
            if (!(event.getSource().getEntity() instanceof Player player))
                return;

            UUID playerUUID = player.getUUID();

            if (CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof Zombie_Talisman, player).isPresent()) {
                int attackCount = attackCounts.getOrDefault(playerUUID, 0);
                attackCount++;
                attackCounts.put(playerUUID, attackCount);

                if (attackCount >= MAX_ATTACKS) {
                    resetAttackSpeed(player, playerUUID);
                } else {
                    lastAttackTimes.put(playerUUID, player.level().getGameTime());
                }
            }
        }
        
    }
}
