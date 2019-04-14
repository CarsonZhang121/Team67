package Model;

import Viewer.Location;

import java.util.*;
import java.io.*;
import java.util.concurrent.ThreadLocalRandom;

public class Puppy {
    //private Location puppyLocations;

    // List the puppyStatus here
    private String puppyStatus; // "stay" or "move"

    int percentToStay;// range from 0 to 100; 100 means always stay; 0 means always move
    int randomNum;

    // Constructor
    public Puppy(int percentToStay){
        this.percentToStay = percentToStay;
    }

       // TODO: add nextAction for Puppy
    public String nextAction(){
        randomNum = ThreadLocalRandom.current().nextInt(0, 100 + 1);
        System.out.printf("The randonNum is %d\n", randomNum);

        if (randomNum < percentToStay){
            //stay
            this.puppyStatus = "stay";

        }else {
            //move
            this.puppyStatus = "move";
        }
        return this.puppyStatus;
    }
 
   /*
    private int decideNextMoveLocation(String[] neightSquareStates){
        List<Integer> indexes = new ArrayList<Integer>();
        int randNum;
        int locIndex;
        for(int i=0; i < neightSquareStates.length; i++){
            if(neightSquareStates[i].equals("grass")||
                    (neightSquareStates[i].equals("empty"))||
                    (neightSquareStates[i].equals("mower"))){
                indexes.add(i);
            }
        }

        int CeilingNum = indexes.size(); // the number of qualified candidates
        randNum = randGenForNextMove.nextInt(CeilingNum);
        locIndex = indexes.get(randNum);
       // System.out.printf("the num of candidates is %d; the random number is %d;the chosen Locindex is %d\n", CeilingNum,randNum,locIndex);
        return locIndex;
    }
    */

    public Location getPuppyLocations() {
        return puppyLocations;
    }

    public void setPuppyLocations(Location puppyLocations) {
        this.puppyLocations = puppyLocations;
    }

    public String getPuppyStatus() {
        return this.puppyStatus;
    }

    public void setPuppyStatus(String puppyStatus) {
        this.puppyStatus = puppyStatus;
    }


}
