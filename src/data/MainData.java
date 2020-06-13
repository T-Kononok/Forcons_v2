package data;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class MainData {

    private HSSFData hssfData = new HSSFData();
    private JournalTableModel model = new JournalTableModel();
    private ArrayList<ArrayList<String>> matrix = null;
    private ArrayList<Boolean> light;

    public void readTable(JTable table, String filename) {
        table.setModel(new DefaultTableModel());
        matrix = hssfData.readHSSFJournal(filename);
        for (int i = 0; i < matrix.size(); i++)
            light.add(false);
        model.setMatrix(matrix);
        table.setModel(model);
    }

    public int getColumnCount() {
        return matrix.size();
    }

    public int getRowCount() {
        return matrix.get(0).size();
    }
}
