package backend.skills;

import backend.*;
import backend.marks.Cell;
import backend.marks.CellsData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class SaComplementSkill extends Skill{

    private int getNewAverageScore(int row, int col, int value) {
        Cell cell;
        double sum = 0;
        int count = 0;
        for (int i = 0; i < CellsData.getColumnCount(); i++){
            if (i == col) {
                sum += value;
                count++;
            } else {
                cell = CellsData.getMark(row, i);
                if (cell.isNumber()) {
                    sum += cell.get();
                    count++;
                }
            }
        }
        return (int) Math.round(sum / count);
    }

    private int getMedian(int row) {
        ArrayList<Integer> array = new ArrayList<>();
        Cell cell;
        for (int i = 0; i < CellsData.getColumnCount(); i++){
            cell = CellsData.getMark(row,i);
            if (cell.isNumber())
                array.add(cell.get());
        }
        return getArrayMedian(array);
    }

    private int getNewMedian(int row, int col, int value) {
        ArrayList<Integer> array = new ArrayList<>();
        Cell cell;
        for (int i = 0; i < CellsData.getColumnCount(); i++){
            if (i == col) {
                array.add(value);
            } else {
                cell = CellsData.getMark(row, i);
                if (cell.isNumber())
                    array.add(cell.get());
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
        if (!ForconsList.minusPoint(1))
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
        Cell cell;
        for (int i = 0; i < CellsData.getColumnCount(); i++) {
            cell = CellsData.getMark(row,i);
            if (cell.canInteract()) {
                newMedian = getNewMedian(row, i, averageScore);
//                System.out.println("row " + row + " i " + i + " newMedian " + newMedian);
                if ((median - newMedian) > maxBenefit) {
                    maxBenefit = median - newMedian;
                    colBite = i;
                }
                if (cell.get() > max) {
                    max = cell.get();
                    colMax = i;
                }
            }
        }

        int col;
        if (maxBenefit > 0)
            col = colBite;
        else
            col = colMax;

        cell = CellsData.getMark(row, col);
//        int old = mark.get();
        cell.set(averageScore);
//        System.out.println("row " + row + " col " + col + " " + old + "->" + mark.get());
        startFon(new YX(row, col), "samurComplement");
    }

}
