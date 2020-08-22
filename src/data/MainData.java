package data;

import data.skills.*;
import elements.DownElementsPanel;
import elements.UpElementsPanel;
import elements.skills.SkillsPanel;
import elements.TableNoGaps;

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

    public static void setTableNoGaps(TableNoGaps table) {
        tableNoGaps = table;
    }

    public static SkillsPanel getSkillsPanel() {
        return tableNoGaps.getSkillsPanel();
    }

    public static TableNoGaps getTableNoGaps() {
        return tableNoGaps;
    }

    public static void readTable(String filename) {
        MarksData.setMatrix(HSSFData.readHSSFJournal(filename));
        readOther();
        tableNoGaps.addRenderer(HSSFData.getMap());
        model.setMatrix(MarksData.getMatrix());
        tableNoGaps.setModel(model);
        tableNoGaps.resizeTable();
    }

    public static void writeTable() {
        HSSFData.writeHSSFJournal(MarksData.getMatrix());
    }

    public static void writeOther() {
        try
        {
            FileWriter writer = new FileWriter("Другое.txt", false);

            writer.write("Защита: " + Skill.getBuffDefense() + "\n");
            writer.write("Долг: " + Skill.getCoins() + "\n");
            if (SkillsData.getBodyBagMap().size() > 0) {
                writer.write("Бадибэг: " + "\n");
                SkillsData.getBodyBagMap().forEach((s, array) -> {
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
                    SkillsData.getBodyBagMap().put(name, array);
                }
            }
            UpElementsPanel.changeElements();
        } catch (FileNotFoundException e) {
            System.out.println("Ошибка чтения списка форсонов");
        }
    }
}
