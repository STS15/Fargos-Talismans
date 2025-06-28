package com.sts15.fargos.mixins;

import com.sts15.fargos.items.providers.Creeper_Talisman_Provider;
import com.sts15.fargos.items.talismans.Creeper_Talisman;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.api.CuriosApi;

@Mixin(NearestAttackableTargetGoal.class)
public abstract class NearestAttackableTargetGoalMixin<T extends LivingEntity> {

    @Unique
    Mob fargos_Talismans_main$mob = ((TargetGoalAccessor) this).fargostalismans$getMob();

    @Shadow protected LivingEntity target;

    @Shadow public abstract void setTarget(LivingEntity target);

    @Inject(method = "findTarget", at = @At("TAIL"))
    private void preventTargetingPlayerWithTalisman(CallbackInfo ci) {
        if (!(fargos_Talismans_main$mob instanceof Creeper)) return;
        if (!(target instanceof Player player)) return;
        boolean hasTalisman = CuriosApi.getCuriosHelper()
                .findEquippedCurio(stack -> stack.getItem() instanceof Creeper_Talisman_Provider, player)
                .isPresent();

        if (hasTalisman && Creeper_Talisman.checkConfigEnabledStatus()) {
            this.setTarget(null);
        }
    }
}
