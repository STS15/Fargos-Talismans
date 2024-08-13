package com.sts15.fargos.mixins;

import java.util.List;

import com.sts15.fargos.utils.TalismanUtil;
import net.minecraft.stats.ServerRecipeBook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.sts15.fargos.items.talismans.Enchanting_Talisman_Provider;
import com.sts15.fargos.items.talismans.Lapis_Talisman_Provider;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import top.theillusivec4.curios.api.CuriosApi;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentInstance;

@Mixin(EnchantmentMenu.class)
public abstract class EnchantmentTableMixin {
	
	@Shadow private ContainerLevelAccess access;
    @Shadow private Container enchantSlots;
    @Shadow private int[] costs;
    @Shadow private int[] enchantClue;
    @Shadow private int[] levelClue;
    @Shadow private DataSlot enchantmentSeed;
    
    @Shadow public abstract List<EnchantmentInstance> getEnchantmentList(RegistryAccess pRegistryAccess, ItemStack pStack, int pSlot, int pCost);
    @Shadow public abstract void slotsChanged(Container pInventory);
	
    private Player currentEnchantmentMenuPlayer;

    @Inject(method = "stillValid", at = @At("HEAD"))
    private void onStillValid(Player player, CallbackInfoReturnable<Boolean> ci) {
        this.currentEnchantmentMenuPlayer = player;
    }

    @Inject(method = "slotsChanged", at = @At("RETURN"), cancellable = true)
    private void modifyEnchantmentCosts(Container container, CallbackInfo ci) {
        if (this.currentEnchantmentMenuPlayer != null && (hasEnchantingEnchantment(this.currentEnchantmentMenuPlayer) )) {
            for (int i = 0; i < this.costs.length; i++) {
                this.costs[i] = Math.max(1, this.costs[i] - (this.costs[i] / 3));
            }
        }
    }
    
    @Inject(method = "clickMenuButton", at = @At("HEAD"), cancellable = true)
    public void clickMenuButton(Player pPlayer, int pId, CallbackInfoReturnable<Boolean> cir) {
        if (pId >= 0 && pId < this.costs.length) {
            ItemStack itemstack = this.enchantSlots.getItem(0);
            ItemStack itemstack1 = this.enchantSlots.getItem(1);
            int i = pId + 1;

            boolean hasLapisTalisman = isWearingLapisTalisman(this.currentEnchantmentMenuPlayer);

            if ((itemstack1.isEmpty() || itemstack1.getCount() < i) && !pPlayer.hasInfiniteMaterials() && !hasLapisTalisman) {
                cir.setReturnValue(false);
            } else if (this.costs[pId] <= 0
                || itemstack.isEmpty()
                || (pPlayer.experienceLevel < i || pPlayer.experienceLevel < this.costs[pId]) && !pPlayer.getAbilities().instabuild) {
                cir.setReturnValue(false);
            } else {
                this.access.execute((p_347276_, p_347277_) -> {
                    ItemStack itemstack2 = itemstack;
                    List<EnchantmentInstance> list = this.getEnchantmentList(p_347276_.registryAccess(), itemstack, pId, this.costs[pId]);
                    if (!list.isEmpty()) {
                        pPlayer.onEnchantmentPerformed(itemstack, i);
                        itemstack2 = itemstack.getItem().applyEnchantments(itemstack, list);
                        this.enchantSlots.setItem(0, itemstack2);

                        if (!hasLapisTalisman) {
                            itemstack1.shrink(i);
                            if (itemstack1.isEmpty()) {
                                this.enchantSlots.setItem(1, ItemStack.EMPTY);
                            }
                        }

                        pPlayer.awardStat(Stats.ENCHANT_ITEM);
                        if (pPlayer instanceof ServerPlayer) {
                            CriteriaTriggers.ENCHANTED_ITEM.trigger((ServerPlayer) pPlayer, itemstack2, i);
                        }

                        this.enchantSlots.setChanged();
                        this.enchantmentSeed.set(pPlayer.getEnchantmentSeed());
                        this.slotsChanged(this.enchantSlots);
                        p_347276_.playSound(null, p_347277_, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS, 1.0F, p_347276_.random.nextFloat() * 0.1F + 0.9F);
                    }
                });
                cir.setReturnValue(true);
            }
        } else {
            cir.setReturnValue(false);
        }
    }
    
    @SuppressWarnings({"deprecation", "removal", "deprecation", "removal"})
    private static boolean hasEnchantingEnchantment(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            if (!TalismanUtil.isTalismanEnabled(serverPlayer, "enchanting_talisman")) return false;
            return CuriosApi.getCuriosHelper().findEquippedCurio(itemStack -> itemStack.getItem() instanceof Enchanting_Talisman_Provider, serverPlayer).isPresent();
        }
        return false;
    }

    @SuppressWarnings({"deprecation", "removal", "deprecation", "removal"})
    private static boolean isWearingLapisTalisman(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            if (!TalismanUtil.isTalismanEnabled(serverPlayer, "lapis_talisman")) return false;
            return CuriosApi.getCuriosHelper().findEquippedCurio(itemStack -> itemStack.getItem() instanceof Lapis_Talisman_Provider, serverPlayer).isPresent();
        }
        return false;
    }
}

