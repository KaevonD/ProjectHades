package me.jnk.projecthades;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class myListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        e.getPlayer().sendMessage("Welcome " + e.getPlayer().getName());
    }
}
