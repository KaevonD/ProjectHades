package me.jnk.projecthades.gods;

import dev.triumphteam.gui.guis.GuiItem;
import org.bukkit.entity.HumanEntity;

public interface God {
    GuiItem getGod();
    void getBlessings(HumanEntity player);
}
