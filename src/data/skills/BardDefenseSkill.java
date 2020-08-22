package data.skills;

import data.ForconsList;
import data.MainData;
import data.MarksData;
import elements.UpElementsPanel;

public class BardDefenseSkill extends Skill{

    @Override
    public void begin() {
        if (!ForconsList.minusPoint(1))
            return;
        buffDefense += 2;
        UpElementsPanel.changeElements();
//        System.out.println(buffAttack + " " + Math.round(buffAttack));
        deathAndCoin();
    }
}
