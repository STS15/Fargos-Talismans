package com.sts15.fargos.mixins;

import com.sts15.fargos.utils.MiningXPTracker;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerGameMode.class)
public class ServerPlayerGameModeMixin {

    @Final
    @Shadow
    protected ServerPlayer player;

    @Inject(method = "destroyBlock", at = @At("HEAD"))
    private void onDestroyBlockStart(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        MiningXPTracker.setMiningPlayer(this.player);
    }

    @Inject(method = "destroyBlock", at = @At("RETURN"))
    private void onDestroyBlockEnd(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        MiningXPTracker.clearMiningPlayer();
    }
}
