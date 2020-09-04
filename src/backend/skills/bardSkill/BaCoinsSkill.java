package backend.skills.bardSkill;

import backend.ForconsList;
import backend.skills.Skill;
import frontend.frame.UpElementsPanel;

public class BaCoinsSkill extends Skill {

    private static int coins = 0;

    public static int get() {
        return coins;
    }

    public static void set(int coins) {
        BaCoinsSkill.coins = coins;
    }

    public static void checkCoin() {
        if (ForconsList.getLevel() > 1) {
            coins++;
            UpElementsPanel.changeElements();
        }
    }

    @Override
    public boolean isActive() {
        return false;
    }
}
