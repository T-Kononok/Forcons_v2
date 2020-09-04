package backend.skills.smotritelSkill;

import backend.ForconsList;
import backend.YX;
import backend.marks.Cell;
import backend.marks.CellsData;
import backend.skills.samuraiSkill.SaKillerQueenSkill;

import java.io.IOException;

public class SmBadSkill extends SaKillerQueenSkill {

    @Override
    public void begin() throws IOException {
        if (!ForconsList.minusPoint(2))
            return;
        Cell cell = getMaxBenefitMark();
        cell.setBad(true);
        startFon(cell.getYX(),"smBad");
//        array.forEach((yx) -> System.out.println(yx.toString()));
    }

    public static Cell checkBad(Cell mainCell) {
        if(mainCell.isBad() || mainCell.isBomb())
            return mainCell;
        int row = mainCell.getRow();
        int col = mainCell.getCol();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0)
                    continue;
                Cell cell = CellsData.secureGetCell(row + i, col + j);
                if (cell != null && cell.isBad() && cell.isCanBite())
                    return cell;
            }
        }
        return mainCell;
    }

    public static void startTransferFon(YX yx) throws IOException {
        startFon(yx,"transferBad");
    }
}
