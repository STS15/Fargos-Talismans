package com.sts15.fargos.events;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.items.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

@EventBusSubscriber(modid = Fargos.MODID)
public class LootDropHandler {

    private static final List<Supplier<? extends Item>> CUSTOM_ITEMS = Arrays.asList(
            ItemInit.FLYING_CARPET,
            ItemInit.SWEETHEART_NECKLACE,
            ItemInit.AMBER_HORSESHOE_BALLOON,
            ItemInit.BUNDLE_OF_HORSESHOE_BALLOONS,
            ItemInit.SHIELD_OF_CTHULHU,
            ItemInit.MASTER_NINJA_GEAR,
            ItemInit.BLESSED_APPLE,
            ItemInit.MECHANICAL_CART,
            ItemInit.ANCIENT_HORN,
            ItemInit.REINDEER_BELLS,
            ItemInit.BRAIN_SCRAMBLER,
            ItemInit.AEOLUS_BOOTS
    );

    private static final Random RANDOM = new Random();

    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event) {
        Level world = event.getEntity().level();
        if (!(world instanceof ServerLevel serverLevel)) return;

        if (event.getSource().getEntity() instanceof Player player) {
            EntityType<?> entityType = event.getEntity().getType();

            if (isTargetMob(event.getEntity().getType())) {
                BlockPos entityPos = event.getEntity().blockPosition();
                ResourceLocation customStructureLocation = ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "dungeon2");

                Registry<Structure> structureRegistry = serverLevel.registryAccess().registryOrThrow(Registries.STRUCTURE);
                Structure customStructure = structureRegistry.get(customStructureLocation);

                if (customStructure != null && serverLevel.structureManager().getStructureAt(entityPos, customStructure).isValid()) {
                    if (shouldDropLoot(player)) {
                        ItemStack loot = getRandomCustomItem();
                        if (!loot.isEmpty()) {
                            event.getEntity().spawnAtLocation(loot);
                        }
                    }
                }
            }

            if (entityType == EntityType.WITHER) {
                spawnCustomDrop(event.getEntity().blockPosition(), serverLevel, ItemInit.ABOMINABLE_ENERGY.get(), 1, 2, player);
            } else if (entityType == EntityType.WARDEN) {
                spawnCustomDrop(event.getEntity().blockPosition(), serverLevel, ItemInit.ABOMINABLE_ENERGY.get(), 2, 3, player);
            } else if (entityType == EntityType.ENDER_DRAGON) {
                spawnCustomDrop(event.getEntity().blockPosition(), serverLevel, ItemInit.ABOMINABLE_ENERGY.get(), 3, 4, player);
            }
        }
    }

    private static void spawnCustomDrop(BlockPos pos, ServerLevel serverLevel, Item item, int min, int max, Player player) {
        int baseDrop = RANDOM.nextInt(max - min + 1) + min;
        Registry<Enchantment> enchantmentRegistry = player.level().registryAccess().registryOrThrow(Registries.ENCHANTMENT);
        Holder<Enchantment> lootingEnchantment = enchantmentRegistry.getHolderOrThrow(Enchantments.LOOTING);
        int lootingLevel = EnchantmentHelper.getItemEnchantmentLevel(lootingEnchantment, player.getMainHandItem());
        int maxBonus = 6;
        double diminishingFactor = 0.5;
        int bonusDrop = (int) Math.min(maxBonus, Math.ceil(lootingLevel * diminishingFactor));
        int totalDrop = baseDrop + RANDOM.nextInt(bonusDrop + 1);

        for (int i = 0; i < totalDrop; i++) {
            serverLevel.addFreshEntity(new ItemEntity(serverLevel, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(item)));
        }
    }

    private static boolean isTargetMob(EntityType<?> entityType) {
        return entityType == EntityType.PIGLIN || entityType == EntityType.PIGLIN_BRUTE
                || entityType == EntityType.MAGMA_CUBE || entityType == EntityType.WITHER_SKELETON;
    }

    private static boolean shouldDropLoot(Player player) {
        Registry<Enchantment> enchantmentRegistry = player.level().registryAccess().registryOrThrow(Registries.ENCHANTMENT);
        Holder<Enchantment> lootingEnchantment = enchantmentRegistry.getHolderOrThrow(Enchantments.LOOTING);
        int lootingLevel = EnchantmentHelper.getItemEnchantmentLevel(lootingEnchantment, player.getMainHandItem());
        double chance = 0.05 + (lootingLevel * 0.02);
        return RANDOM.nextDouble() < chance;
    }

    private static ItemStack getRandomCustomItem() {
        Supplier<? extends Item> randomItemSupplier = CUSTOM_ITEMS.get(RANDOM.nextInt(CUSTOM_ITEMS.size()));
        return new ItemStack(randomItemSupplier.get());
    }
}