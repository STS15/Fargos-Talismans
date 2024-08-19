package com.sts15.fargos.client.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.sts15.fargos.screen.TalismanToggleScreen;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TalismanScreenCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> command = Commands.literal("fargostalismans")
                .requires(source -> source.hasPermission(0))
                .then(Commands.literal("talisman")
                        .then(Commands.literal("gui")
                                        .executes(TalismanScreenCommand::executeToggle)));

        dispatcher.register(command);
    }

    private static int executeToggle(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        TalismanToggleScreen.open();

        return Command.SINGLE_SUCCESS;
    }
}
