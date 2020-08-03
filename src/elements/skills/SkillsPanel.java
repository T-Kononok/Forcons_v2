package elements.skills;

import elements.TableNoGaps;

import javax.swing.*;
import java.awt.*;

public class SkillsPanel extends JPanel {

    private SkillsThread skillsThread;
    private final TableNoGaps tableNoGaps;

    public SkillsPanel(TableNoGaps tableNoGaps) {
        super();
        this.tableNoGaps = tableNoGaps;
        setBorder(BorderFactory.createEmptyBorder());
        setBackground(new Color(0, 0, 0, 0));
        setOpaque(false);
    }

    public TableNoGaps getTable() {
        return tableNoGaps;
    }

    public void startThread() {
        skillsThread = new SkillsThread(this);
        skillsThread.start();
    }

    public void stopThread(){
        skillsThread.stop();
    }

    public void addEffect(SkillEffect skillEffect) {
        skillsThread.addEffect(skillEffect);
        repaint(skillEffect.getX(),skillEffect.getY(),skillEffect.getWidth(),tableNoGaps.getSkillsPanel().getHeight()-skillEffect.getY());
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D canvas = (Graphics2D) g;
        for (SkillEffect effect: skillsThread.getEffects())
            effect.paint(canvas);
    }
}
