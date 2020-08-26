package backend.skills;

import backend.*;
import backend.marks.Cell;
import backend.marks.CellsData;

import java.io.IOException;
import java.util.Random;

public class SaTruckSkill extends Skill{

    @Override
    public void begin() throws IOException {
        if (!ForconsList.minusPoint(2))
                return;
        int col = 0;
        Cell cell;
        boolean empty = true;
        while (empty) {
            col = new Random().nextInt(CellsData.getColumnCount());
            for (int i = 0; i < CellsData.getRowCount(); i++) {
                cell = CellsData.getCell(i, col);
                if (cell.isCr() || cell.isKr())
                    break;
                if (!cell.isEmpty())
                    empty = false;
            }
        }

        for (int i = 0; i < CellsData.getRowCount(); i++) {
            cell = CellsData.getCell(i, col);
            if (cell.isCanBite()) {
//                System.out.print(mark.get() + "->");
                if (checkChance(0.75, cell)) {
                    cell.minusCheck(1 + BaBalladSkill.get());
                    startFon(i, col, "samurTruck");
                }
                else {
                    cell.plusCheck(1 + BaBalladSkill.get());
                    startFon(i, col, "samurTruckHealing");
                }
//                System.out.println(mark.get());
            } else
                startFon(i, col, "samurTruck");
        }
    }
}
