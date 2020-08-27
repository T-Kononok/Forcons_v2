package backend.skills;

import backend.ForconsList;

import java.io.IOException;
import java.util.ArrayList;

public class InDLCSkill extends Skill {

    private static final ArrayList<String> DLCArray = new ArrayList<>();

    public static ArrayList<String> getDLCArray() {
        return DLCArray;
    }

    @Override
    public void begin() {
        if (!ForconsList.minusPoint(2))
            return;
        DLCArray.add(ForconsList.getSelectedValue());
        ForconsList.minusAllPoint();
    }

    public static void addDLC() {
        DLCArray.forEach(ForconsList::add);
        ForconsList.sortPoint();
        ForconsList.sortClass();
    }
}
