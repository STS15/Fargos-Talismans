package com.sts15.fargos.mixins;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.monster.piglin.PiglinBruteAi;
import net.minecraft.world.entity.player.Player;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.sts15.fargos.items.talismans.Gold_Talisman;

import top.theillusivec4.curios.api.CuriosApi;

@Mixin(PiglinBruteAi.class)
public class PiglinBruteAiMixin {
    
	@Inject(method = "findNearestValidAttackTarget", at = @At("HEAD"), cancellable = true)
	private static void onFindNearestValidAttackTarget(AbstractPiglin piglin, CallbackInfoReturnable<Optional<? extends LivingEntity>> cir) {
	    Brain<?> brain = piglin.getBrain();
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
        return CuriosApi.getCuriosHelper().findEquippedCurio(itemStack -> itemStack.getItem() instanceof Gold_Talisman, player).isPresent();
    }
	
}