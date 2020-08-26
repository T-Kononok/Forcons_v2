package backend.skills;

import backend.ForconsList;
import backend.marks.Cell;
import backend.marks.CellsData;
import backend.YX;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class SaKillerQueenSkill extends Skill {

    @Override
    public void begin() throws IOException {
        if (!ForconsList.minusPoint(2))
            return;
        Cell cell = getMaxBenefitMark();
        cell.setBomb(true);
        startFon(cell.getYX(),"samurBomb");
//        array.forEach((yx) -> System.out.println(yx.toString()));
    }

    protected Cell getMaxBenefitMark() {
        ArrayList<YX> array = new ArrayList<>();
        double maxBenefit = 0;
        double benefit;
        for (int row = 0; row < CellsData.getRowCount(); row++) {
            for (int col = 0; col < CellsData.getColumnCount(); col++) {
                Cell cell = CellsData.getCell(row,col);
                if (!cell.isNonBorder() || !cell.canInteract())
                    continue;
                benefit = countBenefit(row, col);
                if (benefit > maxBenefit) {
                    maxBenefit = benefit;
                    array.clear();
                }
                if (benefit == maxBenefit)
                    array.add(new YX(row, col));
            }
        }
        int randIndex = new Random().nextInt(array.size());
        return CellsData.getCell(array.get(randIndex));
    }

    private static double countBenefit(int row, int col) {
        double damage = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0)
                    continue;
                Cell cell = CellsData.secureGetCell(row + i, col + j);
                if (cell != null && cell.isCanBite())
                    if (cell.getBites() < 3)
                        damage += 1;
                    else
                        damage += 0.5;
            }
        }
        return damage;
    }

    public static void blast(Cell mainCell) throws IOException {
        int row = mainCell.getRow();
        int col = mainCell.getCol();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                Cell cell = CellsData.secureGetCell(row + i, col + j);
                if ((i == 0 && j == 0) || cell == null || !cell.isCanBite() || cell.isBomb())
                    continue;
                cell.minusCheck(1);
            }
        }
        startFon(new YX(row - 2, col - 1),"samurBombBlast",5,4,false);
        mainCell.setBomb(false);
    }

}
