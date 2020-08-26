package backend.marks;

import backend.YX;

import java.util.ArrayList;

public class CellsData {

    private static ArrayList<ArrayList<Cell>> matrix = null;

    public static void setMatrix(ArrayList<ArrayList<Cell>> matrix) {
        CellsData.matrix = matrix;
    }

    public static ArrayList<ArrayList<Cell>> getMatrix() {
        return matrix;
    }

    public static Cell secureGetMark(int row, int col) {
        if (row < 0 || row >= getRowCount() || col < 0 || col >= getColumnCount())
            return null;
        return getMark(row, col);
    }

    public static Cell getMark(int row, int col) {
        return matrix.get(row).get(col);
    }

    public static Cell getMark(YX yx) {
        return getMark(yx.getY(),yx.getX());
    }

    public static int getRowCount() {
        return matrix.size();
    }

    public static int getColumnCount() {
        return matrix.get(0).size();
    }

}
