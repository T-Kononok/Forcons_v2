package data;

import data.skills.SamurTsundereSkill;
import elements.ForconsRenderer;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class ForconsList {
    private static final ForsonsListModel forconsListModel = new ForsonsListModel();
    private static final JList<String> forconsList = new JList<>();
    private static int firstIndex = -1;
    private static int secondIndex = -1;

    static {
        forconsList.setModel(forconsListModel);
        setMultipleSelection(true);
        forconsList.setCellRenderer(new ForconsRenderer());
        forconsList.setOpaque(false);
    }

    public static void setMultipleSelection(boolean bol) {
        if (bol)
            forconsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        else
            forconsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public static JList<String> getList(){
        return forconsList;
    }

    public static int getSelectedIndex() {
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

    public static String getSelectedValue() {
        return forconsListModel.get(getSelectedIndex());
    }

    public static boolean isTwoSelected(int index) {
        return (index == firstIndex || index == secondIndex);
    }

    public static void add(int index, String string) {
        forconsListModel.add(index, string);
        forconsList.validate();
    }

    public static void set(int index, String string) {
        forconsListModel.set(index,string);
    }

    public static void sortPoint() {
        forconsList.clearSelection();
        forconsListModel.sortPoint();
    }

    public static void sortClass() {
        forconsList.clearSelection();
        forconsListModel.sortClass();
    }

//    public boolean canMinusPoint(int index, int value) {
//        String string = forconsListModel.get(index);
//        int point = Integer.parseInt(string.substring(string.lastIndexOf(",")+1));
//        point -= value;
//        return point >= 0;
//    }

    public static boolean minusPoint(int value) {
        String string = getSelectedValue();
        int point = Integer.parseInt(string.substring(string.lastIndexOf(",")+1));
        point -= value;
        if (point < 0)
            return false;
        String sub = string.substring(0,string.lastIndexOf(",")+1);
        forconsListModel.set(getSelectedIndex(),sub+point);
        return true;
    }

    public static void minusAllPoint() {
        String string = getSelectedValue();
        int point = Integer.parseInt(string.substring(string.lastIndexOf(",")+1));
        minusPoint(point);
    }

    public static int getLevel() {
        String string = getSelectedValue();
        return Integer.parseInt(string.substring(string.indexOf(",",3)+1,string.lastIndexOf(",")));
    }

    public static void read(String filename) {
        try {
            Scanner scanner = new Scanner(new File(filename));
            while (scanner.hasNextLine())
                forconsListModel.add(scanner.nextLine());
            checkBodyBag();
            checkOvercoming();
            sortPoint();
            sortClass();
        } catch (FileNotFoundException e) {
            System.out.println("Ошибка чтения списка форсонов");
        }
    }

    private static void checkOvercoming () {
        for (int i = 0; i < forconsListModel.getArray().size(); i++) {
            String string = forconsListModel.getArray().get(i);
            String[] subStrs = string.split(",");
            if (subStrs[0].equals("sa") && subStrs[2].equals("3") && Integer.parseInt(subStrs[3]) < 7) {
                int point = Integer.parseInt(string.substring(string.lastIndexOf(",")+1));
                point++;
                String sub = string.substring(0,string.lastIndexOf(",")+1);
                forconsListModel.set(i,sub+point);
            }
        }
    }

    private static void checkBodyBag() {
        forconsListModel.getArray().forEach((s) -> {
            String[] subStrs = s.split(",");
            if (MainData.getBodyBagMap().get(subStrs[1]) != null)
                if (Integer.parseInt(subStrs[3])>=7)
                    MainData.noExiled(subStrs[1]);
                else
                    MainData.exiled(subStrs[1]);
        });
        MainData.getTableNoGaps().repaint();
    }

    public static void checkTsundere() {
        SamurTsundereSkill skill = new SamurTsundereSkill();
        forconsListModel.getArray().forEach((s) -> {
            String[] subStrs = s.split(",");
            if (subStrs[0].equals("sa")) {
                try {
                    skill.begin();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
