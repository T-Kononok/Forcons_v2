package data.skills;

import data.MainData;

public class SmotrLightSkill extends Skill{

    public SmotrLightSkill(MainData mainData) {
        super(mainData);
    }

    @Override
    public boolean begin() {
        if (mainData.getLight().size() == 24)
            return false;
        int row;
        do {
            row = getRandomRow();
        } while (mainData.lightContains(row));
        mainData.addLight(row);
        System.out.println("addLight" + row);
        return true;
    }
}
