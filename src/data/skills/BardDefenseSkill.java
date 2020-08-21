package data.skills;

import data.MainData;
import elements.UpElementsPanel;

public class BardDefenseSkill extends Skill{

    @Override
    public void begin() {
        if (!MainData.minusPoint(1))
            return;
        buffDefense += 2;
        UpElementsPanel.changeElements();
//        System.out.println(buffAttack + " " + Math.round(buffAttack));
        deathAndCoin();
    }
}
