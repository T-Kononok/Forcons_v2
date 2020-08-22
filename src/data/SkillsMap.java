package data;

import data.skills.*;

import java.util.HashMap;
import java.util.Map;

public class SkillsMap {

    private static final Map<String, Map<Integer, Skill>> allSkillMap = new HashMap<>();

    static {
        addBaSkills();
        addInSkills();
        addSaSkills();
        addSmSkills();
    }

    private static void addBaSkills() {
        Map<Integer, Skill> baSkillMap = new HashMap<>();
        baSkillMap.put(1,new SimpleAttackSkill("bardChain", 1, 0.75));
        baSkillMap.put(2,new BardBalladSkill());
        baSkillMap.put(3,new BardDeathSkill());
        baSkillMap.put(4,new BardCoinsSkill());
        baSkillMap.put(5,new BardBodyBagSkill());
        baSkillMap.put(6,new BardDefenseSkill());
        allSkillMap.put("ba",baSkillMap);
    }

    private static void addInSkills() {
        Map<Integer, Skill> inSkillMap = new HashMap<>();
        inSkillMap.put(1,new SimpleAttackSkill("inseClaws", 2, 0.5));
        inSkillMap.put(2,null);
        inSkillMap.put(3,null);
        inSkillMap.put(4,null);
        inSkillMap.put(5,null);
        inSkillMap.put(6,null);
        allSkillMap.put("in",inSkillMap);
    }

    private static void addSaSkills() {
        Map<Integer, Skill> saSkillMap = new HashMap<>();
        saSkillMap.put(1,new SimpleAttackSkill("samuKatana", 1, 1.0));
        saSkillMap.put(2,new SamurTsundereSkill());
        saSkillMap.put(3,new SamurComplementSkill());
        saSkillMap.put(4,new SamurTruckSkill());
        saSkillMap.put(5,new SamurKillerQueenSkill());
        saSkillMap.put(6,new SamurOvercomingSkill());
        allSkillMap.put("sa",saSkillMap);
    }

    private static void addSmSkills() {
        Map<Integer, Skill> smSkillMap = new HashMap<>();
        smSkillMap.put(1,new SimpleAttackSkill("smotrGame", 1, 0.75, 3, 0.17));
        smSkillMap.put(2,null);
        smSkillMap.put(3,null);
        smSkillMap.put(4,null);
        smSkillMap.put(5,new SmotrLightSkill());
        smSkillMap.put(6,null);
        allSkillMap.put("sm",smSkillMap);
    }

    public static Skill getSkill(String className, int number) {
        return allSkillMap.get(className).get(number);
    }

}
