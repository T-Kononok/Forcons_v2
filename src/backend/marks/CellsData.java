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

    public static Cell secureGetCell(int row, int col) {
        if (row < 0 || row >= getRowCount() || col < 0 || col >= getColumnCount())
            return null;
        return getCell(row, col);
    }

    public static Cell getCell(int row, int col) {
        return matrix.get(row).get(col);
    }

    public static Cell getCell(YX yx) {
        return getCell(yx.getY(),yx.getX());
    }

    public static int getRowCount() {
        return matrix.size();
    }

    public static int getColumnCount() {
        return matrix.get(0).size();
    }

}
