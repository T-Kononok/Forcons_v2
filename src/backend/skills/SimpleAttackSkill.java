package backend.skills;

import backend.*;
import backend.marks.Mark;
import backend.marks.MarksData;

import java.io.IOException;

public class SimpleAttackSkill extends Skill {

    private final int damage;
    private final double chance;
    private final int critDamage;
    private final double critChance;
    private final String skillName;

    public SimpleAttackSkill(String skillName, int damage, double chance) {
        this.damage = damage;
        this.chance = chance;
        this.critDamage = 0;
        this.critChance = 0;
        this.skillName = skillName;
    }

    public SimpleAttackSkill(String skillName, int damage, double chance, int critDamage, double critChance) {
        this.damage = damage;
        this.chance = chance;
        this.critDamage = critDamage;
        this.critChance = critChance;
        this.skillName = skillName;
    }

    @Override
    public void begin() throws IOException {
        if (!ForconsList.minusPoint(1))
            return;
        YX yx = getRandomMarkYX();
        Mark mark = MarksData.getMark(yx);
        boolean noMiss = Math.random() < chance;
        if (noMiss) {
//            System.out.print(skillName + " " + mark.get() + "->");
            if (Math.random() < critChance) {
//                System.out.print("(крит)");
                mark.minus(critDamage);
                startFon(yx, skillName + "Crit");
            } else {
                mark.minus(damage + BardBalladSkill.get());
                startFon(yx, skillName);
            }
//            System.out.println(mark.get());
        } else {
//            System.out.println(skillName + " промах");
            startFon(yx,skillName + "Miss");
        }
        if (skillName.equals("bardChain"))
            BardDeathSkill.checkDeath();
    }
}
