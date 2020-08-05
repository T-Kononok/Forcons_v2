package data.skills;

import data.MainData;
import data.Mark;
import data.YX;

import java.io.IOException;

public class SimpleAttackSkill extends Skill {

    private final int damage;
    private final double chance;
    private final int critDamage;
    private final double critChance;
    private final String skillName;

    public SimpleAttackSkill(MainData mainData, String skillName, int damage, double chance) {
        super(mainData);
        this.damage = damage;
        this.chance = chance;
        this.critDamage = 0;
        this.critChance = 0;
        this.skillName = skillName;
    }

    public SimpleAttackSkill(MainData mainData, String skillName, int damage, double chance, int critDamage, double critChance) {
        super(mainData);
        this.damage = damage;
        this.chance = chance;
        this.critDamage = critDamage;
        this.critChance = critChance;
        this.skillName = skillName;
    }

    @Override
    public boolean begin() throws IOException {
        if (!mainData.minusPoint(1))
            return false;
        YX yx = getRandomMarkYX();
        Mark mark = mainData.getMark(yx);
        boolean noMiss = Math.random() < chance;
        if (noMiss) {
//            System.out.print(skillName + " " + mark.get() + "->");
            if (Math.random() < critChance) {
//                System.out.print("(крит)");
                mark.minus(critDamage);
                startFon(yx, skillName + "Crit");
            } else {
                mark.minus(damage + getBuffAttack());
                startFon(yx, skillName);
            }
//            System.out.println(mark.get());
        } else {
//            System.out.println(skillName + " промах");
            startFon(yx,skillName + "Miss");
        }
        return true;
    }
}
