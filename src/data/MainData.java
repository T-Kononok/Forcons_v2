package data;

import data.skills.BardBalladSkill;
import data.skills.SimpleAttackSkill;
import data.skills.Skill;
import data.skills.SmotrLightSkill;
import elements.skills.SkillsPanel;
import elements.TableNoGaps;
import frame.MainFrame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainData {

    private final JournalTableModel model = new JournalTableModel();
    private final MainFrame mainFrame;
    private final TableNoGaps tableNoGaps;
    private ArrayList<ArrayList<Mark>> matrix = null;
    private final ArrayList<Integer> light = new ArrayList<>();
    Map<String, Map<Integer, Skill>> allSkillMap = new HashMap<>();

    public MainData(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.tableNoGaps = mainFrame.getTableNoGaps();
        addSkillMap();
    }

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public SkillsPanel getSkillsPanel() {
        return tableNoGaps.getSkillsPanel();
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

    public void readTable(String filename) {
        HSSFData hssfData = new HSSFData();
        matrix = hssfData.readHSSFJournal(filename);
        model.setMatrix(matrix);
        tableNoGaps.setModel(model);
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

//    public void repaintTable() {
//        model.setMatrix(matrix);
//        tableNoGaps.repaint();
//    }

    public TableNoGaps getTableNoGaps() {
        return tableNoGaps;
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
        baSkillMap.put(1,new SimpleAttackSkill(this,"bardChain", 1, 0.75));
        baSkillMap.put(2,new BardBalladSkill(this));
        baSkillMap.put(3,null);
        baSkillMap.put(4,null);
        baSkillMap.put(5,null);
        baSkillMap.put(6,null);
        allSkillMap.put("ba",baSkillMap);
        Map<Integer, Skill> inSkillMap = new HashMap<>();
        inSkillMap.put(1,new SimpleAttackSkill(this,"inseClaws", 2, 0.5));
        inSkillMap.put(2,null);
        inSkillMap.put(3,null);
        inSkillMap.put(4,null);
        inSkillMap.put(5,null);
        inSkillMap.put(6,null);
        allSkillMap.put("in",inSkillMap);
        Map<Integer, Skill> saSkillMap = new HashMap<>();
        saSkillMap.put(1,new SimpleAttackSkill(this,"samuKatana", 1, 1.0));
        saSkillMap.put(2,null);
        saSkillMap.put(3,null);
        saSkillMap.put(4,null);
        saSkillMap.put(5,null);
        saSkillMap.put(6,null);
        allSkillMap.put("sa",saSkillMap);
        Map<Integer, Skill> smSkillMap = new HashMap<>();
        smSkillMap.put(1,new SimpleAttackSkill(this,"smotrGame", 1, 0.75, 3, 0.17));
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
