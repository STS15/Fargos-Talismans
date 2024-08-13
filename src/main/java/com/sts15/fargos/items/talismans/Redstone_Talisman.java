package com.sts15.fargos.items.talismans;

import java.util.List;
import java.util.Random;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.items.TalismanItem;

import com.sts15.fargos.utils.TalismanUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import top.theillusivec4.curios.api.CuriosApi;
import net.minecraft.world.entity.player.Player;

public class Redstone_Talisman extends TalismanItem implements Redstone_Talisman_Provider {

    private static final String talismanName = "redstone_talisman";

    private static long lastRepairTime = 0;
    private static long nextRepairInterval = 5000;
    private static final Random random = new Random();

    public Redstone_Talisman() {
        super(new Item.Properties().rarity(Rarity.UNCOMMON));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {

        tooltipComponents.add(Component.translatable("item.fargostalismans.tooltip."+talismanName)
                .setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    private static void repairArmorOverTime(Player player, long currentTime) {
        if (currentTime - lastRepairTime > nextRepairInterval) {
            player.getInventory().armor.forEach(Redstone_Talisman::repairSingleArmorItem);
            lastRepairTime = currentTime;
            nextRepairInterval = 5000 + random.nextInt(2500);
        }
    }

    private static void repairSingleArmorItem(ItemStack armorItem) {
        if (armorItem.isDamaged()) {
            armorItem.setDamageValue(armorItem.getDamageValue() - 1);
        }
    }

    @EventBusSubscriber(modid = Fargos.MODID)
    public static class Events {

        @SubscribeEvent
        public static void onPlayerTick(PlayerTickEvent.Pre event) {
            if (event.getEntity() instanceof ServerPlayer player) {
                if (CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof Redstone_Talisman_Provider, player).isPresent()) {
                    if (!TalismanUtil.isTalismanEnabled(player, talismanName)) {
                        return;
                    }
                    long currentTime = System.currentTimeMillis();
                    Redstone_Talisman.repairArmorOverTime(player, currentTime);
                }
            }
        }

    }
}
