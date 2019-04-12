package Model;

import Viewer.Direction;
import Viewer.Location;
import Viewer.SquareState;

import java.util.Arrays;

public class MowerMap extends Lawn{
    private int width = 60;
    private int height = 40;
    private SquareState[][] map;
//    private int remainGrassNumber = 0;
    private int[] fenceLoc; // order: top, right;

    public MowerMap() {
        map = new SquareState[width][height];
    }

    public void initializeMap() {
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                map[i][j] = SquareState.unknown;
            }
        }
        fenceLoc = new int[2];
        Arrays.fill(fenceLoc, -1); // initial fence loc set to -1;
    }

    //TODO: set square state for the one in mowerMap
    public void setSquare(Location loc, SquareState sqState, Direction d){
        int x = loc.getX();
        int y = loc.getY();

        // do not set for out of bound.
        if (x < 0 || x > width-1 || y < 0 || y > height-1)
            return;
        map[x][y] = sqState;

        if (sqState == SquareState.fence) {
            if (d == Direction.north) fenceLoc[0] = loc.getY(); // top fence
            if (d == Direction.east) fenceLoc[1] = loc.getX(); // right fence
        }
    }

    public Boolean isCompleted(){
        for (int i : fenceLoc) {
            if (i < 0) return false; // still have unknown fence. Assume fence is not totally blocked by craters.
        }

        int grassCnt = 0;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < width; y++) {
                if (x < fenceLoc[1] && y < fenceLoc[0]) {
                    if (map[x][y] == SquareState.unknown) return false;
                    if (map[x][y] == SquareState.grass || map[x][y] == SquareState.puppy_grass) grassCnt += 1;
                }
            }
        }
        if (grassCnt > 0) return false;
        return true;
    }

    //TODO: get the square state at a specific location
    public SquareState getSquare(Location loc){
        int x = loc.getX();
        int y = loc.getY();
        if (x < 0 || x > width-1 || y < 0 || y > height-1) return SquareState.out_of_bound;
        return map[x][y];
    }

    //TODO: update the map for each scan
    public void updateMapFromScan(Location loc, SquareState[] surs){
        int x = loc.getX();
        int y = loc.getY();

        // setSquare will handle out of bound.
        setSquare(new Location(x, y+1), surs[0], Direction.north); // North
        setSquare(new Location(x+1, y+1), surs[1], Direction.northeast); // NorthEast
        setSquare(new Location(x+1, y), surs[2], Direction.east); // East
        setSquare(new Location(x+1, y-1), surs[3], Direction.southeast); // SouthEast
        setSquare(new Location(x, y-1), surs[4], Direction.south); // South
        setSquare(new Location(x-1, y-1), surs[5], Direction.southwest); // SouthWest
        setSquare(new Location(x-1, y), surs[6], Direction.west); // West
        setSquare(new Location(x-1, y+1), surs[7], Direction.northwest); // NorthWest

        map[x][y] = SquareState.mower;
    }

    //TODO: extend the map when the mower goes over the boundary
    //set square = 60 x 40, for maximum size of 200 x 200, no need to expand
    private void expand(){
        return;
    }
}
