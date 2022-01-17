package me.jnk.projecthades;

import org.bukkit.plugin.java.JavaPlugin;

public final class ProjectHades extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new myListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
