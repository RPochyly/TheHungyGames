package me.rpochyly.thehungygames;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import net.minecraft.nbt.NBTTagCompound;

public class Event {

    private final TheHungyGames plugin;
    private BukkitTask timer;
    public static List<Player> playerList;
    public static boolean timerRunning = false;
    public static BossBar bossBar;

    public Event(TheHungyGames plugin) {
        this.plugin = plugin;
        Event.playerList = TheHungyGames.getOnlinePlayerList();
    }
        
    public void Timer(final Double targetSeconds) {
        if (timerRunning != true) {
            bossBar = Bukkit.createBossBar("Time remaining", BarColor.YELLOW, BarStyle.SEGMENTED_10);
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
            bossBar.removeAll();
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

    public void tpToWaitingSpawn(Player player) {
        if (TheHungyGames.waitingLocation != null) {
            player.teleport(TheHungyGames.waitingLocation);
        }
    }

    public void givePointsItem(Player itemPlayer, Integer itemPoints) {
        ItemStack itemStack = new ItemStack(Material.KNOWLEDGE_BOOK);
        ItemMeta itemMeta = (ItemMeta) itemStack.getItemMeta();
        // https://www.spigotmc.org/threads/tutorial-the-complete-guide-to-itemstack-nbttags-attributes.131458/
        ArrayList<String> lore = new ArrayList<String>();
        lore.add("This item will give you " + String.valueOf(itemPoints) + " points.");
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        net.minecraft.world.item.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound itemCompound;
        if (nmsItemStack.hasTag()) {
            itemCompound = nmsItemStack.getTag();
        } else {
            itemCompound = new NBTTagCompound();
        }
        itemCompound.setInt("points", itemPoints);
        nmsItemStack.setTag(itemCompound);
        nmsItemStack.save(itemCompound);
        itemStack = CraftItemStack.asBukkitCopy(nmsItemStack);
        itemPlayer.getInventory().addItem(itemStack);
    }

    public void usePointsItem(Player player, ItemStack itemStack) {
        if (TheHungyGames.contestantList.getByName(player.getName()) != null) {
            net.minecraft.world.item.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);
            NBTTagCompound itemCompound;
            if (nmsItemStack.hasTag()) {
                itemCompound = nmsItemStack.getTag();
                int points = itemCompound.getInt("points");
                Contestant contestant = TheHungyGames.contestantList.getByName(player.getName());
                contestant.points += points;
                player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount()-1);
                player.sendMessage(ChatColor.GOLD + "[THG] " + ChatColor.GREEN + "Successfully added " + ChatColor.BOLD + String.valueOf(points) + " points!");
            }
        } else {
            player.sendMessage(ChatColor.GOLD + "[THG] " + ChatColor.YELLOW + ChatColor.ITALIC + "You may not use this item because you're not a registered contestant!");
        }
    }



}
