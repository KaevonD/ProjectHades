package me.jnk.projecthades.gods;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Ares implements God{

    List<GuiItem> blessings = new ArrayList<>();

    GuiItem blessing1;
    GuiItem blessing2;
    GuiItem blessing3;

    public Ares() {
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
                .name(Component.text("Blessing1"))
                .lore(blessing1lore)
                .asGuiItem();
        blessing3 = ItemBuilder.from(Material.BOOK)
                .name(Component.text("Blessing1"))
                .lore(blessing1lore)
                .asGuiItem();
        blessings.add(blessing1);
        blessings.add(blessing2);
        blessings.add(blessing2);

    }


    @Override
    public GuiItem getGod() {
        List<Component> lore = Arrays.asList(
                Component.text("Blessings granted by Ares,"),
                Component.text("the god of war"));

        return ItemBuilder.from(Material.GOLDEN_SWORD)
                .name(Component.text(ChatColor.RED + "" + ChatColor.BOLD + "Ares Blessing"))
                .lore(lore)
                .asGuiItem();
    }

    @Override
    public List<GuiItem> getBlessings() {
        List<GuiItem> randBlessings = new ArrayList<>();
        Collections.shuffle(blessings);

        randBlessings.add(blessings.get(0));
        randBlessings.add(blessings.get(1));
        randBlessings.add(blessings.get(2));

        return randBlessings;
    }
}
