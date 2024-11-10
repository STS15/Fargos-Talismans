package com.sts15.fargos.items.talismans;

import java.lang.reflect.Field;
import java.util.List;
import com.sts15.fargos.Fargos;
import com.sts15.fargos.effect.EffectsInit;
import com.sts15.fargos.init.Config;
import com.sts15.fargos.items.TalismanItem;
import com.sts15.fargos.items.providers.Iron_Golem_Talisman_Provider;
import com.sts15.fargos.utils.TalismanUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
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
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;

public class Iron_Golem_Talisman extends TalismanItem implements Iron_Golem_Talisman_Provider {

    private static final String talismanName = "iron_golem_talisman";
    private static final ResourceLocation HEALTH_BOOST_ID = ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "iron_golem_health_boost");
    private static final ResourceLocation HEALTH_DATA_KEY = ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "iron_golem_health");

    public Iron_Golem_Talisman() {
        super(new Item.Properties().rarity(Rarity.UNCOMMON));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("item.fargostalismans.tooltip." + talismanName,(int)(Config.IRON_GOLEM_TALISMAN_HEALTH_BOOST_MULTIPLIER.getAsDouble() * 100))
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

    public static void resetHealth(Player player) {
        AttributeInstance healthAttribute = player.getAttribute(Attributes.MAX_HEALTH);
        if (healthAttribute != null && healthAttribute.hasModifier(HEALTH_BOOST_ID)) {
            healthAttribute.removeModifier(HEALTH_BOOST_ID);
        }
    }

    public static void increaseHealth(Player player) {
        AttributeInstance healthAttribute = player.getAttribute(Attributes.MAX_HEALTH);
        if (healthAttribute != null && !healthAttribute.hasModifier(HEALTH_BOOST_ID)) {
            AttributeModifier modifier = new AttributeModifier(HEALTH_BOOST_ID, Config.IRON_GOLEM_TALISMAN_HEALTH_BOOST_MULTIPLIER.getAsDouble(), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
            healthAttribute.addTransientModifier(modifier);
        }
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (stack.getItem() == newStack.getItem())
            return;
        Player entity = (Player) slotContext.entity();
        increaseHealth(entity);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (stack.getItem() == newStack.getItem())
            return;
        Player entity = (Player) slotContext.entity();
        boolean hasIronGolemTalismanEffect = entity.hasEffect(EffectsInit.IRON_GOLEM_TALISMAN_EFFECT);
        if (!hasIronGolemTalismanEffect) {
            resetHealth(entity);
        }
    }

    @EventBusSubscriber(modid = Fargos.MODID)
    public static class Events {
        private static int tickCounter = 0;

        @SubscribeEvent
        public static void onPlayerTick(PlayerTickEvent.Pre event) {
            if (!(event.getEntity() instanceof ServerPlayer player))
                return;
            if (++tickCounter < 10) { return; } tickCounter = 0;

            boolean hasEquippedCurio = CuriosApi.getCuriosHelper().findEquippedCurio(equippedStack -> equippedStack.getItem() instanceof Iron_Golem_Talisman_Provider, player).isPresent();
            if (hasEquippedCurio) {
                if (TalismanUtil.isTalismanEnabled(player, talismanName)) {
                    increaseHealth(player);
                } else { // Toggle off is only for config toggle disable, not actual removal
                    resetHealth(player);
                }
            } else {
                resetHealth(player);
            }
        }

        @SubscribeEvent
        public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
            if (!(event.getEntity() instanceof ServerPlayer player)) return;
            player.getPersistentData().putFloat(HEALTH_DATA_KEY.toString(), player.getHealth());
        }

        @SubscribeEvent
        public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
            if (!(event.getEntity() instanceof ServerPlayer player)) return;
            if (player.getPersistentData().contains(HEALTH_DATA_KEY.toString())) {
                boolean hasEquippedCurio = CuriosApi.getCuriosHelper().findEquippedCurio(equippedStack -> equippedStack.getItem() instanceof Iron_Golem_Talisman_Provider, player).isPresent();
                boolean hasEffect = player.hasEffect(EffectsInit.IRON_GOLEM_TALISMAN_EFFECT);
                if (hasEffect || hasEquippedCurio) {
                    increaseHealth(player);
                    player.setHealth(player.getPersistentData().getFloat(HEALTH_DATA_KEY.toString()));
                }
                player.getPersistentData().remove(HEALTH_DATA_KEY.toString());
            }
        }
    }
}
