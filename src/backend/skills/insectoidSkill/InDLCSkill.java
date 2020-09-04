package backend.skills.insectoidSkill;

import backend.ForconsList;
import backend.skills.Skill;

import java.util.ArrayList;

public class InDLCSkill extends Skill {

    private static final ArrayList<String> DLCArray = new ArrayList<>();

    public static ArrayList<String> getDLCArray() {
        return DLCArray;
    }

    @Override
    public void begin() {
        if (!InComboSkill.checkCombo(3) && !ForconsList.minusPoint(2))
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
