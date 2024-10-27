package com.sts15.fargos.mixins;

import com.sts15.fargos.effect.EffectsInit;
import com.sts15.fargos.items.providers.Librarian_Talisman_Provider;
import com.sts15.fargos.utils.MiningXPTracker;
import com.sts15.fargos.utils.TalismanUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.api.CuriosApi;

@Mixin(Block.class)
public abstract class BlockMixin {

    /**
     * Injects at the head of the popExperience method to apply a 1.5x experience multiplier
     * if the player breaking the block has the Librarian Talisman.
     */
    @Inject(method = "popExperience", at = @At("HEAD"), cancellable = true)
    private void injectPopExperience(ServerLevel pLevel, BlockPos pPos, int pAmount, CallbackInfo ci) {
        if (pLevel.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) && !pLevel.restoringBlockSnapshots) {
            ServerPlayer player = (ServerPlayer) MiningXPTracker.CURRENT_MINING_PLAYER.get();
            if (player != null && hasLibrarianTalisman(player)) {
                int modifiedAmount = Math.round(pAmount * 1.5f);
                ExperienceOrb.award(pLevel, Vec3.atCenterOf(pPos), modifiedAmount);
                ci.cancel();
            }
        }
    }

    private boolean hasLibrarianTalisman(ServerPlayer player) {
        if (!TalismanUtil.isTalismanEnabled(player, "librarian_talisman")) {
            return false;
        }
        return player.hasEffect(EffectsInit.LIBRARIAN_TALISMAN_EFFECT)
                ||
               CuriosApi.getCuriosHelper().findEquippedCurio(itemStack -> itemStack.getItem() instanceof Librarian_Talisman_Provider, player).isPresent();
    }
}
