package me.jnk.projecthades.gods;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Athena implements God {

    List<GuiItem> blessings = new ArrayList<>();
    Gui chooseBlessing;

    GuiItem blessing1;
    GuiItem blessing2;
    GuiItem blessing3;

    public Athena() {
        List<Component> blessing1lore = Arrays.asList(
                Component.text("Does this"),
                Component.text("and that"));
        List<Component> blessing2lore = Arrays.asList(
                Component.text("Does this"),
                Component.text("and that"));
        List<Component> blessing3lore = Arrays.asList(
                Component.text("Does this"),
                Component.text("and that"));

        blessing1 = ItemBuilder.from(Material.BOOK)
                .name(Component.text("Blessing1"))
                .lore(blessing1lore)
                .asGuiItem();
        blessing2 = ItemBuilder.from(Material.BOOK)
                .name(Component.text("Blessing2"))
                .lore(blessing2lore)
                .asGuiItem();
        blessing3 = ItemBuilder.from(Material.BOOK)
                .name(Component.text("Blessing3"))
                .lore(blessing3lore)
                .asGuiItem();

        blessings.add(blessing1);
        blessings.add(blessing2);
        blessings.add(blessing3);

    }

    @Override
    public GuiItem getGod() {
        List<Component> lore = Arrays.asList(
                Component.text("Blessings granted by Athena,"),
                Component.text("the goddess of wisdom"));

        return ItemBuilder.from(Material.SHIELD)
                .name(Component.text(ChatColor.YELLOW + "" + ChatColor.BOLD + "Athena Blessing"))
                .lore(lore)
                .asGuiItem(event -> {
                    getBlessings(event.getWhoClicked());
                });
    }

    @Override
    public void getBlessings(HumanEntity player) {
        Collections.shuffle(blessings);

        chooseBlessing = Gui.gui()
                .title(Component.text("Choose a blessing"))
                .rows(3)
                .create();

        chooseBlessing.setItem(11, blessings.get(0));
        chooseBlessing.setItem(13, blessings.get(1));
        chooseBlessing.setItem(15, blessings.get(2));

        chooseBlessing.open(player);
    }
}
