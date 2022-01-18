package com.Taras.HTTPPost;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Switch;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import de.jeff_media.customblockdata.CustomBlockData;

public class HTTPPost extends JavaPlugin implements Listener {

    private static HTTPPost plugin;

    @Override
    public void onEnable() {
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "HTTPPost: Plugin is enabled!");
        lightsOff();
        lightsOn();
        getServer().getPluginManager().registerEvents(new LeverListener(), this);
        getServer().getPluginManager().registerEvents(new PlaceLeverListener(), this);
        plugin = this;

        getCommand("SetLight").setExecutor(new StoreCommand());
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "HTTPPost: Plugin is disabled!");
    }

    public static HTTPPost getPlugin() {
        return plugin;
    }

    public void lightsOff() {
        try {
            URL url = new URL("http://192.168.0.17:3000/assistant");
            URLConnection con = url.openConnection();
            HttpURLConnection http = (HttpURLConnection)con;
            http.setRequestMethod("POST"); // PUT is another valid option
            http.setDoOutput(true);

            byte[] out = "{\"command\":\"turn off the lights\",\"user\":\"Taras Zagajewski\"}".getBytes(StandardCharsets.UTF_8);
            int length = out.length;

            http.setFixedLengthStreamingMode(length);
            http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            http.connect();
            try(OutputStream os = http.getOutputStream()) {
                os.write(out);
            }
            // Do something with http.getInputStream()
        } catch(IOException e) {
            getServer().getConsoleSender().sendMessage(ChatColor.RED + "HTTPPost: Error!");
        }
    }

    public void lightsOn() {
        try {
            URL url = new URL("http://192.168.0.17:3000/assistant");
            URLConnection con = url.openConnection();
            HttpURLConnection http = (HttpURLConnection)con;
            http.setRequestMethod("POST"); // PUT is another valid option
            http.setDoOutput(true);

            byte[] out = "{\"command\":\"turn on the lights\",\"user\":\"Taras Zagajewski\"}".getBytes(StandardCharsets.UTF_8);
            int length = out.length;

            http.setFixedLengthStreamingMode(length);
            http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            http.connect();
            try(OutputStream os = http.getOutputStream()) {
                os.write(out);
            }
            // Do something with http.getInputStream()
        }
        catch(IOException e) {
            getServer().getConsoleSender().sendMessage(ChatColor.RED + "HTTPPost: Error!");
        }
    }

    public void sendCommand() {
        try {
            URL url = new URL("http://192.168.0.101:3000/assistant");
            URLConnection con = url.openConnection();
            HttpURLConnection http = (HttpURLConnection)con;
            http.setRequestMethod("POST"); // PUT is another valid option
            http.setDoOutput(true);

            //String CommandToSend = nameData.get(new NamespacedKey(HTTPPost.getPlugin(), "message"), PersistentDataType.STRING)

            byte[] out = "{\"command\":\"MESSAGE\",\"user\":\"Taras Zagajewski\"}".getBytes(StandardCharsets.UTF_8);
            int length = out.length;

            http.setFixedLengthStreamingMode(length);
            http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            http.connect();
            try(OutputStream os = http.getOutputStream()) {
                os.write(out);
            }
            // Do something with http.getInputStream()
        }
        catch(IOException e) {
            getServer().getConsoleSender().sendMessage(ChatColor.RED + "HTTPPost: Error!");
        }
    }

    public class StoreCommand implements CommandExecutor {

        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

            if (sender instanceof Player) {
                Player p = (Player) sender;

                if (args.length > 0) {

                    StringBuilder Command = new StringBuilder();

                    for (String arg : args) {
                        Command.append(arg + "");
                    }

                    Block block = p.getTargetBlock(null, 200);

                    PersistentDataContainer data = new CustomBlockData(block, plugin);

                    if (data.has(new NamespacedKey(HTTPPost.getPlugin(), "Command"), PersistentDataType.STRING)) {
                        p.sendMessage(ChatColor.GRAY + "There is already a command stored in this block!");
                        p.sendMessage(ChatColor.GRAY + "Command: " + ChatColor.GREEN + data.get(new NamespacedKey(HTTPPost.getPlugin(), "Command"), PersistentDataType.STRING)); //Return current command
                    } else {
                        data.set(new NamespacedKey(HTTPPost.getPlugin(), "Command"), PersistentDataType.STRING, Command.toString());
                        p.sendMessage(ChatColor.GREEN + "Command Stored!");
                    }
                }

            }


            return true;
        }

    }

    public class LeverListener implements Listener {
        @EventHandler
        public void onPull(PlayerInteractEvent event) {
            if(event.getClickedBlock().getType() == Material.LEVER){
                Block block = event.getClickedBlock();
                getServer().getConsoleSender().sendMessage(ChatColor.GOLD + block.getBlockData().getAsString());
                Switch lever = (Switch) block.getBlockData();
                if (!lever.isPowered()) {
                    lightsOn();
                    getServer().getConsoleSender().sendMessage(ChatColor.BLUE + "HTTPPost: Lights On!");
                } else {
                    lightsOff();
                    getServer().getConsoleSender().sendMessage(ChatColor.BLUE + "HTTPPost: Lights Off!");
                }
            }
        }
    }

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




}