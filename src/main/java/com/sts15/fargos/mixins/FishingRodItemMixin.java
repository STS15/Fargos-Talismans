package com.sts15.fargos.mixins;

import com.sts15.fargos.init.Config;
import com.sts15.fargos.items.providers.Soul_of_Trawler_Provider;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.CuriosApi;

@Mixin(FishingRodItem.class)
public abstract class FishingRodItemMixin {

    @Unique
    private static final String EXTRA_TAG = "fargos_trawler_extra";

    @Inject(
            method = "use",
            at = @At("HEAD"),
            cancellable = true
    )
    private void fargostalismans$trawlerMultiCast(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        if (!(level instanceof ServerLevel serverLevel)) return;
        if (!Config.SOUL_OF_TRAWLER_TOGGLE.get()) return;
        if (!hasSoulOfTrawler(player)) return;

        ItemStack rodStack = player.getItemInHand(hand);

        // If bobbers are active, reel them all in
        var activeHooks = level.getEntitiesOfClass(FishingHook.class, player.getBoundingBox().inflate(64))
                .stream()
                .filter(hook -> hook.getOwner() == player && hook.getTags().contains(EXTRA_TAG))
                .toList();

        if (!activeHooks.isEmpty()) {
            for (FishingHook hook : activeHooks) {
                int damage = hook.retrieve(rodStack);  // simulate retrieval
                rodStack.hurtAndBreak(damage, player, LivingEntity.getSlotForHand(hand));
                hook.discard();
            }

            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.FISHING_BOBBER_RETRIEVE, SoundSource.NEUTRAL,
                    1.0F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
            player.gameEvent(GameEvent.ITEM_INTERACT_FINISH);

            cir.setReturnValue(InteractionResultHolder.success(rodStack));
            return;
        }

        // Prevent recast if any player-owned hooks are alive (just in case)
        boolean hasAnyHooks = level.getEntitiesOfClass(FishingHook.class, player.getBoundingBox().inflate(64))
                .stream()
                .anyMatch(hook -> hook.getOwner() == player);
        if (hasAnyHooks) {
            cir.setReturnValue(InteractionResultHolder.sidedSuccess(rodStack, level.isClientSide()));
            return;
        }

        // Cast new bobbers
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.FISHING_BOBBER_THROW, SoundSource.NEUTRAL,
                0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));

        int luck = EnchantmentHelper.getFishingLuckBonus(serverLevel, rodStack, player);
        int lure = (int)(EnchantmentHelper.getFishingTimeReduction(serverLevel, rodStack, player) * 20.0F);
        int amount = Math.max(1, Config.SOUL_OF_TRAWLER_EXTRA_LINES.get());

        for (int i = 0; i < amount; i++) {
            FishingHook hook = new FishingHook(player, level, luck, lure);
            hook.addTag(EXTRA_TAG);
            level.addFreshEntity(hook);
        }

        player.awardStat(Stats.ITEM_USED.get(rodStack.getItem()));
        player.gameEvent(GameEvent.ITEM_INTERACT_START);
        cir.setReturnValue(InteractionResultHolder.sidedSuccess(rodStack, level.isClientSide()));
    }

    @Unique
    private boolean hasSoulOfTrawler(Player player) {
        return CuriosApi.getCuriosHelper()
                .findEquippedCurio(stack -> stack.getItem() instanceof Soul_of_Trawler_Provider, player)
                .isPresent();
    }
}
