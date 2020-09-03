package backend.skills;

import backend.ForconsList;
import backend.YX;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class InComboSkill extends Skill {

    //тут YX как пара int int
    private static final Map<String, YX> mapCombo = new HashMap<>();

    public static boolean contains(String name) {
        AtomicBoolean flag = new AtomicBoolean(false);
        mapCombo.forEach((s,m) -> {
            if (s.equals(name))
                flag.set(true);
        });
        return flag.get();
    }

    public static boolean checkCombo(int number) {
        String name = ForconsList.getSelectedValue().split("_")[1];
        if (!contains(name)) {
            mapCombo.put(name, new YX(0,0));
        }
        if (mapCombo.get(name).getY() == number) {
            mapCombo.get(name).setX(mapCombo.get(name).getX() + 1);
            if (mapCombo.get(name).getX() == 4) {
                mapCombo.get(name).setX(0);
                return true;
            }
        } else {
            mapCombo.get(name).setY(number);
            mapCombo.get(name).setX(1);
        }
        return false;
    }

    @Override
    public boolean isActive() {
        return false;
    }
}
