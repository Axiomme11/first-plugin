package me.axiomme.firstPlugin.listeners;

import me.axiomme.firstPlugin.FirstPlugin;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import java.awt.*;

public class JoinListenerEp11 implements Listener {

    private final FirstPlugin plugin;

    public JoinListenerEp11(FirstPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (!this.plugin.getConfig().getBoolean("join-events.enabled", true)) {
            return;
        }

        String message = this.plugin.getConfig().getString("join-events.message", "<bold><gold>Welcome to the minecraft server !</gold></bold>");
        var formattedMessage = MiniMessage.miniMessage().deserialize(message, Placeholder.unparsed("player", event.getPlayer().getName()));
        event.getPlayer().sendMessage(formattedMessage);

    }
}
