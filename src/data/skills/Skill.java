package data.skills;

import data.*;
import elements.UpElementsPanel;
import elements.skills.SkillEffect;

import java.io.IOException;
import java.util.Random;

public class Skill {

    protected static double buffAttack = 0;
    protected static int buffDefense = 0;
    protected static int coins = 0;

    public static double getBuffAttack() {
        return buffAttack;
    }
    protected static int getIntBuffAttack() {
        return (int)Math.round(buffAttack);
    }
    public static int getBuffDefense() {
        return buffDefense;
    }
    public static int getCoins() {
        return coins;
    }

    public static void setBuffDefense(int buffDefense) {
        Skill.buffDefense = buffDefense;
    }
    public static void setCoins(int coins) {
        Skill.coins = coins;
    }

    protected YX getRandomYX() {
        Random rand = new Random();
        Integer row = getRandomRow();
        return new YX(row, rand.nextInt(MarksData.getColumnCount()));
    }

    protected Integer getRandomRow() {
        Random rand = new Random();
        int row = rand.nextInt(MarksData.getRowCount()+ SkillsData.getLight().size());
        if (row >= MarksData.getRowCount()) {
            row = SkillsData.getLight().get(row - MarksData.getRowCount());
//            System.out.println("light");
        }
        return row;
    }

    protected YX getRandomMarkYX() {
        Mark mark;
        YX yx = new YX();
        yx.setY(getRandomRow());
        Random rand = new Random();
        do {
            yx.setX(rand.nextInt(MarksData.getColumnCount()));
            mark = MarksData.getMark(yx);
        } while (mark.get() == 0 || !mark.canBite());
//        System.out.println("row "+ yx.getY() + " col "+ yx.getX());
        return yx;
    }

    public void begin() throws IOException {
    }

    protected void deathAndCoin() {
        if (ForconsList.getLevel() > 1) {
            coins++;
            UpElementsPanel.changeElements();
        }
        if (Math.random() < 0.05)
            ForconsList.minusAllPoint();
    }

    protected void startFon(YX rowCol, String skillName) throws IOException {
        MainData.getSkillsPanel().addEffect(getStandardSizeEffect(rowCol, skillName));
    }

    protected void startFon(YX rowCol, String skillName, int time) throws IOException {
        SkillEffect skillEffect = getStandardSizeEffect(rowCol, skillName);
        skillEffect.setTime(time);
        MainData.getSkillsPanel().addEffect(skillEffect);
    }

    protected void startFon(YX rowCol, String skillName, boolean endlessly) throws IOException {
        SkillEffect skillEffect = getStandardSizeEffect(rowCol, skillName);
        if (endlessly)
            skillEffect.onEndlessly();
        MainData.getSkillsPanel().addEffect(skillEffect);
    }

    protected SkillEffect getStandardSizeEffect(YX rowCol, String skillName) throws IOException {
        int size = MainData.getTableNoGaps().getCellSize();
        int y = rowCol.getY() * size;
        int x = rowCol.getX() * size + size;
        return new SkillEffect("image/skills/" + skillName + ".png",
                x,y,size*3,size*3);
    }

    protected void startFon(YX rowCol, String skillName, int width, int height, boolean endlessly) throws IOException {
        int size = MainData.getTableNoGaps().getCellSize();
        int y = rowCol.getY() * size + size;
        int x = rowCol.getX() * size + size * 2;
        SkillEffect skillEffect = new SkillEffect("image/skills/" + skillName + ".png",
                x,y,size*width,size*height);
        if (endlessly)
            skillEffect.onEndlessly();
        MainData.getSkillsPanel().addEffect(skillEffect);
    }
}
