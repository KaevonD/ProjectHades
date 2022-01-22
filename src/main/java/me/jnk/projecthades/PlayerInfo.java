package me.jnk.projecthades;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerInfo {

    private String name;            // Player's name
    private final UUID id;          // Player's UUID

    private Boolean inParty;        // If player is in a party or not
    private Boolean inGame;         // If player is in a game or not
    private Boolean isPartyLeader;  // If player is the party leader or not

    private List<PlayerInfo> invites = new ArrayList<>();       // List of players that invited them to a party
    private List<PlayerInfo> partyMembers = new ArrayList<>();  // List of party members, empty if player is not in party

    public Boolean getPartyLeader() {
        return isPartyLeader;
    }

    public void setPartyLeader(Boolean partyLeader) {
        isPartyLeader = partyLeader;
    }

    public PlayerInfo(String name, UUID id) {
        this.name = name;
        this.id = id;
        this.inParty = false;
        this.inGame = false;
        this.isPartyLeader = false;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public UUID getId() {
        return id;
    }

    public Boolean getInParty() {
        return inParty;
    }

    public void setInParty(Boolean inParty) {
        this.inParty = inParty;
    }

    public Boolean getInGame() {
        return inGame;
    }

    public void setInGame(Boolean inGame) {
        this.inGame = inGame;
    }

    public List<PlayerInfo> getInvites() {
        return invites;
    }

    public void addInvite(PlayerInfo player) {
        this.invites.add(player);
    }

    public void removeInvite(PlayerInfo player) {
        this.invites.remove(player);
    }

    public List<PlayerInfo> getPartyMembers() {
        return partyMembers;
    }

    public void setPartyMembers(List<PlayerInfo> partyMembers) {
        this.partyMembers = partyMembers;
    }

    public void addPartyMember(PlayerInfo player) {
        this.partyMembers.add(player);
    }

    public void removePartyMember(PlayerInfo player) {
        this.partyMembers.remove(player);
    }

    public Player getPlayer() {
        for(Player p : Bukkit.getOnlinePlayers()) {
            if(this.getName().equals(p.getName())) {
                return p;
            }
        }
        return null;
    }
}
