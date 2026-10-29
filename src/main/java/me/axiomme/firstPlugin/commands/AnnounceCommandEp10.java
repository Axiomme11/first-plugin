package me.axiomme.firstPlugin.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;


public class AnnounceCommandEp10 {

    public static LiteralCommandNode<CommandSourceStack> create() {
        return Commands.literal("announce")
                .requires(commandSourceStack -> commandSourceStack.getSender().hasPermission("first-plugin.announce"))
                .executes(context -> {
                    sendUsage(context.getSource());
                    return 0;
                })
                .then(Commands.literal("chat")
                        .then(Commands.argument("message", StringArgumentType.greedyString()).executes(context -> {
                            sendChatAnnouncement(StringArgumentType.getString(context, "message"));
                            return Command.SINGLE_SUCCESS;
                        })))
                .then(Commands.literal("actionbar")
                        .then(Commands.argument("message", StringArgumentType.greedyString()).executes(context -> {
                            sendActionBarAnnouncement(StringArgumentType.getString(context, "message"));
                            return Command.SINGLE_SUCCESS;
                        })))
                .then(Commands.literal("title")
                        .then(Commands.argument("message", StringArgumentType.greedyString()).executes(context -> {
                            sendTitleAnnouncement(StringArgumentType.getString(context, "message"));
                            return Command.SINGLE_SUCCESS;
                        })))

                .build();
    }

    private static void sendChatAnnouncement(String message) {
        var announcement = Component.text()
                    .append(Component.text("[ANNOUNCEMENT]  ")
                            .color(NamedTextColor.GOLD)
                            .decorate(TextDecoration.BOLD)
                            .hoverEvent(HoverEvent.showText(Component.text("A server-wide message")
                                    .color(NamedTextColor.GOLD))))
                    .append(Component.text(message)
                            .color(NamedTextColor.WHITE))
                    .append(Component.text(" [Join next event!]")
                            .color(NamedTextColor.AQUA)
                            .hoverEvent(HoverEvent.showText(Component.text("Ready to join ? Click to prepate /warp event !", NamedTextColor.BLUE)))
                            .clickEvent(ClickEvent.suggestCommand("/warp event")))
                    .build();

        Bukkit.getServer().broadcast(announcement);
    }

    private static void sendActionBarAnnouncement(String message) {
        var announcement = Component.text()
                .append(Component.text("♦ ", NamedTextColor.YELLOW))
                .append(Component.text(message, TextColor.color(0x55CCFF)))
                .append(Component.text(" ♦", NamedTextColor.YELLOW))
                .build();


        Bukkit.getServer().sendActionBar(announcement);
    }

    private static void sendTitleAnnouncement(String message) {
        var title = Component.text().append(Component.text(message, NamedTextColor.YELLOW)).build();
        var subtitle = Component.text().append(Component.text("Server announcement", NamedTextColor.GRAY)).build();

        Bukkit.getServer().showTitle(Title.title(title, subtitle));
    }

    private static void sendUsage(CommandSourceStack source) {
        var usage = Component.text()
                .append(Component.text("Usage", NamedTextColor.RED, TextDecoration.BOLD))
                .appendNewline()
                .append(Component.text("/announce chat <message>", NamedTextColor.GRAY))
                .appendNewline()
                .append(Component.text("/announce actionbar <message>", NamedTextColor.GRAY))
                .appendNewline()
                .append(Component.text("/announce title <message>", NamedTextColor.GRAY))
                        .build();

        source.getSender().sendMessage(usage);
    }


}
