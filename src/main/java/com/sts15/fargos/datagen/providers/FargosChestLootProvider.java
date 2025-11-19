package com.sts15.fargos.datagen.providers;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.items.ItemInit;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.EnchantWithLevelsFunction;
import net.minecraft.world.level.storage.loot.functions.LimitCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.BinomialDistributionGenerator;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

public class FargosChestLootProvider implements LootTableSubProvider {

    private final HolderLookup.Provider lookup;

    public FargosChestLootProvider(HolderLookup.Provider lookup) {
        this.lookup = lookup;
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> out) {

        // ANCIENT CITY
        out.accept(key("chests/additional_ancient_city_loot"), loot(
                        pool(item(ItemInit.TALISMAN_BASE_NORMAL.get(), 1, 0.05f)),
                        pool(item(ItemInit.TALISMAN_BASE_ADVANCED.get(), 1, 0.03f)),
                        pool(item(ItemInit.TALISMAN_BASE_EPIC.get(), 1, 0.02f))
                )
        );

        // END CITY
        out.accept(key("chests/additional_end_city_loot"), loot(
                        pool(item(ItemInit.TALISMAN_BASE_ADVANCED.get(), 1, 0.05f)),
                        pool(item(ItemInit.TALISMAN_BASE_EPIC.get(), 1, 0.03f)),
                        pool(item(ItemInit.TALISMAN_BASE_LEGENDARY.get(), 1, 0.02f))
                )
        );

        // GENERIC
        out.accept(key("chests/additional_generic_loot"), loot(
                pool(item(ItemInit.TALISMAN_BASE_NORMAL.get(), 1, 0.03f)))
        );

        // GOOD LOOT
        out.accept(key("chests/additional_good_loot"), loot(
                        pool(item(ItemInit.TALISMAN_BASE_NORMAL.get(), 1, 0.04f)),
                        pool(item(ItemInit.TALISMAN_BASE_ADVANCED.get(), 1, 0.02f))
                )
        );

        // LIBRARY
        out.accept(key("chests/additional_library_loot"), loot(
                        pool(item(ItemInit.TALISMAN_BASE_ADVANCED.get(), 1, 0.05f)),
                        pool(item(ItemInit.TALISMAN_BASE_EPIC.get(), 1, 0.04f))
                )
        );

        // NETHER
        out.accept(key("chests/additional_nether_loot"), loot(
                        pool(item(ItemInit.TALISMAN_BASE_ADVANCED.get(), 1, 0.09f)),
                        pool(item(ItemInit.TALISMAN_BASE_EPIC.get(), 1, 0.06f))
                )
        );

        // TREASURE
        out.accept(key("chests/additional_treasure_loot"), loot(
                        pool(item(ItemInit.TALISMAN_BASE_EPIC.get(), 1, 0.05f)),
                        pool(item(ItemInit.TALISMAN_BASE_LEGENDARY.get(), 1, 0.01f))
                )
        );

        // ALCHEMY CHEST
        out.accept(key("chests/alchemy_ingredients"),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(3, 5))
                                .add(LootItem.lootTableItem(Items.GHAST_TEAR))
                                .add(LootItem.lootTableItem(Items.MAGMA_CREAM))
                                .add(LootItem.lootTableItem(Items.SUGAR))
                                .add(LootItem.lootTableItem(Items.BLAZE_POWDER)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2)))
                                        .apply(LimitCount.limitCount(IntRange.range(1, 3)))
                                )
                                .add(LootItem.lootTableItem(Items.SPIDER_EYE))
                                .add(LootItem.lootTableItem(Items.GUNPOWDER))
                                .add(LootItem.lootTableItem(Items.FERMENTED_SPIDER_EYE))
                                .add(LootItem.lootTableItem(Items.REDSTONE)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 3)))
                                )
                                .add(LootItem.lootTableItem(Items.GLOWSTONE_DUST))
                                .add(LootItem.lootTableItem(Items.NETHER_WART)))
        );

        out.accept(key("chests/dungeon_chest"),
                LootTable.lootTable()
                        .withPool(pool(item(ItemInit.HAND_WARMER.get(), 1, 0.05f)))
                        .withPool(pool(item(ItemInit.OBSIDIAN_HORSESHOE.get(), 1, 0.05f)))
                        .withPool(pool(item(ItemInit.WORM_SCARF.get(), 1, 0.05f)))
                        .withPool(pool(item(ItemInit.BRAIN_OF_CONFUSION.get(), 1, 0.05f)))
                        .withPool(pool(item(ItemInit.POCKET_MIRROR.get(), 1, 0.05f)))
                        .withPool(pool(item(ItemInit.CHARM_OF_MYTHS.get(), 1, 0.05f)))
                        .withPool(pool(item(ItemInit.BEE_CLOAK.get(), 1, 0.05f)))
                        .withPool(pool(item(ItemInit.STAR_VEIL.get(), 1, 0.05f)))
                        .withPool(pool(item(ItemInit.SHINY_STONE.get(), 1, 0.05f)))
                        .withPool(pool(item(ItemInit.HERO_SHIELD.get(), 1, 0.05f)))
                        .withPool(pool(item(ItemInit.FROZEN_SHIELD.get(), 1, 0.05f)))
                        .withPool(pool(item(ItemInit.ANKH_SHIELD.get(), 1, 0.05f)))

                        .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(ItemInit.ABOMINABLE_ENERGY.get())
                                        .apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(2, 5)))
                                ))
                        .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(Items.COAL)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 4)))
                                ))
                        .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(Items.DIAMOND)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2)))
                                ))
                        .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(Items.NETHERITE_SCRAP)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 4)))
                                ))
        );

        out.accept(key("chests/feature_chest"),
                LootTable.lootTable()
                        .withPool(pool(item(ItemInit.HAND_WARMER.get(), 1, 0.05f)))
                        .withPool(pool(item(ItemInit.OBSIDIAN_HORSESHOE.get(), 1, 0.05f)))
                        .withPool(pool(item(ItemInit.WORM_SCARF.get(), 1, 0.05f)))
                        .withPool(pool(item(ItemInit.BRAIN_OF_CONFUSION.get(), 1, 0.05f)))
                        .withPool(pool(item(ItemInit.POCKET_MIRROR.get(), 1, 0.05f)))
                        .withPool(pool(item(ItemInit.CHARM_OF_MYTHS.get(), 1, 0.05f)))
                        .withPool(pool(item(ItemInit.BEE_CLOAK.get(), 1, 0.05f)))
                        .withPool(pool(item(ItemInit.STAR_VEIL.get(), 1, 0.05f)))
                        .withPool(pool(item(ItemInit.SHINY_STONE.get(), 1, 0.05f)))
                        .withPool(pool(item(ItemInit.HERO_SHIELD.get(), 1, 0.05f)))
                        .withPool(pool(item(ItemInit.FROZEN_SHIELD.get(), 1, 0.05f)))
                        .withPool(pool(item(ItemInit.ANKH_SHIELD.get(), 1, 0.05f)))

                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(4, 6))

                                        .add(LootItem.lootTableItem(Items.ARROW)
                                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(16, 32))))

                                        .add(LootItem.lootTableItem(Items.ENDER_PEARL).setWeight(10))

                                        .add(LootItem.lootTableItem(Items.DIAMOND)
                                                .setWeight(3)
                                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 6))))

                                        .add(LootItem.lootTableItem(Items.IRON_INGOT)
                                                .setWeight(10)
                                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 12))))

                                        .add(LootItem.lootTableItem(Items.GOLD_INGOT)
                                                .setWeight(5)
                                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(4, 8))))

                                        .add(LootItem.lootTableItem(Items.REDSTONE)
                                                .setWeight(5)
                                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 18))))

                                        .add(LootItem.lootTableItem(Items.BREAD)
                                                .setWeight(15)
                                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(4, 7))))

                                        .add(LootItem.lootTableItem(Items.APPLE)
                                                .setWeight(15)
                                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 6))))

                                        .add(LootItem.lootTableItem(Items.DIAMOND_PICKAXE)
                                                .setWeight(5)
                                                .apply(LimitCount.limitCount(IntRange.exact(1)))
                                                .apply(
                                                        EnchantWithLevelsFunction.enchantWithLevels(
                                                                lookup,
                                                                UniformGenerator.between(20, 30)
                                                        ).when(LootItemRandomChanceCondition.randomChance(0.25f))
                                                ))

                                        .add(LootItem.lootTableItem(Items.DIAMOND_SWORD)
                                                .setWeight(5)
                                                .apply(LimitCount.limitCount(IntRange.exact(1)))
                                                .apply(
                                                        EnchantWithLevelsFunction.enchantWithLevels(
                                                                lookup,
                                                                UniformGenerator.between(20, 30)
                                                        ).when(LootItemRandomChanceCondition.randomChance(0.25f))
                                                ))


                                                        .add(LootItem.lootTableItem(Items.DIAMOND_CHESTPLATE)
                                                .setWeight(5)
                                                .apply(LimitCount.limitCount(IntRange.exact(1))))

                                        .add(LootItem.lootTableItem(Items.DIAMOND_HELMET)
                                                .setWeight(5)
                                                .apply(LimitCount.limitCount(IntRange.exact(1))))

                                        .add(LootItem.lootTableItem(Items.DIAMOND_LEGGINGS)
                                                .setWeight(5)
                                                .apply(LimitCount.limitCount(IntRange.exact(1))))

                                        .add(LootItem.lootTableItem(Items.DIAMOND_BOOTS)
                                                .setWeight(5)
                                                .apply(LimitCount.limitCount(IntRange.exact(1))))

                                        .add(LootItem.lootTableItem(Items.ENCHANTED_GOLDEN_APPLE)
                                                .apply(LimitCount.limitCount(IntRange.exact(1)))
                                                .when(LootItemRandomChanceCondition.randomChance(0.5f)))

                                        .add(LootItem.lootTableItem(Items.DIAMOND_HORSE_ARMOR))

                                        .add(LootItem.lootTableItem(Items.BOOK)
                                                .apply(
                                                        EnchantWithLevelsFunction.enchantWithLevels(
                                                                lookup,
                                                                UniformGenerator.between(20, 30)
                                                        ).when(LootItemRandomChanceCondition.randomChance(0.25f))
                                                ))
                        )
        );
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------
    private ResourceKey<LootTable> key(String path) {
        return ResourceKey.create(
                Registries.LOOT_TABLE,
                ResourceLocation.fromNamespaceAndPath(Fargos.MODID, path)
        );
    }

    private LootTable.Builder loot(LootPool.Builder... pools) {
        LootTable.Builder b = LootTable.lootTable();
        for (LootPool.Builder p : pools) b.withPool(p);
        return b;
    }

    private LootPool.Builder pool(LootPoolSingletonContainer.Builder<?> entry) {
        return LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(entry);
    }

    private LootPoolSingletonContainer.Builder<?> item(Item item, int weight, float chance) {
        return LootItem.lootTableItem(item)
                .setWeight(weight)
                .when(LootItemRandomChanceCondition.randomChance(chance));
    }
}
