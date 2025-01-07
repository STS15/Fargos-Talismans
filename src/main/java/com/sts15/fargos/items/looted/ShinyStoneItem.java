package com.sts15.fargos.items.looted;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.init.Config;
import com.sts15.fargos.items.TalismanItem;
import com.sts15.fargos.items.providers.Shiny_Stone_Provider;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
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
import java.lang.reflect.Field;
import java.util.*;

@EventBusSubscriber(modid = Fargos.MODID)
public class ShinyStoneItem extends TalismanItem implements Shiny_Stone_Provider {

    private static final Logger LOGGER = LogManager.getLogger(Fargos.MODID);
    private static final String charmName = "shiny_stone";

    private static final long STILL_THRESHOLD_TICKS = 600L;
    private static final int REGEN_DURATION_TICKS = 40;
    private static final int REGEN_AMPLIFIER = 0;
    private static final Map<UUID, BlockPos> lastPositions = new HashMap<>();
    private static final Map<UUID, Long> lastMovementTimes = new HashMap<>();
    private static final Set<UUID> shinyStoneActivePlayers = new HashSet<>();

    public ShinyStoneItem() {
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
        } catch (NoSuchFieldException | IllegalAccessException e) {
            LOGGER.error("Error checking config status for " + charmName, e);
        }
        return isEnabled;
    }

    @SuppressWarnings("removal")
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Pre event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }
        UUID playerId = player.getUUID();
        long currentTick = player.level().getGameTime();

        boolean hasShinyStoneEquipped = CuriosApi.getCuriosHelper()
                .findEquippedCurio(stack -> stack.getItem() instanceof Shiny_Stone_Provider, player)
                .isPresent();

        if (!hasShinyStoneEquipped || !checkConfigEnabledStatus()) {
            removeShinyStoneEffect(player);
            lastPositions.remove(playerId);
            lastMovementTimes.remove(playerId);
            return;
        }

        BlockPos currentPos = player.blockPosition();
        BlockPos lastPos = lastPositions.getOrDefault(playerId, BlockPos.ZERO);
        long lastMoveTick = lastMovementTimes.getOrDefault(playerId, currentTick);

        if (lastPos == BlockPos.ZERO) {
            lastPositions.put(playerId, currentPos);
            lastMovementTimes.put(playerId, currentTick);
            return;
        }

        if (!currentPos.equals(lastPos)) {
            lastPositions.put(playerId, currentPos);
            lastMovementTimes.put(playerId, currentTick);
            removeShinyStoneEffect(player);
            return;
        }

        long ticksStill = currentTick - lastMoveTick;
        if (ticksStill >= STILL_THRESHOLD_TICKS) {
            applyShinyStoneEffect(player);
        } else {
            removeShinyStoneEffect(player);
        }
    }

    private static void applyShinyStoneEffect(Player player) {
        MobEffectInstance currentEffect = player.getEffect(MobEffects.REGENERATION);
        boolean needsRefresh = (currentEffect == null
                || currentEffect.getAmplifier() < REGEN_AMPLIFIER
                || currentEffect.getDuration() < 10);

        if (needsRefresh) {
            player.addEffect(new MobEffectInstance(
                    MobEffects.REGENERATION,
                    REGEN_DURATION_TICKS,
                    REGEN_AMPLIFIER,
                    false,
                    false
            ));
            shinyStoneActivePlayers.add(player.getUUID());
        }
    }

    private static void removeShinyStoneEffect(Player player) {
        if (shinyStoneActivePlayers.contains(player.getUUID())) {
            MobEffectInstance currentEffect = player.getEffect(MobEffects.REGENERATION);
            if (currentEffect != null) {
                player.removeEffect(MobEffects.REGENERATION);
            }
            shinyStoneActivePlayers.remove(player.getUUID());
        }
    }
}
