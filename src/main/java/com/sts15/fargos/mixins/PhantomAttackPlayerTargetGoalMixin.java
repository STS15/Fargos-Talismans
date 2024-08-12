package com.sts15.fargos.mixins;

import com.sts15.fargos.Config;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.player.Player;

import java.util.List;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.sts15.fargos.items.talismans.Spectral_Talisman_Provider;

import top.theillusivec4.curios.api.CuriosApi;

@Mixin(targets = "net.minecraft.world.entity.monster.Phantom$PhantomAttackPlayerTargetGoal")
public abstract class PhantomAttackPlayerTargetGoalMixin {

    @Shadow
    @Final
    Phantom this$0;

    @Inject(method = "canUse", at = @At("HEAD"), cancellable = true)
    private void onCanUse(CallbackInfoReturnable<Boolean> cir) {
        List<Player> players = this$0.level().getNearbyPlayers(TargetingConditions.forCombat().range(64.0), this$0, this$0.getBoundingBox().inflate(16.0, 64.0, 16.0));
        for (Player player : players) {
            //System.out.println("Phantom checking player: " + player.getName().getString() + " (ID: " + player.getUUID() + ")");
            if (hasPhantomTalisman(player)) {
                //System.out.println("Phantom skipped player with talisman: " + player.getName().getString() + " (ID: " + player.getUUID() + ")");
                cir.setReturnValue(false);
                return;
            }
        }
    }

    @Inject(method = "canContinueToUse", at = @At("HEAD"), cancellable = true)
    private void onCanContinueToUse(CallbackInfoReturnable<Boolean> cir) {
        LivingEntity target = this$0.getTarget();

        if (target instanceof Player player) {
            //System.out.println("Phantom targeting player: " + player.getName().getString() + " (ID: " + player.getUUID() + ")");
            if (hasPhantomTalisman(player)) {
                //System.out.println("Phantom stopped targeting player with talisman: " + player.getName().getString() + " (ID: " + player.getUUID() + ")");
                cir.setReturnValue(false);
            }
        }
    }

    @SuppressWarnings({ "deprecation", "removal" })
    private boolean hasPhantomTalisman(Player player) {
        if (!Config.isTalismanEnabledOnClientAndServer("spectral_talisman")) return false;
        return CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof Spectral_Talisman_Provider, player).isPresent();
    }
}
