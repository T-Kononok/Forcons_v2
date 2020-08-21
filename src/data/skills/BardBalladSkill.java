package data.skills;

import data.MainData;

public class BardBalladSkill extends Skill{

    @Override
    public void begin() {
        if (!MainData.minusPoint(1))
            return;
        buffAttack += 0.2;
        MainData.changeUpElements();
//        System.out.println(buffAttack + " " + Math.round(buffAttack));
        deathAndCoin();
    }
}
