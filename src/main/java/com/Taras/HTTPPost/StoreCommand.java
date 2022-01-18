package com.Taras.HTTPPost;

public class StoreCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        System.out.println("Executing Command!");

        if (sender instanceof Player) {
            System.out.println("Sender is a player!");
            Player p = (Player) sender;

            if (args.length > 0) {

                System.out.println("Args length > 0!");

                StringBuilder Command = new StringBuilder();

                for (String arg : args) {
                    Command.append(arg + "");
                }

                Block block = p.getTargetBlock(null, 200);
                if (block.getType() == Material.LEVER) {
                    PersistentDataContainer data = new CustomBlockData(block, plugin);

                    if (data.has(new NamespacedKey(HTTPPost.getPlugin(), "Command"), PersistentDataType.STRING)) {
                        p.sendMessage(ChatColor.GRAY + "There is already a command stored in this block!");
                        p.sendMessage(ChatColor.GRAY + "Command: " + ChatColor.GREEN + data.get(new NamespacedKey(HTTPPost.getPlugin(), "Command"), PersistentDataType.STRING)); //Return current command
                    } else {
                        data.set(new NamespacedKey(HTTPPost.getPlugin(), "Command"), PersistentDataType.STRING, Command.toString());
                        p.sendMessage(ChatColor.GREEN + "Command Stored!");
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "You must be looking at a lever!");
                }

            } else {
                p.sendMessage(ChatColor.RED + "You must include a command!");
            }


            return true;
        }

    }