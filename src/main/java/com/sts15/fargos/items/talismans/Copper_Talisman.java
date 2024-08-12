package com.sts15.fargos.items.talismans;

import java.util.List;

import com.sts15.fargos.Config;
import com.sts15.fargos.Fargos;
import com.sts15.fargos.items.TalismanItem;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
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
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import top.theillusivec4.curios.api.CuriosApi;

public class Copper_Talisman extends TalismanItem {

    private static final String talismanName = "copper_talisman";

    public Copper_Talisman() {
        super(new Item.Properties().rarity(Rarity.UNCOMMON));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {

        if (!Config.isTalismanEnabledServer(talismanName)) {
            tooltipComponents.add(Component.translatable("item.fargostalismans.tooltip.disabled_by_server")
                    .setStyle(Style.EMPTY.withColor(ChatFormatting.RED)));
        } else if (!Config.isTalismanEnabledClient(talismanName)) {
            tooltipComponents.add(Component.translatable("item.fargostalismans.tooltip.disabled_by_client")
                    .setStyle(Style.EMPTY.withColor(ChatFormatting.RED)));
        } else {
            tooltipComponents.add(Component.translatable("item.fargostalismans.tooltip."+talismanName)
                    .setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
        }

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
    
    @EventBusSubscriber(modid = Fargos.MODID)
    public static class Events {

        @SuppressWarnings({ "deprecation", "removal" })
		@SubscribeEvent
        public static void onPlayerTick(PlayerTickEvent.Pre event) {
            Player player = event.getEntity();

            if (CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof Copper_Talisman, player).isPresent()) {
                if (!Config.isTalismanEnabledOnClientAndServer(talismanName))
                    return;
                attractItems(player);
            }
        }

        private static void attractItems(Player player) {
            Level level = player.level();
            AABB area = player.getBoundingBox().inflate(5); // 5-block radius
            List<ItemEntity> items = level.getEntitiesOfClass(ItemEntity.class, area);

            for (ItemEntity item : items) {
                if (!item.hasPickUpDelay() && item.isAlive()) {
                    double speed = 1.0; // Speed at which items are attracted
                    double xDir = player.getX() - item.getX();
                    double yDir = player.getY() - item.getY();
                    double zDir = player.getZ() - item.getZ();
                    double distance = Math.sqrt(xDir * xDir + yDir * yDir + zDir * zDir);

                    if (distance < 1) {
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
