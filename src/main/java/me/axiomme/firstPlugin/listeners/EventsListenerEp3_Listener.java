package me.axiomme.firstPlugin.listeners;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventsListenerEp3_Listener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.joinMessage(Component.text("Welcome to the server ")
                .color(NamedTextColor.GREEN)
                .append(Component.text(event.getPlayer().getName())
                        .color(NamedTextColor.GOLD).decorate(TextDecoration.BOLD)));

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        event.quitMessage(Component.text("Bye bye " + player.getName() + " ! See you later..", NamedTextColor.RED));

    }
}
