package me.jnk.projecthades;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.List;

public class MyListener implements Listener {

    List<PlayerInfo> onlinePlayers = new ArrayList<>();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {

        boolean hasJoined = false;

        for(PlayerInfo p : onlinePlayers) {
            if(p.getId().equals(e.getPlayer().getUniqueId())) {
                hasJoined = true;
                break;
            }
        }
        if(!hasJoined) {
            onlinePlayers.add(new PlayerInfo(e.getPlayer().getName(), e.getPlayer().getUniqueId()));
        }
    }

    @EventHandler
    public void onMovement(PlayerMoveEvent e) {

    }
}
