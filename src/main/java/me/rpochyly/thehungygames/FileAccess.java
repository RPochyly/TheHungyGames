package me.rpochyly.thehungygames;

import java.io.File;
import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
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
        if(contestantList.getContestantList().size() != 0) {
            for (int i = 0; i < contestantList.getContestantList().size(); i++) {
                String playerName = contestantList.getContestantList().get(i).name;
                int playerLifes = contestantList.getContestantList().get(i).lifes;
                int playerPoints = contestantList.getContestantList().get(i).points;
                boolean playerPaused = contestantList.getContestantList().get(i).paused;
                Location lastLocation = contestantList.getContestantList().get(i).lastLocation;
                this.getCustomConfig().set("player." + String.valueOf(playerName) + ".lifes", playerLifes);
                this.getCustomConfig().set("player." + String.valueOf(playerName) + ".points", playerPoints);
                this.getCustomConfig().set("player." + String.valueOf(playerName) + ".paused", playerPaused);
                this.getCustomConfig().set("player." + String.valueOf(playerName) + ".lastlocation", lastLocation);
            }
        }
        try {
            getCustomConfig().save(pluginDataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveAllData() {
        savePlayerData();
        try {
            getCustomConfig().save(pluginDataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removePlayerData(String playerName) {
        if(this.getCustomConfig().getConfigurationSection("player") != null) {
            if(this.getCustomConfig().getConfigurationSection("player").contains(playerName)) {
                this.getCustomConfig().set("player." + String.valueOf(playerName) + ".lifes", null);
                this.getCustomConfig().set("player." + String.valueOf(playerName) + ".points", null);
                this.getCustomConfig().set("player." + String.valueOf(playerName) + ".paused", null);
                this.getCustomConfig().set("player." + String.valueOf(playerName) + ".lastlocation", null);
                this.getCustomConfig().set("player." + String.valueOf(playerName), null);
            } else {
                System.out.println("The requested player " + playerName + " cannot be removed because they don't exist or aren't saved yet.");
            }
        } else {
            System.out.println("There is nothing saved yet.");
        }
    }

    public void loadPlayerData() {
        System.out.println("Reloading THG player data.");
        FileAccess.contestantList.getContestantList().clear();
        if(this.getCustomConfig().getConfigurationSection("player") != null) {
            ConfigurationSection playerSection = this.getCustomConfig().getConfigurationSection("player");
            for(String playerName : playerSection.getKeys(false)) {
                int playerPoints = this.getCustomConfig().getInt("player." + playerName + ".points");
                int playerLifes = this.getCustomConfig().getInt("player." + playerName + ".lifes");
                boolean playerPaused = this.getCustomConfig().getBoolean("player." + playerName + ".paused");
                Location lastLocation = this.getCustomConfig().getLocation("player." + playerName + ".lastlocation");
                if (contestantList.getByName(playerName) == null) {
                    contestantList.addContestantList(playerName, playerLifes, playerPoints);
                    Contestant contestant = contestantList.getByName(playerName);
                    contestant.paused = playerPaused;
                    contestant.lastLocation = lastLocation;
                }
            }
        } else {
            System.out.println("There is no player data to load.");
        }
    }

    public void loadAllData() {
        loadPlayerData();
        TheHungyGames.waitingLocation = this.getCustomConfig().getLocation("options.waitinglocation");
    }
}
            //    String playerName = contestantList.getContestantList().get(i).name;
            //    int playerLifes = contestantList.getContestantList().get(i).lifes;
            //    int playerPoints = contestantList.getContestantList().get(i).points;
            //    this.getCustomConfig().set("player." + String.valueOf(i) + ".name", playerName);
            //    this.getCustomConfig().set("player." + String.valueOf(i) + ".lifes", playerLifes);
            //    this.getCustomConfig().set("player." + String.valueOf(i) + ".points", playerPoints);

            // String playerName = this.getCustomConfig().getString("player." + String.valueOf(i) + ".name");
            // int playerLifes = this.getCustomConfig().getInt("player." + String.valueOf(i) + ".lifes");
            // int playerPoints = this.getCustomConfig().getInt("player." + String.valueOf(i) + ".name");

            //playersNum = this.getCustomConfig().getConfigurationSection("player").getKeys(false).size();
            //List<String> playerNameList = this.getCustomConfig().getStringList("player");
            //for (int i = 0; i < playersNum; i++) {
            //    System.out.println(playerNameList.toString());
            //    String playerName = playerNameList.get(i);
            //    int playerPoints = this.getCustomConfig().getInt(playerName + ".points");
            //    int playerLifes = this.getCustomConfig().getInt(playerName + ".lifes");
            //    if (contestantList.getByName(playerName) == null) {
            //        contestantList.addContestantList(playerName, playerLifes, playerPoints);
            //    }
            //}