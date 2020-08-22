package data;

import data.skills.BardBodyBagSkill;
import data.skills.BardCoinsSkill;
import data.skills.BardDefenseSkill;
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
            BardDefenseSkill.set(Integer.parseInt(string.substring(string.indexOf(": ")+2)));
            string = scanner.nextLine();
            BardCoinsSkill.set(Integer.parseInt(string.substring(string.indexOf(": ")+2)));
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
                    BardBodyBagSkill.getBodyBagMap().put(name, array);
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

            writer.write("Защита: " + BardDefenseSkill.get() + "\n");
            writer.write("Долг: " + BardCoinsSkill.get() + "\n");
            if (BardBodyBagSkill.getBodyBagMap().size() > 0) {
                writer.write("Бадибэг: " + "\n");
                BardBodyBagSkill.getBodyBagMap().forEach((s, array) -> {
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
