package Viewer;

public class Action {
    private String name;
    private int stepSize;
    private Direction direction;

    //TODO: set the mower action
    public void setMoveAction(int stepSize, Direction d){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStepSize() {
        return stepSize;
    }

    public void setStepSize(int stepSize){ this.stepSize = stepSize;}

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {this.direction = direction;}

}
