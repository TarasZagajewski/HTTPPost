package com.Taras.HTTPPost;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Switch;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class LeverListener implements Listener {

    private static final HTTPPost main = HTTPPost.getInstance();

    @EventHandler
    public void onPull(PlayerInteractEvent event) {
        if(event.getClickedBlock().getType() == Material.LEVER){
            Block block = event.getClickedBlock();
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GOLD + block.getBlockData().getAsString());
            Switch lever = (Switch) block.getBlockData();
            if (!lever.isPowered()) {
                main.lightsOn();
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.BLUE + "HTTPPost: Lights On!");
            } else {
                main.lightsOff();
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.BLUE + "HTTPPost: Lights Off!");
            }
        }
    }
}