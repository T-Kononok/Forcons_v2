package data.skills;

import data.MainData;
import data.Mark;

import javax.swing.*;
import java.util.ArrayList;

public class Skill {

    protected static JTable table;
    protected static ArrayList<ArrayList<Mark>> matrix;
    protected static MainData mainData;
    protected static double buffAttack = 0;
    protected static int buffDefense = 0;

    protected Skill(JTable table,ArrayList<ArrayList<Mark>> matrix, MainData mainData) {
        Skill.matrix = matrix;
        Skill.table = table;
        Skill.mainData = mainData;
    }

//    protected Mark getRandomMark() {
//
//    }

    public boolean begin() {
        return true;
    }
}
