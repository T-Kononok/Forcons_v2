package backend.skills;

import backend.ForconsList;
import backend.marks.MarksData;
import backend.marks.Mark;
import backend.YX;

import java.io.IOException;

public class SaTsundereSkill extends Skill {

    public static void checkTsundere() {
        ForconsList.getForconsListModel().getArray().forEach((s) -> {
            String[] subStrs = s.split(",");
            if (subStrs[0].equals("sa")) {
                try {
                    YX yx = getRandomMarkYX();
                    Mark mark = MarksData.getMark(yx);
                    mark.minus(1);
                    startFon(yx, "samuTsundere",400);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean isActive() {
        return false;
    }
}
