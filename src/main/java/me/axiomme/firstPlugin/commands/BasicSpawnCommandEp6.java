package me.axiomme.firstPlugin.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class BasicSpawnCommandEp6 implements BasicCommand {
    @Override
    public void execute(CommandSourceStack source, String[] args) {
        if (!(source.getExecutor() instanceof Player player)) {
            source.getSender().sendMessage(Component.text("Only a player can use this command !", NamedTextColor.RED));
            return;
        }

        if (args.length != 1) {
            source.getSender().sendMessage(Component.text("Usage : <zombie | creeper> ", NamedTextColor.RED));
            return;
        }

        EntityType entityType;
        if (args[0].equals("zombie")) {
            entityType = EntityType.ZOMBIE;
        } else if (args[0].equals("creeper")) {
            entityType = EntityType.CREEPER;
        } else {
            source.getSender().sendMessage(Component.text("Choose between a zombie or a creeper.", NamedTextColor.RED));
            return;
        }

        player.getWorld().spawnEntity(player.getLocation(), entityType);
        source.getSender().sendMessage(Component.text("Spawned a " + entityType.getKey().value() + " !", NamedTextColor.GREEN));

    }
}
