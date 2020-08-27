package backend.skills;

import backend.*;
import backend.marks.Cell;
import backend.marks.CellsData;
import frontend.frame.TableNoGaps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BaBodyBagSkill extends Skill {

    private static final Map<String,ArrayList<YX>> bodyBagMap = new HashMap<>();

    public static Map<String, ArrayList<YX>> getBodyBagMap() {
        return bodyBagMap;
    }

    public static void noExiled(String name) {
        bodyBagMap.get(name).forEach((xy) -> {
            try {
                CellsData.getCell(xy).minusCheck(1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void exiled(String name) {
        bodyBagMap.get(name).forEach((xy) -> CellsData.getCell(xy).setBodyBag(false));
        bodyBagMap.remove(name);
    }

    public static void addBodyBag(YX xy) {
        String[] string = ForconsList.getSelectedValue().split("_");
        if (bodyBagMap.get(string[1]) == null) {
            ArrayList<YX> array = new ArrayList<>();
            array.add(xy);
            bodyBagMap.put(string[1],array);
        } else
            bodyBagMap.get(string[1]).add(xy);
    }

    public static void checkBodyBag() {
        ForconsList.getForconsListModel().getArray().forEach((s) -> {
            String[] subStrs = s.split("_");
            if (bodyBagMap.get(subStrs[1]) != null)
                if (Integer.parseInt(subStrs[3])>=7)
                    noExiled(subStrs[1]);
                else
                    exiled(subStrs[1]);
        });
        TableNoGaps.getSkillsPanel().repaint();
    }

    @Override
    public void begin() throws IOException {
        if (!ForconsList.minusPoint(2))
            return;
        ArrayList<YX> arrayList = new ArrayList<>();
        int max = 0;
        Cell cell;
        for (int i = 0; i < CellsData.getRowCount(); i++) {
            for (int j = 0; j < CellsData.getColumnCount(); j++) {
                cell = CellsData.getCell(i,j);
                if (cell.isNonBorder() && cell.isCanBite()) {
                    if (cell.get() == max)
                        arrayList.add(new YX(i, j));
                    if (cell.get() > max) {
                        max = cell.get();
                        arrayList.clear();
                        arrayList.add(new YX(i, j));
                    }
                }
            }
        }
        Random rand = new Random();
        int randIndex = rand.nextInt(arrayList.size());
        cell = CellsData.getCell(arrayList.get(randIndex));
        if (checkChance(0.5 + bodyBagMap.size() * 0.05, cell)) {
            cell.setBodyBag(true);
            addBodyBag(arrayList.get(randIndex));
            startFon(arrayList.get(randIndex), "bardBodyBag");
        }
        else
            startFon(arrayList.get(randIndex), "bardBodyBagMiss");
        for (int i = 0; i < 2; i++) {
            BaCoinsSkill.checkCoin();
            BaDeathSkill.checkDeath();
        }
    }
}

