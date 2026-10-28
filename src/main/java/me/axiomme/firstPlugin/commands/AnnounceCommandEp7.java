package me.axiomme.firstPlugin.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import it.unimi.dsi.fastutil.Hash;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.w3c.dom.Text;

public class AnnounceCommandEp7 {

    public static LiteralCommandNode<CommandSourceStack> create() {
        return Commands.literal("announce")
                .then(Commands.argument("message", StringArgumentType.greedyString()).executes(context -> {
                    var message = StringArgumentType.getString(context, "message");

                    Bukkit.getServer().broadcast(Component.text("[Announcement] ", NamedTextColor.GOLD)
                            .append(Component.text(message, NamedTextColor.WHITE)));

                    return Command.SINGLE_SUCCESS;
                }))

                .build();
    }
}
