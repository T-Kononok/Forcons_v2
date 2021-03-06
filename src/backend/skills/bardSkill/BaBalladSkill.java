package backend.skills.bardSkill;

import backend.ForconsList;
import backend.skills.Skill;
import frontend.frame.UpElementsPanel;

public class BaBalladSkill extends Skill {

    private static double buffAttack = 0;

    public static int get() {
        return (int) Math.round(buffAttack);
    }

    public static double getDouble() {
        return buffAttack;
    }

    public void begin() {
        if (!ForconsList.minusPoint(1))
            return;
        buffAttack += 0.2;
        UpElementsPanel.changeElements();
        BaCoinsSkill.checkCoin();
        BaDeathSkill.checkDeath();
    }
}
