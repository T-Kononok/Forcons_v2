package data.skills;

import data.MainData;

public class BardBalladSkill extends Skill{

    public BardBalladSkill(MainData mainData) {
        super(mainData);
    }

    @Override
    public void begin() {
        if (!mainData.minusPoint(2))
            return;
        buffAttack += 0.2;
//        System.out.println(buffAttack + " " + Math.round(buffAttack));
    }
}
