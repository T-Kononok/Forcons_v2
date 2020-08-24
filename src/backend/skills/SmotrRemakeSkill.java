package backend.skills;

import backend.ForconsList;
import backend.YX;
import backend.marks.Mark;
import backend.marks.MarksData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class SmotrRemakeSkill extends Skill {

    @Override
    public void begin() throws IOException {
        if (!ForconsList.minusPoint(1))
            return;
        Mark mark;
        int row = getRandomRow();
        int col;
        int average = getAverageScore(row);
        ArrayList<Integer> array = new ArrayList<>();
                                                                //проверить is-ы
        for (int i = 0; i < MarksData.getColumnCount(); i++){
            mark = MarksData.getMark(row,i);
            if (mark.isNumber() && mark.get() < average)
                array.add(mark.get());
        }
        Random rand = new Random();
        do {
            col = rand.nextInt(MarksData.getColumnCount());
            mark = MarksData.getMark(row, col);
        } while (!mark.canPut());
        mark.set(array.get(rand.nextInt(array.size())));
        startFon(row,col,"samurRemake");
    }
}
