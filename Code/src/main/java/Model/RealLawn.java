package Model;

import Viewer.Location;

public class RealLawn extends Lawn {
    private int width;
    private int length;

    //TODO: Set the square state at a specified location
    public void setSquare(Location loc, String status){

    }

    //TODO: Mower cuts the grass
    public Boolean cutSquare(Location loc){

        return false;
    }

    //TODO: Get the state for a location in realLawn
    public String getSquareState(Location loc){
        return null;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
