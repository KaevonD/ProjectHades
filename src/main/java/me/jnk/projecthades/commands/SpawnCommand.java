package me.jnk.projecthades.commands;

import me.jnk.projecthades.ProjectHades;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.metadata.FixedMetadataValue;
import org.jetbrains.annotations.NotNull;

public class SpawnCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {

        if(cmd.getName().equalsIgnoreCase("spawn")) {
            if(sender instanceof Player) {
                Player p = (Player)sender;
                Zombie z = (Zombie)p.getWorld().spawnEntity(p.getLocation(), EntityType.ZOMBIE);
                z.setMetadata("type", new FixedMetadataValue(ProjectHades.getPlugin(), "poop"));

            }
        }

        return false;
    }
}
