package backend.skills;

import backend.ForconsList;
import backend.marks.Cell;
import backend.marks.CellsData;

import java.io.IOException;

public class InPoisonSkill extends Skill {

    private int lastCount = 0;

    @Override
    public void begin() throws IOException {
        if (!ForconsList.minusPoint(1))
            return;
        if (lastCount == 0)
            lastCount = findLastCount();
        Cell cell;
        int poisonCount = 0;
        while (poisonCount != 7) {
            int iterations = 0;
            do {
                cell = CellsData.getMark(getRandomYX());
                iterations++;
                if (iterations > 1000)
                    return;
            }
            while (!cell.isEmpty() || cell.isPoison());
            if (cell.getCol() > lastCount || checkChance(0.25)) {
                cell.setPoison(true);
                startFon(cell.getYX(),"inPoison");
                poisonCount++;
            }
        }
    }

    private static int findLastCount() {
        for (int j = CellsData.getColumnCount()-1; j >= 0; j--) {
            int marksCount = 0;
            for (int i = 0; i < CellsData.getRowCount(); i++) {
                if (CellsData.getMark(i,j).isNumber())
                    marksCount++;
                if (marksCount >= 2)
                    return j;
            }
        }
        return CellsData.getColumnCount()-1;
    }
}
