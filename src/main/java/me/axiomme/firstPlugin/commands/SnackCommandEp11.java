package me.axiomme.firstPlugin.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import me.axiomme.firstPlugin.FirstPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SnackCommandEp11 {

    public static LiteralCommandNode<CommandSourceStack> create(FirstPlugin plugin) {

        return Commands.literal("snack")
                .executes(context -> {
                    var source = context.getSource();
                    if (!(source.getExecutor() instanceof Player player)) {
                        source.getSender().sendMessage(Component.text(
                                "Only a player can use this command !", NamedTextColor.RED));
                        return 0;
                    }

                    var snackAmount = plugin.getConfig().getInt("snack.amount", 5);

                    if (snackAmount <= 0) {
                        player.sendMessage(Component.text("The snack.amount must be greater than 0. (in config.yml)", NamedTextColor.RED));
                        return 0;
                    }

                    var leftover = player.getInventory().addItem(ItemStack.of(Material.COOKIE, snackAmount));
                    if (!leftover.isEmpty()) {
                        player.sendMessage(Component.text("Your inventory could not fit all the cookies. Make some room!", NamedTextColor.RED));
                        return 0;
                    }

                    String template = plugin.getConfig().getString("snack.message", "<gradient:#ed5050:#446aba>SNACK TIME!</gradient><newline> <gray>Enjoy your <gold><amount></gold> cookies!</gray><hover:show_text:'<yellow>Click to prepare another snack !</yellow>'><click:suggest_command:/snack><aqua><underlined>[More cookies]</underlined></aqua></click></hover>");
                    var message = MiniMessage.miniMessage().deserialize(template, Placeholder.unparsed("amount", Integer.toString(snackAmount)));

                    player.sendMessage(message);
                    return Command.SINGLE_SUCCESS;
                })
                .build();
    }
}
