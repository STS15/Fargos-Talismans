//package com.sts15.fargos.mixins;
//
//import com.sts15.fargos.utils.TalismanUtil;
//import net.minecraft.server.level.ServerPlayer;
//import net.minecraft.world.entity.projectile.FishingHook;
//import net.minecraft.core.BlockPos;
//import net.minecraft.world.entity.player.Player;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Shadow;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//import com.sts15.fargos.items.providers.Soul_of_Trawler_Provider;
//
//import top.theillusivec4.curios.api.CuriosApi;
//
//@Mixin(FishingHook.class)
//public abstract class FishingHookMixin {
//
//    @Shadow private int timeUntilHooked;
//    @Shadow private int timeUntilLured;
//
//    @Inject(method = "catchingFish", at = @At("HEAD"), cancellable = true)
//    private void onCatchingFish(BlockPos pPos, CallbackInfo ci) {
//        FishingHook hook = (FishingHook) (Object) this;
//        if (hook.getOwner() instanceof ServerPlayer serverPlayer) {
//            boolean hasSoulOfTrawler = CuriosApi.getCuriosHelper().findEquippedCurio(
//                    stack -> stack.getItem() instanceof Soul_of_Trawler_Provider, serverPlayer
//            ).isPresent();
//
//            if (hasSoulOfTrawler) {
//                if (!TalismanUtil.isTalismanEnabled(serverPlayer, "soul_of_trawler")) {
//                    return;
//                }
//                this.timeUntilHooked = 1;
//                this.timeUntilLured = 1;
//            }
//        }
//    }
//}
