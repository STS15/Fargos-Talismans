package com.sts15.fargos.mixins;

import com.sts15.fargos.Config;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.sts15.fargos.items.talismans.Gold_Talisman_Provider;

import top.theillusivec4.curios.api.CuriosApi;

@Mixin(PiglinAi.class)
public class PiglinAiMixin {
    
	@Inject(method = "findNearestValidAttackTarget", at = @At("HEAD"), cancellable = true)
	private static void onFindNearestValidAttackTarget(Piglin piglin, CallbackInfoReturnable<Optional<? extends LivingEntity>> cir) {
	    Brain<Piglin> brain = piglin.getBrain();
	    Optional<Player> optionalPlayer = brain.getMemory(MemoryModuleType.NEAREST_VISIBLE_PLAYER);
	    if (optionalPlayer.isPresent()) {
	        Player player = optionalPlayer.get();
	        if (hasGoldEnchantment(player)) {
	            cir.setReturnValue(Optional.empty());
	        }
	    }
	}
	
	@SuppressWarnings({ "deprecation", "removal" })
    private static boolean hasGoldEnchantment(Player player) {
		if (!Config.isTalismanEnabledOnClientAndServer("gold_talisman")) return false;
        return CuriosApi.getCuriosHelper().findEquippedCurio(itemStack -> itemStack.getItem() instanceof Gold_Talisman_Provider, player).isPresent();
    }
	
}