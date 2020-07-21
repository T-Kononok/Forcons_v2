package data.skills;

import data.MainData;
import data.Mark;

import javax.swing.*;
import java.util.ArrayList;

public class SimpleAttackSkill extends Skill {


    public SimpleAttackSkill(JTable table, ArrayList<ArrayList<Mark>> matrix, MainData mainData) {
        super(table, matrix, mainData);
    }

    @Override
    public boolean begin() {
        matrix.get(2).get(3).set(7);
        mainData.repaintTable();
        return true;
    }
}
