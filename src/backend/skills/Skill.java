package backend.skills;

import backend.*;
import backend.marks.Mark;
import backend.marks.MarksData;
import frontend.frame.TableNoGaps;
import backend.skillsData.SkillEffect;

import java.io.IOException;
import java.util.Random;

public class Skill {

    public boolean isActive() {
        return true;
    }

    protected static boolean checkChance(double chance) {
        return Math.random() < chance;
    }

    protected static boolean checkChance(double chance, int row) {
        if (SmLightSkill.contains(row))
            return true;
        return checkChance(chance);
    }

    protected static boolean checkChance(double chance, Mark mark) {
        if (SmLightSkill.contains(mark.getRow()))
            return true;
        return Math.random() < chance;
    }

    protected  static YX getRandomYX() {
        Random rand = new Random();
        Integer row = getRandomRow();
        return new YX(row, rand.nextInt(MarksData.getColumnCount()));
    }

    protected static Integer getRandomRow() {
        Random rand = new Random();
        int row = rand.nextInt(MarksData.getRowCount()+ SmLightSkill.getLight().size());
        if (row >= MarksData.getRowCount()) {
            row = SmLightSkill.getLight().get(row - MarksData.getRowCount());
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

    protected int getAverageScore(int row) {
        Mark mark;
        double sum = 0;
        int count = 0;
        for (int i = 0; i < MarksData.getColumnCount(); i++){
            mark = MarksData.getMark(row,i);
            if (mark.isNumber()) {
                sum += mark.get();
                count++;
            }
        }
        return (int) Math.round((sum / count) - 0.0001);
    }

    public void begin() throws IOException {
    }

    protected static void startFon(int row, int col, String skillName) throws IOException {
        TableNoGaps.getSkillsPanel().addEffect(getStandardSizeEffect(new YX(row,col), skillName));
    }

    protected static void startFon(YX rowCol, String skillName) throws IOException {
        TableNoGaps.getSkillsPanel().addEffect(getStandardSizeEffect(rowCol, skillName));
    }

    protected static void startFon(YX rowCol, String skillName, int time) throws IOException {
        SkillEffect skillEffect = getStandardSizeEffect(rowCol, skillName);
        skillEffect.setTime(time);
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
