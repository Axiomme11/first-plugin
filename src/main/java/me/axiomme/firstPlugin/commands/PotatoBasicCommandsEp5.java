package me.axiomme.firstPlugin.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class PotatoBasicCommandsEp5 implements BasicCommand {
    @Override
    public void execute(CommandSourceStack source, String[] args) {
        if (!(source.getExecutor() instanceof Player player)) {
            source.getSender().sendMessage(Component.text("Only a player can use /potato !", NamedTextColor.RED));
            return;
        }

        var dropLocation = player.getLocation();
        Vector vector = dropLocation.getDirection().setY(0).normalize().multiply(4);
        dropLocation.add(vector).add(0, 0.5, 0);

        player.getWorld().dropItem(dropLocation, ItemStack.of(Material.POTATO));
        player.sendMessage(Component.text("A potato has appeared. What could go wrong?", NamedTextColor.GOLD));
    }


}
