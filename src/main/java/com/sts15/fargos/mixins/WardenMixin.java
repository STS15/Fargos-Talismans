package com.sts15.fargos.mixins;

import com.sts15.fargos.items.providers.Master_Ninja_Gear_Provider;
import com.sts15.fargos.utils.TalismanUtil;
import com.sts15.fargos.utils.WardenAttackTracker;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.CuriosApi;

@Mixin(Warden.class)
public abstract class WardenMixin {

	@Inject(
			method = "canTargetEntity",
			at = @At("HEAD"),
			cancellable = true
	)
	private void fargos$preventTargetingUnlessProvoked(Entity potentialTarget, CallbackInfoReturnable<Boolean> cir) {
		if (!(potentialTarget instanceof Player player)) return;

		Warden warden = (Warden)(Object)this;

		boolean hasAttacked = WardenAttackTracker.hasAttacked(warden, player);
		boolean hasNinjaGear = CuriosApi.getCuriosHelper().findEquippedCurio(
				stack -> stack.getItem() instanceof Master_Ninja_Gear_Provider, player
		).isPresent();

		boolean enabled = TalismanUtil.isTalismanEnabled(player, "master_ninja_gear");

		if (hasNinjaGear && enabled && !hasAttacked) {
			cir.setReturnValue(false);
		}
	}
}
