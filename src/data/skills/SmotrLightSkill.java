package data.skills;

import data.MainData;
import data.YX;

import java.io.IOException;

public class SmotrLightSkill extends Skill{

    @Override
    public void begin() throws IOException {
        if (!MainData.minusPoint(3))
            return;
        if (MainData.getLight().size() == 24)
            return;
        int row;
        do {
            row = getRandomRow();
        } while (MainData.lightContains(row));
        MainData.addLight(row);
        startFon(new YX(row,-2), "smotrLight",2,1,true);
//        System.out.println("addLight" + row);
    }
}
