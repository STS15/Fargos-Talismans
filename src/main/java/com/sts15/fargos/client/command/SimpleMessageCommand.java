package com.sts15.fargos.client.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.sts15.fargos.network.NetworkHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class SimpleMessageCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("fargos")
                .requires(source -> source.hasPermission(0))
                .then(Commands.literal("sendmessage")
                        .then(Commands.argument("message", StringArgumentType.greedyString())
                                .executes(context -> {
                                    String message = StringArgumentType.getString(context, "message");
                                    NetworkHandler.sendSimpleMessageToServer(Minecraft.getInstance().player, message);
                                    return Command.SINGLE_SUCCESS;
                                }))));
    }
}
