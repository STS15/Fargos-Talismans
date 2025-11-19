package com.sts15.fargos.items.compat;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.init.Config;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.ModList;

public class IronsCompat {

    private static final ResourceLocation MAX_MANA_ID             = ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "conjurist_max_mana");
    private static final ResourceLocation CAST_TIME_REDUCTION_ID  = ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "conjurist_cast_time_reduction");
    private static final ResourceLocation SPELL_RESIST_ID         = ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "conjurist_spell_resist");
    private static final ResourceLocation SPELL_POWER_ID          = ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "conjurist_spell_power");
    private static final ResourceLocation SUMMON_DAMAGE_ID        = ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "conjurist_summon_damage");
    private static final ResourceLocation COOLDOWN_REDUCTION_ID   = ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "conjurist_cooldown_reduction");

    public static void applyBonuses(Player player) {
        if (!isIronsLoaded() || !Config.SOUL_OF_CONJURIST_TOGGLE.get()) return;

        apply(player, AttributeRegistry.MAX_MANA, MAX_MANA_ID,
                Config.SOUL_OF_CONJURIST_MAX_MANA_ADDITION.get(), AttributeModifier.Operation.ADD_VALUE);

        apply(player, AttributeRegistry.CAST_TIME_REDUCTION, CAST_TIME_REDUCTION_ID,
                Config.SOUL_OF_CONJURIST_CAST_TIME_REDUCTION_MULTIPLIER.get(), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

        apply(player, AttributeRegistry.SPELL_RESIST, SPELL_RESIST_ID,
                Config.SOUL_OF_CONJURIST_SPELL_RESIST_MULTIPLIER.get(), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

        apply(player, AttributeRegistry.SPELL_POWER, SPELL_POWER_ID,
                Config.SOUL_OF_CONJURIST_SPELL_POWER_MULTIPLIER.get(), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

        apply(player, AttributeRegistry.SUMMON_DAMAGE, SUMMON_DAMAGE_ID,
                Config.SOUL_OF_CONJURIST_SUMMON_DAMAGE_MULTIPLIER.get(), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

        apply(player, AttributeRegistry.COOLDOWN_REDUCTION, COOLDOWN_REDUCTION_ID,
                Config.SOUL_OF_CONJURIST_COOLDOWN_REDUCTION_MULTIPLIER.get(), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }

    public static void removeBonuses(Player player) {
        if (!isIronsLoaded()) return;

        remove(player, AttributeRegistry.MAX_MANA, MAX_MANA_ID);
        remove(player, AttributeRegistry.CAST_TIME_REDUCTION, CAST_TIME_REDUCTION_ID);
        remove(player, AttributeRegistry.SPELL_RESIST, SPELL_RESIST_ID);
        remove(player, AttributeRegistry.SPELL_POWER, SPELL_POWER_ID);
        remove(player, AttributeRegistry.SUMMON_DAMAGE, SUMMON_DAMAGE_ID);
        remove(player, AttributeRegistry.COOLDOWN_REDUCTION, COOLDOWN_REDUCTION_ID);
    }

    private static void apply(Player player, Holder<Attribute> attrHolder, ResourceLocation id, double value, AttributeModifier.Operation op) {
        AttributeInstance instance = player.getAttribute(attrHolder);
        if (instance != null && instance.getModifier(id) == null) {
            instance.addTransientModifier(new AttributeModifier(id, value, op));
        }
    }

    private static void remove(Player player, Holder<Attribute> attrHolder, ResourceLocation id) {
        AttributeInstance instance = player.getAttribute(attrHolder);
        if (instance != null && instance.getModifier(id) != null) {
            instance.removeModifier(id);
        }
    }

    private static boolean isIronsLoaded() {
        return ModList.get().isLoaded("irons_spellbooks");
    }
}
