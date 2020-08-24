package backend.skillsData;

import backend.skills.*;

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
        baSkillMap.put(2,new BaBalladSkill());
        baSkillMap.put(3,new BaDeathSkill());
        baSkillMap.put(4,new BaCoinsSkill());
        baSkillMap.put(5,new BaBodyBagSkill());
        baSkillMap.put(6,new BaDefenseSkill());
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
        saSkillMap.put(2,new SaTsundereSkill());
        saSkillMap.put(3,new SaComplementSkill());
        saSkillMap.put(4,new SaTruckSkill());
        saSkillMap.put(5,new SaKillerQueenSkill());
        saSkillMap.put(6,new SaOvercomingSkill());
        allSkillMap.put("sa",saSkillMap);
    }

    private static void addSmSkills() {
        Map<Integer, Skill> smSkillMap = new HashMap<>();
        smSkillMap.put(1,new SimpleAttackSkill("smotrGame", 1, 0.75, 3, 0.17));
        smSkillMap.put(2,null);
        smSkillMap.put(3,new SmRemakeSkill());
        smSkillMap.put(4,new SmRaspberrySkill());
        smSkillMap.put(5,new SmLightSkill());
        smSkillMap.put(6,new SmGoblinSkill());
        allSkillMap.put("sm",smSkillMap);
    }

    public static Skill getSkill(String className, int number) {
        return allSkillMap.get(className).get(number);
    }

}
