package com.sts15.fargos.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.sts15.fargos.network.TalismanType;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;
import java.nio.file.Path;
import java.util.ArrayList;
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

        for (String talisman : getTalismanNames()) {
            ModConfigSpec.BooleanValue toggle = SERVER_BUILDER
                    .comment("Whether " + talisman + " is enabled on the server")
                    .define(talisman, true);
            SERVER_TALISMAN_TOGGLES.put(talisman, toggle);
        }

        SERVER_SPEC = SERVER_BUILDER.build();
        loadServerConfig();
    }

    private static void loadServerConfig() {
        Path configPath = serverModConfig != null ? serverModConfig.getFullPath() : null;
        if (configPath != null) {
            serverConfigFile = CommentedFileConfig.builder(configPath)
                    .sync()
                    .preserveInsertionOrder()
                    .autosave()
                    .writingMode(WritingMode.REPLACE)
                    .build();

            serverConfigFile.load();
            for (String talisman : SERVER_TALISMAN_TOGGLES.keySet()) {
                talismanEnabledStates.put(talisman, SERVER_TALISMAN_TOGGLES.get(talisman).get());
            }
        }
    }

//    public static boolean isTalismanEnabled(ServerPlayer player, String talisman) {
//        return talismanEnabledStates.getOrDefault(talisman, true);
//    }

    public static boolean isTalismanEnabledServer(String talisman) {
        return talismanEnabledStates.getOrDefault(talisman, true);
    }

    public static List<String> getTalismanNames() {
        List<String> talismanNames = new ArrayList<>();
        for (TalismanType type : TalismanType.values()) {
            talismanNames.add(type.name().toLowerCase());
        }
        return talismanNames;
    }

}
