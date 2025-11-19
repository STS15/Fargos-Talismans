package com.sts15.fargos.items.souls;

import java.lang.reflect.Field;
import java.util.List;

import com.illusivesoulworks.caelus.api.CaelusApi;
import com.sts15.fargos.Fargos;
import com.sts15.fargos.effect.EffectsInit;
import com.sts15.fargos.init.Config;
import com.sts15.fargos.init.SoundRegistry;
import com.sts15.fargos.items.crafted.BasicElytraItem;
import com.sts15.fargos.items.providers.Soul_of_Flight_Mastery_Provider;
import com.sts15.fargos.utils.TalismanUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;

public class Soul_of_Flight_Mastery extends BasicElytraItem implements Soul_of_Flight_Mastery_Provider {

    public static final String talismanName = "soul_of_flight_mastery";
    private static final ResourceLocation FLIGHT_ENABLE_ID = ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "soul_of_flight_mastery_enabled");
    private static final ResourceLocation ELYTRA_FLIGHT_ID = ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "soul_of_flight_mastery_elytra");

    public Soul_of_Flight_Mastery() {
        super(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1), ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "textures/entity/elytra/soul_of_flight_mastery.png"));
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

    public static void enableFlight(Player player) {
        // Creative Flight
        AttributeInstance creativeFlight = player.getAttribute(NeoForgeMod.CREATIVE_FLIGHT);
        if (creativeFlight != null && creativeFlight.getBaseValue() == 0 && !player.isCreative() && !player.isSpectator()) {
            creativeFlight.setBaseValue(1);
            player.getPersistentData().putBoolean(FLIGHT_ENABLE_ID.toString(), true);
        }

        // Elytra Flight
        AttributeInstance elytraAttr = player.getAttribute(CaelusApi.getInstance().getFallFlyingAttribute());
        if (elytraAttr != null && elytraAttr.getModifier(ELYTRA_FLIGHT_ID) == null) {
            elytraAttr.addTransientModifier(new AttributeModifier(
                    ELYTRA_FLIGHT_ID, 1.0, AttributeModifier.Operation.ADD_VALUE
            ));
        }

        player.onUpdateAbilities();
    }


    public static void disableFlight(Player player) {
        AttributeInstance creativeFlight = player.getAttribute(NeoForgeMod.CREATIVE_FLIGHT);
        if (creativeFlight != null && creativeFlight.getBaseValue() == 1 && !player.isCreative() && !player.isSpectator()) {
            creativeFlight.setBaseValue(0);
            player.getAbilities().flying = false;
            player.getAbilities().mayfly = false;
        }

        AttributeInstance elytraAttr = player.getAttribute(CaelusApi.getInstance().getFallFlyingAttribute());
        if (elytraAttr != null && elytraAttr.getModifier(ELYTRA_FLIGHT_ID) != null) {
            elytraAttr.removeModifier(ELYTRA_FLIGHT_ID);
        }

        player.onUpdateAbilities();
        player.getPersistentData().remove(FLIGHT_ENABLE_ID.toString());
    }

    private static boolean isFlightEnabledByMod(Player player) {
        return player.getPersistentData().getBoolean(FLIGHT_ENABLE_ID.toString());
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    protected String getFirstTimeMessageKey() {
        return null;
    }

    protected Component getFirstTimeMessageComponent() {
        return null;
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        Level level = slotContext.entity().level();
        if (!level.isClientSide) {
            Player player = (Player) slotContext.entity();
            level.playSound(null, player.blockPosition(), SoundRegistry.EQUIP_TALISMAN.get(), SoundSource.PLAYERS, 1.0F, 1.0F);

            String key = getFirstTimeMessageKey();
            if (key != null && !player.getPersistentData().getBoolean(key)) {
                Component msg = getFirstTimeMessageComponent();
                if (msg != null) {
                    player.sendSystemMessage(msg);
                }
                player.getPersistentData().putBoolean(key, true);
            }
        }

        // Don't forget: enable flight too
        if (TalismanUtil.isTalismanEnabled((Player) slotContext.entity(), talismanName)) {
            enableFlight((Player) slotContext.entity());
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        Level level = slotContext.entity().level();
        if (!level.isClientSide) {
            Player player = (Player) slotContext.entity();
            level.playSound(null, player.blockPosition(), SoundRegistry.UNEQUIP_TALISMAN.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
        }

        disableFlight((Player) slotContext.entity());
    }


    @EventBusSubscriber(modid = Fargos.MODID)
    public static class Events {

        private static int tickCounter = 0;
        @SuppressWarnings({ "removal", "deprecation" })
        @SubscribeEvent
        public static void onPlayerTick(PlayerTickEvent.Pre event) {
            if (!(event.getEntity() instanceof ServerPlayer player)) return;
            if (++tickCounter < 10) return;
            tickCounter = 0;

            boolean configOn  = TalismanUtil.isTalismanEnabled(player, talismanName);
            boolean hasSoul   = player.hasEffect(EffectsInit.SOUL_OF_FLIGHT_MASTERY_EFFECT) || CuriosApi.getCuriosHelper()
                    .findEquippedCurio(stack -> stack.getItem() instanceof Soul_of_Flight_Mastery_Provider, player)
                    .isPresent();
            boolean flightOn  = isFlightEnabledByMod(player);
            boolean shouldFly = configOn && hasSoul;
            if (shouldFly && !flightOn) {
                enableFlight(player);
            }
            else if (!shouldFly && flightOn) {
                disableFlight(player);
            }
        }

    }
}
