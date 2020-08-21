package data;

import com.sun.xml.bind.v2.model.core.EnumLeafInfo;
import data.skills.*;
import elements.UpElementsPanel;
import elements.skills.SkillsPanel;
import elements.TableNoGaps;
import frame.MainFrame;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MainData {

    private final HSSFData hssfData = new HSSFData();
    private final JournalTableModel model = new JournalTableModel();
    private final MainFrame mainFrame;
    private final TableNoGaps tableNoGaps;
    private ArrayList<ArrayList<Mark>> matrix = null;
    private final ArrayList<Integer> light = new ArrayList<>();
    private final Map<String, Map<Integer, Skill>> allSkillMap = new HashMap<>();
    private final Map<String,ArrayList<YX>> bodyBagMap = new HashMap<>();

    public MainData(MainFrame mainFrame) throws IOException {
        this.mainFrame = mainFrame;
        this.tableNoGaps = mainFrame.getTableNoGaps();
        addSkillMap();
    }

    public void changeUpElements() {
        getMainFrame().getUpElementsPanel().changeElements();
    }

    public boolean minusPoint(int value) {
        if (getForconsList().minusPoint(value)) {
            getMainFrame().getDownElementsPanel().changeElements();
            return true;
        }
        return false;
    }

    public void minusAllPoint() {
        getForconsList().minusAllPoint();
        getMainFrame().getDownElementsPanel().changeElements();
    }

    public int getLevel() {
        return getForconsList().getLevel();
    }

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    private ForconsList getForconsList() {
        return mainFrame.getForconsList();
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
        matrix = hssfData.readHSSFJournal(filename);
        readOther();
        tableNoGaps.addRenderer(hssfData.getMap());
        model.setMatrix(matrix);
        tableNoGaps.setModel(model);
        tableNoGaps.resizeTable();
    }

    public void writeTable() throws IOException {
        hssfData.writeHSSFJournal(matrix);
    }

    public void writeOther() {
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

    public void noExiled(String name) {
        bodyBagMap.get(name).forEach((xy) -> getMark(xy).bite());

    }

    public void exiled(String name) {
        bodyBagMap.get(name).forEach((xy) -> getMark(xy).setBodyBag(""));
        bodyBagMap.remove(name);
    }

    public Map<String, ArrayList<YX>> getBodyBagMap() {
        return bodyBagMap;
    }

    public void addBodyBag(YX xy) {
        String[] string = getForconsList().getSelectedValue().split(",");
        if (bodyBagMap.get(string[1]) == null) {
            ArrayList<YX> array = new ArrayList<>();
            array.add(xy);
            bodyBagMap.put(string[1],array);
        } else
            bodyBagMap.get(string[1]).add(xy);
    }

    public void readOther() {
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
            changeUpElements();
        } catch (FileNotFoundException e) {
            System.out.println("Ошибка чтения списка форсонов");
        }
    }

    public void addLight(int number) {
        if (number >= 0 && number < getSize())
            light.add(number);
        else
            System.out.println("Ошибка addLight");
    }

    public boolean lightContains(int row) {
        for (Integer integer : light)
            if (integer == row)
                return true;
        return false;
    }

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
        baSkillMap.put(3,null); //внезапная смерть
        baSkillMap.put(4,null); //чеканная монета
        baSkillMap.put(5,new BardBodyBagSkill(this));
        baSkillMap.put(6,new BardDefenseSkill(this));
        allSkillMap.put("ba",baSkillMap);

        Map<Integer, Skill> inSkillMap = new HashMap<>();
        inSkillMap.put(1,new SimpleAttackSkill(this,"inseClaws", 2, 0.5));
        inSkillMap.put(2,null); //цундере
        inSkillMap.put(3,null);
        inSkillMap.put(4,null);
        inSkillMap.put(5,null);
        inSkillMap.put(6,null);
        allSkillMap.put("in",inSkillMap);

        Map<Integer, Skill> saSkillMap = new HashMap<>();
        saSkillMap.put(1,new SimpleAttackSkill(this,"samuKatana", 1, 1.0));
        saSkillMap.put(2,null);
        saSkillMap.put(3,new SamurComplementSkill(this));
        saSkillMap.put(4,new SamurTruckSkill(this));
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
