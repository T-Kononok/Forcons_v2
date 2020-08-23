package backend.marks;

import backend.YX;
import backend.skills.SamurKillerQueenSkill;

import java.io.IOException;

public class Mark extends AbstractMark{

    private final int row;
    private final int col;

    public Mark(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public void minus(int minusValue) throws IOException {
        super.minus(minusValue);
        if(isBomb())
            SamurKillerQueenSkill.blast(this);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public YX getYX() {
        return new YX(row,col);
    }
}
