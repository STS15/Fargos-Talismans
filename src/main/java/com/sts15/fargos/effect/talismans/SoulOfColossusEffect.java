package com.sts15.fargos.effect.talismans;

import com.sts15.eventslib.effect.MobEffectEndEffect;
import com.sts15.fargos.Fargos;
import com.sts15.fargos.effect.EffectsInit;
import com.sts15.fargos.items.souls.Soul_of_Colossus;
import com.sts15.fargos.utils.TalismanUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public class SoulOfColossusEffect extends MobEffectEndEffect {
    public SoulOfColossusEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public void onEffectAdded(LivingEntity entity, int amplifier) {
        super.onEffectAdded(entity, amplifier);
        if (entity instanceof ServerPlayer) {
            if (TalismanUtil.isTalismanEnabled((ServerPlayer) entity, Soul_of_Colossus.talismanName)) {
                Soul_of_Colossus.increaseHealth((Player) entity);
            }
        }
    }

    @Override
    public void onEffectRemoved(LivingEntity entity, int amplifier) {
        super.onEffectRemoved(entity, amplifier);
        if (entity instanceof Player) {
            Soul_of_Colossus.resetHealth((Player) entity);
        }
    }

    @EventBusSubscriber(modid = Fargos.MODID)
    public static class Events {

        @SubscribeEvent
        public static void onPlayerTick(PlayerTickEvent.Pre event) {
            if (event.getEntity() instanceof ServerPlayer player) {
                if (player.tickCount % 10 == 0) {
                    boolean hasSoulOfColossusEffect = player.hasEffect(EffectsInit.SOUL_OF_COLOSSUS_EFFECT);
                    if (hasSoulOfColossusEffect) {
                        if (TalismanUtil.isTalismanEnabled(player, "soul_of_colossus")) {
                            Soul_of_Colossus.increaseHealth(player);
                        } else {
                            Soul_of_Colossus.resetHealth(player);
                        }
                    }
                }
            }
        }
    }
}