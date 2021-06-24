package me.rpochyly.thehungygames;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class Event {

    private final TheHungyGames plugin;
    private BukkitTask timer;
    public static List<Player> playerList;
    public static boolean timerRunning = false;

    public Event(TheHungyGames plugin) {
        this.plugin = plugin;
        Event.playerList = TheHungyGames.getOnlinePlayerList();
    }

    BossBar bossBar = Bukkit.createBossBar("Time remaining", BarColor.YELLOW, BarStyle.SEGMENTED_10);

    public void Timer(final Double targetSeconds) {
        if (timerRunning != true) {
            timerRunning = true;
            this.timer = new BukkitRunnable(){
                Double seconds = targetSeconds;
                @Override
                public void run() {
                    if ((seconds -= 1) == 0 || seconds < 0) {
                        timer.cancel();
                        bossBar.removeAll();
                        timerRunning = false;
                        // TIMER ENDED
                    } else {
                        Double ratio = seconds / targetSeconds;
                        bossBar.setProgress(ratio);
                    }
                }
            }.runTaskTimer(plugin, 0, 20);
            showTimer();
        } else {
            timer.cancel();
            timerRunning = false;
            this.Timer(targetSeconds);
        }
    }

    public void showTimer() {
        bossBar.setVisible(true);
        for (Player player : TheHungyGames.getOnlinePlayerList()) {
            bossBar.addPlayer(player);
        }
    }
}
