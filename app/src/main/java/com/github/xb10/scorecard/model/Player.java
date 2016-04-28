package com.github.xb10.scorecard.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;


public class Player extends Member implements Serializable {

    private int[] scores;

    public Player(){}

    public Player(String firstName, String lastName, String id, String password, double handicap, String address, ArrayList<String> clubs,int[] scores) {

        super(firstName, lastName, id, password, handicap, address, clubs);
        this.scores = scores;
    }

    /**
     * Bad practice?
     * @param currentMember
     * @param scores
     */
    public Player(Member currentMember, int[] scores){

        super(currentMember.getFirstName(), currentMember.getLastName(), currentMember.getId(), currentMember.getPassword(), currentMember.getHandicap(), currentMember.getAddress(), currentMember.getClubs());
        this.scores = scores;
    }

    public int[] getScores() {
        return scores;
    }

    public int getFrontNineTotal(){

        int total = 0;
        int noOfHoles = scores.length == 18 ? 9 : scores.length;

            for (int i = 0; i < noOfHoles; i++){
                total += scores[i];
            }
        return total;
    }

    public int getBackNineTotal(){

        //set to -1 one for error handling
        int total = 0;

        if (scores.length == 18){
            for (int i = 9; i < scores.length; i++){
                total += scores[i];
            }
            return total;
        }
        return -1;
    }


    public int getScoreTotal() {
        int total = 0;

        for (int i = 0; i < scores.length; i++){
            total += scores[i];
        }
        return total;
    }


    /**
     * Method probably used after every hole registered
     * @param scores
     */
    public void setScores(int[] scores) {
        this.scores = scores;
    }

    /**
     * Calculate players course handicap. The course handicap is the number of strokes a player has on the course.
     * Method uses the courses slope and rating to determine the number of strokes.
     * @param currentCourse
     * @return strokes the player has on the current course
     */
    public int getCourseHandicap(Course currentCourse){

        return (int) Math.round(getHandicap()*currentCourse.getSlope()/113+currentCourse.getRating()-currentCourse.getParTotal());
    }


    /**
     * Method calculates the number of shots per hole that a player has and returns an array of shots/hole containing all holes.
     * @param currentCourse
     * @return
     */
    public int[] getStrokes(Course currentCourse){

        int[] strokes = new int[currentCourse.getHoles().size()];
        int courseHandicap = getCourseHandicap(currentCourse);

        for (int i = 0; i < strokes.length; i++){

            //If there is a remainder in the modulus expression it means that the player gets an additional stroke on the current hole.
            // Else the player only has 'course handicap' / 'number of holes' extra strokes on the hole.
            strokes[i] = courseHandicap % currentCourse.getHoles().size() >= currentCourse.getHoles().get(i).getHcp()

                    ? courseHandicap / currentCourse.getHoles().size() + 1 : courseHandicap / currentCourse.getHoles().size();
        }

        return strokes;
    }

    /**
     * Aggregated points. Method to be used after every score on a hole is registered
     * @param currentCourse
     * @return
     */
    public int getTotalPoints(Course currentCourse){

        int points = 0;
        int[] strokes = getStrokes(currentCourse);

        for (int i = 0; i < currentCourse.getHoles().size(); i++) {

            //Match score with hole only if a score has been registered on the hole.
            if(scores[i] != 0){

                //net score is the amount you have when you add the par and strokes on the hole together and then deduct your score on the hole.
                int netScore = currentCourse.getHoles().get(i).getPar() + strokes[i] - scores[i];

                //You can't get negative points. This check makes sure of that. If your netscore is -1, you get 1 point. 0 and you get 2 points.
                //Adds all your points together
                points += (netScore + 2) > 0 ? netScore + 2 : 0;
            }
        }
        //Returns the aggregated points for every hole.
        return points;
    }


    //Get points on a hole
    public int getPoints(Course currentCourse, int holeNumber){

        int points = 0;
        int[] strokes = getStrokes(currentCourse);

        //Match score with hole only if a score has been registered on the hole.
        if(scores[holeNumber] != 0){

            //net score is the amount you have when you add the par and strokes on the hole together and then deduct your score on the hole.
            int netScore = currentCourse.getHoles().get(holeNumber).getPar() + strokes[holeNumber] - scores[holeNumber];

            //You can't get negative points. This check makes sure of that. If your netscore is -1, you get 1 point. 0 and you get 2 points.
            //Adds all your points together
            points = (netScore + 2) > 0 ? netScore + 2 : 0;
        }
        //Returns the aggregated points for every hole.
        return points;
    }

    /**
     * Another method to be used after each entry of a score on a hole
     * @param currentCourse
     * @return
     */
    public int getUnderOverPar(Course currentCourse){

        int overUnderPar = 0;

        for (int i = 0; i < scores.length; i++){

            if (scores[i] != 0) {
                overUnderPar += scores[i] - currentCourse.getHoles().get(i).getPar();
            }
        }

        return overUnderPar;
    }
}
