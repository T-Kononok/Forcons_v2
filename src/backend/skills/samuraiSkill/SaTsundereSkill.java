package backend.skills.samuraiSkill;

import backend.ForconsList;
import backend.marks.CellsData;
import backend.marks.Cell;
import backend.YX;
import backend.skills.Skill;

import java.io.IOException;

public class SaTsundereSkill extends Skill {

    public static void checkTsundere() {
        ForconsList.getForconsListModel().getArray().forEach((s) -> {
            String[] subStrs = s.split("_");
            if (subStrs[0].equals("sa")) {
                try {
                    Cell cell = getRandomCanBiteCell();
                    cell.minusCheck(1);
                    startFon(cell.getYX(), "samuTsundere",400);
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
