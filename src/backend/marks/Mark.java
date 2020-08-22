package backend.marks;

import backend.YX;

public class Mark extends AbstractMark{

    private final int row;
    private final int col;

    public Mark(int row, int col) {
        this.row = row;
        this.col = col;
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
