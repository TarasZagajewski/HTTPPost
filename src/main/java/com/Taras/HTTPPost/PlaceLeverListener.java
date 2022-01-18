package com.Taras.HTTPPost;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlaceLeverListener implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (event.getBlock().getType() == Material.LEVER) {
            Player p = event.getPlayer();
            p.sendMessage("Lever Placed!");
        } else {
            return;
        }


    }
}