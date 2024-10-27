package com.sts15.fargos.mixins;

import com.sts15.fargos.effect.EffectsInit;
import com.sts15.fargos.items.providers.Librarian_Talisman_Provider;
import com.sts15.fargos.utils.TalismanUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.api.CuriosApi;

@Mixin(Villager.class)
public abstract class VillagerMixin {

    @Inject(method = "rewardTradeXp", at = @At("HEAD"), cancellable = true)
    private void onRewardTradeXp(MerchantOffer offer, CallbackInfo ci) {
        Villager villager = (Villager) (Object) this;
        Level level = villager.level();
        if (!level.isClientSide()) {
            ServerPlayer serverPlayer = (ServerPlayer) villager.getTradingPlayer();
            if (serverPlayer != null) {
                int xp = 3 + villager.getRandom().nextInt(4);
                if (hasLibrarianTalisman(serverPlayer)) {
                    xp = Math.round(xp * 1.5f);
                }
                level.addFreshEntity(new ExperienceOrb(level, villager.getX(), villager.getY() + 0.5D, villager.getZ(), xp));
                ci.cancel();
            }
        }
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
