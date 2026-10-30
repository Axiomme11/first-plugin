package me.axiomme.firstPlugin.listeners;

import me.axiomme.firstPlugin.FirstPlugin;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.Tag;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class LaunchListenerEp13 implements Listener {

    private final FirstPlugin plugin;

    public LaunchListenerEp13(FirstPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onStep(PlayerInteractEvent event) {
        if (event.getAction() != Action.PHYSICAL) {
            return;
        }

        var stepBlock = event.getClickedBlock();
        if (stepBlock == null || !Tag.PRESSURE_PLATES.isTagged(stepBlock.getType())) return;

        Material underMaterial = Material.matchMaterial(this.plugin.getConfig().getString("launch-pad.material", "GOLD_BLOCK"));
        var underBlock = stepBlock.getRelative(BlockFace.DOWN);

        if (underMaterial == null || !underMaterial.isBlock() || !underMaterial.isSolid() || underBlock.getType() != underMaterial) {
            return;
        }


        var player = event.getPlayer();
        if (player.isInsideVehicle() || player.isFlying() || player.isGliding()) return;

        double forward = this.plugin.getConfig().getDouble("launch-pad.forward-strength");
        double upward = this.plugin.getConfig().getDouble("launch-pad.upward-strength");
        var location = player.getLocation();
        location.setPitch(0);
        var direction = location.getDirection();
        direction.multiply(forward);
        direction.setY(upward);

        player.setVelocity(direction);

        player.getWorld().spawnParticle(Particle.CLOUD, location, 20, 0.3, 0.1, 0.3);
        player.playSound(location, Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1.0F, 1.0F);
        var message = MiniMessage.miniMessage().deserialize(this.plugin.getConfig().getString("launch-pad.message", "<rainbow>Ffiooou !!</rainbow>"));
        player.sendMessage(message);
    }
}
