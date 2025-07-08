package com.sts15.fargos.init;

import com.sts15.fargos.Fargos;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class ArmorMaterialRegistry {
    private static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(Registries.ARMOR_MATERIAL, Fargos.MODID);

    public static void register(IEventBus eventBus) {
        ARMOR_MATERIALS.register(eventBus);
    }

    public static DeferredHolder<ArmorMaterial, ArmorMaterial> ERIDANUS = register("eridanus",
            makeArmorMap(18, 26, 16, 10),
            40,
            SoundEvents.ARMOR_EQUIP_NETHERITE,
            () -> Ingredient.of(Tags.Items.INGOTS_NETHERITE),
            8,
            1);

    private static DeferredHolder<ArmorMaterial, ArmorMaterial> register(String name, EnumMap<ArmorItem.Type, Integer> defense, int enchantmentValue, Holder<SoundEvent> equipSound, Supplier<Ingredient> repairIngredient, float toughness, float knockbackResistance) {
        List<ArmorMaterial.Layer> list = List.of(new ArmorMaterial.Layer(Fargos.id(name)));
        return ARMOR_MATERIALS.register(name, ()-> new ArmorMaterial(defense, enchantmentValue, equipSound, repairIngredient, list, toughness, knockbackResistance));
    }

    static public EnumMap<ArmorItem.Type, Integer> makeArmorMap(int helmet, int chestplate, int leggings, int boots) {
        return Util.make(new EnumMap<>(ArmorItem.Type.class), (p_266655_) -> {
            p_266655_.put(ArmorItem.Type.BOOTS, boots);
            p_266655_.put(ArmorItem.Type.LEGGINGS, leggings);
            p_266655_.put(ArmorItem.Type.CHESTPLATE, chestplate);
            p_266655_.put(ArmorItem.Type.HELMET, helmet);
        });
    }

}
