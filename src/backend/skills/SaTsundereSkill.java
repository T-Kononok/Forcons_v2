package backend.skills;

import backend.ForconsList;
import backend.marks.CellsData;
import backend.marks.Cell;
import backend.YX;

import java.io.IOException;

public class SaTsundereSkill extends Skill {

    public static void checkTsundere() {
        ForconsList.getForconsListModel().getArray().forEach((s) -> {
            String[] subStrs = s.split(",");
            if (subStrs[0].equals("sa")) {
                try {
                    YX yx = getRandomMarkYX();
                    Cell cell = CellsData.getMark(yx);
                    cell.minusCheck(1);
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
