package backend.skills.bardSkill;

import backend.ForconsList;
import backend.skills.Skill;

public class BaDeathSkill extends Skill {

    public static void checkDeath() {
        if (checkChance(0.05))
            ForconsList.minusAllPoint();
    }

    @Override
    public boolean isActive() {
        return false;
    }
}
