package com.sts15.fargos.mixins;

import java.util.List;

import com.sts15.fargos.utils.TalismanUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.sts15.fargos.items.talismans.Enchanting_Talisman_Provider;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.level.ServerPlayer;
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
    
    @SuppressWarnings({"deprecation", "removal", "deprecation", "removal"})
    private static boolean hasEnchantingEnchantment(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            if (!TalismanUtil.isTalismanEnabled(serverPlayer, "enchanting_talisman")) return false;
            return CuriosApi.getCuriosHelper().findEquippedCurio(itemStack -> itemStack.getItem() instanceof Enchanting_Talisman_Provider, serverPlayer).isPresent();
        }
        return false;
    }
}

