package backend.skills;

import backend.ForconsList;

public class BardDeathSkill extends Skill {

    public static void checkDeath() {
        if (Math.random() < 0.05)
            ForconsList.minusAllPoint();
    }

    @Override
    public boolean isActive() {
        return false;
    }
}