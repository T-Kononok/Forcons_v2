package data;

import elements.ForconsRenderer;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ForconsList {
    private final ForsonsListModel forconsListModel = new ForsonsListModel();
    private final JList<String> forconsList = new JList<>();

    public ForconsList() {
        forconsList.setModel(forconsListModel);
        forconsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        forconsList.setCellRenderer(new ForconsRenderer());
    }

    public JList<String> getList(){
        return forconsList;
    }

    public int getSelectedIndex() {
        return forconsList.getSelectedIndex();
    }

    public String getSelectedValue() {
        return forconsList.getSelectedValue();
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

    public boolean canMinusPoint(int index, int value) {
        String string = forconsListModel.get(index);
        int point = Integer.parseInt(string.substring(string.lastIndexOf(",")+1));
        point -= value;
        return point >= 0;
    }

    public void minusPoint(int index, int value) {
        String string = forconsListModel.get(index);
        String sub = string.substring(0,string.lastIndexOf(",")+1);
        int point = Integer.parseInt(string.substring(string.lastIndexOf(",")+1));
        point -= value;
        if (point >= 0)
            forconsListModel.set(index,sub+point);
    }

    public void read(String filename) {
        try {
            Scanner scanner = new Scanner(new File(filename));
            while (scanner.hasNextLine())
                forconsListModel.add(scanner.nextLine());
            sortPoint();
            sortClass();
        } catch (FileNotFoundException e) {
            System.out.println("Ошибка чтения списка форсонов");
        }
    }

}
