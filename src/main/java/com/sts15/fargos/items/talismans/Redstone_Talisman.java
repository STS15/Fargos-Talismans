package com.sts15.fargos.items.talismans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import com.sts15.fargos.Fargos;
import com.sts15.fargos.effect.EffectsInit;
import com.sts15.fargos.init.Config;
import com.sts15.fargos.items.TalismanItem;
import com.sts15.fargos.items.providers.Redstone_Talisman_Provider;
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
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import top.theillusivec4.curios.api.CuriosApi;

public class Redstone_Talisman extends TalismanItem implements Redstone_Talisman_Provider {

    private static final String talismanName = "redstone_talisman";
    private static final Map<UUID, PlayerRepairData> repairDataMap = new HashMap<>();
    private static final Random random = new Random();

    public Redstone_Talisman() {
        super(new Item.Properties().rarity(Rarity.UNCOMMON));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("item.fargostalismans.tooltip." + talismanName)
                .setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));

        boolean isEnabled = TalismanUtil.checkConfigEnabledStatus(talismanName);
        if (!isEnabled) {
            tooltipComponents.add(Component.literal("(Disabled in Server Config)")
                    .setStyle(Style.EMPTY.withColor(ChatFormatting.RED)));
        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    private static void repairArmorOverTime(Player player, long currentTime) {
        UUID playerId = player.getUUID();
        PlayerRepairData repairData = repairDataMap.get(playerId);

        if (repairData == null) {
            long baseInterval = Config.REDSTONE_TALISMAN_ARMOR_REPAIR_DURATION.get();
            long nextRepairInterval = baseInterval + random.nextInt(2500);
            repairData = new PlayerRepairData(0L, nextRepairInterval);
            repairDataMap.put(playerId, repairData);
        }

        if (currentTime - repairData.lastRepairTime > repairData.nextRepairInterval) {
            player.getInventory().armor.forEach(Redstone_Talisman::repairSingleArmorItem);
            repairData.lastRepairTime = currentTime;
            long baseInterval = Config.REDSTONE_TALISMAN_ARMOR_REPAIR_DURATION.get();
            repairData.nextRepairInterval = baseInterval + random.nextInt(2500);
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
                if (player.hasEffect(EffectsInit.REDSTONE_TALISMAN_EFFECT) ||
                        CuriosApi.getCuriosHelper().findEquippedCurio(
                                stack -> stack.getItem() instanceof Redstone_Talisman_Provider, player).isPresent()) {

                    if (!TalismanUtil.isTalismanEnabled(player, talismanName)) {
                        return;
                    }
                    long currentTime = System.currentTimeMillis();
                    repairArmorOverTime(player, currentTime);
                }
            }
        }
    }
    private static class PlayerRepairData {
        long lastRepairTime;
        long nextRepairInterval;

        PlayerRepairData(long lastRepairTime, long nextRepairInterval) {
            this.lastRepairTime = lastRepairTime;
            this.nextRepairInterval = nextRepairInterval;
        }
    }
}
