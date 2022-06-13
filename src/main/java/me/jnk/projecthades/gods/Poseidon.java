package me.jnk.projecthades.gods;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class Poseidon implements God {
    @Override
    public ItemStack getGod() {
        ItemStack item = new ItemStack(Material.WATER_BUCKET);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(ChatColor.BLUE + "" + ChatColor.BOLD + "Poseidon Blessing");
        List<String> lore = Arrays.asList("Blessings granted by Poseidon,", "the god of the sea");
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        return item;
    }

    @Override
    public List<ItemStack> getBlessings() {
        return null;
    }
}
