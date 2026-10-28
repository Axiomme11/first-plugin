package me.axiomme.firstPlugin.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;

import javax.inject.Named;

public class HealArgumebtsCommandEp7 {

    public static LiteralCommandNode<CommandSourceStack> create() {
        return Commands.literal("heal").executes( context -> {
                        return fullyHeal(context.getSource());
                })
                .then(Commands.argument("amount", DoubleArgumentType.doubleArg(1.0, 20.0)).executes( context -> {
                        return amountHeal(context.getSource(), DoubleArgumentType.getDouble(context, "amount"));
                }))
                .build();
    }

    private static int fullyHeal(CommandSourceStack source) {
        if (!(source.getExecutor() instanceof Player player)) {
            source.getSender().sendMessage(Component.text(
                    "Only a player can use this command !", NamedTextColor.RED));
            return 0;
        }

        player.setHealth(20);
        player.setFoodLevel(20);
        player.setSaturation(20);
        player.sendMessage(Component.text("Your health is now ", NamedTextColor.GREEN)
                .append(Component.text("full", NamedTextColor.GOLD))
                .append(Component.text(".", NamedTextColor.GREEN))
        );

        return Command.SINGLE_SUCCESS;
    }

    private static int amountHeal(CommandSourceStack source, double amount) {
        if (!(source.getExecutor() instanceof Player player)) {
            source.getSender().sendMessage(Component.text(
                    "Only a player can use this command !", NamedTextColor.RED));
            return 0;
        }

        player.setHealth(amount);
        player.sendMessage(Component.text("Your health is now ", NamedTextColor.GREEN)
                .append(Component.text(amount, NamedTextColor.GOLD))
                .append(Component.text(".", NamedTextColor.GREEN))
        );

        return Command.SINGLE_SUCCESS;
    }
}
