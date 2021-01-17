package com.kms1212.mcplugin.countryregions;

import com.destroystokyo.paper.block.TargetBlockInfo;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class UserAction implements Listener {
    CountryRegions plugin;
    Location pos1, pos2;
    boolean wandStatus = false;


    public Location getPos1() {
        return pos1;
    }

    public Location getPos2() {
        return pos2;
    }

    public boolean getWandStatus() {
        return wandStatus;
    }

    public void setWandStatus(boolean wandStatus) {
        this.wandStatus = wandStatus;
    }

    public UserAction(CountryRegions plugin) {
        this.plugin = plugin;
        pos1 = new Location(null, 0, 0, 0);
        pos2 = new Location(null, 0, 0, 0);
    }

    @EventHandler
    public void onPlayerClick(PlayerInteractEvent event) {
        if (wandStatus) {
            Player p = event.getPlayer();
            if (    (event.getAction() == Action.LEFT_CLICK_BLOCK) &&
                    (p.getInventory().getItemInMainHand().getType() == Material.STONE_SHOVEL))
            {
                if ((pos1.getBlockX() != getLookingBlock(p).getLocation().getBlockY()) &&
                        (pos1.getBlockY() != getLookingBlock(p).getLocation().getBlockY()) &&
                        (pos1.getBlockZ() != getLookingBlock(p).getLocation().getBlockZ())) {
                    pos1 = getLookingBlock(p).getLocation();
                    p.sendMessage("[CountryRegions] Set pos1: (" + Integer.toString(pos1.getBlockX()) + ", " +
                            Integer.toString(pos1.getBlockY()) + ", " + Integer.toString(pos1.getBlockZ()) + ")");
                }
                event.setCancelled(true);
            }
            if (    (event.getAction() == Action.RIGHT_CLICK_BLOCK) &&
                    (p.getInventory().getItemInMainHand().getType() == Material.STONE_SHOVEL))
            {
                if ((pos2.getBlockX() != getLookingBlock(p).getLocation().getBlockY()) &&
                        (pos2.getBlockY() != getLookingBlock(p).getLocation().getBlockY()) &&
                        (pos2.getBlockZ() != getLookingBlock(p).getLocation().getBlockZ())) {
                    pos2 = getLookingBlock(p).getLocation();
                    p.sendMessage("[CountryRegions] Set pos2: (" + Integer.toString(pos2.getBlockX()) + ", " +
                            Integer.toString(pos2.getBlockY()) + ", " + Integer.toString(pos2.getBlockZ()) + ")");
                }
                event.setCancelled(true);
            }
        }
    }

    public Block getLookingBlock(Player p) {
        return p.getTargetBlock(50, TargetBlockInfo.FluidMode.NEVER);
    }
}
