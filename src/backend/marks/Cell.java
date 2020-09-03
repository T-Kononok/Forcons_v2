package backend.marks;

import backend.YX;
import backend.skills.SaKillerQueenSkill;
import backend.skills.SmBadSkill;

import java.io.IOException;

public class Cell extends SeparateCell {

    private final int row;
    private final int col;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public void plusCheck(int plusValue) throws IOException {
        plus(plusValue);
        if (isBomb())
            SaKillerQueenSkill.blast(this);
    }

    public void minusCheck(int minusValue) throws IOException {
        Cell cell = SmBadSkill.checkBad(this);
        cell.minus(minusValue);
        if (cell != this)
            SmBadSkill.startTransferFon(cell.getYX());
        if (cell.isBomb())
            SaKillerQueenSkill.blast(cell);
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
