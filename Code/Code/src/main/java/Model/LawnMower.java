package Model;

import Viewer.*;
import com.sun.tools.javac.util.ArrayUtils;
import sun.java2d.xr.DirtyRegion;


public class LawnMower {
    private MowerStatus currentStatus;
    private int stallTurn;
    private Location currentLoc;
    private Direction currentDirection;
    private Action cacheAction;


    // TODO: get the next action of the mower
    public Action nextAction(MowerMap map, Location currentLoc) {

        SquareState[] neighbors = new SquareState[8];
        neighbors[0] = map.map[currentLoc.getX()][currentLoc.getY() + 1];
        neighbors[1] = map.map[currentLoc.getX() + 1][currentLoc.getY() + 1];
        neighbors[2] = map.map[currentLoc.getX() + 1][currentLoc.getY()];
        neighbors[3] = map.map[currentLoc.getX() + 1][currentLoc.getY() - 1];
        neighbors[4] = map.map[currentLoc.getX()][currentLoc.getY() - 1];
        neighbors[5] = map.map[currentLoc.getX() - 1][currentLoc.getY() - 1];
        neighbors[6] = map.map[currentLoc.getX() - 1][currentLoc.getY()];
        neighbors[7] = map.map[currentLoc.getX() - 1][currentLoc.getY() + 1];

        boolean isUnkown = false;
        for (SquareState a : neighbors) {
            if (a == SquareState.unknown) {
                isUnkown = true;
                break;
            }
        }

        int[] newLoc = new int[2];
        newLoc[0] = currentLoc.getX() + getXY(currentDirection)[0];
        newLoc[1] = currentLoc.getY() + getXY(currentDirection)[1];

        if (isUnkown){
            scan(map, currentLoc);
        }
        else if (map.map[newLoc[0]][newLoc[1]] == SquareState.grass) {
            cacheAction.setStepSize(1);
        }
        else if (map.map[newLoc[0]][newLoc[1]] == SquareState.crater || map.map[newLoc[0]][newLoc[1]] == SquareState.mower || map.map[newLoc[0]][newLoc[1]] == SquareState.puppy_grass || map.map[newLoc[0]][newLoc[1]] == SquareState.puppy_empty || map.map[newLoc[0]][newLoc[1]] == SquareState.puppy_mower || map.map[newLoc[0]][newLoc[1]] == SquareState.fence) {
            changeDirection(currentDirection);
        }
        return null;
    }

    private void scan(MowerMap map, Location currentLoc) {

        SquareState[] scanResult = new SquareState[8];
        map.map[currentLoc.getX()][currentLoc.getY() + 1] = scanResult[0];
        map.map[currentLoc.getX() + 1][currentLoc.getY() + 1] = scanResult[1];
        map.map[currentLoc.getX() + 1][currentLoc.getY()] = scanResult[2];
        map.map[currentLoc.getX() + 1][currentLoc.getY() - 1] = scanResult[3];
        map.map[currentLoc.getX()][currentLoc.getY() - 1] = scanResult[4];
        map.map[currentLoc.getX() - 1][currentLoc.getY() - 1] = scanResult[5];
        map.map[currentLoc.getX() - 1][currentLoc.getY()] = scanResult[6];
        map.map[currentLoc.getX() - 1][currentLoc.getY() + 1] = scanResult[7];
    }

    private void changeDirection(Direction currentDirection) {
        if(currentDirection == Direction.North){
            this.currentDirection = Direction.Northeast;
        }
        else if(currentDirection == Direction.Northeast){
            this.currentDirection = Direction.East;
        }
        else if(currentDirection == Direction.East){
            this.currentDirection = Direction.Southeast;
        }
        else if(currentDirection == Direction.Southeast){
            this.currentDirection = Direction.South;
        }
        else if(currentDirection == Direction.South){
            this.currentDirection = Direction.Southwest;
        }
        else if(currentDirection == Direction.Southwest){
            this.currentDirection = Direction.West;
        }
        else if(currentDirection == Direction.West){
            this.currentDirection = Direction.Northwest;
        }
        else if(currentDirection == Direction.Northwest){
            this.currentDirection = Direction.North;
        }
    }

    public int[] getXY(Direction currentDirection){
        int[] xy = new int[2];
        if(currentDirection == Direction.North){
            xy[0] = 0;
            xy[1] = 1;
        }
        else if(currentDirection == Direction.Northeast){
            xy[0] = 1;
            xy[1] = 1;
        }
        else if(currentDirection == Direction.East){
            xy[0] = 1;
            xy[1] = 0;
        }
        else if(currentDirection == Direction.Southeast){
            xy[0] = 1;
            xy[1] = -1;
        }
        else if(currentDirection == Direction.South){
            xy[0] = 0;
            xy[1] = -1;
        }
        else if(currentDirection == Direction.Southwest){
            xy[0] = -1;
            xy[1] = -1;
        }
        else if(currentDirection == Direction.West){
            xy[0] = -1;
            xy[1] = 0;
        }
        else if(currentDirection == Direction.Northwest){
            xy[0] = -1;
            xy[1] = 1;
        }
        return xy;
    }

    // TODO: update the mower instance
    public void updateMower(MowerMap map, Action cacheAction, Location currentLoc, String s) {
        currentLoc.setX(currentLoc.getX() + getXY(currentDirection)[0]);
        currentLoc.setY(currentLoc.getY() + getXY(currentDirection)[1]);
    }

    // TODO: set status
    public void setStatus(MowerMap map, Location currentLoc, String s) {
        if (map.remainGrassNumber() == 0) {
            currentStatus = MowerStatus.turnedOff;
        } else if (map.map[currentLoc.getX()][currentLoc.getY()] == SquareState.crater || map.map[currentLoc.getX()][currentLoc.getY()] == SquareState.fence) {
            currentStatus = MowerStatus.crashed;
        } else if (map.map[currentLoc.getX()][currentLoc.getY()] == SquareState.puppy_mower) {
            currentStatus = MowerStatus.stalled;
        } else {
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
