package data.skills;

import data.MainData;
import data.Mark;
import data.YX;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class BardBodyBagSkill extends Skill {

    public BardBodyBagSkill(MainData mainData) {
        super(mainData);
    }

    @Override
    public void begin() throws IOException {
        if (!mainData.minusPoint(2))
            return;
        ArrayList<YX> arrayList = new ArrayList<>();
        int max = 0;
        for (int i = 0; i < mainData.getSize(); i++) {
            for (int j = 0; j < mainData.getRowSize(); j++) {
                if (mainData.getMark(i,j).getBodyBag().equals("")) {
                    if (mainData.getMark(i, j).get() == max)
                        arrayList.add(new YX(i, j));
                    if (mainData.getMark(i, j).get() > max) {
                        max = mainData.getMark(i, j).get();
                        arrayList.clear();
                        arrayList.add(new YX(i, j));

                    }
                }
            }
        }
        Random rand = new Random();
        int randIndex = rand.nextInt(arrayList.size());
        Mark mark = mainData.getMark(arrayList.get(randIndex));
        if (Math.random() < 0.5 + mainData.getBodyBagMap().size() * 0.05) {
            mark.setBodyBag(mainData.getMainFrame().getForconsList().getSelectedValue().split(",")[1]);
            mainData.addBodyBag(arrayList.get(randIndex));
            startFon(arrayList.get(randIndex), "bardBodyBag");
        }
        else
            startFon(arrayList.get(randIndex), "bardBodyBagMiss");
    }
}

