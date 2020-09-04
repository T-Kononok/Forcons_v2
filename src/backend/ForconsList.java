package backend;

import backend.models.ForsonsListModel;
import backend.skills.BaBodyBagSkill;
import backend.skills.InBugUseSkill;
import backend.skills.InMicroTransactionSkill;
import backend.skills.SaOvercomingSkill;
import frontend.frame.DownElementsPanel;
import frontend.renderer.ForconsRenderer;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ForconsList {
    private static final ForsonsListModel forconsListModel = new ForsonsListModel();
    private static final JList<String> forconsList = new JList<>();
    private static boolean microTrans = false;
    private static int firstIndex = -1;

    static {
        forconsList.setModel(forconsListModel);
        forconsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        forconsList.setCellRenderer(new ForconsRenderer());
        forconsList.setOpaque(false);
        forconsList.addListSelectionListener(evt -> {
            if (evt.getValueIsAdjusting()) {
                DownElementsPanel.onFon();
                DownElementsPanel.changeElements();
                if (microTrans) {
                    if (!getSelectedValue().split("_")[0].equals("in")) {
                        microTrans = false;
                        InMicroTransactionSkill.inListener(getSelectedIndex());
                    }
                    forconsList.setSelectedIndex(firstIndex);
                } else
                    firstIndex = forconsList.getSelectedIndex();
            }
        });
    }

    public static void setMicroTrans(boolean microTrans) {
        ForconsList.microTrans = microTrans;
    }

    public static JList<String> getList(){
        return forconsList;
    }

    public static int getSelectedIndex() {
        return forconsList.getSelectedIndex();
    }

    public static int getFirstIndex() {
        return firstIndex;
    }

    public static String getSelectedValue() {
        return forconsListModel.get(getSelectedIndex());
    }

    public static void add(int index, String string) {
        forconsListModel.add(index, string);
        forconsList.validate();
    }

    public static void add(String string) {
        forconsListModel.add(string);
        forconsList.validate();
    }

    public static ForsonsListModel getForconsListModel() {
        return forconsListModel;
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

    public static boolean canMinusPoint(int value) {
        String string = getSelectedValue();
        int point = Integer.parseInt(string.substring(string.lastIndexOf(",")+1));
        point -= value;
        return point >= 0;
    }

    private static boolean changeValue(int value) {
        String string = getSelectedValue();
        int point = Integer.parseInt(string.substring(string.lastIndexOf("_")+1));
        point += value;
        if (point < 0)
            return false;
        String sub = string.substring(0,string.lastIndexOf("_")+1);
        forconsListModel.set(getSelectedIndex(),sub+point);
        DownElementsPanel.changeElements();
        return true;
    }

    public static boolean minusPoint(int value) {
        return changeValue(-value);
    }

    public static void minusAllPoint() {
        String string = getSelectedValue();
        int point = Integer.parseInt(string.substring(string.lastIndexOf("_")+1));
        minusPoint(point);
    }

    public static void plusPoint(int value) {
        changeValue(value);
    }

    public static void plusPoint(int index, int value) {
        String string = forconsListModel.get(index);
        int point = Integer.parseInt(string.substring(string.lastIndexOf("_")+1));
        point += value;
        String sub = string.substring(0,string.lastIndexOf("_")+1);
        forconsListModel.set(index,sub+point);
        DownElementsPanel.changeElements();
    }

    public static int getLevel() {
        String string = getSelectedValue();
        return Integer.parseInt(string.substring(string.indexOf("_",3)+1,string.lastIndexOf("_")));
    }

    public static void read(String filename) {
        try {
            Scanner scanner = new Scanner(new File(filename));
            while (scanner.hasNextLine())
                forconsListModel.add(scanner.nextLine());
            SaOvercomingSkill.checkOvercoming();
            sortPoint();
            sortClass();
        } catch (FileNotFoundException e) {
            System.out.println("Ошибка чтения списка форсонов");
        }
    }
}
