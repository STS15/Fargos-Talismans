package com.sts15.fargos.items.talismans;

import java.lang.reflect.Field;
import java.util.List;
import com.sts15.fargos.Fargos;
import com.sts15.fargos.effect.EffectsInit;
import com.sts15.fargos.init.Config;
import com.sts15.fargos.items.TalismanItem;
import com.sts15.fargos.items.providers.Ghast_Talisman_Provider;
import com.sts15.fargos.utils.TalismanUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import top.theillusivec4.curios.api.CuriosApi;

public class Ghast_Talisman extends TalismanItem implements Ghast_Talisman_Provider {
    // Button said this should be more ghast themed, weakness has nothing to do with ghast and he's right

    private static final String talismanName = "ghast_talisman";

    public Ghast_Talisman() {
        super(new Item.Properties().rarity(Rarity.UNCOMMON));
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
    
    @EventBusSubscriber(modid = Fargos.MODID)
    public static class Events {

        private static final double DETECTION_RADIUS = 7.0;

        @SubscribeEvent
        public static void onPlayerTick(PlayerTickEvent.Pre event) {
            if (!(event.getEntity() instanceof Player player)) return;

            Level level = player.level();
            if (level.isClientSide() || !(level instanceof ServerLevel)) return;

            List<LargeFireball> nearbyFireballs = level.getEntitiesOfClass(LargeFireball.class, player.getBoundingBox().inflate(DETECTION_RADIUS));

            for (LargeFireball fireball : nearbyFireballs) {
                Entity ghastSource = findNearestGhast(level, fireball);

                if (ghastSource instanceof Ghast) {
                    redirectFireballTowardGhast(fireball, ghastSource, player);
                }
            }
        }

        private static Entity findNearestGhast(Level level, Entity fireball) {
            double searchRadius = 64.0; // Adjust as needed
            AABB searchBox = fireball.getBoundingBox().inflate(searchRadius);
            List<Ghast> nearbyGhasts = level.getEntitiesOfClass(Ghast.class, searchBox);

            Entity closestGhast = null;
            double closestDistance = Double.MAX_VALUE;

            for (Ghast ghast : nearbyGhasts) {
                double distance = ghast.distanceTo(fireball);
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestGhast = ghast;
                }
            }
            return closestGhast;
        }

        private static final String REDIRECTED_TAG = "RedirectedByPlayer";

        public static void redirectFireballTowardGhast(LargeFireball fireball, Entity targetGhast, Player player) {
            CompoundTag tag = fireball.getPersistentData();
            if (tag.getBoolean(REDIRECTED_TAG)) {
                return;
            }
            tag.putBoolean(REDIRECTED_TAG, true);
            Vec3 fireballPos = fireball.position();
            Vec3 ghastPos = targetGhast.position();
            Vec3 direction = ghastPos.subtract(fireballPos).normalize().scale(0.1);
            fireball.setDeltaMovement(direction.x, direction.y, direction.z);
            fireball.setOwner(player);
            fireball.level().playSound(null, player.blockPosition(), SoundEvents.SHIELD_BLOCK, SoundSource.PLAYERS, 1.0F, 1.0F);
        }
    }
}