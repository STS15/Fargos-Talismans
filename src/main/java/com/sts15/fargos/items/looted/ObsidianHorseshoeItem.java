package com.sts15.fargos.items.looted;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.init.Config;
import com.sts15.fargos.items.TalismanItem;
import com.sts15.fargos.items.providers.Obsidian_Horseshoe_Provider;
import com.sts15.fargos.items.providers.Soul_of_Colossus_Provider;
import com.sts15.fargos.network.NetworkHandler;
import com.sts15.fargos.utils.TalismanUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Blocks;
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

public class ObsidianHorseshoeItem extends TalismanItem implements Obsidian_Horseshoe_Provider {

    private static final Logger LOGGER = LogManager.getLogger(Fargos.MODID);
    private static final String charmName = "obsidian_horseshoe";

    public ObsidianHorseshoeItem() {
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

        private static final int MAX_SHIELD_TICKS = 200;

        @SubscribeEvent
        public static void onPlayerTick(PlayerTickEvent.Post event) {
            if (!(event.getEntity() instanceof ServerPlayer player)) return;
            var tag = player.getPersistentData();

            // 1) Detect equip/unequip
            boolean nowEquipped = CuriosApi.getCuriosHelper()
                    .findEquippedCurio(s -> s.getItem() instanceof Obsidian_Horseshoe_Provider, player)
                    .isPresent();
            boolean wasEquipped = tag.getBoolean("HorseshoeEquipped");

            boolean hasSoulEquipped = CuriosApi.getCuriosHelper()
                    .findEquippedCurio(s -> s.getItem() instanceof Soul_of_Colossus_Provider, player)
                    .isPresent();

            if (hasSoulEquipped) {
                if (tag.contains("FireShieldDuration")) {
                    tag.remove("FireShieldDuration");
                    tag.remove("WasInLava");
                    NetworkHandler.sendSyncFireShieldToClient(player, 0);
                }
                tag.putBoolean("HorseshoeEquipped", false);
                return;
            }

            if (!nowEquipped || !checkConfigEnabledStatus()) {
                if (tag.contains("FireShieldDuration")) {
                    tag.remove("FireShieldDuration");
                    tag.remove("WasInLava");
                    NetworkHandler.sendSyncFireShieldToClient(player, 0);
                }
                tag.putBoolean("HorseshoeEquipped", false);
                return;
            }

            if (!TalismanUtil.isTalismanEnabled(player, charmName))
                return;

            if (nowEquipped && !wasEquipped) {
                tag.putInt("FireShieldDuration", 0);
                tag.putBoolean("WasInLava", player.isInLava() || player.isOnFire());
                NetworkHandler.sendSyncFireShieldToClient(player, 0);
            }
            tag.putBoolean("HorseshoeEquipped", nowEquipped);
            int oldTime = tag.contains("FireShieldDuration")
                    ? tag.getInt("FireShieldDuration")
                    : -1;
            boolean nowHot  = player.isInLava() || player.isOnFire();
            boolean wasHot  = tag.getBoolean("WasInLava");

            boolean startNormally = nowHot && !wasHot && !tag.contains("FireShieldDuration");
            if (tag.contains("FireShieldDuration") || startNormally) {
                int ticks = tag.contains("FireShieldDuration")
                        ? tag.getInt("FireShieldDuration")
                        : MAX_SHIELD_TICKS;

                if (nowHot) {
                    if (ticks > 0) {
                        ticks = ticks - 1;
                    }
                } else {
                    ticks = Math.min(ticks + 1, MAX_SHIELD_TICKS);
                }

                if (!nowHot && ticks >= MAX_SHIELD_TICKS) {
                    tag.remove("FireShieldDuration");
                    tag.remove("WasInLava");
                    ticks = -1; // signal “no tag”
                } else {
                    tag.putInt("FireShieldDuration", ticks);
                    tag.putBoolean("WasInLava", nowHot);
                }
            }

            int newTime = tag.contains("FireShieldDuration")
                    ? tag.getInt("FireShieldDuration")
                    : 0;
            if (newTime != (oldTime < 0 ? 0 : oldTime)) {
                NetworkHandler.sendSyncFireShieldToClient(player, newTime);
            }
        }

        @SuppressWarnings("removal")
        @SubscribeEvent
        public static void onPlayerHurt(LivingIncomingDamageEvent event) {
            if (!(event.getEntity() instanceof ServerPlayer player)) return;

            boolean hasEquipped = CuriosApi.getCuriosHelper()
                    .findEquippedCurio(s -> s.getItem() instanceof Obsidian_Horseshoe_Provider, player)
                    .isPresent();
            if (!hasEquipped || !checkConfigEnabledStatus()) return;
            if (!TalismanUtil.isTalismanEnabled(player, charmName))
                return;

            if (event.getSource().is(DamageTypes.HOT_FLOOR)) {
                BlockPos below = player.blockPosition().below();
                if (player.level().getBlockState(below).is(Blocks.MAGMA_BLOCK)) {
                    event.setAmount(0F);
                    event.setCanceled(true);
                    return;
                }
            }

            boolean hasSoulEquipped = CuriosApi.getCuriosHelper()
                    .findEquippedCurio(s -> s.getItem() instanceof Soul_of_Colossus_Provider, player)
                    .isPresent();
            boolean isFire = event.getSource().is(DamageTypes.IN_FIRE)
                    || event.getSource().is(DamageTypes.ON_FIRE)
                    || event.getSource().is(DamageTypes.LAVA);
            if (isFire) {
                if (hasSoulEquipped) {
                    event.setAmount(0F);
                    event.setCanceled(true);
                }
                else if (player.getPersistentData().getInt("FireShieldDuration") > 0) {
                    event.setAmount(0F);
                    event.setCanceled(true);
                }
            }

        }

    }
}
