package me.rpochyly.thehungygames;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Commands implements CommandExecutor {
    private final TheHungyGames plugin;

    public Commands(TheHungyGames plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(args.length == 0) {
            sender.sendMessage(ChatColor.GOLD + "The Hungy Games" + ChatColor.AQUA + " - " + ChatColor.RED + "BETA VERSION");
            return true;
        } else if(args[0].equalsIgnoreCase("signup")) {
            sender.sendMessage("Pretend Signup");
            return true;
        } else if(args[0].equalsIgnoreCase("manage")) {
            if(args.length > 1) {
                sender.sendMessage("Manage mode");
                return true;
            } else {
                sender.sendMessage("Not enough arguments!");
                return true;
            }
        } else if(args[0].equalsIgnoreCase("info")) {
            if(args.length < 2) {
                sender.sendMessage("Game status");
            } else if(args.length == 3) {
                sender.sendMessage("Game info about " + args[2]);
            }
        }



        return false;
    }
}
