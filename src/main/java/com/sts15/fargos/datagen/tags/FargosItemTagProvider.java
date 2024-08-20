package com.sts15.fargos.datagen.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import com.sts15.fargos.Fargos;
import com.sts15.fargos.items.ItemInit;
import java.util.concurrent.CompletableFuture;

public class FargosItemTagProvider extends ItemTagsProvider {
    public FargosItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, blockTags, Fargos.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        addCurioTags(provider);
    }

    private void addCurioTags(HolderLookup.Provider provider) {
        this.tag(FargosTags.Items.CURIO_CHARM)
            .add(ItemInit.AIR_TALISMAN.get())
            .add(ItemInit.AMETHYST_TALISMAN.get())
            .add(ItemInit.APPLE_TALISMAN.get())
            .add(ItemInit.ARCHITECT_TALISMAN.get())
            .add(ItemInit.ARCTIC_TALISMAN.get())
            .add(ItemInit.BATTLE_TALISMAN.get())
            .add(ItemInit.BLAZE_TALISMAN.get())
            .add(ItemInit.CACTUS_TALISMAN.get())
            .add(ItemInit.COPPER_TALISMAN.get())
            .add(ItemInit.CREEPER_TALISMAN.get())
            .add(ItemInit.DIAMOND_TALISMAN.get())
            .add(ItemInit.DRAGON_TALISMAN.get())
            .add(ItemInit.EARTH_TALISMAN.get())
            .add(ItemInit.EMERALD_TALISMAN.get())
            .add(ItemInit.ENCHANTING_TALISMAN.get())
            .add(ItemInit.ENDERMAN_TALISMAN.get())
            .add(ItemInit.FIRE_TALISMAN.get())
            .add(ItemInit.GHAST_TALISMAN.get())
            .add(ItemInit.GLOWSTONE_TALISMAN.get())
            .add(ItemInit.GOLD_TALISMAN.get())
            .add(ItemInit.IRON_GOLEM_TALISMAN.get())
            .add(ItemInit.IRON_TALISMAN.get())
            .add(ItemInit.LAPIS_TALISMAN.get())
            .add(ItemInit.LIBRARIAN_TALISMAN.get())
            .add(ItemInit.MOOSHROOM_TALISMAN.get())
            .add(ItemInit.NETHER_STAR_TALISMAN.get())
            .add(ItemInit.OBSIDIAN_TALISMAN.get())
            .add(ItemInit.PICKAXE_TALISMAN.get())
            .add(ItemInit.REDSTONE_TALISMAN.get())
            .add(ItemInit.SHULKER_TALISMAN.get())
            .add(ItemInit.SKELETON_TALISMAN.get())
            .add(ItemInit.SPECTRAL_TALISMAN.get())
            .add(ItemInit.THORNY_TALISMAN.get())
            .add(ItemInit.UNDYING_TALISMAN.get())
            .add(ItemInit.VAMPIRIC_TALISMAN.get())
            .add(ItemInit.VINDICATOR_TALISMAN.get())
            .add(ItemInit.VOID_TALISMAN.get())
            .add(ItemInit.WATER_TALISMAN.get())
            .add(ItemInit.WITCH_TALISMAN.get())
            .add(ItemInit.WITHER_TALISMAN.get())
            .add(ItemInit.ZOMBIE_TALISMAN.get())
            .add(ItemInit.FORCE_OF_EXPLORER.get())
            .add(ItemInit.FORCE_OF_MYSTIC.get())
            .add(ItemInit.FORCE_OF_NATURE.get())
            .add(ItemInit.FORCE_OF_OVERWORLD.get())
            .add(ItemInit.FORCE_OF_REJECTORS.get())
            .add(ItemInit.FORCE_OF_WARRIOR.get())
            .add(ItemInit.SOUL_OF_COLOSSUS.get())
            .add(ItemInit.SOUL_OF_DIMENSIONS.get())
            .add(ItemInit.SOUL_OF_FLIGHT_MASTERY.get())
            .add(ItemInit.SOUL_OF_MINECRAFT.get())
            .add(ItemInit.SOUL_OF_SUPERSONIC.get())
            .add(ItemInit.FIRED_TALISMAN.get())
            .add(ItemInit.POISONED_TALISMAN.get())
            .add(ItemInit.WITHERED_TALISMAN.get())
            .add(ItemInit.BLINDED_TALISMAN.get())
            .add(ItemInit.FATIGUED_TALISMAN.get())
            .add(ItemInit.SLOWED_TALISMAN.get())
            .add(ItemInit.NAUSEATED_TALISMAN.get())
            .add(ItemInit.WEAKENED_TALISMAN.get());

            //.add(ItemInit.SOUL_OF_TRAWLER.get()); // May be added in a later update
    }
}
