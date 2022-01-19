package me.jnk.projecthades.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PartyCommands implements CommandExecutor {

    List<Player> party = new ArrayList<>();



    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {

        if(!(sender instanceof Player)) {   //if command is not issued by a player (eg. console)
            return true;
        }



        //otherwise if command is issued by a player
        Player player = (Player)sender;

        if(cmd.getName().equalsIgnoreCase("party")) {
            if(args.length == 0) {
                sender.sendMessage("/party help for a list of party commands.");
            }
            else if(args[0].equals("invite")) {
                if(args.length == 1) {
                    sender.sendMessage("Please specify a player.");
                }
                else if(args[1].equals(sender.getName())) {
                    sender.sendMessage("You can not add yourself to a party.");
                }
                else{
                    for(Player p : Bukkit.getOnlinePlayers()) {
                        if(p.getName().equals(args[1])) {
                            if(party.contains(p)) {
                                sender.sendMessage("That player is already in your party.");
                            }
                            else{
                                party.add(p);
                                sender.sendMessage("Invited " + p.getName() + " to party.");
                                p.sendMessage(sender.getName() + " has invited you to their party.");
                            }
                        }
                    }
                }

            }
            else if(args[0].equals("kick")) {
                if(args.length == 1) {
                    sender.sendMessage("please specify a player");
                }
                else if(args[1].equals(sender.getName())) {
                    sender.sendMessage("You can not kick yourself. Do /party leave.");
                }
                else{
                    for(Player p : Bukkit.getOnlinePlayers()) {
                        if(p.getName().equals(args[1])) {
                            if(party.contains(p)) {
                                party.remove(p);
                                sender.sendMessage("Removed " + p.getName() + " from party.");
                                p.sendMessage("You have been removed from the party");
                            }
                        }
                    }
                }
            }
            else if(args[0].equals("list")) {
                sender.sendMessage("Party Members:");
                for(Player p : party) {
                    sender.sendMessage(p.getName());
                }
            }
            return true;
        }

        return true;
    }
}
