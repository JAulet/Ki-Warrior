package com.bugboy.kiwarrior.commands;

import com.bugboy.kiwarrior.ki.PlayerKiProvider;
import com.google.common.collect.ImmutableList;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.Collection;


public class KiWarriorCommand {

    public static void register(CommandDispatcher<CommandSourceStack> commandDispatcher, CommandBuildContext commandBuildContext) {
        commandDispatcher.register(Commands.literal("setki").requires((commandSourceStack) -> {
            return commandSourceStack.hasPermission(2);
        }).then(Commands.literal("ki").then(Commands.argument("amount" , IntegerArgumentType.integer()).executes( command -> {
           return setKi(command.getSource(), ImmutableList.of(command.getSource().getEntityOrException()), IntegerArgumentType.getInteger(command, "amount"));
        }))).then(Commands.literal("maxki").then(Commands.argument("amount" , IntegerArgumentType.integer()).executes( command -> {
            return setMaxKi(command.getSource(), ImmutableList.of(command.getSource().getEntityOrException()), IntegerArgumentType.getInteger(command, "amount"));
        }))));
    }

    private static int setMaxKi(CommandSourceStack command, Collection<? extends Entity> entities, int target) {
        int i = 0;
        for(Entity entity: entities) {
            if(entity instanceof Player player) {
                player.getCapability(PlayerKiProvider.PLAYER_KI).ifPresent(ki -> {
                    ki.setMaxKi(target);
                });
                ++i;
            }
        }

        command.sendSuccess(() -> {return Component.literal("Max Ki set succcessful");}, true);

        return i;
    }

    private static int setKi(CommandSourceStack command, Collection<? extends Entity> entities, int target) {
        int i = 0;
        for(Entity entity: entities) {
            if(entity instanceof Player player) {
                player.getCapability(PlayerKiProvider.PLAYER_KI).ifPresent(ki -> {
                    ki.setKi(target);
                });
                ++i;
            }
        }

        command.sendSuccess(() -> {return Component.literal("Ki set succcessful");}, true);

        return i;
    }
}
