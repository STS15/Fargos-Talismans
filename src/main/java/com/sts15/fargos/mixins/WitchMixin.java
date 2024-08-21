package com.sts15.fargos.mixins;

import com.sts15.fargos.utils.TalismanUtil;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.sts15.fargos.items.providers.Witch_Talisman_Provider;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import top.theillusivec4.curios.api.CuriosApi;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;

@Mixin(Witch.class)
public abstract class WitchMixin extends Entity {

    public WitchMixin(EntityType<? extends Entity> type, Level worldIn) {
        super(type, worldIn);
    }

    @Inject(method = "performRangedAttack", at = @At("HEAD"), cancellable = true)
    public void injectPerformRangedAttack(LivingEntity target, float distanceFactor, CallbackInfo ci) {
        if (target instanceof Player && hasWitchEnchantment((Player) target)) {
            throwBeneficialPotion((Witch) (Object) this, (Player) target);
            ci.cancel();
        }
    }

    private void throwBeneficialPotion(Witch witch, Player target) {
        Holder<Potion> regeneration = BuiltInRegistries.POTION.getHolderOrThrow(Potions.REGENERATION.getKey());
        Holder<Potion> healing = BuiltInRegistries.POTION.getHolderOrThrow(Potions.HEALING.getKey());
        
        // Choose either regeneration or healing based on the player's health
        Holder<Potion> selectedPotion = target.getHealth() <= 4.0F ? healing : regeneration;

        ItemStack potionStack = PotionContents.createItemStack(Items.SPLASH_POTION, selectedPotion);

        ThrownPotion thrownPotion = new ThrownPotion(witch.level(), witch);
        thrownPotion.setItem(potionStack);

        Vec3 vec3 = target.getDeltaMovement();
        double d0 = target.getX() + vec3.x - witch.getX();
        double d1 = target.getEyeY() - 1.1F - witch.getY();
        double d2 = target.getZ() + vec3.z - witch.getZ();
        double d3 = Math.sqrt(d0 * d0 + d2 * d2);
        double speed = 0.75F;
        double inaccuracy = 8.0F;

        Vec3 direction = new Vec3(d0, d1 + d3 * 0.2, d2).normalize().scale(speed);

        direction = direction.add(
            witch.level().random.nextGaussian() * 0.0075D * inaccuracy,
            witch.level().random.nextGaussian() * 0.0075D * inaccuracy,
            witch.level().random.nextGaussian() * 0.0075D * inaccuracy
        );

        thrownPotion.setDeltaMovement(direction);

        if (!witch.isSilent()) {
            witch.level().playSound(null, witch.getX(), witch.getY(), witch.getZ(), SoundEvents.WITCH_THROW, SoundSource.HOSTILE, 1.0F, 0.8F + witch.level().random.nextFloat() * 0.4F);
        }

        witch.level().addFreshEntity(thrownPotion);
    }

    private static boolean hasWitchEnchantment(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            // Check if the talisman is enabled for the server player
            if (!TalismanUtil.isTalismanEnabled(serverPlayer, "witch_talisman")) {
                return false;
            }
            // Check if the player has the Librarian Talisman equipped
            return CuriosApi.getCuriosHelper().findEquippedCurio(itemStack -> itemStack.getItem() instanceof Witch_Talisman_Provider, serverPlayer).isPresent();
        }
        return false;
    }
}
