package me.jnk.projecthades;

import me.jnk.projecthades.commands.PartyCommands;
import org.bukkit.plugin.java.JavaPlugin;

public final class ProjectHades extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new MyListener(), this);
        getServer().getPluginManager().registerEvents(new GuiListener(), this);

        getCommand("party").setExecutor(new PartyCommands());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
