package me.jnk.projecthades.commands;

import me.jnk.projecthades.MyListener;
import me.jnk.projecthades.PlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

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
                else if(!callerInfo.getPartyLeader() && callerInfo.getInParty()) {
                    pCaller.sendMessage("Only the party leader can invite other players.");
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

                        if(invited.getInvites().contains(callerInfo)) {
                            pCaller.sendMessage("You have already invited this player.");
                            return true;
                        }

                        invited.addInvite(callerInfo);
                        pCaller.sendMessage("Invited " + args[1] + " to party.");
                        invited.getPlayer().sendMessage(pCaller.getName() + " has invited you to their party.");

                        return true;
                    }
                    pCaller.sendMessage("That player is not online.");
                    return true;
                }
            }

            else if(args[0].equals("kick") || args[0].equals("remove")) {
                if(args.length == 1) {
                    pCaller.sendMessage("Please specify a player.");
                }
                else if(args.length > 2) {
                    pCaller.sendMessage("Please only specify one player to remove from your party.");
                }
                else if(!callerInfo.getInParty()){
                    pCaller.sendMessage("You are not in a party.");
                }
                else if(!callerInfo.getPartyLeader()) {
                    pCaller.sendMessage("Only the party leader may kick players from the party.");
                }
                else if(args[1].equals(pCaller.getName())) {
                    pCaller.sendMessage("You can not kick yourself. Do /party leave.");
                }
                else {
                    PlayerInfo playerKicked = listen.getPlayer(args[1]);

                    if(!callerInfo.getPartyMembers().contains(playerKicked) || playerKicked == null) {
                        pCaller.sendMessage("This player is not in your party.");
                        return true;
                    }

                    callerInfo.removePartyMember(playerKicked);
                    playerKicked.setInParty(false);
                    playerKicked.setPartyMembers(null);
                    playerKicked.getPlayer().sendMessage("You were kicked from the party.");

                    for(PlayerInfo partyMember : callerInfo.getPartyMembers()) {
                        partyMember.getPlayer().sendMessage(playerKicked.getName() + " was kicked from party.");
                    }

                }
            }

            else if(args[0].equals("list")) {
                if(callerInfo.getInParty()) {
                    pCaller.sendMessage("Party Members:");
                    for(PlayerInfo list : callerInfo.getPartyMembers()) {
                        pCaller.sendMessage(list.getName());
                    }
                }
                else{
                    pCaller.sendMessage("You are not in a party.");
                }
            }

            else if(args[0].equals("accept") || args[0].equals("join")) {
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

                            if(p.equals(inviter)){
                                callerInfo.setInParty(true);
                                callerInfo.removeInvite(inviter);

                                if(inviter.getInParty()) {
                                    System.out.println("ooga booga");
                                    for(PlayerInfo pMember : inviter.getPartyMembers()) {
                                        pMember.getPlayer().sendMessage(pCaller.getName() + " joined your party.");
                                    }
                                }
                                else{
                                    inviter.setPartyLeader(true);
                                    inviter.setInParty(true);
                                    inviter.addPartyMember(inviter);
                                    inviter.getPlayer().sendMessage(pCaller.getName() + " joined your party.");
                                }


                                inviter.addPartyMember(callerInfo);
                                callerInfo.setPartyMembers(inviter.getPartyMembers());
                                pCaller.sendMessage("Joined " + inviter.getName() + "'s party.");
                                return true;
                            }
                        }
                        pCaller.sendMessage("That player did not invite you.");

                    }
                    else {
                        pCaller.sendMessage("That player is not online.");
                    }
                }
            }
            else if(args[0].equals("leave")) {

                if(callerInfo.getInParty()) {

                    if(callerInfo.getPartyLeader()) {   // If the player that left is the party leader
                        if(callerInfo.getPartyMembers().size() > 1) {
                            PlayerInfo newLeader = callerInfo.getPartyMembers().get(1);
                            newLeader.setPartyLeader(true);
                            newLeader.getPlayer().sendMessage("You are now the leader of the party");
                        }
                    }

                    for (PlayerInfo pMember : callerInfo.getPartyMembers()) {
                        if (!pMember.equals(callerInfo)) {
                            pMember.getPlayer().sendMessage(callerInfo.getName() + " has left the party");
                        }
                    }
                    callerInfo.removePartyMember(callerInfo);
                    callerInfo.setPartyMembers(null);
                    callerInfo.setInParty(false);

                    pCaller.sendMessage("You have left the party.");

                }
                else{
                    pCaller.sendMessage("You are not in a party.");
                }
            }
            else {
                pCaller.sendMessage("Unknown command. Do /party help for a list of party commands.");
            }
        }
        return true;
    }
}
