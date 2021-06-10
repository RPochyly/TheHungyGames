package me.rpochyly.thehungygames;



import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class TheHungyGames extends JavaPlugin implements Listener {

    private File dataFile;
    private FileConfiguration dataFileConfig;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(this,this);
        this.getCommand("thg").setExecutor(new Commands(this));
        createDataFile();

        System.out.println("The Hungy Games has been initialized");

        // DEBUG (can be deleted)
        Contestant RPochyly = new Contestant();
        RPochyly.name = "RPochyly";
        RPochyly.lifes = 3;
        RPochyly.points = 7;
        System.out.println("Information about " + RPochyly.name + ": Lifes - " + RPochyly.lifes + "; Points - " + RPochyly.points);

    }

    public FileConfiguration getDataFileConfig() {
        return this.dataFileConfig;
    }

    private void createDataFile() {
        dataFile = new File(getDataFolder(), "data.yml");
        if(!dataFile.exists()) {
            dataFile.getParentFile().mkdirs();
            saveResource("data.yml", false);
        }

        dataFileConfig = new YamlConfiguration();
        try {
            dataFileConfig.load(dataFile);
        } catch(IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }


    @EventHandler
    public void DamageTaken(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        Entity attacker = event.getDamager();
        //if(((Player)entity).getHealth() <= 0) {
        if(entity instanceof Player) {
            if(attacker instanceof Player) {
                System.out.println("This player has killed you: " + attacker.toString());
            } else {
                System.out.println("Entity " + attacker.toString() + " has killed you");
            }
        //    }
        }
    }

    // TODO: Detekovat smrt hráčem, přidat důvod proč zemřel; Nápad - loterie manhunt koho musíš zabít
    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("Disabling The Hungy Games");
    }
}
