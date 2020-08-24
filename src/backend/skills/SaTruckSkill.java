package backend.skills;

import backend.*;
import backend.marks.Mark;
import backend.marks.MarksData;

import java.io.IOException;
import java.util.Random;

public class SaTruckSkill extends Skill{

    @Override
    public void begin() throws IOException {
        if (!ForconsList.minusPoint(2))
                return;
        int col = 0;
        Mark mark;
        boolean empty = true;
        while (empty) {
            col = new Random().nextInt(MarksData.getColumnCount());
            for (int i = 0; i < MarksData.getRowCount(); i++) {
                mark = MarksData.getMark(i, col);
                if (mark.isCrorKr())
                    break;
                if (!mark.isEmpty())
                    empty = false;
            }
        }

        for (int i = 0; i < MarksData.getRowCount(); i++) {
            mark = MarksData.getMark(i, col);
            if (mark.canBite()) {
//                System.out.print(mark.get() + "->");
                if (checkChance(0.75,mark)) {
                    mark.minus(1 + BaBalladSkill.get());
                    startFon(i, col, "samurTruck");
                }
                else {
                    mark.plus(1 + BaBalladSkill.get());
                    startFon(i, col, "samurTruckHealing");
                }
//                System.out.println(mark.get());
            } else
                startFon(i, col, "samurTruck");
        }
    }
}
