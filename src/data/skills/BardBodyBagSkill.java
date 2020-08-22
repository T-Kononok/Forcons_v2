package data.skills;

import data.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BardBodyBagSkill extends Skill {

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
        if (Math.random() < 0.5 + SkillsData.getBodyBagMap().size() * 0.05) {
            mark.setBodyBag(ForconsList.getSelectedValue().split(",")[1]);
            SkillsData.addBodyBag(arrayList.get(randIndex));
            startFon(arrayList.get(randIndex), "bardBodyBag");
        }
        else
            startFon(arrayList.get(randIndex), "bardBodyBagMiss");
        SkillsData.deathAndCoin();
        SkillsData.deathAndCoin();
    }
}

