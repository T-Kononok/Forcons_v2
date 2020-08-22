package data;

import java.util.ArrayList;

public class MarksData {

    private static ArrayList<ArrayList<Mark>> matrix = null;

    public static void setMatrix(ArrayList<ArrayList<Mark>> matrix) {
        MarksData.matrix = matrix;
    }

    public static ArrayList<ArrayList<Mark>> getMatrix() {
        return matrix;
    }

    public static Mark getMark(int row, int column) {
        return matrix.get(row).get(column);
    }

    public static Mark getMark(YX yx) {
        return getMark(yx.getY(),yx.getX());
    }

    public static int getRowCount() {
        return matrix.size();
    }

    public static int getColumnCount() {
        return matrix.get(0).size();
    }

}
