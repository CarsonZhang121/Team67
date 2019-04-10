package Model;

import Viewer.Location;
import Viewer.SquareState;

public class RealLawn extends Lawn {
    private int width;
    private int length;

    public RealLawn() {
    }

    public RealLawn(int width, int length) {
        this.width = width;
        this.length = length;
    }

    public RealLawn(int width) {
        this.width = width;
    }

    public void setSquare(Location loc, String status){
        int x = loc.getX();
        int y = loc.getY();
        this.squares[x][y] = SquareState.valueOf(status);
    }

    public boolean cutSquare(Location loc){
        int x = loc.getX();
        int y = loc.getY();
        boolean iscut = false;
        if (this.squares[x][y] == SquareState.empty){
            iscut = true;
        }
        else if(this.squares[x][y] == SquareState.grass){
            iscut = false;
        }
        return iscut;
    }

    public String getSquareState(Location loc){
        int x = loc.getX();
        int y = loc.getY();
        return squares[x][y].toString();
    }

    public void initSquares(int[][] pos, String[] state){
        try {
            int l;
            for (int i = 0; i < this.width; i++){
                for (int j = 0; j < this.length; j++){
                    Location loc = new Location();
                    loc.setX(i);
                    loc.setY(j);
                    setSquare(loc, "grass");
                }
            }

            if ((l = pos.length) == state.length) {
                for (int i = 0; i < l; i++){
                    Location loc = new Location();
                    loc.setX(pos[i][0]);
                    loc.setY(pos[i][1]);
                    setSquare(loc, state[i]);
                }
            }

        } catch (Exception e){
            System.out.println("[ERROR]: Invalid initialization of real lawn!");
        }
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
