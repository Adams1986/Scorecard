package com.github.xb10.scorecard;

/**
 * Created by ADI on 25-03-2016.
 */
public class HoleCardViewTemplate {

    private int holeNumber;
    private int par;
    private int[] score;
    private String[] players = new String[4];

    public HoleCardViewTemplate(int holeNumber, int par, int[] scores, String[] players) {

        this.holeNumber = holeNumber;
        this.par = par;
        this.score = scores;
        this.players = players;
    }

    public int getHoleNumber() {
        return holeNumber;
    }

    public void setHoleNumber(int holeNumber) {
        this.holeNumber = holeNumber;
    }

    public int getPar() {
        return par;
    }

    public void setPar(int par) {
        this.par = par;
    }

    public int[] getScores() {
        return score;
    }

    public void setScore(int[] score) {
        this.score = score;
    }

    public String[] getPlayers() {
        return players;
    }

    public void setPlayers(String[] players) {
        this.players = players;
    }
}
