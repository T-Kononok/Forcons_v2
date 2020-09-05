package backend;

import backend.marks.CellsData;
import backend.models.JournalTableModel;
import backend.skills.bardSkill.BaBodyBagSkill;
import backend.skills.bardSkill.BaCoinsSkill;
import backend.skills.bardSkill.BaDefenseSkill;
import backend.skills.insectoidSkill.InDLCSkill;
import backend.skills.smotritelSkill.SmRaspberrySkill;
import frontend.frame.UpElementsPanel;
import frontend.frame.TableNoGaps;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadWriteData {

    public static void readTable(String filename) {
        CellsData.setMatrix(HSSFData.readHSSFJournal(filename));
        readOther();
        TableNoGaps.addRenderer(HSSFData.getStyleMap());
        JournalTableModel model = new JournalTableModel();
        model.setMatrix(CellsData.getMatrix());
        TableNoGaps.setModel(model);
        TableNoGaps.resizeTable();
    }

    public static void writeTable() {
        HSSFData.writeHSSFJournal(CellsData.getMatrix());
    }

    public static void readOther() {
        try {
            File file = new File(HSSFData.getFileName() + " другое.txt");
            if (!file.exists())
                return;
            Scanner scanner = new Scanner(file);
            if (!scanner.hasNextLine())
                return;
            String string = scanner.nextLine();
            while (scanner.hasNextLine()) {
                switch (string) {
                    case "Защита:":
                        string = scanner.nextLine();
                        BaDefenseSkill.set(Integer.parseInt(string));
                        if (scanner.hasNextLine())
                            string = scanner.nextLine();
                        break;
                    case "Долг:":
                        string = scanner.nextLine();
                        BaCoinsSkill.set(Integer.parseInt(string));
                        if (scanner.hasNextLine())
                            string = scanner.nextLine();
                        break;
                    case "Малина:":
                        string = scanner.nextLine();
                        String[] subStrings = string.split(" ");
                        for (String subString : subStrings)
                            SmRaspberrySkill.getRaspberryArray().add(Integer.parseInt(subString));
                        if (scanner.hasNextLine())
                            string = scanner.nextLine();
                        break;
                    case "DLC:":
                        while (scanner.hasNextLine()) {
                            string = scanner.nextLine();
                            if (!string.contains("in_"))
                                break;
                            InDLCSkill.getDLCArray().add(string);
                        }
                        break;
                    case "Бадибэг:":
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
                            BaBodyBagSkill.getBodyBagMap().put(name, array);
                        }
                        break;
                }
            }
            UpElementsPanel.changeElements();
        } catch (FileNotFoundException e) {
            System.out.println("Ошибка в readOther");
        }
    }

    public static void writeOther() {
        try
        {
            FileWriter writer = new FileWriter(
                    HSSFData.getFileName() + " другое.txt", false);
            writer.write("Защита:\n" + BaDefenseSkill.get() + "\n");
            writer.write("Долг:\n" + BaCoinsSkill.get() + "\n");
            if (SmRaspberrySkill.getRaspberryArray().size() > 0) {
                writer.write("Малина:" + "\n");
                SmRaspberrySkill.getRaspberryArray().forEach((i) -> {
                    try {
                        writer.write(i + " ");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                writer.write("\n");
            }
            if (InDLCSkill.getDLCArray().size() > 0) {
                writer.write("DLC:" + "\n");
                InDLCSkill.getDLCArray().forEach((string) -> {
                    try {
                        writer.write(string + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
            if (BaBodyBagSkill.getBodyBagMap().size() > 0) {
                writer.write("Бадибэг:" + "\n");
                BaBodyBagSkill.getBodyBagMap().forEach((s, array) -> {
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
