package com.sts15.fargos.effect.talismans;

import com.sts15.eventslib.effect.MobEffectEndEffect;
import com.sts15.fargos.Fargos;
import com.sts15.fargos.effect.EffectsInit;
import com.sts15.fargos.items.souls.Soul_of_Flight_Mastery;
import com.sts15.fargos.utils.TalismanUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public class SoulOfFlightMasteryEffect extends MobEffectEndEffect {
    public SoulOfFlightMasteryEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public void onEffectAdded(LivingEntity entity, int amplifier) {
        super.onEffectAdded(entity, amplifier);
        if (entity instanceof ServerPlayer) {
            if (TalismanUtil.isTalismanEnabled((ServerPlayer) entity, Soul_of_Flight_Mastery.talismanName)) {
                Soul_of_Flight_Mastery.enableFlight((Player) entity);
            }
        }
    }

    @Override
    public void onEffectRemoved(LivingEntity entity, int amplifier) {
        super.onEffectRemoved(entity, amplifier);
        if (entity instanceof Player) {
            Soul_of_Flight_Mastery.disableFlight((Player) entity);
        }
    }

    @EventBusSubscriber(modid = Fargos.MODID)
    public static class Events {

        @SubscribeEvent
        public static void onPlayerTick(PlayerTickEvent.Pre event) {
            if (event.getEntity() instanceof ServerPlayer player) {
                if (player.tickCount % 10 == 0) {
                    if (player.hasEffect(EffectsInit.SOUL_OF_FLIGHT_MASTERY_EFFECT)) {
                        if (TalismanUtil.isTalismanEnabled(player, "soul_of_colossus")) {
                            Soul_of_Flight_Mastery.enableFlight(player);
                        } else {
                            Soul_of_Flight_Mastery.disableFlight(player);
                        }
                    }
                }
            }
        }
    }
}