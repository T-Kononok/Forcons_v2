package backend.skills;

import backend.*;
import backend.marks.Mark;
import backend.marks.MarksData;
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
                MarksData.getMark(xy).minus(1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void exiled(String name) {
        bodyBagMap.get(name).forEach((xy) -> MarksData.getMark(xy).setBodyBag(""));
        bodyBagMap.remove(name);
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

    public static void checkBodyBag() {
        ForconsList.getForconsListModel().getArray().forEach((s) -> {
            String[] subStrs = s.split(",");
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
        Mark mark;
        for (int i = 0; i < MarksData.getRowCount(); i++) {
            for (int j = 0; j < MarksData.getColumnCount(); j++) {
                mark = MarksData.getMark(i,j);
                if (mark.getBodyBag().equals("") && mark.canBite()) {
                    if (mark.get() == max)
                        arrayList.add(new YX(i, j));
                    if (mark.get() > max) {
                        max = mark.get();
                        arrayList.clear();
                        arrayList.add(new YX(i, j));
                    }
                }
            }
        }
        Random rand = new Random();
        int randIndex = rand.nextInt(arrayList.size());
        mark = MarksData.getMark(arrayList.get(randIndex));
        if (checkChance(0.5 + bodyBagMap.size() * 0.05, mark)) {
            mark.setBodyBag(ForconsList.getSelectedValue().split(",")[1]);
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

