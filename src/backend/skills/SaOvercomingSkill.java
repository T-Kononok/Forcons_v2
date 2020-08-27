package backend.skills;

import backend.ForconsList;
import backend.models.ForsonsListModel;

public class SaOvercomingSkill extends Skill{

    public static void checkOvercoming() {
        ForsonsListModel model = ForconsList.getForconsListModel();
        for (int i = 0; i < model.getArray().size(); i++) {
            String string = model.getArray().get(i);
            String[] subStrs = string.split("_");
            if (subStrs[0].equals("sa") && subStrs[2].equals("3") && Integer.parseInt(subStrs[3]) < 7) {
                int point = Integer.parseInt(string.substring(string.lastIndexOf("_")+1));
                point++;
                String sub = string.substring(0,string.lastIndexOf("_")+1);
                model.set(i,sub+point);
            }
        }
    }

    @Override
    public boolean isActive() {
        return false;
    }
}
