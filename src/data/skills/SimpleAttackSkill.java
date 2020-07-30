package data.skills;

import data.MainData;
import data.Mark;
import data.YX;

public class SimpleAttackSkill extends Skill {

    public final int damage;
    public final double chance;
    public final int critDamage;
    public final double critChance;
    public final String skillName;

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
    public boolean begin() {
        YX yx = getRandomMarkYX();
        Mark mark = mainData.getMark(yx);
        boolean noMiss = Math.random() < chance;
        if (noMiss) {
            System.out.print(skillName + " " + mark.get() + "->");
            if (Math.random() < critChance) {
                System.out.print("(крит)");
                mark.minus(critDamage);
                startFon(yx, skillName + "Crit");
            } else {
                mark.minus(damage);
                startFon(yx, skillName);
            }
            System.out.println(mark.get());
        } else {
            System.out.print(skillName + " промах");
            startFon(yx,skillName + "Miss");
        }
        return true;
    }
}
