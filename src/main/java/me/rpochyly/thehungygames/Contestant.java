package me.rpochyly.thehungygames;

import org.bukkit.Location;

public class Contestant {
    public int points, lifes;
    public String name;
    public boolean dead;
    public boolean paused;
    public Location lastLocation;

//    ArrayList<Contestant> Contestant = TheHungyGames

    public Contestant(String name, int lifes, int points) {
        this.name = name;
        this.lifes = lifes;
        this.points = points;
    }

   // public void setLifes(int newLifes) {
   //     this.lifes = newLifes;
   //}

    public void deathState(boolean deathState) {
        this.dead = deathState;
    }
}
