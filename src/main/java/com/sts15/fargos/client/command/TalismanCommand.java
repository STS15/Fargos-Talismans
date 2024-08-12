package com.sts15.fargos.client.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.sts15.fargos.Config;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class TalismanCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("fargostalismans")
                .requires(source -> source.hasPermission(0))
                .then(Commands.literal("talisman")
                        .then(Commands.literal("toggle")
                                .then(Commands.argument("talisman", StringArgumentType.word())
                                        .executes(context -> {
                                            String talisman = StringArgumentType.getString(context, "talisman");
                                            ServerPlayer player = context.getSource().getPlayerOrException();
                                            Config.toggleTalisman(player, talisman);
                                            return Command.SINGLE_SUCCESS;
                                        })))));
    }
}
