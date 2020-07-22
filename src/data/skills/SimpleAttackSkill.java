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
        Pair<Integer,Integer> yx = getRandomYX();
        Mark mark = mainData.getMark(yx);
        mark.set(1);
        ArrayList<String> fileNames = new ArrayList<>();
        for (int i = 0; i < 9; i++)
            fileNames.add("image/SimpleAttack"+i+".png");
        startFon(yx,fileNames);
        return true;
    }
}
