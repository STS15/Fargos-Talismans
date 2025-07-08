package com.sts15.fargos.mixins;

import com.sts15.fargos.items.looted.BeeCloakItem;
import com.sts15.fargos.items.providers.Bee_Cloak_Provider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.api.CuriosApi;

@Mixin(Mob.class)
public abstract class MobMixin {

    @Inject(method = "setTarget", at = @At("HEAD"), cancellable = true)
    private void onSetTarget(LivingEntity target, CallbackInfo ci) {
        Mob mob = (Mob)(Object)this;

        if (mob instanceof Bee bee && target instanceof Player player) {
            boolean hasBeeCloak = CuriosApi.getCuriosHelper()
                    .findEquippedCurio(stack -> stack.getItem() instanceof Bee_Cloak_Provider, player)
                    .isPresent();

            if (hasBeeCloak && BeeCloakItem.checkConfigEnabledStatus()) {
                ci.cancel();
            }
        }
    }
}
