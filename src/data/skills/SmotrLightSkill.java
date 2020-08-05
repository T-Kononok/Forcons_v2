package data.skills;

import data.MainData;
import data.YX;

import java.io.IOException;

public class SmotrLightSkill extends Skill{

    public SmotrLightSkill(MainData mainData) {
        super(mainData);
    }

    @Override
    public boolean begin() throws IOException {
        if (mainData.getLight().size() == 24)
            return false;
        int row;
        do {
            row = getRandomRow();
        } while (mainData.lightContains(row));
        mainData.addLight(row);
        startFon(new YX(row,-2), "smotrLight",2,1,true);
//        System.out.println("addLight" + row);
        return true;
    }
}
