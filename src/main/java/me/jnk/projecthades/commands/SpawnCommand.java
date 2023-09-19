package me.jnk.projecthades.commands;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;


public class SpawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {

        if(!(sender instanceof Player)) {
            return true;
        }

        if(cmd.getName().equalsIgnoreCase("spawn")) {
            Player p = (Player)sender;

            if(args.length == 0) {
                // If player does not specify what enemy to spawn
                p.sendMessage("Please specify what enemy to spawn.");
            }

            JSONParser parser = new JSONParser();
            try {

                String file = Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("ProjectHades"))
                        .getDataFolder().getAbsolutePath() + "/mobs/" + args[0] + ".json";

                Object obj = parser.parse(new FileReader(file));
                JSONObject jsonObject = (JSONObject) obj;

//                create entity
                Entity ent = p.getWorld().spawnEntity(p.getLocation(), EntityType.valueOf((String) jsonObject.get("entity")));

//                get entity stats from json file
                double damage = ((Number)jsonObject.get("damage")).doubleValue();
                double health = ((Number)jsonObject.get("health")).doubleValue();

//                set stats
                ((LivingEntity)ent).getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(damage);
                ((LivingEntity)ent).getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
                ((LivingEntity)ent).setHealth(health);


            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }

        }

        return false;
    }
}
