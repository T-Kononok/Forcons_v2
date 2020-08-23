package backend.skills;

import backend.ForconsList;
import backend.marks.Mark;
import backend.marks.MarksData;
import backend.YX;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class SamurKillerQueenSkill extends Skill {

    @Override
    public void begin() throws IOException {
        if (!ForconsList.minusPoint(2))
            return;
        ArrayList<YX> array = new ArrayList<>();
        double maxDamage = 0;
        double damage;
        for (int row = 0; row < MarksData.getRowCount(); row++) {
            for (int col = 0; col < MarksData.getColumnCount(); col++) {
                Mark mark = MarksData.getMark(row,col);
                if (mark.isBomb() || !mark.canInteract())
                    continue;
                damage = countDamage(row, col);
//                System.out.println(row + " " + col + ":" + damage);
                if (damage > maxDamage) {
                    maxDamage = damage;
                    array.clear();
                }
                if (damage == maxDamage)
                    array.add(new YX(row, col));

            }
        }
        int randIndex = new Random().nextInt(array.size());
        MarksData.getMark(array.get(randIndex)).setBomb(true);
        startFon(array.get(randIndex),"samurBomb");
//        array.forEach((yx) -> System.out.println(yx.toString()));
    }

    private static double countDamage(int row, int col) {
        double damage = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0)
                    continue;
                Mark mark = MarksData.secureGetMark(row + i, col + j);
                if (mark != null && mark.canBite())
                    if (mark.getBites() < 3)
                        damage += 1;
                    else
                        damage += 0.5;
            }
        }
        return damage;
    }

    public static void blast(Mark mainMark) throws IOException {
        int row = mainMark.getRow();
        int col = mainMark.getCol();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                Mark mark = MarksData.secureGetMark(row + i, col + j);
                if ((i == 0 && j == 0) || mark == null || !mark.canBite() || mark.isBomb())
                    continue;
                mark.minus(1);
            }
        }
        startFon(new YX(row - 2, col - 1),"samurBombBlast",5,4,false);
        mainMark.setBomb(false);
    }

}
