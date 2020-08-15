package data.skills;

import data.MainData;
import data.Mark;
import data.YX;
import elements.skills.SkillEffect;

import java.io.IOException;
import java.util.Random;

public class Skill {

    protected  MainData mainData;
    protected static double buffAttack = 0;
    protected static int buffDefense = 0;

    protected Skill(MainData mainData) {
        this.mainData = mainData;
    }

    protected int getBuffAttack() {
        return (int)Math.round(buffAttack);
    }

    protected YX getRandomYX() {
        Random rand = new Random();
        Integer row = getRandomRow();
        return new YX(row, rand.nextInt(mainData.getRowSize()));
    }

    protected Integer getRandomRow() {
        Random rand = new Random();
        int row = rand.nextInt(mainData.getSize()+mainData.getLight().size());
        if (row >= mainData.getSize()) {
            row = mainData.getLight().get(row - mainData.getSize());
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
            yx.setX(rand.nextInt(mainData.getRowSize()));
            mark = mainData.getMark(yx);
        } while (mark.get() == 0);
//        System.out.println("row "+ yx.getY() + " col "+ yx.getX());
        return yx;
    }

    protected void onChange(Mark mark, String imageFile, int number) {
        if (mark != null)
            mark.onChange(imageFile,number);
    }

    public void begin() throws IOException {
    }

    protected void startFon(YX rowCol, String skillName) throws IOException {
        mainData.getSkillsPanel().addEffect(getStandardSizeEffect(rowCol, skillName));
    }

    protected void startFon(YX rowCol, String skillName, boolean endlessly) throws IOException {
        SkillEffect skillEffect = getStandardSizeEffect(rowCol, skillName);
        if (endlessly)
            skillEffect.onEndlessly();
        mainData.getSkillsPanel().addEffect(skillEffect);
    }

    protected SkillEffect getStandardSizeEffect(YX rowCol, String skillName) throws IOException {
        int size = mainData.getTableNoGaps().getCellSize();
        int y = rowCol.getY() * size;
        int x = rowCol.getX() * size + size;
        return new SkillEffect("image/skills/" + skillName + ".png",
                x,y,size*3,size*3);
    }

    protected void startFon(YX rowCol, String skillName, int width, int height, boolean endlessly) throws IOException {
        int size = mainData.getTableNoGaps().getCellSize();
        int y = rowCol.getY() * size + size;
        int x = rowCol.getX() * size + size * 2;
        SkillEffect skillEffect = new SkillEffect("image/skills/" + skillName + ".png",
                x,y,size*width,size*height);
        if (endlessly)
            skillEffect.onEndlessly();
        mainData.getSkillsPanel().addEffect(skillEffect);
    }
}
