package me.jnk.projecthades.gods;

import dev.triumphteam.gui.guis.GuiItem;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface God {
    GuiItem getGod();
    List<GuiItem> getBlessings();
}
