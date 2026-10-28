package me.axiomme.firstPlugin.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;

public class SetSpeedCommandEp7 {

    public static LiteralCommandNode<CommandSourceStack> create() {
        return Commands.literal("speed").executes(context -> {
            var source = context.getSource();
            source.getSender().sendMessage(Component.text("Use /speed fly <speed> or /speed walk <speed>.", NamedTextColor.RED));
            return Command.SINGLE_SUCCESS;
        })
                .then(Commands.literal("walk")
                        .then(Commands.argument("speed", FloatArgumentType.floatArg(0.0F, 1.0F)).executes(context -> {
                            return setSpeed(context.getSource(), FloatArgumentType.getFloat(context, "speed"), SpeedType.WALK);
                        }))
                )
                .then(Commands.literal("fly")
                        .then(Commands.argument("speed", FloatArgumentType.floatArg(0.0F, 1.0F)).executes(context -> {
                            return setSpeed(context.getSource(), FloatArgumentType.getFloat(context, "speed"), SpeedType.FLY);
                        }))
                )
                .build();
    }

    private static int setSpeed(CommandSourceStack source, float speed, SpeedType type) {
        if (!(source.getExecutor() instanceof Player player)) {
            source.getSender().sendMessage(Component.text(
                    "Only a player can use this command !", NamedTextColor.RED));
            return 0;
        }

        if (type == SpeedType.WALK) {

            player.setWalkSpeed(speed);
            source.getSender().sendMessage(Component.text("Your walk speed is now ", NamedTextColor.GREEN)
                    .append(Component.text(speed, NamedTextColor.GOLD)));
            return Command.SINGLE_SUCCESS;
        } else { //type == SpeedType.FLY

            player.setFlySpeed(speed);
            source.getSender().sendMessage(Component.text("Your fly speed is now ", NamedTextColor.GREEN)
                    .append(Component.text(speed, NamedTextColor.GOLD)));
            return Command.SINGLE_SUCCESS;
        }

    }

    private enum SpeedType {
        WALK,
        FLY
    }
}


