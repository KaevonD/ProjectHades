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
import java.util.Iterator;
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
                // If player does not specify what party command to use
                pCaller.sendMessage("/party help for a list of party commands.");
            }

            else if(args[0].equals("invite")) { // Party invite commands
                if(args.length == 1) {
                    // If player does not specify a player to invite
                    pCaller.sendMessage("Please specify a player.");
                }
                else if(!callerInfo.getPartyLeader() && callerInfo.getInParty()) {
                    // If the player inviting is not the party leader
                    pCaller.sendMessage("Only the party leader can invite other players.");
                }
                else if(args[1].equals(pCaller.getName())) {
                    // If the player tries to invite themselves to a party
                    pCaller.sendMessage("You can not add yourself to a party.");
                }
                else if(args.length > 2) {
                    // If the player specifies more than one player
                    pCaller.sendMessage("Please only specify one player to invite.");
                }
                else{
                    // When the player properly uses the command, specifying one player to invite
                    if(isOnline(args[1])) {
                        // If the player invited is online
                        PlayerInfo invited = listen.getPlayer(args[1]);

                        if(invited.getInvites().contains(callerInfo)) {
                            // If the player invited has already been invited
                            pCaller.sendMessage("You have already invited this player.");
                            return true;
                        }

                        // Send invitation to the player invited
                        invited.addInvite(callerInfo);
                        invited.getPlayer().sendMessage(pCaller.getName() + " has invited you to their party.");

                        // Notify the player that they successfully invited the other player
                        pCaller.sendMessage("Invited " + args[1] + " to party.");

                        return true;
                    }
                    // If the player invited is not online
                    pCaller.sendMessage("That player is not online.");
                    return true;
                }
            }

            else if(args[0].equals("kick") || args[0].equals("remove")) {   // Party kick/remove commands
                if(args.length == 1) {
                    // If the player does not specify who to kick
                    pCaller.sendMessage("Please specify a player.");
                }
                else if(args.length > 2) {
                    // If the player specifies more than one player to kick
                    pCaller.sendMessage("Please only specify one player to remove from your party.");
                }
                else if(!callerInfo.getInParty()){
                    // If the player tries to kick someone while not in a party
                    pCaller.sendMessage("You are not in a party.");
                }
                else if(!callerInfo.getPartyLeader()) {
                    // If the player tries to kick someone when they are not the leader of their party
                    pCaller.sendMessage("Only the party leader may kick players from the party.");
                }
                else if(args[1].equals(pCaller.getName())) {
                    // If the player tries to kick themselves from the party
                    pCaller.sendMessage("You can not kick yourself. Do /party leave.");
                }
                else {
                    //If the player correctly specifies one player to kick from the party that isn't themselves while they are the leader

                    PlayerInfo playerKicked = listen.getPlayer(args[1]);

                    if(!callerInfo.getPartyMembers().contains(playerKicked) || playerKicked == null) {
                        // If the player they are trying to kick is not in their party
                        pCaller.sendMessage("This player is not in your party.");
                        return true;
                    }

                    // If the player specified is in the party then remove them from the party list
                    callerInfo.removePartyMember(playerKicked);
                    playerKicked.setInParty(false);
                    playerKicked.setPartyMembers(null);
                    playerKicked.getPlayer().sendMessage("You were kicked from the party.");

                    for(PlayerInfo partyMember : callerInfo.getPartyMembers()) {
                        // Notify everyone remaining in the party that the player was kicked
                        partyMember.getPlayer().sendMessage(playerKicked.getName() + " was kicked from party.");
                    }

                }
            }

            else if(args[0].equals("list")) {   // List out the player's party members
                if(callerInfo.getInParty()) {
                    // If the player is in a party
                    pCaller.sendMessage("Party Members:");
                    for(PlayerInfo list : callerInfo.getPartyMembers()) {
                        pCaller.sendMessage(list.getName());
                    }
                }
                else{
                    // If the player is not in a party
                    pCaller.sendMessage("You are not in a party.");
                }
            }

            else if(args[0].equals("accept") || args[0].equals("join")) {   // Party accept/join commands
                if(args.length == 1) {
                    // If the player did not specify who's invite to accept
                    pCaller.sendMessage("Please specify which players invite you want to accept.");
                }
                else if(args.length > 2) {
                    // If the player specified multiple people's invite to accept
                    pCaller.sendMessage("Please only specify the player who's invite you want to accept.");
                }
                else if(callerInfo.getInParty()) {
                    pCaller.sendMessage("You are already in a party. Leave a party with /party leave to accept another parties invite.");
                    return true;
                }
                else {
                    // If the player properly specified one player's invite to accept
                    if(isOnline(args[1])) {
                        // If the player who invited them is online
                        PlayerInfo inviter = listen.getPlayer(args[1]);

                        for(PlayerInfo p : callerInfo.getInvites()) {
                            // Search through the list of invitations to see if the player specified did invite them

                            if(p.equals(inviter)) {
                                // If the player specified did invite them
                                callerInfo.setInParty(true);        // Set the player to be in a party
                                callerInfo.removeInvite(inviter);   // Remove the invitation

                                if(inviter.getInParty()) {
                                    // If the inviter is already in a party, add player to that party
                                    for(PlayerInfo pMember : inviter.getPartyMembers()) {
                                        pMember.getPlayer().sendMessage(pCaller.getName() + " joined your party.");
                                    }
                                }
                                else {
                                    // If the inviter is not yet in a party, make the inviter the leader
                                    inviter.setPartyLeader(true);
                                    inviter.setInParty(true);

                                    // Create a new party
                                    List<PlayerInfo> temp = new ArrayList<>();
                                    inviter.setPartyMembers(temp);

                                    // Add the inviter to the party
                                    inviter.addPartyMember(inviter);

                                    inviter.getPlayer().sendMessage(pCaller.getName() + " joined your party.");

                                }

                                // Add the player who accepted the invite to the party
                                inviter.addPartyMember(callerInfo);
                                callerInfo.setPartyMembers(inviter.getPartyMembers());
                                pCaller.sendMessage("Joined " + inviter.getName() + "'s party.");
                                return true;
                            }
                        }
                        // If the player specified did not invite the player
                        pCaller.sendMessage("That player did not invite you.");

                    }
                    else {
                        // If the player specified is not online
                        pCaller.sendMessage("That player is not online.");
                    }
                }
            }
            else if(args[0].equals("leave")) {  // Party leave commands

                if(callerInfo.getInParty()) {
                    // If the player is in a party
                    if(callerInfo.getPartyLeader()) {
                        // If the player that left is the party leader
                        if(callerInfo.getPartyMembers().size() > 1) {   // GET RID OF THIS WHEN YOU MAKE IT SO PARTIES DISBAND AT ONE PLAYER
                            // If there are more people in the party, make the next player in the party the party leader
                            PlayerInfo newLeader = callerInfo.getPartyMembers().get(1);
                            newLeader.setPartyLeader(true);
                            newLeader.getPlayer().sendMessage("You are now the leader of the party");
                        }
                        callerInfo.setPartyLeader(false);
                    }

                    for (PlayerInfo pMember : callerInfo.getPartyMembers()) {
                        // Notify everyone in the party that the player has left the party
                        if (!pMember.equals(callerInfo)) {
                            pMember.getPlayer().sendMessage(callerInfo.getName() + " has left the party");
                        }
                    }

                    // Remove player from party
                    callerInfo.removePartyMember(callerInfo);
                    callerInfo.setPartyMembers(null);
                    callerInfo.setInParty(false);

                    pCaller.sendMessage("You have left the party.");

                }
                else{
                    // If player is not in a party
                    pCaller.sendMessage("You are not in a party.");
                }
            }
            else {
                // If party command specified does not match any of the commands
                pCaller.sendMessage("Unknown command. Do /party help for a list of party commands.");
            }
        }
        return true;
    }
}
