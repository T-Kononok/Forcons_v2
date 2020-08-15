package data.skills;

import data.MainData;
import data.Mark;
import data.YX;
import elements.skills.SkillEffect;

import java.io.IOException;
import java.util.Random;

public class SamurTruckSkill extends Skill{

    public SamurTruckSkill(MainData mainData) {
        super(mainData);
    }

    @Override
    public void begin() throws IOException {
        if (!mainData.minusPoint(2))
                return;
        int col = 0;
        Mark mark;
        boolean empty = true;
        while (empty) {
            col = new Random().nextInt(mainData.getRowSize());
            for (int i = 0; i < mainData.getSize(); i++) {
                mark = mainData.getMark(i, col);
                if (mark.get() != 0)
                    empty = false;
            }
        }

        for (int i = 0; i < mainData.getSize(); i++) {
            mark = mainData.getMark(i, col);
            if (mark.get() != 0) {
//                System.out.print(mark.get() + "->");
                if (Math.random() < 0.75)
                    mark.bite();
                else
                    mark.plus(1);
//                System.out.println(mark.get());
            }
            startFon(new YX(i, col), "samurTruck");
        }
    }
}
