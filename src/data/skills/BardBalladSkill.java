package data.skills;

import data.ForconsList;
import data.MainData;
import data.MarksData;
import data.SkillsData;
import elements.UpElementsPanel;

public class BardBalladSkill extends Skill{

    @Override
    public void begin() {
        if (!ForconsList.minusPoint(1))
            return;
        buffAttack += 0.2;
        UpElementsPanel.changeElements();
//        System.out.println(buffAttack + " " + Math.round(buffAttack));
        deathAndCoin();
    }
}
