package data.skills;

import data.*;

import java.io.IOException;
import java.util.ArrayList;

public class SmotrLightSkill extends Skill{

    private static final ArrayList<Integer> light = new ArrayList<>();

    public static ArrayList<Integer> getLight() {
        return light;
    }

    private void addLight(int number) {
        if (number >= 0 && number < MarksData.getRowCount())
            light.add(number);
        else
            System.out.println("Ошибка addLight");
    }

    private boolean lightContains(int row) {
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
        } while (lightContains(row));
        addLight(row);
        startFon(new YX(row,-2), "smotrLight",2,1,true);
//        System.out.println("addLight" + row);
    }
}
