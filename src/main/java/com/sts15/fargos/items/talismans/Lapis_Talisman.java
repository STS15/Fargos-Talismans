package com.sts15.fargos.items.talismans;

import java.util.List;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.items.TalismanItem;

import com.sts15.fargos.utils.TalismanUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ExperienceOrb;
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

public class Lapis_Talisman extends TalismanItem {

    private static final String talismanName = "lapis_talisman";

    public Lapis_Talisman() {
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

        @SuppressWarnings({ "deprecation", "removal" })
        @SubscribeEvent
        public static void onPlayerTick(PlayerTickEvent.Pre event) {
            if (event.getEntity() instanceof ServerPlayer player) {
                if (CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof Lapis_Talisman, player).isPresent()) {
                    if (!TalismanUtil.isTalismanEnabled(player, talismanName))
                        return;
                    attractXPOrbs(player);
                }
            }
        }

        private static void attractXPOrbs(Player player) {
            Level level = player.level();
            AABB area = player.getBoundingBox().inflate(7); // 5-block radius
            List<ExperienceOrb> xpOrbs = level.getEntitiesOfClass(ExperienceOrb.class, area);

            for (ExperienceOrb xpOrb : xpOrbs) {
                if (xpOrb.isAlive()) {
                    double speed = 1.0; // Speed at which XP orbs are attracted
                    double xDir = player.getX() - xpOrb.getX();
                    double yDir = player.getY() - xpOrb.getY();
                    double zDir = player.getZ() - xpOrb.getZ();
                    double distance = Math.sqrt(xDir * xDir + yDir * yDir + zDir * zDir);

                    if (distance < 1) {
                        xpOrb.teleportTo(player.getX(), player.getY(), player.getZ());
                        xpOrb.playerTouch(player); // Automatically collect the XP when it's close enough
                    } else {
                        xpOrb.setDeltaMovement(
                                xpOrb.getDeltaMovement().add(xDir / distance * speed, yDir / distance * speed, zDir / distance * speed)
                        );
                    }
                }
            }
        }
    }
}