package me.rpochyly.thehungygames;



import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;


public final class TheHungyGames extends JavaPlugin implements Listener {

    public static ContestantListClass contestantList;
    public static FileAccess fileAccess;
    public static Event eventTHG;
    public static Location waitingLocation;

    public TheHungyGames() {
        TheHungyGames.contestantList = new ContestantListClass();
        TheHungyGames.fileAccess = new FileAccess(this);
        TheHungyGames.eventTHG = new Event(this);
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(this,this);
        this.getCommand("thg").setExecutor(new Commands(this));
        fileAccess.createDataFile();
        fileAccess.loadAllData();

        System.out.println("The Hungy Games has been initialized");
    }

    public static List<Player> getOnlinePlayerList() {
        List<Player> playerList = new ArrayList<>(Bukkit.getOnlinePlayers());
        return playerList;
    }

    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent event) {
        if (Event.bossBar != null) {
            eventTHG.showTimer();

        }
    }

    @EventHandler
    public void EntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        if(event.getDamager().getType() == EntityType.PLAYER) {
            Player attacker = (Player) event.getDamager();
            if (TheHungyGames.contestantList.getByName(attacker.getName()) != null){
                Contestant attackerContestant = TheHungyGames.contestantList.getByName(attacker.getName());
                if (attackerContestant.paused == true) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void playerDeathEvent(PlayerDeathEvent event) {
        String playerName = event.getEntity().getName();
        Contestant currentContestant = contestantList.getByName(playerName);
        if (currentContestant == null) {
            System.out.println("Player " + playerName + " is not in Contestants list");
        } else {
            if(currentContestant.lifes > 0) {
                currentContestant.lifes = currentContestant.lifes - 1;
                if(currentContestant.lifes == 1) {
                    event.getEntity().sendTitle(ChatColor.RED + "You have died! ", ChatColor.RED + "" + ChatColor.BOLD + String.valueOf(currentContestant.lifes) + " life" + ChatColor.RESET + ChatColor.RED + " remaining", 10, 140, 30);
                } else {
                    event.getEntity().sendTitle(ChatColor.RED + "You have died! ", ChatColor.RED + "" + ChatColor.BOLD + String.valueOf(currentContestant.lifes) + " lifes" + ChatColor.RESET + ChatColor.RED + " remaining", 10, 140, 30);
                }
                event.setDeathMessage(null);
                if(currentContestant.lifes == 0) {
                    currentContestant.dead = true;
                    event.getEntity().setGameMode(GameMode.SPECTATOR);
                    Bukkit.broadcastMessage(ChatColor.GOLD + "[THG] " + ChatColor.RED + "The player " + ChatColor.BOLD + playerName + ChatColor.RESET + ChatColor.RED + " has lost all their lifes.");
                } else if (currentContestant.lifes == 1) {
                    Bukkit.broadcastMessage(ChatColor.GOLD + "[THG] " + ChatColor.RED + "The player " + ChatColor.BOLD + playerName + ChatColor.RESET + ChatColor.RED + " has now only " + String.valueOf(currentContestant.lifes) + " life.");
                } else {
                    Bukkit.broadcastMessage(ChatColor.GOLD + "[THG] " + ChatColor.RED + "The player " + ChatColor.BOLD + playerName + ChatColor.RESET + ChatColor.RED + " has now only " + String.valueOf(currentContestant.lifes) + " lifes.");
                }
            }
            TheHungyGames.fileAccess.savePlayerData();
        }
    }

    @EventHandler
    public void PlayerInteractionEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (player.getInventory().getItemInMainHand().getType().equals(Material.KNOWLEDGE_BOOK) ) {
            eventTHG.usePointsItem(player, player.getInventory().getItemInMainHand());
            event.setCancelled(true);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("Disabling The Hungy Games");
        fileAccess.saveAllData();
    }
}