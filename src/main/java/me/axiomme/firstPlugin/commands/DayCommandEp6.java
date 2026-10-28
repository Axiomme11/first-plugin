package me.axiomme.firstPlugin.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;

public class DayCommandEp6 {

    public static LiteralCommandNode<CommandSourceStack> create() {
        return Commands.literal("day")
                .executes( context -> {
                    return setTime( context.getSource(), 6000L, "noon");
                })
                .then(Commands.literal("sunrise").executes( context -> {
                    return setTime(context.getSource(), 23000L, "sunrise");
                }))
                .then(Commands.literal("sunset").executes( context -> {
                    return setTime(context.getSource(), 12000L, "sunset");
                }))
                .build();
    }

    private static int setTime(CommandSourceStack source, long time, String timeName) {
        if (!(source.getExecutor() instanceof Player player)) {
            source.getSender().sendMessage(Component.text(
                    "Only a player can use this command !", NamedTextColor.RED));
            return 0;
        }

        player.getWorld().setTime(time);
        player.sendMessage(Component.text("Time set to " + timeName, NamedTextColor.GREEN));
        return Command.SINGLE_SUCCESS;
    }
}
