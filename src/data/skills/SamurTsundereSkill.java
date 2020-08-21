package data.skills;

import data.MainData;
import data.Mark;
import data.YX;

import java.io.IOException;

public class SamurTsundereSkill extends Skill {

    @Override
    public void begin() throws IOException {
        YX yx = getRandomMarkYX();
        Mark mark = MainData.getMark(yx);
        mark.minus(1);
        startFon(yx, "samuTsundere",500);
    }
}
