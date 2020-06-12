package data;

import elements.ForconsRenderer;

import javax.swing.*;
import java.util.ArrayList;

public class ForconsList {
    private final SortForconsArray arrayForsons = new SortForconsArray();
    private final DefaultListModel<String> forconsListModel = new DefaultListModel<>();
    private final JList<String> forconsList = new JList<>();

    public ForconsList() {
        forconsList.setModel(forconsListModel);
        forconsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        forconsList.setCellRenderer(new ForconsRenderer());
    }

    public ArrayList<String> getArray() {
        return arrayForsons.getArray();
    }

    public JList<String> getList(){
        return forconsList;
    }

    public void sortPoint() {
        forconsList.clearSelection();
        forconsListModel.clear();
        arrayForsons.sortPoint();
        arrayForsons.getArray().forEach(forconsListModel::addElement);
    }

    public void  sortClass() {
        forconsList.clearSelection();
        forconsListModel.clear();
        arrayForsons.sortClass();
        arrayForsons.getArray().forEach(forconsListModel::addElement);
    }

}
