package com.Taras.HTTPPost;

import de.jeff_media.customblockdata.CustomBlockData;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

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

                PersistentDataContainer data = new CustomBlockData(block, HTTPPost.getInstance());

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