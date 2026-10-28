package me.axiomme.firstPlugin.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class SpawnCommandTreeEp6 {

    public static LiteralCommandNode<CommandSourceStack> create() {
        return Commands.literal("spawn")
                .then(Commands.literal("zombie")
                        .executes(context -> { return spawnMob(context.getSource(), EntityType.ZOMBIE);
                        }))
                .then(Commands.literal("creeper")
                        .executes(context -> { return spawnMob(context.getSource(), EntityType.CREEPER);
                        }))
                .build();
    }

    private static int spawnMob(CommandSourceStack source, EntityType type) {
        if (!(source.getExecutor() instanceof Player player)) {
            source.getSender().sendMessage(Component.text(
                    "Only a player can use this command !",NamedTextColor.RED));
            return 0;
        }

        player.getWorld().spawnEntity(player.getLocation(), EntityType.CREEPER);
        player.sendMessage(Component.text("Spwned a " + type.getKey().value() + " !", NamedTextColor.GREEN));
        return Command.SINGLE_SUCCESS;
    }
}
