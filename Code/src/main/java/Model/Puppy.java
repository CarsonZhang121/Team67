package Model;

import Viewer.Location;

public class Puppy {
    private Location puppyLocations;
    // List the puppyStatus here
    private int puppyStatus;


    // TODO: add nextAction for Puppy
    public String nextAction(){
        return null;
    }

    public Location getPuppyLocations() {
        return puppyLocations;
    }

    public void setPuppyLocations(Location puppyLocations) {
        this.puppyLocations = puppyLocations;
    }

    public int getPuppyStatus() {
        return puppyStatus;
    }

    public void setPuppyStatus(int puppyStatus) {
        this.puppyStatus = puppyStatus;
    }
}
