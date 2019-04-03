package Model;

import Viewer.Direction;
import Viewer.Location;
import Viewer.SquareState;

public class MowerMap extends Lawn{
    private int width = 601;
    private int length = 601;

    public void initialMap(int width, int length){
        for(int i = 0; i < width; i++){
            for(int j = 0; j < length; j++)
                squares[i][j] = "unkown";
        }
    }

    //TODO: set square state for the one in mowerMap
    public void setSquare(Location loc, SquareState sqState, Direction d){
        square[loc[0]][loc[1]] = "grass";
    }

    //TODO: determine if all the grasses has been cut
    // What is the logic here???
    public Boolean isCompleted(){
        int grassremain = remainGrassNumber();
        Boolean isCompleted = false;
        if(grassremain == 0){
            isCompleted = true
        }
        return isCompleted;
    }

    //TODO: get the square state at a specific location
    public SquareState getSquare(Location loc){

        return square[loc[0]][loc[1]];
    }

    //TODO: update the map for each scan
    public void updateMapFromScan(String[] neighbors){

        square[currentLoc[0]][currentLoc[1] + 1] = neighbors[0];
        square[currentLoc[0] + 1][currentLoc[1] + 1] = neighbors[1];
        square[currentLoc[0] + 1][currentLoc[1]] = neighbors[2];
        square[currentLoc[0] + 1][currentLoc[1] - 1] = neighbors[3];
        square[currentLoc[0]][currentLoc[1] - 1] = neighbors[4];
        square[currentLoc[0] - 1][currentLoc[1] - 1] = neighbors[5];
        square[currentLoc[0] - 1][currentLoc[1]] = neighbors[6];
        square[currentLoc[0] - 1][currentLoc[1] + 1] = neighbors[7];
    }

    //TODO: extend the map when the mower goes over the boundary
    //set square = 601 x 601, for maximum size of 200 x 200, no need to expand
    private void expand(){

    }
}
