package data.skills;

import data.MainData;
import data.Mark;
import data.YX;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class SamurComplementSkill extends Skill{

    public SamurComplementSkill(MainData mainData) {
        super(mainData);
    }

    private int getAverageScore(int row) {
        Mark mark;
        double sum = 0;
        int count = 0;
        for (int i = 0; i < mainData.getRowSize(); i++){
            mark = mainData.getMark(row,i);
            if (mark.get() != 0) {
                sum += mark.get();
                count++;
            }
        }
        return (int) Math.round((sum / count) - 0.0001);
    }

    private int getNewAverageScore(int row, int col, int value) {
        Mark mark;
        double sum = 0;
        int count = 0;
        for (int i = 0; i < mainData.getRowSize(); i++){
            if (i == col) {
                sum += value;
                count++;
            } else {
                mark = mainData.getMark(row, i);
                if (mark.get() != 0) {
                    sum += mark.get();
                    count++;
                }
            }
        }
        return (int) Math.round(sum / count);
    }

    private int getMedian(int row) {
        ArrayList<Integer> array = new ArrayList<>();
        Mark mark;
        for (int i = 0; i < mainData.getRowSize(); i++){
            mark = mainData.getMark(row,i);
            if (mark.get() != 0)
                array.add(mark.get());
        }
        return getArrayMedian(array);
    }

    private int getNewMedian(int row, int col, int value) {
        ArrayList<Integer> array = new ArrayList<>();
        Mark mark;
        for (int i = 0; i < mainData.getRowSize(); i++){
            if (i == col) {
                array.add(value);
            } else {
                mark = mainData.getMark(row, i);
                if (mark.get() != 0)
                    array.add(mark.get());
            }
        }
        return getArrayMedian(array);
    }

    private int getArrayMedian(ArrayList<Integer> array) {
        Collections.sort(array);
        int median;
        if (array.size() % 2 == 0)
            median = (int) Math.round( (double) (array.get(array.size()/2) + (array.get(array.size()/2) - 1)) / 2);
        else
            median = array.get(array.size()/2);
        return median;
    }

    @Override
    public void begin() throws IOException {
        if (!mainData.minusPoint(1))
            return;

        int row = getRandomRow();
        int colBite = 0;
        int colMax = 0;
        int median = getMedian(row);
//        System.out.println("median " + median);
        int averageScore = getAverageScore(row);
//        System.out.println("averageScore " + averageScore);
        int newMedian;
        double maxBenefit = 0;
        int max = 0;
        Mark mark;
        for (int i = 0; i < mainData.getRowSize(); i++) {
            mark = mainData.getMark(row,i);
            if (mark.get() != 0 && mark.canBite()) {
                newMedian = getNewMedian(row, i, averageScore);
//                System.out.println("row " + row + " i " + i + " newMedian " + newMedian);
                if ((median - newMedian) > maxBenefit) {
                    maxBenefit = median - newMedian;
                    colBite = i;
                }
                if (mark.get() > max) {
                    max = mark.get();
                    colMax = i;
                }
            }
        }

        int col;
        if (maxBenefit > 0)
            col = colBite;
        else
            col = colMax;

        mark = mainData.getMark(row, col);
//        int old = mark.get();
        mark.set(averageScore);
//        System.out.println("row " + row + " col " + col + " " + old + "->" + mark.get());
        startFon(new YX(row, col), "samurComplement");
    }

}
