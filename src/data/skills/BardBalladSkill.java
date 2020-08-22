package data.skills;

import data.ForconsList;
import data.SkillsData;
import elements.UpElementsPanel;

public class BardBalladSkill extends Skill{

    private static double buffAttack = 0;

    @Override
    public void setField(Object object) {
        buffAttack = (Double) object;
    }

    @Override
    public int getIntField() {
        return (int) Math.round(buffAttack);
    }

    @Override
    public double getDoubleField() {
        return buffAttack;
    }

    @Override
    public void begin() {
        if (!ForconsList.minusPoint(1))
            return;
        buffAttack += 0.2;
        UpElementsPanel.changeElements();
//        System.out.println(buffAttack + " " + Math.round(buffAttack));
        SkillsData.deathAndCoin();
    }
}
