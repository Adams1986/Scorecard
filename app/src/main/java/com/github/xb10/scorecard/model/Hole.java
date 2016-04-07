package com.github.xb10.scorecard.model;


import java.io.Serializable;


public class Hole implements Serializable {

    private int id;
    private int number;
    private int par;
    private int hcp;
    private int length;


    public Hole(int number, int hcp, int par, int length) {

        this.number = number;
        this.hcp = hcp;
        this.par = par;
        this.length = length;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getHcp() {
        return hcp;
    }

    public void setHcp(int hcp) {
        this.hcp = hcp;
    }

    public int getPar() {
        return par;
    }

    public void setPar(int par) {
        this.par = par;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
