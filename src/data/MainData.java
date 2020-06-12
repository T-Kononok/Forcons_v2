package data;

import javax.swing.*;
import java.util.ArrayList;

public class MainData {

    private HSSFData hssfData = new HSSFData();
    private JournalTableModel model = new JournalTableModel();
    private ArrayList<ArrayList<String>> matrix = null;

    public void readTable(JTable table, String filename) {
        matrix = hssfData.readHSSFJournal(filename);
        model.setMatrix(matrix);
        table.setModel(model);
    }
}
