package me.axiomme.firstPlugin.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class GlowCommandEp7 {

    public static LiteralCommandNode<CommandSourceStack> create() {
        return Commands.literal("glow")
                .then(Commands.argument("enabled", BoolArgumentType.bool()).executes( context -> {
                    return ApplyGlowing(context.getSource(), BoolArgumentType.getBool(context, "enabled"));
                }))
                .build();
    }


    private static int ApplyGlowing(CommandSourceStack source, boolean enabled) {
        if (!(source.getExecutor() instanceof Player player)) {
            source.getSender().sendMessage(Component.text(
                    "Only a player can use this command !", NamedTextColor.RED));
            return 0;
        }

        player.setGlowing(enabled);
        player.sendMessage(Component.text(enabled ? "You are now glowing" : "Your glow is off.", NamedTextColor.GREEN));
        return Command.SINGLE_SUCCESS;
    }
}
