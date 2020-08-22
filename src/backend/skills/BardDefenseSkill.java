package backend.skills;

import backend.ForconsList;
import frontend.frame.UpElementsPanel;

public class BardDefenseSkill extends Skill{

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
//        System.out.println(buffAttack + " " + Math.round(buffAttack));
        BardCoinsSkill.checkCoin();
        BardDeathSkill.checkDeath();
    }
}
