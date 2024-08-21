package com.sts15.fargos.items.talismans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.items.TalismanItem;

import com.sts15.fargos.items.providers.Battle_Talisman_Provider;
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
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import top.theillusivec4.curios.api.CuriosApi;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Battle_Talisman extends TalismanItem implements Battle_Talisman_Provider {

    private static final String talismanName = "battle_talisman";
    
    private static final Map<UUID, Integer> invinciblePlayers = new HashMap<>();
    private static final Logger LOGGER = LogManager.getLogger();

    public Battle_Talisman() {
        super(new Item.Properties().rarity(Rarity.UNCOMMON));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {

        tooltipComponents.add(Component.translatable("item.fargostalismans.tooltip."+talismanName)
                .setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
    
    @EventBusSubscriber(modid = Fargos.MODID)
    public static class Events {
               
        @SuppressWarnings({ "removal", "deprecation" })
        @SubscribeEvent
        public static void onLivingHurt(LivingIncomingDamageEvent event) {
            if (!(event.getEntity() instanceof ServerPlayer player))
                return;

            if (CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof Battle_Talisman_Provider, player).isPresent()) {
                if (!TalismanUtil.isTalismanEnabled(player, talismanName))
                    return;
                UUID playerUUID = player.getUUID();
                Integer invincibilityTicks = invinciblePlayers.get(playerUUID);

                if (invincibilityTicks == null || invincibilityTicks <= 0) {
                    invinciblePlayers.put(playerUUID, 5);
                    //LOGGER.info("Player {} will be invincible for 5 ticks after taking initial damage.", player.getName().getString());
                } else {
                    //LOGGER.info("Player {} is currently invincible for {} more ticks.", player.getName().getString(), invincibilityTicks);
                    event.setCanceled(true);
                }
            }
        }

        @SubscribeEvent
        public static void onPlayerTick(PlayerTickEvent.Pre event) {
            Player player = event.getEntity();
            UUID playerUUID = player.getUUID();
            if (invinciblePlayers.containsKey(playerUUID)) {
                int ticks = invinciblePlayers.get(playerUUID);
                if (ticks > 0) {
                    invinciblePlayers.put(playerUUID, ticks - 1);
                    //LOGGER.info("Decreasing invincibility ticks for player {}: {} ticks remaining.", player.getName().getString(), ticks - 1);
                } else {
                    invinciblePlayers.remove(playerUUID);
                    //LOGGER.info("Player {} is no longer invincible.", player.getName().getString());
                }
            }
        }
    }
}
