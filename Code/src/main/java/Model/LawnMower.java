package Model;

import Viewer.Action;
import Viewer.Direction;
import Viewer.Location;
import Viewer.MowerStatus;

import java.util.Arrays;


public class LawnMower {
    private MowerStatus currentStatus;
    private int stallTurn;
    private Location currentLoc;
    private Direction currentDirection;
    private Action cacheAction;


    // TODO: get the next action of the mower
    public Action nextAction(MowerMap map){

        String[] neighbors = new String[8];
        int x = currentLoc.getX();
        int y = currentLoc.getY();
        neighbors[0] = map.squares[x][y + 1].name();
        neighbors[1] = map.squares[x + 1][y + 1].name();
        neighbors[2] = map.squares[x + 1][y].name();
        neighbors[3] = map.squares[x + 1][y - 1].name();
        neighbors[4] = map.squares[x][y - 1].name();
        neighbors[5] = map.squares[x - 1][y - 1].name();
        neighbors[6] = map.squares[x - 1][y].name();
        neighbors[7] = map.squares[x - 1][y + 1].name();

        if(Arrays.asList(neighbors).contains("unkown")){
            cacheAction.setName("unknown");
        }
        else if(map[x + 1 * currentDirection[0]][y + 1 * currentDirection[1]] == "grass" && map[x + 2 * currentDirection[0]][y + 2 * currentDirection[1]] == "grass"){
            cacheAction = [move, 2, direction = currentDirection];
        }
        else if(map[x + 1 * currentDirection[0]][y + 1 * currentDirection[1]] == "grass"){
            cacheAction = [move, 1, direction = currentDirection];
        }
        else if(map[x + 1 * currentDirection[0]][y + 1 * currentDirection[1]] == "crater" || map[x + 1 * currentDirection[0]][y + 1 * currentDirection[1]] == "puppy" || map[currentloc[0] + 1 * currentDirection[0]][currentloc[1] + 1 * currentDirection[1]] == "fence"){
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
    public void setStatus(MowerMap map, String s){
        if(map.remainGrassNumber() == 0){
            currentStatus = "turned_off";
        }
        elif(map[currentLoc[0]][currentLoc[1]] == "crater" || map[currentLoc[0]][currentLoc[1]] == "fence"){
            currentStatus = "crashed";
        }
        elif(map[currentLoc[0]][currentLoc[1]] == "puppy_mower"){
            currentStatus = "stalled";
        }
        else{
            currentStatus = "active";
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
