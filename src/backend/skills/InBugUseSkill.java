package backend.skills;
import backend.ForconsList;
import java.util.ArrayList;

public class InBugUseSkill extends Skill {

    private static final ArrayList<String> arrayNoFirstBegin = new ArrayList<>();

    public static boolean isFirstBegin(String name) {
        for (String string : arrayNoFirstBegin)
            if (string.equals(name))
                return false;
        return true;
    }

    public static void addIfFirstBegin() {
        String name = ForconsList.getSelectedValue().split("_")[1];
        if (!isFirstBegin(name))
            return;
        arrayNoFirstBegin.add(name);
    }

    @Override
    public void begin() {
        if (!ForconsList.canMinusPoint(1))
            return;
        String name = ForconsList.getSelectedValue().split("_")[1];
        if (!isFirstBegin(name))
            return;
        if (checkChance(0.5))
            ForconsList.plusPoint(5);
        else
            ForconsList.minusAllPoint();
    }
}
