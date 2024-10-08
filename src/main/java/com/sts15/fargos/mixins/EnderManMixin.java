package com.sts15.fargos.mixins;

import com.sts15.fargos.effect.EffectsInit;
import com.sts15.fargos.utils.TalismanUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import top.theillusivec4.curios.api.CuriosApi;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.sts15.fargos.items.providers.Enderman_Talisman_Provider;

@Mixin(EnderMan.class)
public class EnderManMixin {
    @Inject(at = @At(value = "HEAD"), method = "isLookingAtMe", cancellable = true)
    protected void calmEndermans(Player player, CallbackInfoReturnable<Boolean> info) {
        if (player instanceof ServerPlayer serverPlayer) {
            if (player.hasEffect(EffectsInit.ENDERMAN_TALISMAN_EFFECT) || CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof Enderman_Talisman_Provider, serverPlayer).isPresent()) {
                if (!TalismanUtil.isTalismanEnabled(serverPlayer, "enderman_talisman")) {
                    return;
                }
                info.setReturnValue(false);
            }
        }
    }
}
