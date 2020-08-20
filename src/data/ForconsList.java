package data;

import elements.ForconsRenderer;
import frame.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ForconsList {
    private final ForsonsListModel forconsListModel = new ForsonsListModel();
    private final JList<String> forconsList = new JList<>();
    private int firstIndex = -1;
    private int secondIndex = -1;
    private final MainData mainData;

    public ForconsList(MainData mainData) {
        this.mainData = mainData;
        forconsList.setModel(forconsListModel);
        setMultipleSelection(true);
        forconsList.setCellRenderer(new ForconsRenderer(mainData));
        forconsList.setOpaque(false);
    }

    public void setMultipleSelection(boolean bol) {
        if (bol)
            forconsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        else
            forconsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public JList<String> getList(){
        return forconsList;
    }

    public int getSelectedIndex() {
        if (forconsList.getSelectedIndices().length == 1) {
            firstIndex = forconsList.getSelectedIndex();
            secondIndex = -1;
        }
        if (forconsList.getSelectedIndices().length == 2) {
            if (forconsList.getSelectedIndices()[1] == firstIndex)
                secondIndex = forconsList.getSelectedIndices()[0];
            else
                secondIndex = forconsList.getSelectedIndices()[1];
        }
        return firstIndex;
    }

    public String getSelectedValue() {
        return forconsListModel.get(getSelectedIndex());
    }

    public boolean isTwoSelected(int index) {
        return (index == firstIndex || index == secondIndex);
    }

    public void add(int index, String string) {
        forconsListModel.add(index, string);
        forconsList.validate();
    }

    public void set(int index, String string) {
        forconsListModel.set(index,string);
    }

    public void sortPoint() {
        forconsList.clearSelection();
        forconsListModel.sortPoint();
    }

    public void sortClass() {
        forconsList.clearSelection();
        forconsListModel.sortClass();
    }

//    public boolean canMinusPoint(int index, int value) {
//        String string = forconsListModel.get(index);
//        int point = Integer.parseInt(string.substring(string.lastIndexOf(",")+1));
//        point -= value;
//        return point >= 0;
//    }

    public boolean minusPoint(int value) {
        String string = getSelectedValue();
        int point = Integer.parseInt(string.substring(string.lastIndexOf(",")+1));
        point -= value;
        if (point < 0)
            return false;
        String sub = string.substring(0,string.lastIndexOf(",")+1);
        forconsListModel.set(getSelectedIndex(),sub+point);
        return true;
    }

    public void minusAllPoint() {
        String string = getSelectedValue();
        int point = Integer.parseInt(string.substring(string.lastIndexOf(",")+1));
        minusPoint(point);
    }

    public int getLevel() {
        String string = getSelectedValue();
        return Integer.parseInt(string.substring(string.indexOf(",",3)+1,string.lastIndexOf(",")));
    }

    public void read(String filename) {
        try {
            Scanner scanner = new Scanner(new File(filename));
            while (scanner.hasNextLine())
                forconsListModel.add(scanner.nextLine());
            checkBodyBag();
            sortPoint();
            sortClass();
        } catch (FileNotFoundException e) {
            System.out.println("Ошибка чтения списка форсонов");
        }
    }

    private void checkBodyBag() {
        forconsListModel.getArray().forEach((s) -> {
            String[] subStrs = s.split(",");
            if (mainData.getBodyBagMap().get(subStrs[1]) != null)
                if (Integer.parseInt(subStrs[3])>=7)
                    mainData.noExiled(subStrs[1]);
                else
                    mainData.exiled(subStrs[1]);
        });
        mainData.getTableNoGaps().repaint();
    }

}
