package Model;

import Viewer.Location;
import Viewer.SquareState;

public class RealLawn extends Lawn {
    private int width;
    private int height;

    public RealLawn() {
    }

    public RealLawn(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public RealLawn(int width) {
        this.width = width;
    }

    public void setSquare(Location loc, String status){
        int x = loc.getX();
        int y = loc.getY();
        this.squares[x][y] = SquareState.valueOf(status);
    }

    // no need to consider puppy here.
    public boolean cutSquare(Location loc){
        int x = loc.getX();
        int y = loc.getY();

        if (x < 0 || x > width-1 || y < 0 || y > height-1) return false; // fence

        if (squares[x][y] == SquareState.empty) return true;
        if (squares[x][y] == SquareState.grass) {
            squares[x][y] = SquareState.empty;
            return true;
        }
        return false;
    }

    public SquareState getSquareState(Location loc){
        int x = loc.getX();
        int y = loc.getY();
        if (x < 0 || x > width-1 || y < 0 || y > height-1) return SquareState.fence;
        return squares[x][y];
    }

    public void initSquares(int[][] pos, String[] state){
        try {
            int l;
            for (int i = 0; i < this.width; i++){
                for (int j = 0; j < this.height; j++){
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

    private void renderHorizontalBar(int size) {
        System.out.print(" ");
        for (int k = 0; k < size; k++) {
            System.out.print("-");
        }
        System.out.println("");
    }

    public void renderLawn(Location[] mowerLocations) {
        int i, j;
        int charWidth = 2 * width + 2;

        // display the rows of the lawn from top to bottom
        for (j = height - 1; j >= 0; j--) {
            renderHorizontalBar(charWidth);

            // display the Y-direction identifier
            System.out.print(j);

            // display the contents of each square on this row
            for (i = 0; i < width; i++) {
                System.out.print("|");

                boolean isMower = false;
                for (Location l : mowerLocations) {
                    if (i == l.getX() && j == l.getY()) {
                        System.out.print("M");
                        isMower = true;
                        break;
                    }
                }

                if (isMower) continue;
                else {
                    if (squares[i][j] == SquareState.empty) System.out.print(" ");
                    else if (squares[i][j] == SquareState.grass) System.out.print("g");
                    else if (squares[i][j] == SquareState.puppy_empty) System.out.print("p");
                    else if (squares[i][j] == SquareState.puppy_grass) System.out.print("pg");
                    else if (squares[i][j] == SquareState.puppy_mower) System.out.print("pm");
                    else System.out.print("c");
                }
            }
            System.out.println("|");
        }
        renderHorizontalBar(charWidth);

        // display the column X-direction identifiers
        System.out.print(" ");
        for (i = 0; i < width; i++) {
            System.out.print(" " + i);
        }
        System.out.println("");
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getheight() {
        return height;
    }

    public void setheight(int height) {
        this.height = height;
    }
}
