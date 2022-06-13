package me.jnk.projecthades.gods;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class Zeus implements God {
    @Override
    public ItemStack getGod() {
        ItemStack item = new ItemStack(Material.YELLOW_DYE);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Zeus Blessing");
        List<String> lore = Arrays.asList("Blessings granted by Zeus,", "the god of the sky");
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        return item;
    }

    @Override
    public List<ItemStack> getBlessings() {
        return null;
    }
}
