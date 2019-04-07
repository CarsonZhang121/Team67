package Model;

import Viewer.Direction;
import Viewer.Location;
import Viewer.SquareState;

public class MowerMap extends Lawn{
    private int width = 60;
    private int length = 40;
    SquareState[][] map;


    public void initialMap(int width, int length){

        for(int i = 0; i < width; i++){
            for(int j = 0; j < length; j++){
                map[i][j] = SquareState.unknown;
            }
        }
    }

    //TODO: set square state for the one in mowerMap
    public void setSquare(Location loc, SquareState sqState, Direction d){

        map[loc.getX()][loc.getY()] = SquareState.empty;
    }

    //TODO: determine if all the grasses has been cut
    // What is the logic here???
    public Boolean isCompleted(MowerMap map){
        int grassremain = map.remainGrassNumber();
        Boolean isCompleted = false;
        if(grassremain == 0){
            isCompleted = true;
        }
        return isCompleted;
    }

    //TODO: get the square state at a specific location
    public SquareState getSquare(Location loc){

        SquareState localstate;
        localstate = map[loc.getX()][loc.getY()];
        return localstate;
    }

    //TODO: update the map for each scan
    public void updateMapFromScan(Location loc, SquareState[] neighbors){

        map[loc.getX()][loc.getY() + 1] = neighbors[0];
        map[loc.getX() + 1][loc.getY() + 1] = neighbors[1];
        map[loc.getX() + 1][loc.getY()] = neighbors[2];
        map[loc.getX() + 1][loc.getY() - 1] = neighbors[3];
        map[loc.getX()][loc.getY() - 1] = neighbors[4];
        map[loc.getX() - 1][loc.getY() - 1] = neighbors[5];
        map[loc.getX() - 1][loc.getY()] = neighbors[6];
        map[loc.getX() - 1][loc.getY() + 1] = neighbors[7];
    }

    //TODO: extend the map when the mower goes over the boundary
    //set square = 60 x 40, for maximum size of 200 x 200, no need to expand
    private void expand(){

    }
}
