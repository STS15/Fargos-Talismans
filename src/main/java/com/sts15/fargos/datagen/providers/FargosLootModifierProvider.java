package com.sts15.fargos.datagen.providers;

import com.google.common.collect.ImmutableList;
import com.sts15.fargos.Fargos;
import com.sts15.fargos.loot.AppendLootModifier;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.predicates.AnyOfCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

import java.util.concurrent.CompletableFuture;

public class FargosLootModifierProvider extends GlobalLootModifierProvider {

    public FargosLootModifierProvider(
            PackOutput output,
            CompletableFuture<HolderLookup.Provider> lookupProvider
    ) {
        super(output, lookupProvider, Fargos.MODID);
    }

    @Override
    protected void start() {

        appendModifier("ancient_city_modifier",
                table("minecraft:chests/ancient_city"),
                "fargostalismans:chests/additional_ancient_city_loot"
        );

        appendModifier("compat_generic_loot_modifier",
                tables(
                        "betterdungeons:zombie_dungeon/chests/common",
                        "betterdungeons:skeleton_dungeon/chests/common",
                        "betterdungeons:spider_dungeon/chests/egg_room",
                        "betterstrongholds:chests/common",
                        "betterstrongholds:chests/grand_library",
                        "structory:harvest/graveyard",
                        "structory:harvest/graveyard2",
                        "structory:harvest/old_manor/common",
                        "structory:ruin/swamp/loot",
                        "structory:ruin/taiga/illager_low",
                        "structory:ruin/taiga/illager_high",
                        "structory:harvest/manor2/loot",
                        "idas:chests/wizardtower/wizardtower_library",
                        "idas:chests/apothecary_abode/apothecary_abode",
                        "nova_structures:chests/badland_miner_outpost_towers"
                ),
                "fargostalismans:chests/additional_generic_loot"
        );

        appendModifier("compat_good_loot_modifier",
                tables(
                        "betterdeserttemples:chests/tomb",
                        "betterdeserttemples:chests/storage",
                        "betterdeserttemples:chests/library",
                        "betterdungeons:zombie_dungeon/chests/tombstone",
                        "betterwitchhuts:chests/hut_0",
                        "structory:library/high",
                        "structory:library/low",
                        "structory:ruin/taiga/illager_treasure",
                        "idas:chests/wizardtower/wizardtower_top",
                        "idas:chests/tinkers_workshop/tinkers_workshop_vault"
                ),
                "fargostalismans:chests/additional_good_loot"
        );

        appendModifier("compat_treasure_loot_modifier",
                tables(
                        "betterdeserttemples:chests/pharaoh_hidden",
                        "betterfortresses:chests/worship"
                ),
                "fargostalismans:chests/additional_treasure_loot"
        );

        appendModifier("end_city_modifier",
                table("minecraft:chests/end_city_treasure"),
                "fargostalismans:chests/additional_end_city_loot"
        );

        appendModifier("nether_loot_modifier",
                tables(
                        "minecraft:chests/nether_bridge",
                        "minecraft:chests/bastion_treasure",
                        "minecraft:chests/bastion_bridge",
                        "minecraft:chests/bastion_other"
                ),
                "fargostalismans:chests/additional_nether_loot"
        );

        appendModifier("stronghold_library_modifier",
                table("minecraft:chests/stronghold_library"),
                "fargostalismans:chests/additional_library_loot"
        );

        appendModifier("vanilla_generic_loot_modifier",
                tables(
                        "minecraft:chests/buried_treasure",
                        "minecraft:chests/desert_pyramid",
                        "minecraft:chests/jungle_temple",
                        "minecraft:chests/pillager_outpost",
                        "minecraft:chests/shipwreck_map",
                        "minecraft:chests/simple_dungeon",
                        "minecraft:chests/stronghold_crossing",
                        "minecraft:chests/stronghold_corridor",
                        "minecraft:chests/underwater_ruin_big",
                        "minecraft:chests/underwater_ruin_small",
                        "minecraft:chests/woodland_mansion",
                        "minecraft:chests/village/village_cartographer",
                        "minecraft:chests/village/village_temple",
                        "minecraft:chests/ruined_portal",
                        "minecraft:chests/abandoned_mineshaft"
                ),
                "fargostalismans:chests/additional_generic_loot"
        );
    }

    private LootItemCondition[] table(String tableId) {
        LootItemCondition.Builder cond =
                LootTableIdCondition.builder(ResourceLocation.parse(tableId));

        return new LootItemCondition[]{
                AnyOfCondition.anyOf(cond).build()
        };
    }

    private LootItemCondition[] tables(String... tables) {
        ImmutableList.Builder<LootItemCondition.Builder> list = ImmutableList.builder();
        for (String t : tables) {
            list.add(LootTableIdCondition.builder(ResourceLocation.parse(t)));
        }

        return new LootItemCondition[]{
                AnyOfCondition.anyOf(list.build().toArray(LootItemCondition.Builder[]::new)).build()
        };
    }

    private void appendModifier(String name, LootItemCondition[] conditions, String key) {
        add(name, new AppendLootModifier(conditions, key));
    }
}
