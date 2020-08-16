package data.skills;

import data.MainData;

public class BardDefenseSkill extends Skill{

    public BardDefenseSkill(MainData mainData) {
        super(mainData);
    }

    @Override
    public void begin() {
        if (!mainData.minusPoint(1))
            return;
        buffDefense += 2;
//        System.out.println(buffAttack + " " + Math.round(buffAttack));
        deathAndCoin();
    }
}
