package me.jnk.projecthades.commands;

import me.jnk.projecthades.MyListener;
import me.jnk.projecthades.PlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PartyCommands implements CommandExecutor {

    boolean isOnline(String name) {
        for(Player p : Bukkit.getOnlinePlayers()) {
            if(p.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {

        if(!(sender instanceof Player)) {   //if command is not issued by a player (eg. console)
            return true;
        }

        //otherwise if command is issued by a player
        MyListener listen = new MyListener();

        Player pCaller = (Player)sender;
        PlayerInfo callerInfo = listen.getPlayer(pCaller.getName());


        if(cmd.getName().equalsIgnoreCase("party")) {
            if(args.length == 0) {
                pCaller.sendMessage("/party help for a list of party commands.");
            }
            else if(args[0].equals("invite")) { // party invite commands
                if(args.length == 1) {
                    pCaller.sendMessage("Please specify a player.");
                }
                else if(args[1].equals(pCaller.getName())) {
                    pCaller.sendMessage("You can not add yourself to a party.");
                }
                else if(args.length > 2) {
                    pCaller.sendMessage("Please only specify one player to invite.");
                }
                else{
                    if(isOnline(args[1])) {
                        PlayerInfo invited = listen.getPlayer(args[1]);

                        invited.addInvite(callerInfo);
                        pCaller.sendMessage("Invited " + args[1] + " to party.");
                        invited.getPlayer().sendMessage(pCaller.getName() + " has invited you to their party.");

                        return true;
                    }
                    pCaller.sendMessage("That player is not online.");
                }
            }
            else if(args[0].equals("kick") || args[0].equals("remove")) {
                if(args.length == 1) {
                    pCaller.sendMessage("please specify a player.");
                }
                else if(args.length > 2) {
                    pCaller.sendMessage("Please only specify one player to remove from your party.");
                }
                else if(!callerInfo.getInParty()){
                    pCaller.sendMessage("You are not in a party.");
                }
                else if(args[1].equals(pCaller.getName())) {
                    pCaller.sendMessage("You can not kick yourself. Do /party leave.");
                }
//                else{
////                    for(PlayerInfo p : callerInfo.getPartyMembers()) {
////                        if(p.getName().equals(args[1])) {
////                            p.removePartyMember();
////                        }
////                    }
//                }
            }
            else if(args[0].equals("list")) {
                if(callerInfo.getInParty()) {
                    pCaller.sendMessage("Party Members:");
                    for(PlayerInfo list : callerInfo.getPartyMembers()) {
                        pCaller.sendMessage(list.getName());
                    }
                }


            }
            else if(args[0].equals("accept")) {
                if(args.length == 1) {
                    pCaller.sendMessage("Please specify which players invite you want to accept.");
                }
                else if(args.length > 2) {
                    pCaller.sendMessage("Please only specify the player who's invite you want to accept.");
                }
                else {

                    if(isOnline(args[1])) {
                        if(callerInfo.getInParty()) {
                            pCaller.sendMessage("You are already in a party. Leave a party with /party leave to accept another parties invite.");
                            return true;
                        }
                        PlayerInfo inviter = listen.getPlayer(args[1]);

                        for(PlayerInfo p : callerInfo.getInvites()) {

                            if(p.getId().equals(inviter.getId())){
                                callerInfo.setInParty(true);
                                callerInfo.removeInvite(inviter);
                                callerInfo.addPartyMember(inviter);
                                callerInfo.addPartyMember(callerInfo);

                                if(inviter.getInParty()) {
                                    for(PlayerInfo pMember : inviter.getPartyMembers()) {
                                        if(!pMember.getId().equals(callerInfo.getId())){
                                            callerInfo.addPartyMember(pMember);
                                            pMember.addPartyMember(callerInfo);
                                        }
                                    }
                                }
                                else {
                                    inviter.setInParty(true);
                                    inviter.addPartyMember(inviter);
                                    inviter.addPartyMember(callerInfo);
                                }
                            }
                        }

                    }
                    else {
                        pCaller.sendMessage("That player is ");
                    }
                }
            }
        }
        return true;
    }
}
