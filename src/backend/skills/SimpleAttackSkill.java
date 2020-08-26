package backend.skills;

import backend.*;
import backend.marks.Cell;
import backend.marks.CellsData;

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
        Cell cell = getRandomCanBiteCell();
        boolean noMiss = checkChance(chance, cell);
        if (noMiss) {
            if (checkChance(critChance, cell)) {
                cell.minusCheck(critDamage);
                startFon(cell.getYX(), skillName + "Crit");
            } else {
                cell.minusCheck(damage + BaBalladSkill.get());
                startFon(cell.getYX(), skillName);
            }
        } else {
            startFon(cell.getYX(),skillName + "Miss");
        }
        if (skillName.equals("bardChain"))
            BaDeathSkill.checkDeath();
    }
}
