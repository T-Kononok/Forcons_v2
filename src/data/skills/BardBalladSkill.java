package data.skills;

import data.MainData;

public class BardBalladSkill extends Skill{

    public BardBalladSkill(MainData mainData) {
        super(mainData);
    }

    @Override
    public boolean begin() {
        if (!mainData.minusPoint(2))
            return false;
        buffAttack += 0.2;
//        System.out.println(buffAttack + " " + Math.round(buffAttack));
        return true;
    }
}
