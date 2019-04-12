package Model;

import Viewer.*;

import java.util.HashMap;
import java.util.Random;

public class SimulationMonitor {
    private static Random randGenerator;
    private Location[] mowerLocations;
    private Direction[] mowerDirections;
    private Puppy[] puppyList;
    private LawnMower[] mowerList;
    private RealLawn lawn;
    private MowerMap mowerMap;
    private int totalSize;
    private int totalGrass;
    private int stoppedMowers;
    private boolean simulationOn;
    private int totalTurn;
    private int stallTurn;
    private double stayPercent;

    private HashMap<Direction, Integer> xDIR_MAP;
    private HashMap<Direction, Integer> yDIR_MAP;

    // initialize the simulation.
    public void initialize(InputFile input) {
        randGenerator = new Random();
        simulationOn = true;
        lawn = new RealLawn(input.getLawnWidth(), input.getLawnHeight());
        mowerMap = new MowerMap();
        mowerMap.initializeMap();
        lawn.squares = new SquareState[input.getLawnWidth()][input.getLawnHeight()];
        mowerLocations = input.getMowerLocations();
        mowerDirections = input.getMowerInitialDirections();
        totalSize = input.getLawnWidth() * input.getLawnHeight();
        totalGrass = totalSize;
        totalTurn = input.getTotalTurn();
        stallTurn = input.getStallTurn();
        stayPercent = input.getStayPercent();
        stoppedMowers = 0;

        for(int i = 0; i < input.getLawnWidth(); i++){
            for(int j = 0; j < input.getLawnHeight(); j++){
                lawn.squares[i][j] = SquareState.grass;
            }
        }

        mowerList = new LawnMower[mowerLocations.length];
        for (int i = 0; i < mowerLocations.length; i++) {
            lawn.setSquare(mowerLocations[i], "empty");
            mowerList[i] = new LawnMower(mowerLocations[i], mowerDirections[i]);
        }

        Location[] craterLocs = input.getCraterLocations();
        for (int i = 0; i < craterLocs.length; i++) {
            lawn.setSquare(craterLocs[i], "crater");
            totalGrass -= 1;
        }

        Location[] puppyLocations = input.getPuppyLocations();
        puppyList = new Puppy[puppyLocations.length];
        for (int i = 0; i < puppyLocations.length; i++) {
            lawn.setSquare(puppyLocations[i], "puppy_grass");
            puppyList[i] = new Puppy(stayPercent);
            puppyList[i].setPuppyLocation(puppyLocations[i]);
        }
    }

    public SimulationMonitor() {
        xDIR_MAP = new HashMap<Direction, Integer>();
        xDIR_MAP.put(Direction.north, 0);
        xDIR_MAP.put(Direction.northeast, 1);
        xDIR_MAP.put(Direction.east, 1);
        xDIR_MAP.put(Direction.southeast, 1);
        xDIR_MAP.put(Direction.south, 0);
        xDIR_MAP.put(Direction.southwest, -1);
        xDIR_MAP.put(Direction.west, -1);
        xDIR_MAP.put(Direction.northwest, -1);

        yDIR_MAP = new HashMap<Direction, Integer>();
        yDIR_MAP.put(Direction.north, 1);
        yDIR_MAP.put(Direction.northeast, 1);
        yDIR_MAP.put(Direction.east, 0);
        yDIR_MAP.put(Direction.southeast, -1);
        yDIR_MAP.put(Direction.south, -1);
        yDIR_MAP.put(Direction.southwest, -1);
        yDIR_MAP.put(Direction.west, 0);
        yDIR_MAP.put(Direction.northwest, 1);
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
            SquareState status = lawn.getSquareState(new Location(x, y));
            if (status.toString().substring(0, 5).equals("puppy")) {
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
    public SquareState[] scan(Location loc){
        SquareState[] sur = new SquareState[8];
        int x = loc.getX();
        int y = loc.getY();
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
        simulationOn = false;
    }

    // TODO: get # of grasses cut
    public int getCutGrass(){
        return lawn.getGrassCut();
    }

    // TODO: update the puppy
    public void updatePuppy(){
        for (int i = 0; i < puppyList.length; i++) {
            String action = puppyList[i].nextAction(); // nextAction, should update the internal puppyStatus of Puppy.
            if (action == "stay") continue;
            else {
                // scan surrounding
                Location curentLoc = puppyList[i].getPuppyLocation();
                SquareState[] sur = scan(curentLoc);
                int randomMoveChoice = randGenerator.nextInt(sur.length);
                while (!(sur[randomMoveChoice].toString().equals("empty") || sur[randomMoveChoice].toString().equals("grass") ||
                        sur[randomMoveChoice].toString().equals("mower"))) {
                    randomMoveChoice = randGenerator.nextInt(sur.length);
                }
                Direction[] dirs = new Direction[]{Direction.north, Direction.northeast, Direction.east, Direction.southeast,
                        Direction.south, Direction.southwest, Direction.west, Direction.northeast};
                int xOrientation = xDIR_MAP.get(dirs[randomMoveChoice]);
                int yOrientation = yDIR_MAP.get(dirs[randomMoveChoice]);
                puppyList[i].setPuppyLocation(new Location(curentLoc.getX()+xOrientation,curentLoc.getY()+yOrientation));
            }
        }
    }

    public boolean issimulationOn() {
        return simulationOn;
    }

    public void runOneTurn() {
        System.out.println(totalTurn);
        if (totalTurn == 0) {
            simulationOn = false;
            return;
        }
        for (int i = 0; i < mowerList.length; i++) {
            if (mowerList[i].getCurrentStatus() == MowerStatus.crashed) continue;
            if (mowerList[i].getCurrentStatus() == MowerStatus.stalled) {
                mowerList[i].setStallTurn(mowerList[i].getStallTurn()-1);
                continue;
            }

            Action act = mowerList[i].nextAction(mowerMap);
            if (act.getName().equals("move")) {
                String moveResult = moveMower(i, act);
                if (moveResult.equals("Moved")) mowerList[i].updateMower(mowerMap, act);
                else if (moveResult.equals("Stall")) {
                    mowerList[i].setStallTurn(stallTurn);
                    // update mowermap.
                    mowerList[i].setStatus(mowerMap, mowerLocations[i], lawn.getSquareState(mowerLocations[i]));
                }
                else {
                    mowerList[i].setCurrentStatus(MowerStatus.crashed);
                    // udpate mowermap.
                    mowerList[i].setStatus(mowerMap, mowerLocations[i], lawn.getSquareState(mowerLocations[i]));
                    stoppedMowers += 1;
                }
            } else if (act.getName().equals("scan")) {
                SquareState[] sur = scan(mowerLocations[i]);
                mowerMap.updateMapFromScan(mowerLocations[i], sur);
            } else {
                mowerList[i].setCurrentStatus(MowerStatus.turnedOff);
                stoppedMowers += 1;
            }
        }
        if (stoppedMowers == mowerList.length) {
            simulationOn = false;
            return;
        }
        updatePuppy(); // should also update mower status, if puppy move to mower.
        totalTurn -= 1;
        lawn.renderLawn(mowerLocations);
    }

    public void report() {
        System.out.print(totalSize);
        System.out.print(",");
        System.out.print(totalGrass);
        System.out.print(",");
        System.out.print(getCutGrass());
    }
}
