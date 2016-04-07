package com.github.xb10.scorecard.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Member implements Serializable{

    private String firstName;
    private String lastName;
    private String id;
    private String password;
    private double handicap;
    private String address;
    private ArrayList<String> clubs;

    public Member(){}

    public Member(String firstName, String lastName, String id, String password, double handicap, String address, ArrayList<String> clubs) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.password = password;
        this.handicap = handicap;
        this.address = address;
        this.clubs = clubs;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getHandicap() {
        return handicap;
    }

    public void setHandicap(double handicap) {
        this.handicap = handicap;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<String> getClubs() {
        return clubs;
    }

    public void setClubs(ArrayList<String> clubs) {
        this.clubs = clubs;
    }

    public String getFullName(){

        return firstName + " " + lastName;
    }
}
