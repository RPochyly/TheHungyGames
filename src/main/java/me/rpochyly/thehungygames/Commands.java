package me.rpochyly.thehungygames;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
    private final TheHungyGames plugin;
    public static ContestantListClass contestantList;
    public static Location waitingLocation;

    public Commands(TheHungyGames plugin) {
        this.plugin = plugin;
        Commands.contestantList = TheHungyGames.contestantList;
        Commands.waitingLocation = TheHungyGames.waitingLocation;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Contestant currentContestant = contestantList.getByName(sender.getName());
        Contestant otherContestant;

        if (args.length == 0) {
            sender.sendMessage(
                    ChatColor.GOLD + "The Hungy Games" + ChatColor.AQUA + " - " + ChatColor.RED + "ALPHA VERSION 0.1 (TIMERS AND SETUP)");
            return true;
        } else if (args[0].equalsIgnoreCase("signup")) {
            if (currentContestant == null) {
                contestantList.addContestantList(sender.getName(), 3, 0);
                currentContestant = contestantList.getByName(sender.getName());
                TheHungyGames.fileAccess.savePlayerData();
                sender.sendMessage(ChatColor.GOLD + "[THG] " + ChatColor.GREEN + "Successfuly registered as " + ChatColor.BOLD + currentContestant.name);
            } else {
                sender.sendMessage(ChatColor.GOLD + "[THG] " + ChatColor.YELLOW + ChatColor.ITALIC + "You are already registered. Please unregister using /thg exit");
            }
        } else if (args[0].equalsIgnoreCase("admin")) {
            if (args.length > 1) {
                if (args[1].equalsIgnoreCase("timer")) {
                    if (args[2].equalsIgnoreCase("set")) {
                        if (args.length != 4) {
                            sender.sendMessage(ChatColor.GOLD + "[THG] " + ChatColor.YELLOW + ChatColor.ITALIC + "Wrong syntax!");
                            sender.sendMessage(ChatColor.GOLD + "[THG] " + ChatColor.YELLOW + ChatColor.ITALIC + "Please use: /thg manage timer set [time in seconds].");
                        } else {
                            int seconds;
                            try {
                                seconds = Integer.parseInt(args[3]);
                            } catch (NumberFormatException e) {
                                sender.sendMessage(ChatColor.GOLD + "[THG] " + ChatColor.YELLOW + ChatColor.ITALIC + "The value you entered is not a number!");
                                return false;
                            }
                            Double doubleSeconds = Double.valueOf(seconds);
                            TheHungyGames.eventTHG.Timer(doubleSeconds);
                        }
                    }
                } else if (args[1].equalsIgnoreCase("setwaitspawn")) {
                    Player player = (Player) sender;
                    waitingLocation = player.getLocation();
                    TheHungyGames.fileAccess.getCustomConfig().set("options.waitinglocation", waitingLocation);
                    TheHungyGames.fileAccess.saveAllData();
                    sender.sendMessage(ChatColor.GOLD + "[THG] " + ChatColor.GREEN + "Wait spawn has been set!");
                } else if (args[1].equalsIgnoreCase("tpwaitspawn")) {
                    if (TheHungyGames.waitingLocation != null) {
                        Player player = (Player) sender;
                        player.teleport(TheHungyGames.waitingLocation);
                    } else {
                        sender.sendMessage(ChatColor.GOLD + "[THG] " + ChatColor.YELLOW + ChatColor.ITALIC + "There is no wait spawn location set!");
                    }
                }
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
                if(currentContestant.dead == true) {
                    sender.sendMessage(ChatColor.GOLD + "[THG] " + ChatColor.YELLOW + ChatColor.ITALIC + "You cannot unregister while being dead. You'll be automatically unregistered when THG ends.");
                } else {
                    if (args.length == 2) {
                        if (args[1].equalsIgnoreCase("confirm")) {
                            currentContestant = contestantList.getByName(sender.getName());
                            contestantList.removeContestantList(currentContestant);
                            TheHungyGames.fileAccess.removePlayerData(currentContestant.name);
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
        } else if (args[0].equalsIgnoreCase("save")) {
            TheHungyGames.fileAccess.saveAllData();
            sender.sendMessage(ChatColor.GOLD + "[THG] " + ChatColor.GREEN + "All data successfully saved!");
        } else if (args[0].equalsIgnoreCase("reload")) {
            TheHungyGames.fileAccess.createDataFile();
            TheHungyGames.fileAccess.loadPlayerData();
            sender.sendMessage(ChatColor.GOLD + "[THG] " + ChatColor.GREEN + "Reloaded all data!");
        }
        return false;
    }
}
