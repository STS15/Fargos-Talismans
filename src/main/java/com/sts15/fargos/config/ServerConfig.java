package com.sts15.fargos.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerConfig {

    private static final ModConfigSpec.Builder SERVER_BUILDER = new ModConfigSpec.Builder();

    public static final Map<String, ModConfigSpec.BooleanValue> SERVER_TALISMAN_TOGGLES = new HashMap<>();
    public static final Map<String, Boolean> talismanEnabledStates = new HashMap<>();

    public static final ModConfigSpec SERVER_SPEC;
    public static CommentedFileConfig serverConfigFile;
    public static ModConfig serverModConfig;

    static {
        SERVER_BUILDER.comment(
                "###########################",
                "# Fargos Talismans Config #",
                "# Server-side settings    #",
                "###########################",
                "",
                "Each talisman can be enabled or disabled. Defaults to enabled.",
                ""
        );

        // Initialize server-side toggles for all talismans dynamically
        for (String talisman : getTalismanNames()) {
            ModConfigSpec.BooleanValue toggle = SERVER_BUILDER
                    .comment("Whether " + talisman + " is enabled on the server")
                    .define(talisman, true);
            SERVER_TALISMAN_TOGGLES.put(talisman, toggle);
        }

        SERVER_SPEC = SERVER_BUILDER.build();

        // Load the configuration file and populate talismanEnabledStates
        loadServerConfig();
    }

    private static void loadServerConfig() {
        // Build the path to the configuration file
        Path configPath = serverModConfig != null ? serverModConfig.getFullPath() : null;

        // Check if the config path is available, and load the configuration
        if (configPath != null) {
            serverConfigFile = CommentedFileConfig.builder(configPath)
                    .sync()
                    .preserveInsertionOrder()
                    .autosave()
                    .writingMode(WritingMode.REPLACE)
                    .build();

            serverConfigFile.load();

            // Populate talismanEnabledStates after the config is loaded
            for (String talisman : SERVER_TALISMAN_TOGGLES.keySet()) {
                talismanEnabledStates.put(talisman, SERVER_TALISMAN_TOGGLES.get(talisman).get());
            }
        }
    }

    public static void toggleTalisman(ServerPlayer player, String talisman) {
        System.out.println("Talisman requested: " + talisman);
        System.out.println("Current talisman states: " + talismanEnabledStates);

        if (talismanEnabledStates.containsKey(talisman)) {
            boolean currentState = talismanEnabledStates.get(talisman);
            boolean newState = !currentState;
            talismanEnabledStates.put(talisman, newState);

            // Update server-side config if present
            if (serverModConfig != null && serverConfigFile != null) {
                System.out.println("Server-side config is available. Toggling talisman.");
                SERVER_TALISMAN_TOGGLES.get(talisman).set(newState);
                serverConfigFile.set(talisman, newState);
                serverConfigFile.save();

                if (player != null) {
                    player.sendSystemMessage(Component.literal(talisman + " is now " + (newState ? "enabled" : "disabled")));
                }
            } else {
                System.out.println("Server-side config or config file is missing.");
                if (serverModConfig == null) System.out.println("Missing serverModConfig.");
                if (serverConfigFile == null) System.out.println("Missing serverConfigFile.");

                if (player != null) {
                    player.sendSystemMessage(Component.literal("Toggling server-side configurations is not allowed through commands."));
                }
            }
        } else {
            System.out.println("Unknown talisman: " + talisman);
            if (player != null) {
                player.sendSystemMessage(Component.literal("Unknown talisman: " + talisman));
                player.sendSystemMessage(Component.literal("Valid Talismans: " + getTalismanNames()));
            }
        }
    }

    public static boolean isTalismanEnabled(ServerPlayer player, String talisman) {
        return talismanEnabledStates.getOrDefault(talisman, true);
    }

    public static boolean isTalismanEnabledServer(String talisman) {
        return talismanEnabledStates.getOrDefault(talisman, true);
    }

    public static List<String> getTalismanNames() {
        return List.of(
                "air_talisman",
                "amethyst_talisman",
                "apple_talisman",
                "architect_talisman",
                "arctic_talisman",
                "battle_talisman",
                "blaze_talisman",
                "cactus_talisman",
                "copper_talisman",
                "creeper_talisman",
                "diamond_talisman",
                "dragon_talisman",
                "earth_talisman",
                "emerald_talisman",
                "enchanting_talisman",
                "enderman_talisman",
                "fire_talisman",
                "ghast_talisman",
                "glowstone_talisman",
                "gold_talisman",
                "iron_golem_talisman",
                "iron_talisman",
                "lapis_talisman",
                "librarian_talisman",
                "mooshroom_talisman",
                "nether_star_talisman",
                "obsidian_talisman",
                "pickaxe_talisman",
                "redstone_talisman",
                "shulker_talisman",
                "skeleton_talisman",
                "spectral_talisman",
                "thorny_talisman",
                "undying_talisman",
                "vampiric_talisman",
                "vindicator_talisman",
                "void_talisman",
                "water_talisman",
                "witch_talisman",
                "wither_talisman",
                "zombie_talisman"
        );
    }
}
