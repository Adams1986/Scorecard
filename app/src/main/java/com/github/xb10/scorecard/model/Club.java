package com.github.xb10.scorecard.model;


import java.io.Serializable;
import java.util.ArrayList;

public class Club implements Serializable {

    private int id;
    private String name;
    private ArrayList<Course>courses;

    public Club(int id, String name, ArrayList<Course> courses) {
        this.id = id;
        this.name = name;
        this.courses = courses;
    }

    //Empty constructor for convenience when using the wizard to create a new scorecard
    public Club(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }
}
