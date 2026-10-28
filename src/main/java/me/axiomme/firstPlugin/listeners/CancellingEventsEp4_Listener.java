package me.axiomme.firstPlugin.listeners;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class CancellingEventsEp4_Listener implements Listener {

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e) {
        Player player = e.getPlayer();
        if (!e.getPlayer().isSneaking()) {
            return;
        }

        if (e.getCause() != PlayerTeleportEvent.TeleportCause.CONSUMABLE_EFFECT ) {
            return;
        }

        e.setCancelled(true);
        player.sendMessage(Component.text( "Nop ! ", NamedTextColor.YELLOW)
                .append(Component.text("you are sneaking", NamedTextColor.RED)));
    }

    @EventHandler
    public void onPlayerFall(EntityDamageEvent event) {

        if ( !(event.getEntity() instanceof Player player) ) {
            return;
        }

        if (event.getCause() != EntityDamageEvent.DamageCause.FALL) {
            return;
        }

        boolean shieldOnMainHand = player.getInventory().getItemInMainHand().getType() == Material.SHIELD;
        boolean shieldOnOffHand = player.getInventory().getItemInOffHand().getType() == Material.SHIELD;

        if (shieldOnMainHand || shieldOnOffHand) {
            event.setCancelled(true);
            player.sendMessage(Component.text( "Save by your Shield !", NamedTextColor.YELLOW));
            player.setFoodLevel(player.getFoodLevel() - 2);
        }
    }
}
