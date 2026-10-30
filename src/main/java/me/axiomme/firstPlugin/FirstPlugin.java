package me.axiomme.firstPlugin;

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import me.axiomme.firstPlugin.commands.*;
import me.axiomme.firstPlugin.listeners.*;
import org.bukkit.plugin.java.JavaPlugin;

public final class FirstPlugin extends JavaPlugin {

    // https://youtube.com/playlist?list=PLZb14wOLO7NA&si=t2_ESWy1q9E4ZIoS Tuto yt
    @Override
    public void onEnable() {
        System.out.println("The plugin is Enable !");
        getServer().getPluginManager().registerEvents(new EventsListenerEp3_Listener(), this);
        getServer().getPluginManager().registerEvents(new CancellingEventsEp4_Listener(), this);

        //BasicCommands
        registerCommand("heal2", "h2", new HealBasicCommandsEp5());
        registerCommand("potato", "prout", new PotatoBasicCommandsEp5());
        getServer().getPluginManager().registerEvents(new BasicCommandsEp5_listener(), this);

        //CommandsTrees
        registerCommand("spawn2", "spw2", new BasicSpawnCommandEp6());
        getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            event.registrar().register(SpawnCommandTreeEp6.create());
            event.registrar().register(DayCommandEp6.create());
            event.registrar().register(HealArgumentsCommandEp7.create());
            event.registrar().register(GlowCommandEp7.create());
            event.registrar().register(SetSpeedCommandEp7.create());
            event.registrar().register(AdminCommandsEp8.AdminHealCommand());
            event.registrar().register(AnnounceCommandEp10.create());
        });


        saveDefaultConfig();
        getConfig();
        getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            event.registrar().register(SnackCommandEp11.create(this));
        });

        getServer().getPluginManager().registerEvents(new JoinListenerEp11(this), this);
        getServer().getPluginManager().registerEvents(new LaunchListenerEp13(this), this);
    }

    @Override
    public void onDisable() {
        System.out.println("The plugin is Disable !");
    }
}
