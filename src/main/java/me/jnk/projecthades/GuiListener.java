package me.jnk.projecthades;

import me.jnk.projecthades.gods.*;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.ipvp.canvas.Menu;
import org.ipvp.canvas.type.ChestMenu;

import java.util.*;
import java.util.ArrayList;

public class GuiListener implements Listener {

    ArrayList<God> gods = new ArrayList<God>();

    public GuiListener() {
        gods.add(new Ares());
        gods.add(new Artemis());
        gods.add(new Athena());
        gods.add(new Aphrodite());
        gods.add(new Demeter());
        gods.add(new Hermes());
        gods.add(new Poseidon());
        gods.add(new Zeus());
    }

    @EventHandler
    public void clickButton(PlayerInteractEvent e) {
        if(e.getClickedBlock().getType() != Material.STONE_BUTTON || e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Menu blessings = ChestMenu.builder(3).title("Choose a blessing").build();

        Collections.shuffle(gods);

        blessings.getSlot(11).setItemTemplate(p -> {
            return gods.get(0).getGod();
        });
        blessings.getSlot(13).setItemTemplate(p -> {
            return gods.get(1).getGod();
        });
        blessings.getSlot(15).setItemTemplate(p -> {
            return gods.get(2).getGod();
        });

        blessings.open(e.getPlayer());

    }

}