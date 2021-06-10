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

    // TODO: Souborová databáze, nebo spíš uložení do souboru. Zatím netestované a vykomentované protože jsem to ještě nechtěl dělat
    //private File dataFile;
    //private FileConfiguration dataFileConfig;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(this,this);
        this.getCommand("thg").setExecutor(new Commands(this));
        //createDataFile();

        System.out.println("The Hungy Games has been initialized");


    }

    // Znovu soubory na uložení stavu

    //public FileConfiguration getDataFileConfig() {
    //    return this.dataFileConfig;
    //}

    //private void createDataFile() {
    //    dataFile = new File(getDataFolder(), "data.yml");
    //    if(!dataFile.exists()) {
    //        dataFile.getParentFile().mkdirs();
    //        saveResource("data.yml", false);
    //    }
    //    dataFileConfig = new YamlConfiguration();
    //    try {
    //        dataFileConfig.load(dataFile);
    //    } catch(IOException | InvalidConfigurationException e) {
    //        e.printStackTrace();
    //    }
//}

        //ArrayList<Contestant> contestantArrayList = new ArrayList<Contestant>();

    // Kontroluje hráče, zatím nemá fungovat i když jaksi funguje. Toto dodělám potom
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

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("Disabling The Hungy Games");
    }
}