package com.Taras.HTTPPost;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Switch;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class LeverListener implements Listener {
    @EventHandler
    public void onPull(PlayerInteractEvent event) {
        if (event.getClickedBlock().getType() == Material.LEVER) {
            Block block = event.getClickedBlock();
            getServer().getConsoleSender().sendMessage(ChatColor.GOLD + block.getBlockData().getAsString());
            Switch lever = (Switch) block.getBlockData();
            if (!lever.isPowered()) {
                lightsOn();
                getServer().getConsoleSender().sendMessage(ChatColor.BLUE + "HTTPPost: Command sent!");
            } else {
                lightsOff();
                getServer().getConsoleSender().sendMessage(ChatColor.BLUE + "HTTPPost: Command sent!");
            }
        }
    }
}