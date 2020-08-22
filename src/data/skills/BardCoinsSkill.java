package data.skills;

import data.ForconsList;
import data.SkillsData;
import elements.UpElementsPanel;

public class BardCoinsSkill extends Skill {

    private static int coins = 0;

    public static int get() {
        return coins;
    }

    public static void set(int coins) {
        BardCoinsSkill.coins = coins;
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