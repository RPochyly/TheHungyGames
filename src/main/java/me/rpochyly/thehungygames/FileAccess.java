package me.rpochyly.thehungygames;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class FileAccess {

    private File pluginDataFile;
    private FileConfiguration pluginData;
    private static ContestantListClass contestantList;
    private final TheHungyGames plugin;

    public FileAccess(TheHungyGames plugin) {
        this.plugin = plugin;
        FileAccess.contestantList = TheHungyGames.contestantList;
    }


    public FileConfiguration getCustomConfig() {
        return this.pluginData;
    }


    public void createDataFile() {
        pluginDataFile = new File(plugin.getDataFolder(), "data.yml");
        if (!pluginDataFile.exists()) {
            pluginDataFile.getParentFile().mkdirs();
            plugin.saveResource("data.yml", true);
        }

        pluginData = new YamlConfiguration();
        try {
            pluginData.load(pluginDataFile);
            System.out.println("Loaded plugin data!");
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void savePlayerData() {
        if(contestantList.getContestantList().size() == 0) {
            System.out.println("Saving player data failed!");
            System.out.println("There is no data to save.");
        } else {
            for (int i = 0; i < contestantList.getContestantList().size(); i++) {
                String playerName = contestantList.getContestantList().get(i).name;
                int playerLifes = contestantList.getContestantList().get(i).lifes;
                int playerPoints = contestantList.getContestantList().get(i).points;
                this.getCustomConfig().set("player." + String.valueOf(i) + ".name", playerName);
                this.getCustomConfig().set("player." + String.valueOf(i) + ".lifes", playerLifes);
                this.getCustomConfig().set("player." + String.valueOf(i) + ".points", playerPoints);
            }
        }
        
        try {
            getCustomConfig().save(pluginDataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void loadPlayerData() {
        int playersNum;
        if(this.getCustomConfig().getConfigurationSection("player") != null) {
            playersNum = this.getCustomConfig().getConfigurationSection("player").getKeys(false).size();
            for (int i = 0; i < playersNum; i++) {
                String playerName = this.getCustomConfig().getString("player." + String.valueOf(i) + ".name");
                int playerLifes = this.getCustomConfig().getInt("player." + String.valueOf(i) + ".lifes");
                int playerPoints = this.getCustomConfig().getInt("player." + String.valueOf(i) + ".name");
                if (contestantList.getByName(playerName) == null) {
                    contestantList.addContestantList(playerName, playerLifes, playerPoints);
                }
            }
        } else {
            System.out.println("There is nothing to load.");
        }
    }
}
