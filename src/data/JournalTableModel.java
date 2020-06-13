package data;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class JournalTableModel extends AbstractTableModel {

    private ArrayList<ArrayList<Mark>> matrix;

    public void setMatrix(ArrayList<ArrayList<Mark>> matrix) {
        this.matrix = matrix;
    }

    public int getColumnCount() {
        if (matrix == null)
            return 0;
        return matrix.get(0).size();
    }

    public int getRowCount() {
        if (matrix == null)
            return 0;
        return matrix.size();
    }

    public Object getValueAt(int row, int col) {
        if (matrix == null)
            return 0;
        return matrix.get(row).get(col).toString();
    }

    public String getColumnName(int col) {
        return "";
    }

    public Class<?> getColumnClass(int col) {
        return String.class;
    }

}
