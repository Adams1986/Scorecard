package com.github.xb10.scorecard.model;


import java.io.Serializable;
import java.util.ArrayList;

public class Course implements Serializable{

    private int id;
    private String name;
    private ArrayList<Hole>holes;
    private double slope;
    private double rating;
    private CourseMetaData metaData;

    public Course(String name, ArrayList<Hole> holes, double slope, double rating, CourseMetaData metaData) {

        this.name = name;
        this.holes = holes;
        this.slope = slope;
        this.rating = rating;
        this.metaData = metaData;
    }

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

    public ArrayList<Hole> getHoles() {
        return holes;
    }

    public void setHoles(ArrayList<Hole> holes) {
        this.holes = holes;
    }

    public double getSlope() {
        return slope;
    }

    public void setSlope(double slope) {
        this.slope = slope;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public CourseMetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(CourseMetaData metaData) {
        this.metaData = metaData;
    }

    /**
     * Simple method for returning the length of the course. To be used for informing normal scorecard data to user
     * @return length
     */
    public int getLengthTotal(){

        int length = 0;

        for (Hole h: holes){

            length += h.getLength();
        }
        return length;
    }

    /**
     * Returns the length of the first nine holes. Information required by user
     * @return length
     */
    public int getLengthFrontNine(){

        int length = 0;

        if (holes.size() == 18){

            for (int i = 0; i < holes.size() / 2 ; i++){

                length += holes.get(i).getLength();
            }
        }

        return length;
    }

    /**
     * Returns the length of the first nine holes. Information required by user
     * @return length
     */
    public int getLengthBackNine() {

        int length = 0;

        if (holes.size() == 18) {

            //start at index 8 => hole 9 (index 0 = hole 1 - obv).
            for (int i = 8; i < holes.size(); i++) {

                length += holes.get(i).getLength();
            }
        }

        return length;
    }

    public int getParTotal(){

        int parTotal=0;

        for(Hole h : holes){

            parTotal += h.getPar();


        }
        return parTotal;

    }

    public int getParFrontNine(){

        int parTotal = 0;

        for(int i = 0; i < (holes.size()/2); i++){

            parTotal += holes.get(i).getPar();


        }
        return parTotal;

    }

    public int getParBackNine(){

        int parTotal = 0;

        for(int i = 9; i < (holes.size()); i++){

            parTotal += holes.get(i).getPar();


        }
        return parTotal;

    }

    public ArrayList<Hole>getFrontNine(){

        ArrayList<Hole>tempHoles = new ArrayList<>();

        if (holes.size() == 18) {

            for(int i = 0; i < (holes.size()/2); i++){
                tempHoles.add(holes.get(i));
            }
            return tempHoles;
        }
        return holes;

    }

    public ArrayList<Hole>getBackNine(){

        ArrayList<Hole>tempHoles = new ArrayList<>();

        if (holes.size() == 18) {
            for(int i = 9; i < holes.size(); i++){

                tempHoles.add(holes.get(i));

            }
            return tempHoles;
        }
        //don't return any holes if only 9 holes, because then there is only front nine
        return null;
    }
}
