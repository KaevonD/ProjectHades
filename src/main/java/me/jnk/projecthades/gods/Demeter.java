package me.jnk.projecthades.gods;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class Demeter implements God {
    @Override
    public ItemStack getGod() {
        ItemStack item = new ItemStack(Material.LEGACY_SNOW_BALL);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Demeter Blessing");
        List<String> lore = Arrays.asList("Blessings granted by Demeter,", "the goddess of harvest");
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        return item;
    }

    @Override
    public List<ItemStack> getBlessings() {
        return null;
    }
}
