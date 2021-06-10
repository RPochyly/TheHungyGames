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

    // Třída která kontroluje příkazy a stará se o ně. Výchozí příkaz je /thg a za ním argumenty. O to už se starají ty if statementy.

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(args.length == 0) {
            sender.sendMessage(ChatColor.GOLD + "The Hungy Games" + ChatColor.AQUA + " - " + ChatColor.RED + "BETA VERSION");
            return true;
        } else if(args[0].equalsIgnoreCase("signup")) {

            // Tady testuju přidávání toho hráče do seznamu těch účastníků (Contestant). Trošku divné to ale je a jsou tu i nějaké debug věci nooo

            int points = Integer.parseInt(args[1]);
            ContestantListClass ContestantList = new ContestantListClass();
            ContestantList.addContestantList(sender.getName(),3,0);
            sender.sendMessage("Successfuly registered");
            for(int i = 0; i < ContestantList.getContestantList().size(); i++) {
                sender.sendMessage(String.valueOf(ContestantList.getContestantList().get(i)));
            }




            return true;
        } else if(args[0].equalsIgnoreCase("manage")) {
            if(args.length > 1) {
                sender.sendMessage("TODO: Manage mode");
            } else {
                sender.sendMessage("Not enough arguments!");
            }
            return true;
        } else if(args[0].equalsIgnoreCase("info")) {
            if(args.length < 2) {
                sender.sendMessage("TODO: Game status");
            } else if(args.length == 3) {
                sender.sendMessage("TODO: Game info about " + args[2]);
            }
        }



        return false;
    }
}
