package data;

import elements.UpElementsPanel;
import elements.TableNoGaps;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadWriteData {

    public static void readTable(String filename) {
        MarksData.setMatrix(HSSFData.readHSSFJournal(filename));
        readOther();
        TableNoGaps.addRenderer(HSSFData.getStyleMap());
        JournalTableModel model = new JournalTableModel();
        model.setMatrix(MarksData.getMatrix());
        TableNoGaps.setModel(model);
        TableNoGaps.resizeTable();
    }

    public static void writeTable() {
        HSSFData.writeHSSFJournal(MarksData.getMatrix());
    }

    public static void readOther() {
        try {
            Scanner scanner = new Scanner(new File("Другое.txt"));
            String string = scanner.nextLine();
            SkillsMap.getDefenseSkill().setField(Integer.parseInt(string.substring(string.indexOf(": ")+2)));
            string = scanner.nextLine();
            SkillsData.setCoins(Integer.parseInt(string.substring(string.indexOf(": ")+2)));
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

    public static void writeOther() {
        try
        {
            FileWriter writer = new FileWriter("Другое.txt", false);

            writer.write("Защита: " + SkillsMap.getDefenseSkill().getIntField() + "\n");
            writer.write("Долг: " + SkillsData.getCoins() + "\n");
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
}
