package com.sts15.fargos.items.crafted;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.illusivesoulworks.caelus.api.CaelusApi;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

/**
 * A custom Elytra that grants flight by adding +1 to the fall_flying attribute
 * ONLY when in the "back" Curios slot.
 */
public class BasicElytraItem extends Item implements ICurioItem {
    private static final ResourceLocation FARGOS_FALL_FLYING_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath("fargos", "fargos_elytra_uuid");

    private final ResourceLocation texture;

    public BasicElytraItem(Properties props, ResourceLocation texture) {
        super(props);
        this.texture = texture;
    }

    public ResourceLocation getElytraTexture() {
        return texture;
    }

    @Override
    public boolean canElytraFly(ItemStack stack, LivingEntity entity) {
        return true;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return 432;
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return repair.is(Items.PHANTOM_MEMBRANE);
    }

    private static void applyEffects(Player player, CompoundTag playerData) {
        System.out.println("[applyEffects] STEP 1: Retrieving fall_flying Holder<Attribute> from CaelusApi...");
        Holder<Attribute> fallFlyingHolder = CaelusApi.getInstance().getFallFlyingAttribute();
        System.out.println("[applyEffects]  -> fallFlyingHolder = " + fallFlyingHolder);

        System.out.println("[applyEffects] STEP 2: Getting AttributeInstance from the Player using the Holder<Attribute>...");
        AttributeInstance attributeInstance = player.getAttribute(fallFlyingHolder);
        System.out.println("[applyEffects]  -> attributeInstance = " + attributeInstance);

        System.out.println("[applyEffects] STEP 3: Checking if attributeInstance is null...");
        if (attributeInstance == null) {
            System.out.println("[applyEffects]  -> attributeInstance is NULL, returning without applying effects.");
            return;
        }

        System.out.println("[applyEffects] STEP 4: Checking if our custom modifier is already present...");
        if (attributeInstance.getModifier(FARGOS_FALL_FLYING_MODIFIER_ID) == null) {
            System.out.println("[applyEffects]  -> Modifier not found, creating and adding it now...");
            AttributeModifier modifier = new AttributeModifier(
                    FARGOS_FALL_FLYING_MODIFIER_ID,
                    1.0,
                    AttributeModifier.Operation.ADD_VALUE
            );
            attributeInstance.addTransientModifier(modifier);
            System.out.println("[applyEffects]  -> Modifier added: " + modifier);
        } else {
            System.out.println("[applyEffects]  -> Modifier is already present, skipping creation.");
        }

        System.out.println("[applyEffects] STEP 5: Marking item as active in player data and updating abilities...");
        playerData.putBoolean("FargosElytraActive", true);
        player.onUpdateAbilities();
        System.out.println("[applyEffects]  -> Done applying effects!");
    }


    private static void removeEffects(Player player, CompoundTag playerData) {
        AttributeInstance elytraAttribute = player.getAttribute(CaelusApi.getInstance().getFallFlyingAttribute());
        if (elytraAttribute != null) {
            AttributeModifier modifier = elytraAttribute.getModifier(FARGOS_FALL_FLYING_MODIFIER_ID);
            if (modifier != null) {
                elytraAttribute.removeModifier(modifier);
            }
        }
        playerData.remove("FargosElytraActive");
        player.onUpdateAbilities();
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (stack.getItem() == newStack.getItem())
            return;
        Player entity = (Player) slotContext.entity();
        applyEffects(entity, entity.getPersistentData());
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (stack.getItem() == newStack.getItem())
            return;
        Player entity = (Player) slotContext.entity();
        removeEffects(entity, entity.getPersistentData());
    }
}
