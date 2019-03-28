package Model;

import Viewer.Action;
import Viewer.Direction;
import Viewer.Location;
import Viewer.MowerStatus;


public class LawnMower {
    private MowerStatus currentStatus;
    private int stallTurn;
    private Location currentLoc;
    private Direction currentDirection;
    private Action cacheAction;


    // TODO: get the next action of the mower
    public Action nextAction(MowerMap map){

        return null;
    }

    // TODO: update the mower instance
    public void updateMower(MowerMap map, Action a, Location loc, String s){

    }

    // TODO: set status
    public void setStatus(MowerMap map, String s){

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
