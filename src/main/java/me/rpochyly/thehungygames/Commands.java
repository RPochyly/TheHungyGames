package me.rpochyly.thehungygames;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Commands implements CommandExecutor {
    private final TheHungyGames plugin;
    private ContestantListClass contestantList;

    public Commands(TheHungyGames plugin) {
        this.plugin = plugin;
        this.contestantList = new ContestantListClass();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Contestant currentContestant = contestantList.getByName(sender.getName());
        Contestant otherContestant;

        if (args.length == 0) {
            sender.sendMessage(
                    ChatColor.GOLD + "The Hungy Games" + ChatColor.AQUA + " - " + ChatColor.RED + "BETA VERSION 0.1");
            return true;
        } else if (args[0].equalsIgnoreCase("signup")) {
            if (currentContestant == null) {
                contestantList.addContestantList(sender.getName(), 3, 0);
                currentContestant = contestantList.getByName(sender.getName());
                sender.sendMessage(ChatColor.GOLD + "[THG] " + ChatColor.GREEN + "Successfuly registered as " + ChatColor.BOLD + currentContestant.name);
            } else {
                sender.sendMessage(ChatColor.GOLD + "[THG] " + ChatColor.YELLOW + ChatColor.ITALIC + "You are already registered. Please unregister using /thg exit");
            }
        } else if (args[0].equalsIgnoreCase("manage")) {
            if (args.length > 1) {
                sender.sendMessage(ChatColor.GOLD + "[THG] " + ChatColor.YELLOW + ChatColor.ITALIC + "TODO: Manage mode");
            } else {
                sender.sendMessage(ChatColor.GOLD + "[THG] " + ChatColor.YELLOW + ChatColor.ITALIC + "Not enough arguments!");
            }
        } else if (args[0].equalsIgnoreCase("info")) {
            if (args.length < 2) {
                sender.sendMessage(ChatColor.GOLD + "[THG] " + ChatColor.YELLOW + ChatColor.ITALIC + "TODO: Game status");
            } else if (args.length == 2) {
                otherContestant = contestantList.getByName(args[1].toString());
                if (otherContestant == null) {
                    sender.sendMessage(ChatColor.GOLD + "[THG] " + ChatColor.YELLOW + ChatColor.ITALIC + "This player isn't a contestant or you mistyped their name.");
                } else {
                    sender.sendMessage(ChatColor.GOLD + "[THG] " + ChatColor.RESET + "Info about another contestant:");
                    sender.sendMessage("Name:    " + otherContestant.name);
                    sender.sendMessage("Points:  " + String.valueOf(otherContestant.points));
                    if (otherContestant.lifes == 3) { 
                        sender.sendMessage("Lifes:   " + ChatColor.GREEN + String.valueOf(otherContestant.lifes));
                    } else if (otherContestant.lifes == 2) {
                        sender.sendMessage("Lifes:   " + ChatColor.YELLOW + String.valueOf(otherContestant.lifes));
                    } else if (otherContestant.lifes == 1) {
                        sender.sendMessage("Lifes:   " + ChatColor.RED + String.valueOf(otherContestant.lifes));
                    } else {
                        sender.sendMessage("Lifes:   " + ChatColor.DARK_RED + "Dead");
                    }
                }
            }
        } else if (args[0].equalsIgnoreCase("exit")) {
            if (currentContestant == null) {
                sender.sendMessage(ChatColor.GOLD + "[THG] " + ChatColor.YELLOW + ChatColor.ITALIC + "You have to be registered first to unregister.");
            } else {
                if (args.length == 2) {
                    if (args[1].equalsIgnoreCase("confirm")) {
                        currentContestant = contestantList.getByName(sender.getName());
                        contestantList.removeContestantList(currentContestant);
                        sender.sendMessage(ChatColor.GOLD + "[THG] " + ChatColor.GREEN + "Successfully removed " + ChatColor.BOLD + currentContestant.name + ChatColor.RESET + ChatColor.GREEN + " from contestants!");
                    } else {
                        sender.sendMessage(ChatColor.GOLD + "[THG] " + ChatColor.RESET + "Are you sure you want to leave The Hungy Games? Your points and lifes will be removed and you can't join again until the next season of The Hungy Games.");
                        sender.sendMessage(ChatColor.YELLOW + "If you're sure, please use " + ChatColor.RED + ChatColor.BOLD + "/thg exit confirm");
                    }
                } else {
                    sender.sendMessage(ChatColor.GOLD + "[THG] " + ChatColor.RESET + "Are you sure you want to leave The Hungy Games? Your points and lifes will be removed and you can't join again until the next season of The Hungy Games.");
                    sender.sendMessage(ChatColor.YELLOW + "If you're sure, please use " + ChatColor.RED + ChatColor.BOLD + " /thg exit confirm");
                }
            }
        }
        return false;
    }
}
