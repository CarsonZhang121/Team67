package Model;

import Viewer.*;
import com.sun.tools.javac.util.ArrayUtils;


public class LawnMower {
    private MowerStatus currentStatus;
    private int stallTurn;
    private Location currentLoc;
    private Direction currentDirection;
    private Action cacheAction;


    // TODO: get the next action of the mower
    public Action nextAction(MowerMap map, Location currentLoc){

        SquareState[] neighbors = new SquareState[8];
        neighbors[0] = map[currentLoc.getX()][currentLoc.getY() + 1];
        neighbors[1] = map[currentLoc.getX() + 1][currentLoc.getY() + 1];
        neighbors[2] = map[currentLoc.getX() + 1][currentLoc.getY()];
        neighbors[3] = map[currentLoc.getX() + 1][currentLoc.getY() - 1];
        neighbors[4] = map[currentLoc.getX()][currentLoc.getY() - 1];
        neighbors[5] = map[currentLoc.getX() - 1][currentLoc.getY() - 1];
        neighbors[6] = map[currentLoc.getX() - 1][currentLoc.getY()];
        neighbors[7] = map[currentLoc.getX() - 1][currentLoc.getY() + 1];

        boolean isunkown = false;
        for(SquareState a : neighbors){
            if(a == SquareState.unknown){
                isunkown = true;
                break;
            }
        }

        if(isunkown == true )){
            cacheAction = Scan;
        }
        else if(map[currentloc[0] + 1 * currentDirection[0]][currentloc[1] + 1 * currentDirection[1]] == "grass" && map[currentloc[0] + 2 * currentDirection[0]][currentloc[1] + 2 * currentDirection[1]] == "grass"){
            cacheAction = [move, 2, direction = currentDirection];
        }
        else if(map[currentloc[0] + 1 * currentDirection[0]][currentloc[1] + 1 * currentDirection[1]] == "grass"){
            cacheAction = [move, 1, direction = currentDirection];
        }
        else if(map[currentloc[0] + 1 * currentDirection[0]][currentloc[1] + 1 * currentDirection[1]] == "crater" || map[currentloc[0] + 1 * currentDirection[0]][currentloc[1] + 1 * currentDirection[1]] == "puppy" || map[currentloc[0] + 1 * currentDirection[0]][currentloc[1] + 1 * currentDirection[1]] == "fence"){
            cacheAction = [move, 0, direction = currentDirection.next];
        }
        return null;
    }

    // TODO: update the mower instance
    public void updateMower(MowerMap map, Action a, Location loc, String s){
        if(a == Scan){
            map.updateMapFromScan(String[] neighbors);
        }
        elif(a == [move, 2, direction = currentDirection]){
            currentLoc[0] = currentloc[0] + 2 * currentDirection[0];
            currentLoc[1] = currentloc[1] + 2 * currentDirection[1];
        }
        elif(a == [move, 1, direction = currentDirection]){
            currentLoc[0] = currentloc[0] + 1 * currentDirection[0];
            currentLoc[1] = currentloc[1] + 1 * currentDirection[1];
        }
        elif(a == [move, 0, direction = currentDirection.next]){
            currentDirection = currentDirection.next;
        }
    }

    // TODO: set status
    public void setStatus(MowerMap map, Location currentLoc, String s){
        if(map.remainGrassNumber() == 0){
            currentStatus = MowerStatus.turnedOff;
        }
        else if(map[currentLoc.getX()][currentLoc.getY()] == SquareState.crater || map[currentLoc.getX()][currentLoc.getY()] == SquareState.fence){
            currentStatus = MowerStatus.crashed;
        }
        else if(map[currentLoc.getX()][currentLoc.getY()] == SquareState.puppy_mower){
            currentStatus = MowerStatus.stalled;
        }
        else{
            currentStatus = MowerStatus.active;
        }
    }

    public MowerStatus getCurrentStatus() {

        return currentStatus;
    }

    public void setCurrentStatus(MowerStatus currentStatus) {

        this.currentStatus = currentStatus;
    }

    public int getStallTurn() {

        return stallTurn;
    }

    public void setStallTurn(int stallTurn) {

        this.stallTurn = stallTurn;
    }

    public Location getCurrentLoc() {

        return currentLoc;
    }

    public void setCurrentLoc(Location currentLoc) {

        this.currentLoc = currentLoc;
    }

    public Direction getCurrentDirection() {

        return currentDirection;
    }

    public void setCurrentDirection(Direction currentDirection) {

        this.currentDirection = currentDirection;
    }

    public Action getCacheAction() {

        return cacheAction;
    }

    public void setCacheAction(Action cacheAction) {

        this.cacheAction = cacheAction;
    }
}
