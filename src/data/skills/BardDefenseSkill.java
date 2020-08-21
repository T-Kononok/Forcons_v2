package data.skills;

import data.MainData;

public class BardDefenseSkill extends Skill{

    @Override
    public void begin() {
        if (!MainData.minusPoint(1))
            return;
        buffDefense += 2;
        MainData.changeUpElements();
//        System.out.println(buffAttack + " " + Math.round(buffAttack));
        deathAndCoin();
    }
}
