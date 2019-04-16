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
    // TO DO: should also set mower's initial locations in MowerMap !
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
            lawn.setSquare(mowerLocations[i], SquareState.mower);
            mowerMap.setSquare(mowerLocations[i], SquareState.mower, Direction.south); // the last parameter does not matter in this case.
            mowerList[i] = new LawnMower(mowerLocations[i], mowerDirections[i]);
        }

        Location[] craterLocs = input.getCraterLocations();
        for (int i = 0; i < craterLocs.length; i++) {
            lawn.setSquare(craterLocs[i], SquareState.crater);
            totalGrass -= 1;
        }

        Location[] puppyLocations = input.getPuppyLocations();
        puppyList = new Puppy[puppyLocations.length];
        for (int i = 0; i < puppyLocations.length; i++) {
            lawn.setSquare(puppyLocations[i], SquareState.puppy_grass);
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
    // major change: also update mwoermap in this method. simplify code.
    public void moveMower(int mowerID, Action action){
        int xOrientation, yOrientation;

        int stepSize = action.getStepSize(); // get the move step size
        int x = mowerLocations[mowerID].getX();
        int y = mowerLocations[mowerID].getY();
        xOrientation = xDIR_MAP.get(mowerDirections[mowerID]);
        yOrientation = yDIR_MAP.get(mowerDirections[mowerID]);
        while (stepSize > 0) {
            x += xOrientation;
            y += yOrientation;
            SquareState status = lawn.getSquareState(new Location(x, y));
            // check other Mower
            if (status == SquareState.mower || status == SquareState.puppy_mower) {
                // stop before the other mower's square.
                x -= xOrientation;
                y -= yOrientation;
                // update current mower location.
                mowerLocations[mowerID] = new Location(x, y);
                // also update this location is real Lawn.
                lawn.setSquare(mowerLocations[mowerID], SquareState.mower);
                // also update this location in mower map.
                mowerMap.setSquare(mowerLocations[mowerID], SquareState.mower, mowerDirections[mowerID]);
                mowerList[mowerID].setStallTurn(stallTurn);
                return;
            }

            // check puppy (puppy_grass, puppy_empty)
            if (status.toString().substring(0, 5).equals("puppy")) {
                mowerLocations[mowerID] = new Location(x, y); // mower move into puppy's location.
                lawn.setSquare(mowerLocations[mowerID], SquareState.puppy_mower);
                mowerMap.setSquare(mowerLocations[mowerID], SquareState.puppy_mower, mowerDirections[mowerID]);
                mowerList[mowerID].setStallTurn(stallTurn);

                // set previous location to empty.
                Location prevLoc = new Location(x-xOrientation, y-yOrientation);
                if (lawn.getSquareState(prevLoc) == SquareState.mower) {
                    lawn.setSquare(prevLoc, SquareState.empty);
                    mowerMap.setSquare(prevLoc, SquareState.empty, mowerDirections[mowerID]);
                }
                else if (lawn.getSquareState(prevLoc) == SquareState.puppy_mower) {
                    lawn.setSquare(prevLoc, SquareState.puppy_empty);
                    mowerMap.setSquare(prevLoc, SquareState.puppy_empty, mowerDirections[mowerID]);
                }
                return;
            }

            // check if square can cut or not. No need to consider puppy or mower here.
            // set previous location to empty.
            Location prevLoc = new Location(x-xOrientation, y-yOrientation);
            if (lawn.getSquareState(prevLoc) == SquareState.mower) {
                lawn.setSquare(prevLoc, SquareState.empty);
                mowerMap.setSquare(prevLoc, SquareState.empty, mowerDirections[mowerID]);
            }
            else if (lawn.getSquareState(prevLoc) == SquareState.puppy_mower) {
                lawn.setSquare(prevLoc, SquareState.puppy_empty);
                mowerMap.setSquare(prevLoc, SquareState.puppy_empty, mowerDirections[mowerID]);
            }

            if (!lawn.cutSquare(new Location(x, y))) {
                // if crashed, the crashed mower supports to know it crashed into fence or crater.
                if (lawn.getSquareState(new Location(x, y)) == SquareState.fence)
                    mowerMap.setSquare(new Location(x, y), SquareState.fence, mowerDirections[mowerID]);
                else
                    mowerMap.setSquare(new Location(x, y), SquareState.crater, mowerDirections[mowerID]);
                mowerList[mowerID].setCurrentStatus(MowerStatus.crashed);
                stoppedMowers += 1;
                return;
            } else {
                // set current location to mower.
                lawn.setSquare(new Location(x, y), SquareState.mower);
                mowerMap.setSquare(new Location(x, y), SquareState.mower, mowerDirections[mowerID]);
            }
            stepSize -= 1;
        }
        mowerLocations[mowerID] = new Location(x, y);
        mowerDirections[mowerID] = action.getDirection();
        lawn.setSquare(mowerLocations[mowerID], SquareState.mower);
        mowerMap.setSquare(mowerLocations[mowerID], SquareState.mower, mowerDirections[mowerID]);
        mowerList[mowerID].setCurrentLoc(mowerLocations[mowerID]);
        mowerList[mowerID].setCurrentDirection(mowerDirections[mowerID]);
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


    // TODO: get # of grasses cut
    public int getCutGrass(){
        return lawn.getGrassCut();
    }

    // TODO: update the puppy
    public void updatePuppy(){
        for (int i = 0; i < puppyList.length; i++) {
            String action = puppyList[i].nextAction();
            if (action == "stay") continue;
            else {
                // scan surrounding
                Location currentLoc = puppyList[i].getPuppyLocation();
                SquareState[] sur = scan(currentLoc);
                int randomMoveChoice = randGenerator.nextInt(sur.length);
                // find a safe random location.
                while (!(sur[randomMoveChoice] == SquareState.empty ||
                        sur[randomMoveChoice] == SquareState.grass ||
                        sur[randomMoveChoice] == SquareState.mower)) {
                    randomMoveChoice = randGenerator.nextInt(sur.length);
                }
                Direction[] dirs = new Direction[]{Direction.north, Direction.northeast, Direction.east,
                        Direction.southeast, Direction.south, Direction.southwest, Direction.west,
                        Direction.northwest};

                int xOrientation = xDIR_MAP.get(dirs[randomMoveChoice]);
                int yOrientation = yDIR_MAP.get(dirs[randomMoveChoice]);
                int x = currentLoc.getX() + xOrientation;
                int y = currentLoc.getY() + yOrientation;
                Location newLoc = new Location(x, y);
                puppyList[i].setPuppyLocation(newLoc);
                // update the real lawn based on puppy movement.
                // update the new location.
                if (lawn.getSquareState(newLoc) == SquareState.empty) lawn.setSquare(newLoc, SquareState.puppy_empty);
                else if (lawn.getSquareState(newLoc) == SquareState.grass) lawn.setSquare(newLoc, SquareState.puppy_grass);
                else {
                    // check the mower list.
                    lawn.setSquare(newLoc, SquareState.puppy_mower);
                    for (int j = 0; j < mowerList.length; j++) {
                        if (x == mowerLocations[j].getX() && y == mowerLocations[j].getY())
                            mowerList[j].setStallTurn(stallTurn);
                    }
                }
                // update previous location.
                if (lawn.getSquareState(currentLoc) == SquareState.puppy_grass) lawn.setSquare(currentLoc, SquareState.grass);
                else if (lawn.getSquareState(currentLoc) == SquareState.puppy_empty) lawn.setSquare(currentLoc, SquareState.empty);
                else if (lawn.getSquareState(currentLoc) == SquareState.puppy_mower) lawn.setSquare(currentLoc, SquareState.mower);
            }
        }
    }

    public boolean issimulationOn() {
        return simulationOn;
    }

    public void runOneTurn() {
        if (totalTurn == 114) {
            System.out.println("stop");
        }
        System.out.println(totalTurn);
        if (totalTurn == 0) {
            simulationOn = false;
            return;
        }
        // update mower
        for (int i = 0; i < mowerList.length; i++) {
            if (mowerList[i].getCurrentStatus() == MowerStatus.crashed) continue;
            if (mowerList[i].getCurrentStatus() == MowerStatus.turnedOff) continue;
            if (mowerList[i].getCurrentStatus() == MowerStatus.stalled) {
                mowerList[i].setStallTurn(mowerList[i].getStallTurn() - 1);
                continue;
            }
            System.out.print("Mower at Location: ");
            System.out.println(String.format("(%d, %d)", mowerLocations[i].getX(), mowerLocations[i].getY()));
            Action act = mowerList[i].nextAction(mowerMap);
            System.out.println(String.format("Action: %s", act.getName()));
            if (act.getName().equals("move")) {
                moveMower(i, act);
                System.out.println(String.format("Current direction: %s", mowerDirections[i].toString()));
                System.out.println(String.format("Step size: %d", act.getStepSize()));
                System.out.println(String.format("Next direction: %s", act.getDirection().toString()));
            }
            else if (act.getName().equals("scan")) {
                SquareState[] sur = scan(mowerLocations[i]);
                mowerMap.updateMapFromScan(mowerLocations[i], sur);
            }
            else {
                mowerList[i].setCurrentStatus(MowerStatus.turnedOff);
                stoppedMowers += 1;
            }
        }
        if (stoppedMowers == mowerList.length) {
            simulationOn = false;
            return;
        }
        // update puppy
        updatePuppy();

        totalTurn -= 1;
        lawn.renderLawn(mowerLocations);
        mowerMap.renderLawn();
    }

    public void report() {
        System.out.print(totalSize);
        System.out.print(",");
        System.out.print(totalGrass);
        System.out.print(",");
        System.out.print(getCutGrass());
    }
}
