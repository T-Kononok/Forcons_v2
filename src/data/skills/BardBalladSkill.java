package data.skills;

import data.MainData;

public class BardBalladSkill extends Skill{

    public BardBalladSkill(MainData mainData) {
        super(mainData);
    }

    @Override
    public boolean begin() {
//        System.out.println(matrix.get(2).get(3).toStyle());
//        buffAttack += 0.25;
        return true;
    }
}
