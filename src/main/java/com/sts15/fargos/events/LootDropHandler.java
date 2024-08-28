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
        }
    }

    private static boolean isTargetMob(EntityType<?> entityType) {
        return entityType == EntityType.CREEPER || entityType == EntityType.ZOMBIE
                || entityType == EntityType.SKELETON || entityType == EntityType.SPIDER;
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