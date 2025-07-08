package com.sts15.fargos.items.scavenged;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.init.Config;
import com.sts15.fargos.items.TalismanItem;
import com.sts15.fargos.items.providers.Aeolus_Boots_Provider;
import com.sts15.fargos.items.providers.Blessed_Apple_Provider;
import com.sts15.fargos.utils.TalismanUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.lang.reflect.Field;
import java.util.List;

public class BlessedAppleItem extends TalismanItem implements ICurioItem, Blessed_Apple_Provider {

    private static final Logger LOGGER = LogManager.getLogger(Fargos.MODID);
    public static final String charmName = "blessed_apple";

    private static final ResourceLocation STEP_ASSIST_MODIFIER_ID =
            ResourceLocation.fromNamespaceAndPath("fargos", "blessed_apple_step_assist");

    private static final AttributeModifier STEP_ASSIST_MODIFIER =
            new AttributeModifier(STEP_ASSIST_MODIFIER_ID, 0.4, AttributeModifier.Operation.ADD_VALUE); // 0.6 base + 0.4 = 1.0


    public BlessedAppleItem() {
        super(new Properties().rarity(Rarity.RARE));
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
        try {
            String fieldName = charmName.toUpperCase() + "_TOGGLE";
            Field toggleField = Config.class.getField(fieldName);
            return ((ModConfigSpec.BooleanValue) toggleField.get(null)).get();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            LOGGER.error("Error checking config status for " + charmName);
            return true;
        }
    }

    private static void applyStepAssist(Player player) {
        AttributeInstance step = player.getAttribute(Attributes.STEP_HEIGHT);
        if (step != null && step.getModifier(STEP_ASSIST_MODIFIER_ID) == null) {
            step.addTransientModifier(STEP_ASSIST_MODIFIER);
        }
    }

    private static void removeStepAssist(Player player) {
        AttributeInstance step = player.getAttribute(Attributes.STEP_HEIGHT);
        if (step != null && step.getModifier(STEP_ASSIST_MODIFIER_ID) != null) {
            step.removeModifier(STEP_ASSIST_MODIFIER);
        }
    }


    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        if (!player.level().isClientSide) {
            removeStepAssist(player);
        }
    }

    @EventBusSubscriber(modid = Fargos.MODID)
    public static class Events {

        @SubscribeEvent
        public static void onPlayerTick(PlayerTickEvent.Pre event) {
            if (!(event.getEntity() instanceof ServerPlayer player)) return;

            boolean equipped = CuriosApi.getCuriosHelper().findEquippedCurio(
                    stack -> stack.getItem() instanceof Blessed_Apple_Provider, player).isPresent();
            boolean enabled = TalismanUtil.isTalismanEnabled(player, charmName);

            if (!equipped || !enabled) {
                removeStepAssist(player);
                return;
            }

            AttributeInstance step = player.getAttribute(Attributes.STEP_HEIGHT);
            boolean hasModifier = step != null && step.getModifier(STEP_ASSIST_MODIFIER_ID) != null;

            if (player.isSprinting()) {
                if (!hasModifier) applyStepAssist(player);
            } else {
                if (hasModifier) removeStepAssist(player);
            }
        }

    }
}
