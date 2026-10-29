package me.axiomme.firstPlugin.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.List;

public class AdminCommandsEp8 {

    public static LiteralCommandNode<CommandSourceStack> AdminHealCommand() {
        return Commands.literal("admin")
                .requires(commandSourceStack -> {
                    return commandSourceStack.getSender().hasPermission("first-plugin.admin");
                })
                .then(Commands.literal("heal")
                    .requires(commandSourceStack -> {
                    return commandSourceStack.getSender().hasPermission("first-plugin.admin.heal");
                    })
                    .executes(context -> {
                        var source = context.getSource();
                        if (!(source.getExecutor() instanceof Player player)) {
                            source.getSender().sendMessage(Component.text(
                                    "Only a player can use this command !", NamedTextColor.RED));
                            return 0;
                        }
                        return healTargets(context, List.of(player));
                    })
                        .then(Commands.argument("targets", ArgumentTypes.players())
                                .requires(commandSourceStack -> {
                                    return commandSourceStack.getSender().hasPermission("first-plugin.admin.heal.targets");
                                })
                                .executes( context -> {
                                    var resolver = context.getArgument("targets", PlayerSelectorArgumentResolver.class);
                                    var players = resolver.resolve(context.getSource());
                                    return healTargets(context, players);
                                })))


                .then(Commands.literal("gamemode")
                        .requires(commandSourceStack -> {
                            return commandSourceStack.getSender().hasPermission("first-plugin.admin.gamemode");
                        })
                        .then(Commands.argument("gamemode", ArgumentTypes.gameMode())
                                .requires(commandSourceStack -> {
                                    return commandSourceStack.getSender().hasPermission("first-plugin.admin.gamemode.gamemode");
                                })
                                .executes( context -> {
                                    var source = context.getSource();
                                    if (!(source.getExecutor() instanceof Player player)) {
                                        source.getSender().sendMessage(Component.text(
                                                "Only a player can use this command or select a player name if you using this from the console !", NamedTextColor.RED));
                                        return 0;
                                    }
                                    var gamemode = context.getArgument("gamemode", GameMode.class);
                                    return changeGamemode(context, gamemode, List.of(player));
                        })
                            .then(Commands.argument("targets", ArgumentTypes.players())
                                    .requires(commandSourceStack -> {
                                        return commandSourceStack.getSender().hasPermission("first-plugin.admin.gamemode.gamemode.targets");
                                    })
                                    .executes( context -> {
                                        var resolver = context.getArgument("targets", PlayerSelectorArgumentResolver.class);
                                        var players = resolver.resolve(context.getSource());
                                        var gamemode = context.getArgument("gamemode", GameMode.class);
                                        return changeGamemode(context, gamemode, players);
                                    }))))
                .build();
    }

    private static int healTargets(CommandContext<CommandSourceStack> context, List<Player> targets) {
        for (Player player : targets) {
            player.setHealth(20);
            player.setFoodLevel(20);
            player.setFireTicks(0);
            player.sendMessage(Component.text("You have been healed", NamedTextColor.GREEN));
        }

        context.getSource().getSender().sendMessage(Component.text("Healed " + targets.size() + " players.", NamedTextColor.GREEN));
        return Command.SINGLE_SUCCESS;
    }

    private static int changeGamemode(CommandContext<CommandSourceStack> context, GameMode gamemode, List<Player> targets) {
        for (Player player : targets) {
            player.setGameMode(gamemode);
            player.sendMessage(Component.text("Your gamemode is now ", NamedTextColor.GREEN)
                    .append(Component.text(gamemode.toString(), NamedTextColor.AQUA)));
        }

        context.getSource().getSender().sendMessage(Component.text("Set " + targets.size() + " players to ", NamedTextColor.GREEN)
                .append(Component.text(gamemode.toString(), NamedTextColor.AQUA)));
        return Command.SINGLE_SUCCESS;
    }
}

