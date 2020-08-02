package elements;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SkillsPanel extends JPanel {

    private ArrayList<SkillEffect> effects = new ArrayList<>();

    public SkillsPanel() {
        SkillsThread skillsThread = new SkillsThread(this);
        skillsThread.start();
    }

    public ArrayList<SkillEffect> getEffects() {
        return effects;
    }

    public void addEffect(SkillEffect skillEffect) {
        effects.add(skillEffect);
    }

    public void removeEffect(SkillEffect skillEffect) {
        effects.remove(skillEffect);
    }

    public SkillEffect getEffect(int index) {
        return effects.get(index);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D canvas = (Graphics2D) g;
        for (SkillEffect effect: effects) {
            effect.paint(canvas);
        }
        if (effects.size() > 0)
            setBackground(Color.RED);
    }
}
