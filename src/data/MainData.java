package data;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class MainData {

    private HSSFData hssfData = new HSSFData();
    private JournalTableModel model = new JournalTableModel();
    private ArrayList<ArrayList<Mark>> matrix = null;
    private ArrayList<Boolean> light = new ArrayList<>();

    public void readTable(JTable table, String filename) {
        matrix = hssfData.readHSSFJournal(filename);
        for (int i = 0; i < matrix.size(); i++)
            light.add(false);
        model.setMatrix(matrix);
        table.setModel(model);
    }

    ///для проверок
    private ArrayList<ArrayList<Mark>> createMatrix(int startRow, int countRow,int startColumn, int countColumn) {
        ArrayList<ArrayList<Mark>> matrix2 = new ArrayList<>();
        for (int i = startRow; i < countRow; i++) {
            ArrayList<Mark> marks = new ArrayList<>();
            for (int j = startColumn; j < countColumn; j++) {
                marks.add(matrix.get(i).get(j));
            }
            matrix2.add(marks);
        }
        return matrix2;
    }

    public int getColumnCount() {
        return matrix.size();
    }

    public int getRowCount() {
        return matrix.get(0).size();
    }
}
