package com.sts15.fargos.mixins;

import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import top.theillusivec4.curios.api.CuriosApi;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.sts15.fargos.items.talismans.Enderman_Talisman;

@Mixin(EnderMan.class)
public class EnderManMixin {
    @SuppressWarnings({ "deprecation", "removal" })
	@Inject(at = @At(value = "HEAD"), method = "isLookingAtMe", cancellable = true)
    protected void calmEndermans(Player player, CallbackInfoReturnable<Boolean> info) {
        if (CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof Enderman_Talisman, player).isPresent())
        	info.setReturnValue(false);
    }
}