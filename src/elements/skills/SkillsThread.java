package elements.skills;
import elements.TableNoGaps;

import java.util.ArrayList;

public class SkillsThread extends Thread {
    private final ArrayList<SkillEffect> effects = new ArrayList<>();

    public void addEffect(SkillEffect skillEffect){
        effects.add(skillEffect);
    }

    public ArrayList<SkillEffect> getEffects() {
        return effects;
    }

    @Override
    public void run() {
        while (true) {

            System.out.print("");

            if (effects.size() > 0) {

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                int x,y,width,height;
                for (int i = 0; i < effects.size(); i++) {
                    if (!effects.get(i).minusTime()) {
                        x = effects.get(i).getX();
                        y = effects.get(i).getY();
                        width = effects.get(i).getWidth();
                        height = TableNoGaps.getSkillsPanel().getHeight()-effects.get(i).getY();
                        effects.remove(effects.get(i));
//                        System.out.println("repaint");
                        TableNoGaps.getSkillsPanel().repaint(x,y,width,height);
                    }
                }
            }
        }
    }
}
