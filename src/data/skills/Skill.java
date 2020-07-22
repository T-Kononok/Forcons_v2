package data.skills;

import data.MainData;
import data.Mark;
import elements.TableTimer;
import javafx.util.Pair;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class Skill {

    protected  JTable table;
    protected  ArrayList<ArrayList<Mark>> matrix;
    protected  MainData mainData;
    protected static double buffAttack = 0;
    protected static int buffDefense = 0;

    protected Skill(JTable table,ArrayList<ArrayList<Mark>> matrix, MainData mainData) {
        this.matrix = matrix;
        this.table = table;
        this.mainData = mainData;
    }

    protected Pair<Integer, Integer> getRandomYX() {
        Random rand = new Random();
        return new Pair<>(rand.nextInt(mainData.getSize()), rand.nextInt(mainData.getRowSize()));
    }

    protected void onChange(Mark mark, String imageFile) {
        if (mark != null)
            mark.onChange(imageFile);
    }

    public boolean begin() {
        return true;
    }

    protected void startFon(Pair<Integer,Integer> yx, ArrayList<String> fileNames) {
        ArrayList<Mark> marks = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            Mark leftMark = mainData.getMark(yx.getKey()+i,yx.getValue()-1);
            marks.add(leftMark);
            Mark centerMark = mainData.getMark(yx.getKey()+i,yx.getValue());
            marks.add(centerMark);
            Mark rightMark = mainData.getMark(yx.getKey()+i,yx.getValue()+1);
            marks.add(rightMark);
        }

        for (int i = 0; i < marks.size(); i++)
            onChange(marks.get(i),fileNames.get(i));
        mainData.repaintTable();
        TableTimer markTimer = new TableTimer(table,marks);
        markTimer.start();
    }
}
