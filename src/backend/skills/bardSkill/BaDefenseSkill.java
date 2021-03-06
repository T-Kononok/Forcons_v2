package backend.skills.bardSkill;

import backend.ForconsList;
import backend.skills.Skill;
import frontend.frame.UpElementsPanel;

public class BaDefenseSkill extends Skill {

    private static int buffDefense = 0;

    public static void set(Object object) {
        buffDefense = (Integer) object;
    }

    public static int get() {
        return buffDefense;
    }

    @Override
    public void begin() {
        if (!ForconsList.minusPoint(1))
            return;
        buffDefense += 2;
        UpElementsPanel.changeElements();
        BaCoinsSkill.checkCoin();
        BaDeathSkill.checkDeath();
    }
}
