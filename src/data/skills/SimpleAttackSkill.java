package data.skills;

import data.MainData;
import data.Mark;
import data.YX;

public class SimpleAttackSkill extends Skill {

    public SimpleAttackSkill(MainData mainData) {
        super(mainData);
    }

    @Override
    public boolean begin() {
        YX yx = getRandomMarkYX();
        Mark mark = mainData.getMark(yx);
        System.out.print("simpleAttack " + mark.get()+"->");
        mark.bite();
        System.out.println(mark.get());
        startFon(yx,"SimpleAttack");
        return true;
    }
}
