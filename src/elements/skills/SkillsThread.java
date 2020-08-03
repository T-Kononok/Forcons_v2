package elements.skills;
import java.util.ArrayList;

public class SkillsThread extends Thread {
    private final ArrayList<SkillEffect> effects = new ArrayList<>();
    private final SkillsPanel skillsPanel;

    public SkillsThread(SkillsPanel skillsPanel) {
        this.skillsPanel = skillsPanel;
    }

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
                        height = skillsPanel.getTable().getHeight()-effects.get(i).getY();
                        effects.remove(effects.get(i));
                        System.out.println("repaint");
                        skillsPanel.repaint(x,y,width,height);
                    }
                }
            }
        }
    }
}
