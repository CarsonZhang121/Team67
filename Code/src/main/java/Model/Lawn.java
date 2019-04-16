package Model;

import Viewer.SquareState;

public class Lawn {
    protected SquareState[][] squares;
    private int grassCut = 0;
    private int grassRemain = 0;

    // Would it be better to use variables to record (1) the # of grass cut and
    // (2) the # of grass left

    public int getGrassCut(){
        for (SquareState[] a : squares)
            for(SquareState b : a)
                if(b == SquareState.empty)
                    this.grassCut += 1;

        return grassCut;
    }

    public int getGrassRemain(){
        return this.grassRemain;
    }

    // update the grass state only when the grass is cut
    public void updateGrassNum(){
        this.grassCut += 1;
        this.grassRemain -= 1;
    }

    public void setGrassRemain(int grassRemain) {
        this.grassRemain = grassRemain;
    }
}
