package com.sts15.fargos.mixins;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.effect.EffectsInit;
import com.sts15.fargos.items.providers.Librarian_Talisman_Provider;
import com.sts15.fargos.utils.TalismanUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.api.CuriosApi;

@Mixin(Villager.class)
public abstract class VillagerMixin {

    @Shadow
    private Player lastTradedPlayer;

    //ide may not find the target but it still valid
    @ModifyArg(method = "rewardTradeXp", at = @At(value = "NEW", target = "Lnet/minecraft/world/entity/ExperienceOrb;(Lnet/minecraft/world/level/Level;DDDI)V"), index = 4)
    private int ModifyRewardTradeXp(int xp) {
        if (lastTradedPlayer instanceof ServerPlayer player && !lastTradedPlayer.level().isClientSide()) {
            if (hasLibrarianTalisman(player)) {
                xp = Math.round(xp * 1.5f);
            }
        }
        return xp;
    }

    private boolean hasLibrarianTalisman(ServerPlayer player) {
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
