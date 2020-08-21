package data;

import data.skills.*;
import elements.DownElementsPanel;
import elements.UpElementsPanel;
import elements.skills.SkillsPanel;
import elements.TableNoGaps;
import frame.MainFrame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MainData {

    private static final JournalTableModel model = new JournalTableModel();
    private static TableNoGaps tableNoGaps;
    private static ArrayList<ArrayList<Mark>> matrix = null;
    private static final ArrayList<Integer> light = new ArrayList<>();
    private static final Map<String, Map<Integer, Skill>> allSkillMap = new HashMap<>();
    private static final Map<String,ArrayList<YX>> bodyBagMap = new HashMap<>();

    static {
        addSkillMap();
    }

    public static void setTableNoGaps(TableNoGaps table) {
        tableNoGaps = table;
    }

    public static boolean minusPoint(int value) {
        if (ForconsList.minusPoint(value)) {
            DownElementsPanel.changeElements();
            return true;
        }
        return false;
    }

    public static void minusAllPoint() {
        ForconsList.minusAllPoint();
        DownElementsPanel.changeElements();
    }

    public static SkillsPanel getSkillsPanel() {
        return tableNoGaps.getSkillsPanel();
    }

    public static ArrayList<Integer> getLight() {
        return light;
    }

    public static Mark getMark(int row, int column) {
        return matrix.get(row).get(column);
    }

    public static Mark getMark(YX yx) {
        return getMark(yx.getY(),yx.getX());
    }

    public static int getSize() {
        return matrix.size();
    }

    public static int getRowSize() {
        return matrix.get(0).size();
    }

    public static void readTable(String filename) {
        matrix = HSSFData.readHSSFJournal(filename);
        readOther();
        tableNoGaps.addRenderer(HSSFData.getMap());
        model.setMatrix(matrix);
        tableNoGaps.setModel(model);
        tableNoGaps.resizeTable();
    }

    public static void writeTable() {
        HSSFData.writeHSSFJournal(matrix);
    }

    public static void writeOther() {
        try
        {
            FileWriter writer = new FileWriter("Другое.txt", false);

            writer.write("Защита: " + Skill.getBuffDefense() + "\n");
            writer.write("Долг: " + Skill.getCoins() + "\n");
            if (bodyBagMap.size() > 0) {
                writer.write("Бадибэг: " + "\n");
                bodyBagMap.forEach((s, array) -> {
                    try {
                        writer.write(s + "\n");
                        for (YX yx : array)
                            writer.write(yx.toString() + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
            writer.flush();
        }
        catch(IOException ex){
            System.out.println("Ошибка в writeOther");
        }
    }

    public static void noExiled(String name) {
        bodyBagMap.get(name).forEach((xy) -> getMark(xy).minus(1));
    }

    public static void exiled(String name) {
        bodyBagMap.get(name).forEach((xy) -> getMark(xy).setBodyBag(""));
        bodyBagMap.remove(name);
    }

    public static Map<String, ArrayList<YX>> getBodyBagMap() {
        return bodyBagMap;
    }

    public static void addBodyBag(YX xy) {
        String[] string = ForconsList.getSelectedValue().split(",");
        if (bodyBagMap.get(string[1]) == null) {
            ArrayList<YX> array = new ArrayList<>();
            array.add(xy);
            bodyBagMap.put(string[1],array);
        } else
            bodyBagMap.get(string[1]).add(xy);
    }

    public static void readOther() {
        try {
            Scanner scanner = new Scanner(new File("Другое.txt"));
            String string = scanner.nextLine();
            Skill.setBuffDefense(Integer.parseInt(string.substring(string.indexOf(": ")+2)));
            string = scanner.nextLine();
            Skill.setCoins(Integer.parseInt(string.substring(string.indexOf(": ")+2)));
            if (scanner.hasNextLine()) {
                scanner.nextLine();
                string = scanner.nextLine();
                while (scanner.hasNextLine()) {
                    String name = string;
                    ArrayList<YX> array = new ArrayList<>();
                    while (scanner.hasNextLine()) {
                        string = scanner.nextLine();
                        if (!string.contains(","))
                            break;
                        array.add(new YX(string));
                    }
                    bodyBagMap.put(name, array);
                }
            }
            UpElementsPanel.changeElements();
        } catch (FileNotFoundException e) {
            System.out.println("Ошибка чтения списка форсонов");
        }
    }

    public static void addLight(int number) {
        if (number >= 0 && number < getSize())
            light.add(number);
        else
            System.out.println("Ошибка addLight");
    }

    public static boolean lightContains(int row) {
        for (Integer integer : light)
            if (integer == row)
                return true;
        return false;
    }

    public static TableNoGaps getTableNoGaps() {
        return tableNoGaps;
    }

    private static void addSkillMap() {
        Map<Integer, Skill> baSkillMap = new HashMap<>();
        baSkillMap.put(1,new SimpleAttackSkill("bardChain", 1, 0.75));
        baSkillMap.put(2,new BardBalladSkill());
        baSkillMap.put(3,null); //внезапная смерть
        baSkillMap.put(4,null); //чеканная монета
        baSkillMap.put(5,new BardBodyBagSkill());
        baSkillMap.put(6,new BardDefenseSkill());
        allSkillMap.put("ba",baSkillMap);

        Map<Integer, Skill> inSkillMap = new HashMap<>();
        inSkillMap.put(1,new SimpleAttackSkill("inseClaws", 2, 0.5));
        inSkillMap.put(2,null); //цундере
        inSkillMap.put(3,null);
        inSkillMap.put(4,null);
        inSkillMap.put(5,null);
        inSkillMap.put(6,null);
        allSkillMap.put("in",inSkillMap);

        Map<Integer, Skill> saSkillMap = new HashMap<>();
        saSkillMap.put(1,new SimpleAttackSkill("samuKatana", 1, 1.0));
        saSkillMap.put(2,null);
        saSkillMap.put(3,new SamurComplementSkill());
        saSkillMap.put(4,new SamurTruckSkill());
        saSkillMap.put(5,null);
        saSkillMap.put(6,null);
        allSkillMap.put("sa",saSkillMap);

        Map<Integer, Skill> smSkillMap = new HashMap<>();
        smSkillMap.put(1,new SimpleAttackSkill("smotrGame", 1, 0.75, 3, 0.17));
        smSkillMap.put(2,null);
        smSkillMap.put(3,null);
        smSkillMap.put(4,null);
        smSkillMap.put(5,new SmotrLightSkill());
        smSkillMap.put(6,null);
        allSkillMap.put("sm",smSkillMap);
    }

    public static Skill getSkill(String className, int number) {
        return allSkillMap.get(className).get(number);
    }
}
