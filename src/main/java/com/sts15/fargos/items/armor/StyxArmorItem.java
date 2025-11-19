package com.sts15.fargos.items.armor;

import com.sts15.fargos.entity.armor.eridanus.EridanusArmorModel;
import com.sts15.fargos.entity.armor.eridanus.EridanusArmorRenderer;
import com.sts15.fargos.entity.armor.styx.StyxArmorModel;
import com.sts15.fargos.entity.armor.styx.StyxArmorRenderer;
import com.sts15.fargos.init.ArmorMaterialRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

import java.util.List;

public class StyxArmorItem extends ExtendedArmorItem {

    public StyxArmorItem(Type slot, Properties settings) {
        super(ArmorMaterialRegistry.STYX, slot, settings.fireResistant().rarity(Rarity.EPIC).setNoRepair().stacksTo(1));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        Player player = Minecraft.getInstance().player;
        if (player == null) return;
        if (hasFullStyxSet(player)) {
//            tooltipComponents.add(Component.literal("§6Set Bonus:").withStyle(ChatFormatting.GOLD));
//            tooltipComponents.add(Component.literal("§7+15% Damage, +20% Crit, +10% DR, +4 Minions/Sentries"));
//            tooltipComponents.add(Component.literal("§7Double tap ↓ to toggle Eridanus' Blessing"));
        }

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    private boolean hasFullStyxSet(Player player) {
        ItemStack head = player.getItemBySlot(EquipmentSlot.HEAD);
        ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
        ItemStack legs = player.getItemBySlot(EquipmentSlot.LEGS);
        ItemStack feet = player.getItemBySlot(EquipmentSlot.FEET);

        return head.getItem() instanceof StyxArmorItem &&
                chest.getItem() instanceof StyxArmorItem &&
                legs.getItem() instanceof StyxArmorItem &&
                feet.getItem() instanceof StyxArmorItem;
    }


    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
        if((entity instanceof LivingEntity) && (entity.isOnFire()) &&
                ((LivingEntity) entity).getItemBySlot(EquipmentSlot.HEAD).is(this) &&
                ((LivingEntity) entity).getItemBySlot(EquipmentSlot.CHEST).is(this) &&
                ((LivingEntity) entity).getItemBySlot(EquipmentSlot.LEGS).is(this) &&
                ((LivingEntity) entity).getItemBySlot(EquipmentSlot.FEET).is(this)
        ) {
            entity.clearFire();
        }
    }

    @Override
    public boolean isEnderMask(ItemStack stack, Player player, EnderMan endermanEntity) {
        return player.getItemBySlot(EquipmentSlot.HEAD).is(this);
    }

    @Override
    public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer) {
        return true;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public GeoArmorRenderer<?> supplyRenderer() {
        return new StyxArmorRenderer(new StyxArmorModel());
    }


}