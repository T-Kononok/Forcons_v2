package data.skills;

import data.MainData;
import data.Mark;
import data.YX;
import elements.TableTimer;
import java.util.ArrayList;
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
            System.out.println("light");
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
        System.out.println("row "+ yx.getY() + " col "+ yx.getX());
        return yx;
    }

    protected void onChange(Mark mark, String imageFile, int number) {
        if (mark != null)
            mark.onChange(imageFile,number);
    }

    public boolean begin() {
        return true;
    }

    protected void startFon(YX yx, String skillName) {
        ArrayList<Mark> marks = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            Mark leftMark = mainData.getMark(yx.getY()+i,yx.getX()-1);
            marks.add(leftMark);
            Mark centerMark = mainData.getMark(yx.getY()+i,yx.getX());
            marks.add(centerMark);
            Mark rightMark = mainData.getMark(yx.getY()+i,yx.getX()+1);
            marks.add(rightMark);
        }

        for (int i = 0; i < marks.size(); i++) {
            onChange(marks.get(i), "image/skills/" + skillName + ".png", i);
        }
        mainData.repaintTable();
        TableTimer markTimer = new TableTimer(mainData,marks);
        markTimer.start();
    }
}
