package com.sts15.fargos.items.looted;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.init.Config;
import com.sts15.fargos.items.TalismanItem;
import com.sts15.fargos.items.providers.Hero_Shield_Provider;
import com.sts15.fargos.utils.TalismanUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.theillusivec4.curios.api.CuriosApi;
import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.List;


public class HeroShieldItem extends TalismanItem implements Hero_Shield_Provider {

    private static final Logger LOGGER = LogManager.getLogger(Fargos.MODID);
    private static final String charmName = "hero_shield";
    private static final ResourceLocation HERO_SHIELD_KNOCKBACK_RES_ID = ResourceLocation.fromNamespaceAndPath(Fargos.MODID,"hero_shield_aggro");
    private static final AttributeModifier HERO_SHIELD_KB_RESIST_MODIFIER = new AttributeModifier(HERO_SHIELD_KNOCKBACK_RES_ID, 1.0, AttributeModifier.Operation.ADD_VALUE);

    public HeroShieldItem() {
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

        @SuppressWarnings("removal")
        @SubscribeEvent
        public static void onPlayerTick(PlayerTickEvent.Pre event) {
            if (!(event.getEntity() instanceof ServerPlayer player)) return;

            boolean hasShieldEquipped = CuriosApi.getCuriosHelper()
                    .findEquippedCurio(stack -> stack.getItem() instanceof Hero_Shield_Provider, player)
                    .isPresent();
            if (!TalismanUtil.isTalismanEnabled(player, charmName))
                return;

            if (hasShieldEquipped && HeroShieldItem.checkConfigEnabledStatus()) {
                addKnockbackResistance(player);
            } else {
                removeKnockbackResistance(player);
            }
        }

        private static void addKnockbackResistance(Player player) {
            AttributeInstance kbAttr = player.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
            if (kbAttr != null && !kbAttr.hasModifier(HERO_SHIELD_KB_RESIST_MODIFIER.id())) {
                kbAttr.addTransientModifier(HERO_SHIELD_KB_RESIST_MODIFIER);
            }
        }

        private static void removeKnockbackResistance(Player player) {
            AttributeInstance kbAttr = player.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
            if (kbAttr != null && kbAttr.hasModifier(HERO_SHIELD_KB_RESIST_MODIFIER.id())) {
                kbAttr.removeModifier(HERO_SHIELD_KB_RESIST_MODIFIER);
            }
        }

        @SubscribeEvent
        public static void onLivingChangeTarget(LivingChangeTargetEvent event) {
            if (!(event.getEntity() instanceof Mob mob)) return;
            if (!checkConfigEnabledStatus()) return;

            double radius = 16.0;

            if (mob.level() instanceof ServerLevel serverLevel) {
                List<ServerPlayer> shieldBearers = serverLevel.getEntitiesOfClass(
                        ServerPlayer.class,
                        new AABB(mob.blockPosition()).inflate(radius),
                        player -> CuriosApi.getCuriosHelper()
                                .findEquippedCurio(stack -> stack.getItem() instanceof Hero_Shield_Provider, player)
                                .isPresent()
                                &&
                                TalismanUtil.isTalismanEnabled(player, charmName)
                );

                if (!shieldBearers.isEmpty()) {
                    shieldBearers.stream()
                            .filter(mob::hasLineOfSight)
                            .min(Comparator.comparingDouble(p -> p.distanceToSqr(mob)))
                            .ifPresent(event::setNewAboutToBeSetTarget);
                }
            }
        }

    }
}
