package data.skills;

import data.*;

import java.io.IOException;

public class SmotrLightSkill extends Skill{

    @Override
    public void begin() throws IOException {
        if (!ForconsList.minusPoint(3))
            return;
        if (SkillsData.getLight().size() == 24)
            return;
        int row;
        do {
            row = getRandomRow();
        } while (SkillsData.lightContains(row));
        SkillsData.addLight(row);
        startFon(new YX(row,-2), "smotrLight",2,1,true);
//        System.out.println("addLight" + row);
    }
}
