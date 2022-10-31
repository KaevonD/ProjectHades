package me.jnk.projecthades;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.math.transform.AffineTransform;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.World;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyListener implements Listener {

    final static File dir = new File(Bukkit.getServer().getPluginManager().getPlugin("ProjectHades")
            .getDataFolder().getAbsolutePath() + "/schematics");
    File[] files = dir.listFiles();
    static List<PlayerInfo> onlinePlayers = new ArrayList<>();

    public static PlayerInfo getPlayer(String name) {
        for(PlayerInfo pInfo : onlinePlayers) {
            if(pInfo.getName().equals(name)) {
                return pInfo;
            }
        }
        return null;
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        boolean hasJoined = false;



        for(PlayerInfo p : onlinePlayers) {
            if(p.getId().equals(e.getPlayer().getUniqueId())) {
                hasJoined = true;
                break;
            }
        }
        if(!hasJoined) {
            onlinePlayers.add(new PlayerInfo(e.getPlayer().getName(), e.getPlayer().getUniqueId()));
        }

    }

//    @EventHandler
//    public void buttonPress(PlayerInteractEvent e) throws IOException {
//        if(e.getClickedBlock().getType() != Material.STONE_BUTTON || e.getAction() != Action.RIGHT_CLICK_BLOCK) {
//            return;
//        }
//
//        Block block = e.getClickedBlock();
//
//        Location location = e.getClickedBlock().getLocation();
//        Random rand = new Random();
//        File file = files[rand.nextInt(files.length)];
//        Clipboard clipboard;
//        ClipboardFormat format = ClipboardFormats.findByFile(file);
//
//        try (ClipboardReader reader = format.getReader(new FileInputStream(file))) {
//            clipboard = reader.read();
//            World world = BukkitAdapter.adapt(e.getPlayer().getWorld());
//
//            try (EditSession editSession = WorldEdit.getInstance().newEditSession(world)){
//                ClipboardHolder holder = new ClipboardHolder(clipboard);
//                holder.setTransform(new AffineTransform().rotateY(rotation(block)));
//                Operation operation = holder
//                        .createPaste(editSession)
//                        .to(BlockVector3.at(location.getX(), location.getY()-1, location.getZ()))
//                        .build();
//                Operations.complete(operation);
//            }
//
//        }
//        catch(Exception error) {
//            error.printStackTrace();
//        }
//    }

    public int rotation(Block block) {
        String direction = block.getBlockData().getAsString().substring(40,41);
        switch (direction) {
            case "s":
                return 0;
            case "w":
                return 270;
            case "e":
                return 90;
            default:
                return 180;
        }
    }
}
