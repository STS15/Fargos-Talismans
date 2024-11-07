package com.sts15.fargos.client.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.sts15.fargos.config.PlayerDataHandler;
import com.sts15.fargos.network.NetworkHandler;
import com.sts15.fargos.network.TalismanType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class TalismanCommand {

    private static final SuggestionProvider<CommandSourceStack> SUGGEST_TALISMAN = (context, builder) -> {
        for (TalismanType talisman : TalismanType.values()) {
            builder.suggest(talisman.name().toLowerCase());
        }
        return builder.buildFuture();
    };

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> command = Commands.literal("fargostalismans")
                .requires(source -> source.hasPermission(0))
                .then(Commands.literal("talisman")
                        .then(Commands.literal("toggle")
                                .then(Commands.argument("talisman", StringArgumentType.word())
                                        .suggests(SUGGEST_TALISMAN)
                                        .executes(TalismanCommand::executeToggle))));

        dispatcher.register(command);
    }

    public static int executeToggle(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        String talismanName = StringArgumentType.getString(context, "talisman").toUpperCase();
        TalismanType talismanType;
        try {
            talismanType = TalismanType.valueOf(talismanName);
        } catch (IllegalArgumentException e) {
            context.getSource().sendFailure(Component.literal("Invalid talisman name: " + talismanName));
            return 0;
        }

        if (context.getSource().getEntity() instanceof ServerPlayer player) {
            // Server-side
            boolean isEnabled = !PlayerDataHandler.getTalismanState(player, talismanType.name());
            PlayerDataHandler.setTalismanState(player, talismanType.name(), isEnabled);
            player.displayClientMessage(Component.literal("Toggled talisman: " + talismanName + " to " + (isEnabled ? "enabled" : "disabled")), true);
        } else {
            // Client-side
            int talismanIndex = talismanType.getIndex();
            boolean isEnabled = !PlayerDataHandler.getTalismanState(context.getSource().getPlayerOrException(), talismanType.name());
            NetworkHandler.sendToggleTalismanStateToServer(talismanIndex, isEnabled);
        }

        return Command.SINGLE_SUCCESS;
    }
}
