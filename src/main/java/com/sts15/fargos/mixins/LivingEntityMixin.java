package com.sts15.fargos.mixins;

import com.sts15.fargos.effect.EffectsInit;
import com.sts15.fargos.items.looted.StarVeilItem;
import com.sts15.fargos.items.providers.Librarian_Talisman_Provider;
import com.sts15.fargos.items.providers.Star_Veil_Provider;
import com.sts15.fargos.utils.TalismanUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.api.CuriosApi;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Redirect(method = "dropExperience", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ExperienceOrb;award(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/phys/Vec3;I)V"))
    private void onDropExperience(ServerLevel level, Vec3 position, int amount) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity instanceof Player) {
            ExperienceOrb.award(level, position, amount);
            return;
        }
        if (entity.getLastAttacker() instanceof ServerPlayer serverPlayer && hasLibrarianTalisman(serverPlayer)) {
            int modifiedAmount = Math.round(amount * 1.5f);
            ExperienceOrb.award(level, position, modifiedAmount);
        } else {
            ExperienceOrb.award(level, position, amount);
        }
    }

    @Inject(method = "handleDamageEvent", at = @At("TAIL"))
    private void extendInvulnerability(DamageSource damageSource, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;

        if (entity instanceof Player player) {
            boolean hasStarVeilEquipped = CuriosApi.getCuriosHelper()
                    .findEquippedCurio(stack -> stack.getItem() instanceof Star_Veil_Provider, player)
                    .isPresent();

            if (hasStarVeilEquipped && StarVeilItem.checkConfigEnabledStatus() && TalismanUtil.isTalismanEnabled(player, "star_veil")) {
                int currentIFrames = player.invulnerableTime;
                player.invulnerableTime = currentIFrames + 10;
            }

        }
    }

    private boolean hasLibrarianTalisman(ServerPlayer player) {
        if (!TalismanUtil.isTalismanEnabled(player, "librarian_talisman")) {
            return false;
        }
        return player.hasEffect(EffectsInit.LIBRARIAN_TALISMAN_EFFECT)
                || CuriosApi.getCuriosHelper()
                .findEquippedCurio(itemStack -> itemStack.getItem() instanceof Librarian_Talisman_Provider, player)
                .isPresent();
    }
}
