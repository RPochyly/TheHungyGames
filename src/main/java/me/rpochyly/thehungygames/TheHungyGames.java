package me.rpochyly.thehungygames;



import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;


public final class TheHungyGames extends JavaPlugin implements Listener {

    public static ContestantListClass contestantList;
    public static FileAccess fileAccess;

    public TheHungyGames() {
        TheHungyGames.contestantList = new ContestantListClass();
        TheHungyGames.fileAccess = new FileAccess(this);
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(this,this);
        this.getCommand("thg").setExecutor(new Commands(this));
        fileAccess.createDataFile();
        fileAccess.loadPlayerData();

        System.out.println("The Hungy Games has been initialized");
    }

    // @EventHandler
    // public void DamageTaken(EntityDamageByEntityEvent event) {
    //     Entity entity = event.getEntity();
    //     Entity attacker = event.getDamager();
    //     if()
    //     if(entity instanceof Player) {
    //         if(attacker instanceof Player) {
    //             System.out.println("This player has killed you: " + attacker.toString());
    //         } else {
    //             System.out.println("Entity " + attacker.toString() + " has killed you");
    //         }
    //     }
    // }

    @EventHandler
    public void playerDeathEvent(PlayerDeathEvent event) {
        String playerName = event.getEntity().getName();
        Contestant currentContestant = contestantList.getByName(playerName);
        if (currentContestant == null) {
            System.out.println("Player " + playerName + " is not in Contestants list");
        } else {
            // String title, String subtitle, int fadeIn, int stay, int fadeOut. 
            currentContestant.setLifes((currentContestant.lifes - 1));
            event.getEntity().sendTitle(ChatColor.RED + "You have died! ", ChatColor.RED + "" + ChatColor.BOLD + String.valueOf(currentContestant.lifes) + " lifes" + ChatColor.RESET + ChatColor.RED + " remaining", 10, 140, 30);
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
            TheHungyGames.fileAccess.savePlayerData();
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("Disabling The Hungy Games");
        fileAccess.savePlayerData();
    }
}