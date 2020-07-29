package data;

import data.skills.BardBalladSkill;
import data.skills.SimpleAttackSkill;
import data.skills.Skill;
import data.skills.SmotrLightSkill;
import frame.MainFrame;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainData {

    private final JournalTableModel model = new JournalTableModel();
    private final JTable table;
    private final JTable kostTable;
    private final MainFrame mainFrame;
    private ArrayList<ArrayList<Mark>> matrix = null;
    private ArrayList<Integer> light = new ArrayList<>();
    Map<String, Map<Integer, Skill>> allSkillMap = new HashMap<>();

    public MainData(MainFrame mainFrame, JTable table, JTable kostTable) {
        this.mainFrame = mainFrame;
        this.table = table;
        this.kostTable = kostTable;
        addSkillMap();
    }

    public JTable getTable() {
        return table;
    }
    public JTable getKostTable() {
        return kostTable;
    }

    public ArrayList<ArrayList<Mark>> getMatrix() {
        return matrix;
    }
    public ArrayList<Integer> getLight() {
        return light;
    }

    public Mark getMark(int row, int column) {
        if (row >= 0 && row < matrix.size() && column >= 0 && column < matrix.get(0).size())
            return matrix.get(row).get(column);
        return null;
    }

    public Mark getMark(YX yx) {
        return getMark(yx.getY(),yx.getX());
    }

    public int getSize() {
        return matrix.size();
    }

    public int getRowSize() {
        return matrix.get(0).size();
    }

    public void readTable(JTable table, String filename) {
        HSSFData hssfData = new HSSFData();
        matrix = hssfData.readHSSFJournal(filename);
        model.setMatrix(matrix);
        table.setModel(model);
    }

    public void addLight(int number) {
        System.out.println(getSize() + " " + number);
        if (number >= 0 && number < getSize())
            light.add(number);
        else
            System.out.println("Ошибка addLight");
        mainFrame.changeLeftImage();
    }

    public boolean lightContains(int row) {
        for (Integer integer : light)
            if (integer == row)
                return true;
        return false;
    }

    public void repaintTable() {
        table.setVisible(false);
        kostTable.setVisible(false);
        model.setMatrix(matrix);
        table.setVisible(true);
        kostTable.setVisible(true);
    }

//    ///для проверок
//    private ArrayList<ArrayList<Mark>> createMatrix(int startRow, int countRow,int startColumn, int countColumn) {
//        ArrayList<ArrayList<Mark>> matrix2 = new ArrayList<>();
//        for (int i = startRow; i < countRow; i++) {
//            ArrayList<Mark> marks = new ArrayList<>();
//            for (int j = startColumn; j < countColumn; j++) {
//                marks.add(matrix.get(i).get(j));
//            }
//            matrix2.add(marks);
//        }
//        return matrix2;
//    }

    private void addSkillMap() {
        Map<Integer, Skill> baSkillMap = new HashMap<>();
        baSkillMap.put(1,new SimpleAttackSkill(this));
        baSkillMap.put(2,new BardBalladSkill(this));
        baSkillMap.put(3,null);
        baSkillMap.put(4,null);
        baSkillMap.put(5,null);
        baSkillMap.put(6,null);
        allSkillMap.put("ba",baSkillMap);
        Map<Integer, Skill> inSkillMap = new HashMap<>();
        inSkillMap.put(1,null);
        inSkillMap.put(2,null);
        inSkillMap.put(3,null);
        inSkillMap.put(4,null);
        inSkillMap.put(5,null);
        inSkillMap.put(6,null);
        allSkillMap.put("in",inSkillMap);
        Map<Integer, Skill> saSkillMap = new HashMap<>();
        saSkillMap.put(1,null);
        saSkillMap.put(2,null);
        saSkillMap.put(3,null);
        saSkillMap.put(4,null);
        saSkillMap.put(5,null);
        saSkillMap.put(6,null);
        allSkillMap.put("sa",saSkillMap);
        Map<Integer, Skill> smSkillMap = new HashMap<>();
        smSkillMap.put(1,null);
        smSkillMap.put(2,null);
        smSkillMap.put(3,null);
        smSkillMap.put(4,null);
        smSkillMap.put(5,new SmotrLightSkill(this));
        smSkillMap.put(6,null);
        allSkillMap.put("sm",smSkillMap);
    }

    public Skill getSkill(String className, int number) {
        return allSkillMap.get(className).get(number);
    }
}
