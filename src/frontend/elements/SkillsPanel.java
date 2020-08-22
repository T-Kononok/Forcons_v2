package frontend.elements;

import backend.skillsData.SkillEffect;
import backend.skillsData.SkillsThread;
import frontend.frame.TableNoGaps;

import javax.swing.*;
import java.awt.*;

public class SkillsPanel extends JPanel {

    private SkillsThread skillsThread;

    public SkillsPanel() {
        super();
        setBorder(BorderFactory.createEmptyBorder());
        setBackground(new Color(0, 0, 0, 0));
        setOpaque(false);
    }


    public void startThread() {
        skillsThread = new SkillsThread();
        skillsThread.start();
    }

    public void stopThread(){
        skillsThread.stop();
    }

    public void addEffect(SkillEffect skillEffect) {
        skillsThread.addEffect(skillEffect);
        repaint(skillEffect.getX(),skillEffect.getY(),skillEffect.getWidth(),TableNoGaps.getSkillsPanel().getHeight()-skillEffect.getY());
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D canvas = (Graphics2D) g;
        for (SkillEffect effect: skillsThread.getEffects())
            effect.paint(canvas);
    }
}
