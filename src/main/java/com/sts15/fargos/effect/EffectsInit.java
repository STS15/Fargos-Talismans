package com.sts15.fargos.effect;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.effect.talismans.*;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class EffectsInit {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, Fargos.MODID);

    public static final Holder<MobEffect> AIR_TALISMAN_EFFECT = MOB_EFFECTS.register("air_talisman_effect",
            () -> new AirTalismanEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));
    public static final Holder<MobEffect> AMETHYST_TALISMAN_EFFECT = MOB_EFFECTS.register("amethyst_talisman_effect",
            () -> new AmethystTalismanEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));
    public static final Holder<MobEffect> APPLE_TALISMAN_EFFECT = MOB_EFFECTS.register("apple_talisman_effect",
            () -> new AppleTalismanEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));
    public static final Holder<MobEffect> ARCHITECT_TALISMAN_EFFECT = MOB_EFFECTS.register("architect_talisman_effect",
            () -> new ArchitectTalismanEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));
    public static final Holder<MobEffect> ARCTIC_TALISMAN_EFFECT = MOB_EFFECTS.register("arctic_talisman_effect",
            () -> new ArcticTalismanEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));
    public static final Holder<MobEffect> BATTLE_TALISMAN_EFFECT = MOB_EFFECTS.register("battle_talisman_effect",
            () -> new BattleTalismanEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));
    public static final Holder<MobEffect> BLAZE_TALISMAN_EFFECT = MOB_EFFECTS.register("blaze_talisman_effect",
            () -> new BlazeTalismanEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));
    public static final Holder<MobEffect> BLINDED_TALISMAN_EFFECT = MOB_EFFECTS.register("blinded_talisman_effect",
            () -> new BlindedTalismanEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));
    public static final Holder<MobEffect> CACTUS_TALISMAN_EFFECT = MOB_EFFECTS.register("cactus_talisman_effect",
            () -> new CactusTalismanEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));
    public static final Holder<MobEffect> COPPER_TALISMAN_EFFECT = MOB_EFFECTS.register("copper_talisman_effect",
            () -> new CopperTalismanEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));
    public static final Holder<MobEffect> CREEPER_TALISMAN_EFFECT = MOB_EFFECTS.register("creeper_talisman_effect",
            () -> new CreeperTalismanEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));
    public static final Holder<MobEffect> DAY_TALISMAN_EFFECT = MOB_EFFECTS.register("day_talisman_effect",
            () -> new DayTalismanEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));
    public static final Holder<MobEffect> DIAMOND_TALISMAN_EFFECT = MOB_EFFECTS.register("diamond_talisman_effect",
            () -> new DiamondTalismanEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));
    public static final Holder<MobEffect> DRAGON_TALISMAN_EFFECT = MOB_EFFECTS.register("dragon_talisman_effect",
            () -> new DragonTalismanEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));
    public static final Holder<MobEffect> EARTH_TALISMAN_EFFECT = MOB_EFFECTS.register("earth_talisman_effect",
            () -> new EarthTalismanEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));
    public static final Holder<MobEffect> EMERALD_TALISMAN_EFFECT = MOB_EFFECTS.register("emerald_talisman_effect",
            () -> new EmeraldTalismanEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));
    public static final Holder<MobEffect> ENCHANTING_TALISMAN_EFFECT = MOB_EFFECTS.register("enchanting_talisman_effect",
            () -> new EnchantingTalismanEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));
    public static final Holder<MobEffect> ENDERMAN_TALISMAN_EFFECT = MOB_EFFECTS.register("enderman_talisman_effect",
            () -> new EndermanTalismanEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));
    public static final Holder<MobEffect> FATIGUED_TALISMAN_EFFECT = MOB_EFFECTS.register("fatigued_talisman_effect",
            () -> new FatiguedTalismanEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));
    public static final Holder<MobEffect> FIRE_TALISMAN_EFFECT = MOB_EFFECTS.register("fire_talisman_effect",
            () -> new FireTalismanEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));
    public static final Holder<MobEffect> FIRED_TALISMAN_EFFECT = MOB_EFFECTS.register("fired_talisman_effect",
            () -> new FiredTalismanEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));
    public static final Holder<MobEffect> FULL_MOON_TALISMAN_EFFECT = MOB_EFFECTS.register("full_moon_talisman_effect",
            () -> new FullMoonTalismanEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));
    public static final Holder<MobEffect> GHAST_TALISMAN_EFFECT = MOB_EFFECTS.register("ghast_talisman_effect",
            () -> new GhastTalismanEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));
    public static final Holder<MobEffect> GLOWSTONE_TALISMAN_EFFECT = MOB_EFFECTS.register("glowstone_talisman_effect",
            () -> new GlowstoneTalismanEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));
    public static final Holder<MobEffect> GOLD_TALISMAN_EFFECT = MOB_EFFECTS.register("gold_talisman_effect",
            () -> new GoldTalismanEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));
    public static final Holder<MobEffect> IRON_GOLEM_TALISMAN_EFFECT = MOB_EFFECTS.register("iron_golem_talisman_effect",
            () -> new IronGolemTalismanEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));
    public static final Holder<MobEffect> IRON_TALISMAN_EFFECT = MOB_EFFECTS.register("iron_talisman_effect",
            () -> new IronTalismanEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));
    public static final Holder<MobEffect> LAPIS_TALISMAN_EFFECT = MOB_EFFECTS.register("lapis_talisman_effect",
            () -> new LapisTalismanEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));
    public static final Holder<MobEffect> LIBRARIAN_TALISMAN_EFFECT = MOB_EFFECTS.register("librarian_talisman_effect",
            () -> new LibrarianTalismanEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));
    public static final Holder<MobEffect> MOOSHROOM_TALISMAN_EFFECT = MOB_EFFECTS.register("mooshroom_talisman_effect",
            () -> new MooshroomTalismanEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));
    public static final Holder<MobEffect> NAUSEATED_TALISMAN_EFFECT = MOB_EFFECTS.register("nauseated_talisman_effect",
            () -> new NauseatedTalismanEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));
    public static final Holder<MobEffect> NETHER_STAR_TALISMAN_EFFECT = MOB_EFFECTS.register("nether_star_talisman_effect",
            () -> new NetherStarTalismanEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));
    public static final Holder<MobEffect> NIGHT_TALISMAN_EFFECT = MOB_EFFECTS.register("night_talisman_effect",
            () -> new NightTalismanEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));
    public static final Holder<MobEffect> OBSIDIAN_TALISMAN_EFFECT = MOB_EFFECTS.register("obsidian_talisman_effect",
            () -> new ObsidianTalismanEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));
    public static final Holder<MobEffect> PICKAXE_TALISMAN_EFFECT = MOB_EFFECTS.register("pickaxe_talisman_effect",
            () -> new PickaxeTalismanEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));
    public static final Holder<MobEffect> POISONED_TALISMAN_EFFECT = MOB_EFFECTS.register("poisoned_talisman_effect",
            () -> new PoisonedTalismanEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));
    public static final Holder<MobEffect> RAIN_TALISMAN_EFFECT = MOB_EFFECTS.register("rain_talisman_effect",
            () -> new RainTalismanEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));
    public static final Holder<MobEffect> REDSTONE_TALISMAN_EFFECT = MOB_EFFECTS.register("redstone_talisman_effect",
            () -> new RedstoneTalismanEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));
    public static final Holder<MobEffect> SHULKER_TALISMAN_EFFECT = MOB_EFFECTS.register("shulker_talisman_effect",
            () -> new ShulkerTalismanEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));
    public static final Holder<MobEffect> SKELETON_TALISMAN_EFFECT = MOB_EFFECTS.register("skeleton_talisman_effect",
            () -> new SkeletonTalismanEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));
    public static final Holder<MobEffect> SLOWED_TALISMAN_EFFECT = MOB_EFFECTS.register("slowed_talisman_effect",
            () -> new SlowedTalismanEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));
    public static final Holder<MobEffect> SNOWY_TALISMAN_EFFECT = MOB_EFFECTS.register("snowy_talisman_effect",
            () -> new SnowyTalismanEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));
    public static final Holder<MobEffect> SPECTRAL_TALISMAN_EFFECT = MOB_EFFECTS.register("spectral_talisman_effect",
            () -> new SpectralTalismanEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));
    public static final Holder<MobEffect> STORM_TALISMAN_EFFECT = MOB_EFFECTS.register("storm_talisman_effect",
            () -> new StormTalismanEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));
    public static final Holder<MobEffect> SUN_TALISMAN_EFFECT = MOB_EFFECTS.register("sun_talisman_effect",
            () -> new SunTalismanEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));
    public static final Holder<MobEffect> THORNY_TALISMAN_EFFECT = MOB_EFFECTS.register("thorny_talisman_effect",
            () -> new ThornyTalismanEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));
    public static final Holder<MobEffect> TRUE_SUN_TALISMAN_EFFECT = MOB_EFFECTS.register("true_sun_talisman_effect",
            () -> new TrueSunTalismanEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));
    public static final Holder<MobEffect> UNDYING_TALISMAN_EFFECT = MOB_EFFECTS.register("undying_talisman_effect",
            () -> new UndyingTalismanEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));
    public static final Holder<MobEffect> VAMPIRIC_TALISMAN_EFFECT = MOB_EFFECTS.register("vampiric_talisman_effect",
            () -> new VampiricTalismanEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));
    public static final Holder<MobEffect> VINDICATOR_TALISMAN_EFFECT = MOB_EFFECTS.register("vindicator_talisman_effect",
            () -> new VindicatorTalismanEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));
    public static final Holder<MobEffect> VOID_TALISMAN_EFFECT = MOB_EFFECTS.register("void_talisman_effect",
            () -> new VoidTalismanEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));
    public static final Holder<MobEffect> WATER_TALISMAN_EFFECT = MOB_EFFECTS.register("water_talisman_effect",
            () -> new WaterTalismanEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));
    public static final Holder<MobEffect> WEAKENED_TALISMAN_EFFECT = MOB_EFFECTS.register("weakened_talisman_effect",
            () -> new WeakenedTalismanEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));
    public static final Holder<MobEffect> WITCH_TALISMAN_EFFECT = MOB_EFFECTS.register("witch_talisman_effect",
            () -> new WitchTalismanEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));
    public static final Holder<MobEffect> WITHER_TALISMAN_EFFECT = MOB_EFFECTS.register("wither_talisman_effect",
            () -> new WitherTalismanEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));
    public static final Holder<MobEffect> WITHERED_TALISMAN_EFFECT = MOB_EFFECTS.register("withered_talisman_effect",
            () -> new WitheredTalismanEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));
    public static final Holder<MobEffect> ZOMBIE_TALISMAN_EFFECT = MOB_EFFECTS.register("zombie_talisman_effect",
            () -> new ZombieTalismanEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));

    public static final Holder<MobEffect> SOUL_OF_COLOSSUS_EFFECT = MOB_EFFECTS.register("soul_of_colossus_effect",
            () -> new SoulOfColossusEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));
    public static final Holder<MobEffect> SOUL_OF_FLIGHT_MASTERY_EFFECT = MOB_EFFECTS.register("soul_of_flight_mastery_effect",
            () -> new SoulOfFlightMasteryEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));
    public static final Holder<MobEffect> SOUL_OF_SUPERSONIC_EFFECT = MOB_EFFECTS.register("soul_of_supersonic_effect",
            () -> new SoulOfSupersonicEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));


    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
