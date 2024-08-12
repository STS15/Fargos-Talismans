package com.sts15.fargos;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.sts15.fargos.network.NetworkHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Config {

    private static final ModConfigSpec.Builder CLIENT_BUILDER = new ModConfigSpec.Builder();

    public static final Map<String, ModConfigSpec.BooleanValue> CLIENT_TALISMAN_TOGGLES = new HashMap<>();
    public static final Map<String, Boolean> talismanEnabledStates = new HashMap<>();

    static final ModConfigSpec CLIENT_SPEC;
    public static CommentedFileConfig clientConfigFile;
    public static ModConfig clientModConfig;

    static {
        CLIENT_BUILDER.comment(
                "###########################",
                "# Fargos Talismans Config #",
                "# Client-side settings    #",
                "###########################",
                "",
                "Each talisman can be enabled or disabled. Defaults to enabled.",
                ""
        );

        // Initialize client-side toggles for all talismans dynamically
        for (String talisman : getTalismanNames()) {
            ModConfigSpec.BooleanValue toggle = CLIENT_BUILDER
                    .comment("Whether " + talisman + " is enabled on the client")
                    .define(talisman, true);
            CLIENT_TALISMAN_TOGGLES.put(talisman, toggle);
        }

        CLIENT_SPEC = CLIENT_BUILDER.build();

        // Load the configuration file and populate talismanEnabledStates
        loadClientConfig();
    }

    private static void loadClientConfig() {
        // Build the path to the configuration file
        Path configPath = clientModConfig != null ? clientModConfig.getFullPath() : null;

        // Check if the config path is available, and load the configuration
        if (configPath != null) {
            clientConfigFile = CommentedFileConfig.builder(configPath)
                    .sync()
                    .preserveInsertionOrder()
                    .autosave()
                    .writingMode(WritingMode.REPLACE)
                    .build();

            clientConfigFile.load();

            // Populate talismanEnabledStates after the config is loaded
            for (String talisman : CLIENT_TALISMAN_TOGGLES.keySet()) {
                talismanEnabledStates.put(talisman, CLIENT_TALISMAN_TOGGLES.get(talisman).get());
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

            // Update client-side config if present
            if (clientModConfig != null && clientConfigFile != null) {
                System.out.println("Client-side config is available. Toggling talisman.");
                CLIENT_TALISMAN_TOGGLES.get(talisman).set(newState);
                clientConfigFile.set(talisman, newState);
                clientConfigFile.save();

                if (player != null) {
                    player.sendSystemMessage(Component.literal(talisman + " is now " + (newState ? "enabled" : "disabled")));
                }
            } else {
                System.out.println("Client-side config or config file is missing.");
                if (clientModConfig == null) System.out.println("Missing clientModConfig.");
                if (clientConfigFile == null) System.out.println("Missing clientConfigFile.");

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

    public static boolean isTalismanEnabledClient(String talisman) {
        return talismanEnabledStates.getOrDefault(talisman, false);
    }

    public static boolean isTalismanEnabledServer(String talisman) {
        return true;  // Simplified assumption for now
    }

    public static boolean isTalismanEnabledOnClientAndServer(String talisman) {
        return talismanEnabledStates.getOrDefault(talisman, false);
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
