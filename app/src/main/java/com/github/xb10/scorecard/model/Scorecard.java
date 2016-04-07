package com.github.xb10.scorecard.model;


import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;

public class Scorecard implements Serializable{

    private int id;
    private Date date;
    private ArrayList<Player>players;
    private Club club;

    public Scorecard(int id, Date date, ArrayList<Player> players, Club club) {
        this.id = id;
        this.date = date;
        this.players = players;
        this.club = club;
    }

    public Scorecard(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }
}
