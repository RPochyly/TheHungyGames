package me.rpochyly.thehungygames;


public class Contestant {
    public int points, lifes;
    public String name;

//    ArrayList<Contestant> Contestant = TheHungyGames

    public Contestant(String name, int lifes, int points) {
        this.name = name;
        this.lifes = lifes;
        this.points = points;
    }
}
