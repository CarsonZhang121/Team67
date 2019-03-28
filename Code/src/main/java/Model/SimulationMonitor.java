package Model;

import Viewer.Direction;
import Viewer.Location;
import Viewer.MowerStatus;

public class SimulationMonitor {
    private MowerStatus currentStatus;
    private Direction[] mowerDirections;
    private Puppy[] puppyList;
    private RealLawn lawn;
    private int totalSize;
    private int tatoalGrass;
    private int stoppedMowers;


    //TODO: move the mower
    public String moveMower(LawnMower mower){
        return null;
    }

    // TODO: mower scan
    public String[] scan(Location loc){
        return null;
    }

    // TODO: determine if the simulation should stop
    public void stopSimulation(){

    }

    // TODO: get # of grasses cut
    public int getCutGrass(){
        return 0;
    }

    // TODO: update the puppy
    public void updatePuppy(){

    }

    public MowerStatus getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(MowerStatus currentStatus) {
        this.currentStatus = currentStatus;
    }

    public Direction[] getMowerDirections() {
        return mowerDirections;
    }

    public void setMowerDirections(Direction[] mowerDirections) {
        this.mowerDirections = mowerDirections;
    }

    public Puppy[] getPuppyList() {
        return puppyList;
    }

    public void setPuppyList(Puppy[] puppyList) {
        this.puppyList = puppyList;
    }

    public RealLawn getLawn() {
        return lawn;
    }

    public void setLawn(RealLawn lawn) {
        this.lawn = lawn;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public int getTatoalGrass() {
        return tatoalGrass;
    }

    public void setTatoalGrass(int tatoalGrass) {
        this.tatoalGrass = tatoalGrass;
    }

    public int getStoppedMowers() {
        return stoppedMowers;
    }

    public void setStoppedMowers(int stoppedMowers) {
        this.stoppedMowers = stoppedMowers;
    }
}
