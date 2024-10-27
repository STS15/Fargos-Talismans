package com.sts15.fargos.mixins;

import com.sts15.fargos.effect.EffectsInit;
import com.sts15.fargos.items.providers.Librarian_Talisman_Provider;
import com.sts15.fargos.utils.TalismanUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.List;

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class AbstractFurnaceBlockEntityMixin {

    /**
     * Redirects the call to ExperienceOrb.award in createExperience to apply experience multiplier.
     */
    @Redirect(
            method = "createExperience",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/ExperienceOrb;award(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/phys/Vec3;I)V"
            )
    )
    private static void redirectAwardExperience(ServerLevel level, Vec3 position, int amount, ServerLevel pLevel, Vec3 pPopVec, int pRecipeIndex, float pExperience) {
        // Attempt to find the player near the furnace
        ServerPlayer player = (ServerPlayer) level.getNearestPlayer(position.x(), position.y(), position.z(), 5.0, false);
        if (player != null && hasLibrarianTalisman(player)) {
            int modifiedAmount = Math.round(amount * 1.5f);
            ExperienceOrb.award(level, position, modifiedAmount);
        } else {
            ExperienceOrb.award(level, position, amount);
        }
    }

    private static boolean hasLibrarianTalisman(ServerPlayer player) {
        if (!TalismanUtil.isTalismanEnabled(player, "librarian_talisman")) {
            return false;
        }
        return player.hasEffect(EffectsInit.LIBRARIAN_TALISMAN_EFFECT)
                || CuriosApi.getCuriosHelper()
                .findEquippedCurio(
                        itemStack -> itemStack.getItem() instanceof Librarian_Talisman_Provider,
                        player
                )
                .isPresent();
    }
}
