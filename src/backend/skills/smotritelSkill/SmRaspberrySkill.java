package backend.skills.smotritelSkill;

import backend.ForconsList;
import backend.YX;
import backend.skills.Skill;

import java.io.IOException;
import java.util.ArrayList;

public class SmRaspberrySkill extends Skill {
    private static ArrayList<Integer> raspberryArray = new ArrayList<>();

    public static ArrayList<Integer> getRaspberryArray() {
        return raspberryArray;
    }

    public static boolean contains(int row) {
        for (Integer integer : raspberryArray)
            if (integer == row)
                return true;
        return false;
    }

    @Override
    public void begin() throws IOException {
        if (raspberryArray.size() == 24)
            return;
        if (!ForconsList.minusPoint(4))
            return;
        int row;
        do {
            row = getRandomRow();
        } while (contains(row));
        raspberryArray.add(row);
        startFon(new YX(row,-1), "smRaspberry",1,1,true);
    }

    public static void startFons() {
        raspberryArray.forEach((i) -> {
            try {
                startFon(new YX(i,-1), "smRaspberry",1,1,true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
