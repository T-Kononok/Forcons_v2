package data.skills;

import data.ForconsList;
import data.MainData;
import data.Mark;
import data.YX;
import frame.MainFrame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class BardBodyBagSkill extends Skill {

    @Override
    public void begin() throws IOException {
        if (!MainData.minusPoint(2))
            return;
        ArrayList<YX> arrayList = new ArrayList<>();
        int max = 0;
        Mark mark;
        for (int i = 0; i < MainData.getSize(); i++) {
            for (int j = 0; j < MainData.getRowSize(); j++) {
                mark = MainData.getMark(i,j);
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
        mark = MainData.getMark(arrayList.get(randIndex));
        if (Math.random() < 0.5 + MainData.getBodyBagMap().size() * 0.05) {
            mark.setBodyBag(ForconsList.getSelectedValue().split(",")[1]);
            MainData.addBodyBag(arrayList.get(randIndex));
            startFon(arrayList.get(randIndex), "bardBodyBag");
        }
        else
            startFon(arrayList.get(randIndex), "bardBodyBagMiss");
    }
}

