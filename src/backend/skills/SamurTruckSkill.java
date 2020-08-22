package backend.skills;

import backend.*;
import backend.marks.Mark;
import backend.marks.MarksData;

import java.io.IOException;
import java.util.Random;

public class SamurTruckSkill extends Skill{

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
            if (mark.get() != 0) {
//                System.out.print(mark.get() + "->");
                if (Math.random() < 0.75) {
                    mark.minus(1 + BardBalladSkill.get());
                    startFon(new YX(i, col), "samurTruck");
                }
                else {
                    mark.plus(1 + BardBalladSkill.get());
                    startFon(new YX(i, col), "samurTruckHealing");
                }
//                System.out.println(mark.get());
            } else
                startFon(new YX(i, col), "samurTruck");
        }
    }
}
