package backend.skills;

import backend.ForconsList;
import backend.YX;
import backend.marks.Cell;
import backend.marks.CellsData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class SmRemakeSkill extends Skill {

    @Override
    public void begin() throws IOException {
        if (!ForconsList.minusPoint(1))
            return;
        Cell cell;
        int row = getRandomRow();
        int col;
        int average = getAverageScore(row);
        ArrayList<Integer> array = new ArrayList<>();
        for (int i = 0; i < CellsData.getColumnCount(); i++){
            cell = CellsData.getCell(row,i);
            if (cell.isNumber() && cell.get() < average) {
                array.add(cell.get());
            }
        }
        Random rand = new Random();
        do {
            col = rand.nextInt(CellsData.getColumnCount());
            cell = CellsData.getCell(row, col);
        } while (!cell.isEmpty() && !cell.isPoison());
        boolean flag = cell.isPoison();
        cell.set(array.get(rand.nextInt(array.size())));
        if (flag) {
            cell.minus(1);
            startFon(new YX(row,col),"inPoison",200);
        }
        startFon(row,col,"smRemake");
    }
}
