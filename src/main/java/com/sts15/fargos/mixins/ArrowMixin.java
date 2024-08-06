package com.sts15.fargos.mixins;

import com.sts15.fargos.items.talismans.Skeleton_Talisman;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.api.CuriosApi;

@Mixin(Arrow.class)
public abstract class ArrowMixin extends AbstractArrowMixin {
	
	@Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        Arrow arrow = (Arrow) (Object) this;
        LivingEntity owner = (LivingEntity) arrow.getOwner();
        if (owner instanceof Player player) {
            boolean hasSkeletonTalisman = CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof Skeleton_Talisman, player).isPresent();
            if (hasSkeletonTalisman) {
                arrow.setNoGravity(true); // This may help stabilize the arrow's flight for higher speeds
            }
        }
    }

    @Inject(method = "makeParticle", at = @At("HEAD"), cancellable = true)
    private void onMakeParticle(int pParticleAmount, CallbackInfo ci) {
        Arrow arrow = (Arrow) (Object) this;
        LivingEntity owner = (LivingEntity) arrow.getOwner();
        if (owner instanceof Player player) {
            boolean hasSkeletonTalisman = CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof Skeleton_Talisman, player).isPresent();
            if (hasSkeletonTalisman) {
                ci.cancel(); // Cancel the particle effect if the player is wearing the Skeleton Talisman
            }
        }
    }
}
