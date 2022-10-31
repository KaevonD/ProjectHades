package me.jnk.projecthades;

import me.jnk.projecthades.commands.PartyCommands;
import me.jnk.projecthades.commands.SpawnCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class ProjectHades extends JavaPlugin {

    private static ProjectHades plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        getServer().getPluginManager().registerEvents(new MyListener(), this);
        getServer().getPluginManager().registerEvents(new GuiListener(), this);
        getServer().getPluginManager().registerEvents(new DamageListener(), this);

        getCommand("party").setExecutor(new PartyCommands());
        getCommand("spawn").setExecutor(new SpawnCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static ProjectHades getPlugin() {
        return plugin;
    }
}
