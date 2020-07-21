package data;

import data.skills.Skill;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainData {

    private final HSSFData hssfData = new HSSFData();
    private final JournalTableModel model = new JournalTableModel();
    private ArrayList<ArrayList<Mark>> matrix = null;
    private final ArrayList<Boolean> light = new ArrayList<>();
    Map<String, Map<Integer, Skill>> allSkillMap = new HashMap<>();

    public MainData() {
        addSkillMap();
    }

    public void readTable(JTable table, String filename) {
        matrix = hssfData.readHSSFJournal(filename);
        for (int i = 0; i < matrix.size(); i++)
            light.add(false);
        model.setMatrix(matrix);
        table.setModel(model);
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
        baSkillMap.put(1,null);
        baSkillMap.put(2,null);
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
        smSkillMap.put(5,null);
        smSkillMap.put(6,null);
        allSkillMap.put("sm",smSkillMap);
    }

    public Skill getSkill(String className, int number) {
        return allSkillMap.get(className).get(number);
    }
}
