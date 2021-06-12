package me.rpochyly.thehungygames;


import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;


public final class TheHungyGames extends JavaPlugin implements Listener {


    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(this,this);
        this.getCommand("thg").setExecutor(new Commands(this));
        //createDataFile();

        System.out.println("The Hungy Games has been initialized");


    }

    @EventHandler
    public void DamageTaken(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        Entity attacker = event.getDamager();
        if(entity instanceof Player) {
            if(attacker instanceof Player) {
                System.out.println("This player has killed you: " + attacker.toString());
            } else {
                System.out.println("Entity " + attacker.toString() + " has killed you");
            }
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("Disabling The Hungy Games");
    }
}