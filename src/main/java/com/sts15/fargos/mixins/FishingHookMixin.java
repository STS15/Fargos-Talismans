package com.sts15.fargos.mixins;

import com.sts15.fargos.init.Config;
import com.sts15.fargos.items.providers.Soul_of_Trawler_Provider;
import com.sts15.fargos.items.souls.Soul_of_Trawler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.level.Level;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.api.CuriosApi;

@Mixin(FishingHook.class)
public abstract class FishingHookMixin {

    @Shadow private boolean biting;
    @Shadow private int timeUntilLured;
    @Shadow private int timeUntilHooked;

    @Unique
    private boolean fargos$autoReeledThisBite = false;

    @Inject(method = "tick", at = @At("HEAD"))
    private void fargostalismans$tickReduceBiteTime(CallbackInfo ci) {
        if (!Soul_of_Trawler.checkConfigEnabledStatus()) return;
        this.timeUntilLured = Math.max(1, this.timeUntilLured - 1);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void fargostalismans$reduceBiteAndAutoReel(CallbackInfo ci) {
        FishingHook self = (FishingHook) (Object) this;
        Player owner = self.getPlayerOwner();
        if (!(owner instanceof ServerPlayer player)) return;
        if (!hasSoulOfTrawler(player)) return;
        if (!Config.SOUL_OF_TRAWLER_TOGGLE.get()) return;

        // Auto-reel when fish is biting and not already auto-reeled this bite
        if (this.biting && Config.SOUL_OF_TRAWLER_AUTO_REEL.get()) {
            if (!fargos$autoReeledThisBite) {
                // Find the rod in main or offhand
                ItemStack rodStack = player.getMainHandItem();
                InteractionHand hand = InteractionHand.MAIN_HAND;
                if (!(rodStack.getItem() instanceof FishingRodItem)) {
                    rodStack = player.getOffhandItem();
                    hand = InteractionHand.OFF_HAND;
                }
                if (rodStack.getItem() instanceof FishingRodItem) {
                    // Perform retrieval equivalent to right-click
                    int damage = self.retrieve(rodStack);
                    ItemStack original = rodStack.copy();
                    rodStack.hurtAndBreak(damage, player, LivingEntity.getSlotForHand(hand));
                    if (rodStack.isEmpty()) {
                        net.neoforged.neoforge.event.EventHooks.onPlayerDestroyItem(player, original, hand);
                    }
                    // Play retrieve sound / event
                    Level level = player.level();
                    level.playSound(
                            null,
                            player.getX(),
                            player.getY(),
                            player.getZ(),
                            SoundEvents.FISHING_BOBBER_RETRIEVE,
                            SoundSource.NEUTRAL,
                            1.0F,
                            0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F)
                    );
                    player.gameEvent(GameEvent.ITEM_INTERACT_FINISH);
                    // Mark so we don't double-reel this bite
                    fargos$autoReeledThisBite = true;
                }
            }
        } else if (!this.biting) {
            // reset when bite window closes
            fargos$autoReeledThisBite = false;
        }
    }

    @Unique
    private boolean hasSoulOfTrawler(Player player) {
        return CuriosApi.getCuriosHelper()
                .findEquippedCurio(stack -> stack.getItem() instanceof Soul_of_Trawler_Provider, player)
                .isPresent();
    }
}
