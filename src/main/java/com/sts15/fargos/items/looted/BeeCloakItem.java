package com.sts15.fargos.items.looted;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.init.Config;
import com.sts15.fargos.items.TalismanItem;
import com.sts15.fargos.items.providers.Bee_Cloak_Provider;
import com.sts15.fargos.utils.TalismanUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.theillusivec4.curios.api.CuriosApi;
import java.lang.reflect.Field;
import java.util.List;

public class BeeCloakItem extends TalismanItem implements Bee_Cloak_Provider {

    private static final Logger LOGGER = LogManager.getLogger(Fargos.MODID);
    private static final String charmName = "bee_cloak";

    public BeeCloakItem() {
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
        public static void onLivingChangeTarget(LivingChangeTargetEvent event) {
            if (!(event.getEntity() instanceof Bee bee)) return;

            LivingEntity newTarget = event.getNewAboutToBeSetTarget();
            if (!(newTarget instanceof Player player)) return;

            boolean hasBeeCloak = CuriosApi.getCuriosHelper()
                    .findEquippedCurio(stack -> stack.getItem() instanceof Bee_Cloak_Provider, player)
                    .isPresent();

            if (hasBeeCloak && checkConfigEnabledStatus()) {
                if (!TalismanUtil.isTalismanEnabled(player, charmName))
                    return;
                event.setCanceled(true);
                bee.setTarget(null);
                bee.stopBeingAngry();
            }
        }

        @SubscribeEvent
        public static void onPlayerTick(PlayerTickEvent.Post event) {
            if (!(event.getEntity() instanceof ServerPlayer player)) return;

            boolean hasBeeCloak = CuriosApi.getCuriosHelper()
                    .findEquippedCurio(stack -> stack.getItem() instanceof Bee_Cloak_Provider, player)
                    .isPresent();
            if (!hasBeeCloak || !checkConfigEnabledStatus()) return;
            if (!TalismanUtil.isTalismanEnabled(player, charmName)) return;

            BlockPos playerPos = player.blockPosition();
            Level level = player.level();
            int radius = 3;
            boolean nearBeeNest = BlockPos.betweenClosedStream(
                    playerPos.offset(-radius, -radius, -radius),
                    playerPos.offset(radius, radius, radius)
            ).anyMatch(pos -> {
                var state = level.getBlockState(pos);
                return state.is(Blocks.BEE_NEST) || state.is(Blocks.BEEHIVE);
            });

            if (nearBeeNest) {
                if (level.getGameTime() % 40 == 0 && player.getFoodData().needsFood()) {
                    player.getFoodData().eat(1, 0.5F);
                    level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BEE_LOOP, player.getSoundSource(),0.3F, 0.8F);
                    for (int i = 0; i < 3; i++) {
                        double offsetX = (player.getRandom().nextDouble() - 0.5) * 0.5;
                        double offsetZ = (player.getRandom().nextDouble() - 0.5) * 0.5;
                        double posY = player.getY() + 1.0;

                        ((ServerLevel) level).sendParticles(
                                ParticleTypes.FALLING_HONEY,
                                player.getX() + offsetX,
                                posY,
                                player.getZ() + offsetZ,
                                1, 0.0, -0.1, 0.0, 0.02
                        );
                    }
                }
            }
        }



    }
}

