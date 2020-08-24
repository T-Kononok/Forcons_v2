package backend.skills;

import backend.*;
import backend.marks.MarksData;
import frontend.frame.TableNoGaps;

import java.io.IOException;
import java.util.ArrayList;

public class SmLightSkill extends Skill{

    private static final ArrayList<Integer> light = new ArrayList<>();

    public static ArrayList<Integer> getLight() {
        return light;
    }

    public static boolean contains(int row) {
        for (Integer integer : light)
            if (integer == row)
                return true;
        return false;
    }

    @Override
    public void begin() throws IOException {
        if (light.size() == 24)
            return;
        if (!ForconsList.minusPoint(3))
            return;
        int row;
        do {
            row = getRandomRow();
        } while (contains(row));
        light.add(row);
        startFon(new YX(row-1,0), "smLight", MarksData.getColumnCount(),3,true);
    }
}
