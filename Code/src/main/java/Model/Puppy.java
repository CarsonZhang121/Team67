package Model;

import Viewer.Location;
import Viewer.SquareState;

import java.util.*;

public class Puppy {
    //private Location puppyLocations;

    // List the puppyStatus here
   // private int puppyStatus;
    private String puppyStatus; // "stay" or "move"
    private Location puppyLocation;

    double percentToStay;// range from 0 to 1; 1 means always stay; 0 means always move
    Random randGenForAction;
    Random randGenForNextMove;

    // Constructor
    public Puppy(double percentToStay){
        this.percentToStay = percentToStay;
        randGenForAction = new Random();
        randGenForNextMove = new Random();
    }

 
    public String nextAction(){
        String actionStr;
        double randDub = randGenForAction.nextDouble();
        if (randDub < percentToStay){
            //stay
            actionStr = this.puppyStatus = "stay";
        }else {
            //move
            actionStr = this.puppyStatus = "move";
        }
        return actionStr;
    }

//    private int decideNextMoveLocation(SquareState[] neightSquareStates){
//        List<Integer> indexes = new ArrayList<Integer>();
//        int randNum;
//        int locIndex;
//        for(int i=0; i < neightSquareStates.length; i++){
//            if(neightSquareStates[i] == SquareState.grass ||
//                    (neightSquareStates[i] == SquareState.empty)||
//                    (neightSquareStates[i] == SquareState.mower)){
//                indexes.add(i);
//            }
//        }
//
//        int CeilingNum = indexes.size(); // the number of qualified candidates
//        randNum = randGenForNextMove.nextInt(CeilingNum);
//        locIndex = indexes.get(randNum);
//       // System.out.printf("the num of candidates is %d; the random number is %d;the chosen Locindex is %d\n", CeilingNum,randNum,locIndex);
//        return locIndex;
//    }


    public Location getPuppyLocation() {
        return puppyLocation;
    }

    public void setPuppyLocation(Location puppyLocation) {
        this.puppyLocation = puppyLocation;
    }

    public String getPuppyStatus() {
        return this.puppyStatus;
    }

    public void setPuppyStatus(String puppyStatus) {
        this.puppyStatus = puppyStatus;
    }


}
