package com.sts15.fargos.items.souls;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.items.TalismanItem;
import com.sts15.fargos.utils.TalismanUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
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
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import top.theillusivec4.curios.api.CuriosApi;
import java.util.List;

public class Soul_of_Colossus extends TalismanItem implements Soul_of_Colossus_Provider {

    private static final String talismanName = "soul_of_colossus";
    private static final double HEALTH_MULTIPLIER = 4.0;
    private static final ResourceLocation HEALTH_BOOST_ID = ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "soul_of_colossus_health_boost");

    public Soul_of_Colossus() {
        super(new Item.Properties().rarity(Rarity.EPIC));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("item.fargostalismans.tooltip." + talismanName)
                .setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    private static void resetHealth(Player player) {
        AttributeInstance healthAttribute = player.getAttribute(Attributes.MAX_HEALTH);
        if (healthAttribute != null && healthAttribute.hasModifier(HEALTH_BOOST_ID)) {
            healthAttribute.removeModifier(HEALTH_BOOST_ID);
        }
    }

    private static void increaseHealth(Player player) {
        AttributeInstance healthAttribute = player.getAttribute(Attributes.MAX_HEALTH);
        if (healthAttribute != null && !healthAttribute.hasModifier(HEALTH_BOOST_ID)) {
            AttributeModifier modifier = new AttributeModifier(HEALTH_BOOST_ID, HEALTH_MULTIPLIER, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
            healthAttribute.addTransientModifier(modifier);
        }
    }

    private static void negateNegativeEffects(Player player) {
        List<MobEffectInstance> negativeEffects = player.getActiveEffects().stream()
                .filter(effectInstance -> !effectInstance.getEffect().value().isBeneficial())
                .toList();

        for (MobEffectInstance effect : negativeEffects) {
            player.removeEffect(effect.getEffect());
        }
    }

    @EventBusSubscriber(modid = Fargos.MODID)
    public static class Events {

        @SuppressWarnings({ "removal", "deprecation" })
        @SubscribeEvent
        public static void onPlayerTick(PlayerTickEvent.Pre event) {
            if (!(event.getEntity() instanceof ServerPlayer player))
                return;

            if (CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof Soul_of_Colossus_Provider, player).isPresent()) {
                if (!TalismanUtil.isTalismanEnabled(player, talismanName))
                    return;

                increaseHealth(player);
                negateNegativeEffects(player);
            } else {
                resetHealth(player);
            }
        }
    }
}
