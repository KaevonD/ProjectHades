package me.jnk.projecthades;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import me.jnk.projecthades.gods.*;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.player.PlayerInteractEvent;
import java.util.*;
import java.util.ArrayList;

public class GuiListener implements Listener {

    ArrayList<God> gods = new ArrayList<>();

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

        Gui blessings = Gui.gui()
                .title(Component.text("Choose a god to receive a blessing from"))
                .rows(3)
                .create();


        Collections.shuffle(gods);

        blessings.setItem(11, gods.get(0).getGod());
        blessings.setItem(13, gods.get(1).getGod());
        blessings.setItem(15, gods.get(2).getGod());

        blessings.open(e.getPlayer());

    }

}