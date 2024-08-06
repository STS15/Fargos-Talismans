package com.sts15.fargos.mixins;

import com.sts15.fargos.items.talismans.Skeleton_Talisman;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.api.CuriosApi;

@Mixin(AbstractArrow.class)
public abstract class AbstractArrowMixin {

    @Inject(method = "shoot", at = @At("HEAD"), cancellable = true)
    private void onShoot(double x, double y, double z, float velocity, float inaccuracy, CallbackInfo ci) {
        AbstractArrow arrow = (AbstractArrow) (Object) this;
        LivingEntity owner = (LivingEntity) arrow.getOwner();
        if (owner instanceof Player player) {
            boolean hasSkeletonTalisman = CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof Skeleton_Talisman, player).isPresent();
            if (hasSkeletonTalisman) {
                // Double the velocity and set inaccuracy to 0
                Vec3 direction = new Vec3(x, y, z).normalize();
                double newVelocity = velocity * 2;
                arrow.setDeltaMovement(direction.scale(newVelocity));
                ci.cancel(); // Cancel the original shoot method call
            }
        }
    }
}
