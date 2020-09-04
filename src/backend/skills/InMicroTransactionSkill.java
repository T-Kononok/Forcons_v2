package backend.skills;

import backend.ForconsList;

public class InMicroTransactionSkill extends Skill{

    public static void inListener(int index) {
        ForconsList.plusPoint(index, 1);
    }

    @Override
    public void begin() {
        InBugUseSkill.addIfFirstBegin();
        if (!InComboSkill.checkCombo(4) && !ForconsList.minusPoint(1))
            return;
        ForconsList.setMicroTrans(true);
    }
}
