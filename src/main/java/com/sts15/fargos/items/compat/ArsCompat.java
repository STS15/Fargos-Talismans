package com.sts15.fargos.items.compat;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.init.Config;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.registries.Registries;

import java.util.Optional;

public class ArsCompat {

    // Transient modifiers
    private static final ResourceLocation SPELL_DAMAGE_ID = ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "arch_wizard_spell_damage");
    private static final ResourceLocation MANA_REGEN_ID   = ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "arch_wizard_mana_regen");
    private static final ResourceLocation MAX_MANA_ID     = ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "arch_wizard_max_mana");

    // Attributes from Ars Nouveau
    private static final ResourceLocation ARS_SPELL_DAMAGE = ResourceLocation.fromNamespaceAndPath("ars_nouveau", "spell_damage_bonus");
    private static final ResourceLocation ARS_MANA_REGEN   = ResourceLocation.fromNamespaceAndPath("ars_nouveau", "mana_regen_bonus");
    private static final ResourceLocation ARS_MAX_MANA     = ResourceLocation.fromNamespaceAndPath("ars_nouveau", "max_mana");

    private static double getSpellDamageMultiplier() { return Config.SOUL_OF_ARCH_WIZARD_SPELL_DAMAGE_MULTIPLIER.get(); }
    private static double getManaRegenMultiplier()   { return Config.SOUL_OF_ARCH_WIZARD_MANA_REGEN_MULTIPLIER.get(); }
    private static double getMaxManaAddition()       { return Config.SOUL_OF_ARCH_WIZARD_MAX_MANA_ADDITION.get(); }

    public static void applyBonuses(Player player) {
        if (!Config.SOUL_OF_ARCH_WIZARD_TOGGLE.get()) return;

        // Look up attributes dynamically from the registry; if Ars isn't loaded these return empty.
        applyAttribute(player, ARS_SPELL_DAMAGE, SPELL_DAMAGE_ID, getSpellDamageMultiplier(), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        applyAttribute(player, ARS_MANA_REGEN,   MANA_REGEN_ID,   getManaRegenMultiplier(), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        applyAttribute(player, ARS_MAX_MANA,     MAX_MANA_ID,     getMaxManaAddition(),     AttributeModifier.Operation.ADD_VALUE);
    }

    public static void removeBonuses(Player player) {
        removeAttribute(player, ARS_SPELL_DAMAGE, SPELL_DAMAGE_ID);
        removeAttribute(player, ARS_MANA_REGEN,   MANA_REGEN_ID);
        removeAttribute(player, ARS_MAX_MANA,     MAX_MANA_ID);
    }

    private static Optional<Holder.Reference<Attribute>> getAttrHolder(Player player, ResourceLocation rl) {
        Registry<Attribute> reg = player.level().registryAccess().registryOrThrow(Registries.ATTRIBUTE);
        ResourceKey<Attribute> key = ResourceKey.create(Registries.ATTRIBUTE, rl);
        return reg.getHolder(key);
    }

    private static void applyAttribute(Player player, ResourceLocation attrId, ResourceLocation modId,
                                       double amount, AttributeModifier.Operation op) {
        getAttrHolder(player, attrId).ifPresent(holder -> {
            AttributeInstance inst = player.getAttribute(holder);
            if (inst != null && inst.getModifier(modId) == null) {
                inst.addTransientModifier(new AttributeModifier(modId, amount, op));
            }
        });
    }

    private static void removeAttribute(Player player, ResourceLocation attrId, ResourceLocation modId) {
        getAttrHolder(player, attrId).ifPresent(holder -> {
            AttributeInstance inst = player.getAttribute(holder);
            if (inst != null && inst.getModifier(modId) != null) {
                inst.removeModifier(modId);
            }
        });
    }
}