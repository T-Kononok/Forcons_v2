package data.skills;

import data.ForconsList;
import data.SkillsData;
import elements.UpElementsPanel;

public class BardDefenseSkill extends Skill{

    private static int buffDefense = 0;

    @Override
    public void setField(Object object) {
        buffDefense = (Integer) object;
    }

    @Override
    public int getIntField() {
        return buffDefense;
    }

    @Override
    public void begin() {
        if (!ForconsList.minusPoint(1))
            return;
        buffDefense += 2;
        UpElementsPanel.changeElements();
//        System.out.println(buffAttack + " " + Math.round(buffAttack));
        SkillsData.deathAndCoin();
    }
}
