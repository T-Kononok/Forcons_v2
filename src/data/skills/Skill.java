package data.skills;

import data.MainData;
import data.Mark;
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

    protected Pair<Integer, Integer> getRandomMarkXY() {
        Random rand = new Random();
        return new Pair<>(rand.nextInt(matrix.size()), rand.nextInt(matrix.get(0).size()));
    }

    public boolean begin() {
        return true;
    }
}
