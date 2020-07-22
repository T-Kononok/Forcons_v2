package data.skills;

import data.MainData;
import data.Mark;
import elements.TableTimer;
import javafx.util.Pair;

import javax.swing.*;
import java.util.ArrayList;

public class SimpleAttackSkill extends Skill {


    public SimpleAttackSkill(JTable table, ArrayList<ArrayList<Mark>> matrix, MainData mainData) {
        super(table, matrix, mainData);
    }

    @Override
    public boolean begin() {
        Pair<Integer,Integer> yx = getRandomMarkXY();
        Mark mark = matrix.get(yx.getKey()).get(yx.getValue());
        Mark upMark = null;
        Mark downMark = null;
        Mark rightMark = null;
        Mark leftMark = null;
        if (yx.getKey()-1 >= 0)
            upMark = matrix.get(yx.getKey()-1).get(yx.getValue());
        if (yx.getKey()+1 < matrix.size())
            downMark = matrix.get(yx.getKey()+1).get(yx.getValue());
        if (yx.getValue()-1 >= 0)
            rightMark = matrix.get(yx.getKey()).get(yx.getValue()-1);
        if (yx.getValue()+1 < matrix.get(0).size())
            leftMark = matrix.get(yx.getKey()).get(yx.getValue()+1);
        mark.set(1);
        mark.setChange(true);
        if (upMark != null)
            upMark.setChange(true);
        if (downMark != null)
            downMark.setChange(true);
        if (rightMark != null)
            rightMark.setChange(true);
        if (leftMark != null)
            leftMark.setChange(true);
        mainData.repaintTable();
        TableTimer markTimer = new TableTimer(table,mark);
        markTimer.start();
        if (upMark != null) {
            TableTimer upMarkTimer = new TableTimer(table,upMark);
            upMarkTimer.start();
        }
        if (downMark != null) {
            TableTimer downMarkTimer = new TableTimer(table,downMark);
            downMarkTimer.start();
        }
        if (rightMark != null) {
            TableTimer rightMarkTimer = new TableTimer(table,rightMark);
            rightMarkTimer.start();
        }
        if (leftMark != null) {
            TableTimer leftMarkTimer = new TableTimer(table,leftMark);
            leftMarkTimer.start();
        }
        return true;
    }
}
