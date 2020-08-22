package data.skills;

import data.*;
import elements.TableNoGaps;
import elements.UpElementsPanel;
import elements.skills.SkillEffect;

import java.io.IOException;
import java.util.Random;

public class Skill {

    public boolean isActive() {
        return true;
    }

    protected  static YX getRandomYX() {
        Random rand = new Random();
        Integer row = getRandomRow();
        return new YX(row, rand.nextInt(MarksData.getColumnCount()));
    }

    protected static Integer getRandomRow() {
        Random rand = new Random();
        int row = rand.nextInt(MarksData.getRowCount()+ SmotrLightSkill.getLight().size());
        if (row >= MarksData.getRowCount()) {
            row = SmotrLightSkill.getLight().get(row - MarksData.getRowCount());
//            System.out.println("light");
        }
        return row;
    }

    protected static YX getRandomMarkYX() {
        Mark mark;
        YX yx = new YX();
        yx.setY(getRandomRow());
        Random rand = new Random();
        do {
            yx.setX(rand.nextInt(MarksData.getColumnCount()));
            mark = MarksData.getMark(yx);
        } while (!mark.canBite());
//        System.out.println("row "+ yx.getY() + " col "+ yx.getX());
        return yx;
    }

    public void begin() throws IOException {
    }

    protected static void startFon(YX rowCol, String skillName) throws IOException {
        TableNoGaps.getSkillsPanel().addEffect(getStandardSizeEffect(rowCol, skillName));
    }

    protected static void startFon(YX rowCol, String skillName, int time) throws IOException {
        SkillEffect skillEffect = getStandardSizeEffect(rowCol, skillName);
        skillEffect.setTime(time);
        TableNoGaps.getSkillsPanel().addEffect(skillEffect);
    }

    protected static void startFon(YX rowCol, String skillName, boolean endlessly) throws IOException {
        SkillEffect skillEffect = getStandardSizeEffect(rowCol, skillName);
        if (endlessly)
            skillEffect.onEndlessly();
        TableNoGaps.getSkillsPanel().addEffect(skillEffect);
    }

    protected static SkillEffect getStandardSizeEffect(YX rowCol, String skillName) throws IOException {
        int size = TableNoGaps.getCellSize();
        int y = rowCol.getY() * size;
        int x = rowCol.getX() * size + size;
        return new SkillEffect("image/skills/" + skillName + ".png",
                x,y,size*3,size*3);
    }

    protected static void startFon(YX rowCol, String skillName, int width, int height, boolean endlessly) throws IOException {
        int size = TableNoGaps.getCellSize();
        int y = rowCol.getY() * size + size;
        int x = rowCol.getX() * size + size * 2;
        SkillEffect skillEffect = new SkillEffect("image/skills/" + skillName + ".png",
                x,y,size*width,size*height);
        if (endlessly)
            skillEffect.onEndlessly();
        TableNoGaps.getSkillsPanel().addEffect(skillEffect);
    }
}
