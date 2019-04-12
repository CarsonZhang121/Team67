package Model;

import Model.*;
import Viewer.*;
import Controller.*;

import java.util.HashMap;
import java.util.Random;

public class SimulationMonitor {
    private static Random randGenerator;
    private Location[] mowerLocations;
    private Direction[] mowerDirections;
    private Puppy[] puppyList;
    private RealLawn lawn;
    private int totalSize;
    private int totalGrass;
    private int stoppedMowers;
    private boolean simulationOnStatus;

    private HashMap<Direction, Integer> xDIR_MAP;
    private HashMap<Direction, Integer> yDIR_MAP;

    public void initalize(InputFile input) {
        randGenerator = new Random();
        simulationOnStatus = true;
        lawn = new RealLawn(input.getLawnWidth(), input.getLawnHeight());
        lawn.squares = new SquareState[input.getLawnWidth()][input.getLawnHeight()];
        mowerLocations = input.getMowerLocations();
        mowerDirections = input.getMowerInitialDirections();
        totalSize = input.getLawnWidth() * input.getLawnHeight();
        totalGrass = totalSize;

        for(int i = 0; i < input.getLawnWidth(); i++){
            for(int j = 0; j < input.getLawnHeight(); j++){
                lawn.squares[i][j] = SquareState.grass;
            }
        }

        for (int i = 0; i < mowerLocations.length; i++) {
            lawn.setSquare(mowerLocations[i], "empty");
        }

        Location[] craterLocs = input.getCraterLocations();
        for (int i = 0; i < craterLocs.length; i++) {
            lawn.setSquare(craterLocs[i], "crater");
            totalGrass -= 1;
        }

        Location[] puppyLocations = input.getPuppyLocations();
        for (int i = 0; i < puppyLocations.length; i++) {
            lawn.setSquare(puppyLocations[i], "puppy_grass");
        }
        for(int i = 0; i < input.getLawnWidth(); i++){
            for(int j = 0; j < input.getLawnHeight(); j++){
                System.out.println(lawn.squares[i][j]);
            }
        }
    }

    public SimulationMonitor() {

        xDIR_MAP = new HashMap<>();
        xDIR_MAP.put(Direction.North, 0);
        xDIR_MAP.put(Direction.Northeast, 1);
        xDIR_MAP.put(Direction.East, 1);
        xDIR_MAP.put(Direction.Southeast, 1);
        xDIR_MAP.put(Direction.South, 0);
        xDIR_MAP.put(Direction.Southwest, -1);
        xDIR_MAP.put(Direction.West, -1);
        xDIR_MAP.put(Direction.Northwest, -1);

        yDIR_MAP = new HashMap<>();
        yDIR_MAP.put(Direction.North, 1);
        yDIR_MAP.put(Direction.Northeast, 1);
        yDIR_MAP.put(Direction.East, 0);
        yDIR_MAP.put(Direction.Southeast, -1);
        yDIR_MAP.put(Direction.South, -1);
        yDIR_MAP.put(Direction.Southwest, -1);
        yDIR_MAP.put(Direction.West, 0);
        yDIR_MAP.put(Direction.Northwest, 1);
    }


    //TODO: move the mower
    // assmue the input action is move
    public String moveMower(int mowerID, Action action){
        int xOrientation, yOrientation;

        int stepSize = action.getStepSize(); // get the move step size
        int x = mowerLocations[mowerID].getX();
        int y = mowerLocations[mowerID].getY();
        xOrientation = xDIR_MAP.get(mowerDirections[mowerID]);
        yOrientation = yDIR_MAP.get(mowerDirections[mowerID]);
        while (stepSize > 0) {
            x += xOrientation;
            y += yOrientation;
            // check puppy
            String status = lawn.getSquareState(new Location(x, y));
            if (status.substring(0, 5).equals("puppy")) {
                mowerLocations[mowerID] = new Location(x, y); // mower move into puppy's location.
                return "Stall";
            }

            // check if square can cut or not.
            if (!lawn.cutSquare(new Location(x, y))) {
                return "Crashed";
            }
            stepSize -= 1;
        }
        mowerLocations[0] = new Location(x, y);
        mowerDirections[0] = action.getDirection();
        return "Moved";
    }

    // TODO: mower scan
    public String[] scan(Location loc){
        String[] sur = new String[8];
        int x = mowerLocations[0].getX();
        int y = mowerLocations[0].getY();
        // clockwise scan
        sur[0] = lawn.getSquareState(new Location(x, y+1)); // North
        sur[1] = lawn.getSquareState(new Location(x+1, y+1)); // NorthEast
        sur[2] = lawn.getSquareState(new Location(x+1, y)); // East
        sur[3] = lawn.getSquareState(new Location(x+1, y-1)); // SouthEast
        sur[4] = lawn.getSquareState(new Location(x, y-1)); // South
        sur[5] = lawn.getSquareState(new Location(x-1, y-1)); // SouthWest
        sur[6] = lawn.getSquareState(new Location(x-1, y)); // West
        sur[7] = lawn.getSquareState(new Location(x-1, y+1)); // NorthWest
        return sur;
    }

    // TODO: stop the simulation
    public void stopSimulation(){
        simulationOnStatus = false;
    }

    // TODO: get # of grasses cut
    public int getCutGrass(){
        return lawn.getGrassCut();
    }

    // TODO: update the puppy
    public void updatePuppy(){
        for (Puppy p : puppyList) {
            String[] neightSquareState = new String[8];
            String action = p.nextAction(neightSquareState); // nextAction, should update the internal puppyStatus of Puppy.
            if (action == "stay") continue;
            else {
                // scan surrounding
                Location curentLoc = p.getPuppyLocations();
                String[] sur = scan(curentLoc);
                int randomMoveChoice = randGenerator.nextInt(sur.length);
                // Question? on page 2: puupy will move to safe (grass or empty) location.
                // Then, how could puppy move to a mower location?
                while (!(sur[randomMoveChoice].equals("empty") || sur[randomMoveChoice].equals("grass"))) {
                    randomMoveChoice = randGenerator.nextInt(sur.length);
                }
                Direction[] dirs = new Direction[]{Direction.North, Direction.Northeast, Direction.East, Direction.Southeast,
                        Direction.South, Direction.Southwest, Direction.West, Direction.Northeast};
                int xOrientation = xDIR_MAP.get(dirs[randomMoveChoice]);
                int yOrientation = yDIR_MAP.get(dirs[randomMoveChoice]);
                p.setPuppyLocations(new Location(curentLoc.getX()+xOrientation,curentLoc.getY()+yOrientation));
            }
        }
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
        return totalGrass;
    }

    public void setTatoalGrass(int tatoalGrass) {
        this.totalGrass = tatoalGrass;
    }

    public int getStoppedMowers() {
        return stoppedMowers;
    }

    public void setStoppedMowers(int stoppedMowers) {
        this.stoppedMowers = stoppedMowers;
    }
}
