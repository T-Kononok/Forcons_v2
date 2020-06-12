package data;

import elements.ForconsRenderer;

import javax.swing.*;
import java.util.Vector;

public class ForconsList {
    private final SortForconsVector vectorForsons = new SortForconsVector();
    private final DefaultListModel<String> forconsListModel = new DefaultListModel<>();
    private final JList<String> forconsList = new JList<>();

    public ForconsList() {
        forconsList.setModel(forconsListModel);
        forconsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        forconsList.setCellRenderer(new ForconsRenderer());
    }

    public Vector<String> getVector() {
        return vectorForsons.getVector();
    }

    public JList<String> getList(){
        return forconsList;
    }

    public void sortPoint() {
        forconsList.clearSelection();
        forconsListModel.clear();
        vectorForsons.sortPoint();
        vectorForsons.getVector().forEach(forconsListModel::addElement);
    }

    public void  sortClass() {
        forconsList.clearSelection();
        forconsListModel.clear();
        vectorForsons.sortClass();
        vectorForsons.getVector().forEach(forconsListModel::addElement);
    }

}
