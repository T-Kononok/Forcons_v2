package data.skills;

import data.MainData;
import data.Mark;

import javax.swing.*;
import java.util.ArrayList;

public class BardBalladSkill extends Skill{


    public BardBalladSkill(JTable table, ArrayList<ArrayList<Mark>> matrix, MainData mainData) {
        super(table, matrix, mainData);
    }

    @Override
    public boolean begin() {
        System.out.println(matrix.get(2).get(3).toStyle());
        buffAttack += 0.25;
        return true;
    }
}
