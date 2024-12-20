package com.sts15.fargos.items.talismans;

import java.lang.reflect.Field;
import java.util.List;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.effect.EffectsInit;
import com.sts15.fargos.init.Config;
import com.sts15.fargos.items.TalismanItem;

import com.sts15.fargos.utils.TalismanUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import top.theillusivec4.curios.api.CuriosApi;

public class Copper_Talisman extends TalismanItem {

    private static final String talismanName = "copper_talisman";

    public Copper_Talisman() {
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
    
    @EventBusSubscriber(modid = Fargos.MODID)
    public static class Events {

        @SubscribeEvent
        public static void onPlayerTick(PlayerTickEvent.Pre event) {
            if (event.getEntity() instanceof ServerPlayer player) {
                if (player.hasEffect(EffectsInit.COPPER_TALISMAN_EFFECT) || CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof Copper_Talisman, player).isPresent()) {
                    if (!TalismanUtil.isTalismanEnabled(player, talismanName))
                        return;
                    attractItems(player);
                }
            }
        }

        private static void attractItems(Player player) {
            Level level = player.level();
            AABB area = player.getBoundingBox().inflate(Config.COPPER_TALISMAN_AREA.getAsInt()); // 10-block radius
            AABB playerArea = player.getBoundingBox().inflate(Config.COPPER_TALISMAN_PLAYER_AREA.getAsInt()); // 15-block radius
            List<ItemEntity> items = level.getEntitiesOfClass(ItemEntity.class, area);

            List<ServerPlayer> nearbyPlayers = level.getEntitiesOfClass(ServerPlayer.class, playerArea).stream()
                    .filter(p -> CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof Copper_Talisman, p).isPresent())
                    .toList();

            for (ItemEntity item : items) {
                if (!item.hasPickUpDelay() && item.isAlive()) {
                    ServerPlayer closestPlayer = null;
                    double closestDistance = Double.MAX_VALUE;
                    for (ServerPlayer p : nearbyPlayers) {
                        double distance = p.distanceToSqr(item);
                        if (distance < closestDistance) {
                            closestDistance = distance;
                            closestPlayer = p;
                        }
                    }

                    if (closestPlayer != null && closestPlayer == player) {
                        double speed = Config.COPPER_TALISMAN_SPEED.getAsDouble();
                        double xDir = player.getX() - item.getX();
                        double yDir = player.getEyeY() - item.getY();
                        double zDir = player.getZ() - item.getZ();
                        double distance = Math.sqrt(xDir * xDir + yDir * yDir + zDir * zDir);

                        if (distance < Config.COPPER_TALISMAN_DISTANCE_TO_PICKUP.getAsDouble()) {
                            item.teleportTo(player.getX(), player.getY(), player.getZ());
                        } else {
                            item.setDeltaMovement(
                                    item.getDeltaMovement().add(xDir / distance * speed, yDir / distance * speed, zDir / distance * speed)
                            );
                        }
                    }
                }
            }
        }
    }
}
