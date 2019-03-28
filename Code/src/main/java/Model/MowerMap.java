package Model;

import Viewer.Direction;
import Viewer.Location;
import Viewer.SquareState;

public class MowerMap {
    private int width = 201;
    private int length = 201;

    //TODO: set square state for the one in mowerMap
    public void setSquare(Location loc, SquareState sqState, Direction d){

    }

    //TODO: determine if all the grasses has been cut
    // What is the logic here???
    public Boolean isCompleted(){
        return false;
    }

    //TODO: get the square state at a specific location
    public SquareState getSquare(Location loc){
        return SquareState.grass;
    }

    //TODO: update the map for each scan
    public void updateMapFromScan(String[] neighbors){

    }

    //TODO: extend the map when the mower goes over the boundary
    private void expand(){

    }
}
