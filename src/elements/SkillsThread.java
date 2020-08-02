package elements;

import data.MainData;

import java.util.ArrayList;
import java.util.Iterator;

public class SkillsThread extends Thread {

        private final SkillsPanel skillsPanel;

        public SkillsThread(SkillsPanel skillsPanel) {
            this.skillsPanel = skillsPanel;
        }

        @Override
        public void run() {
            boolean repaint;
            while (true) {
                if (skillsPanel.getEffects().size() > 0) {
                    try {
                        Thread.sleep(100);
                        for (SkillEffect effect : skillsPanel.getEffects()) {
                            effect.write();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    repaint = false;

//                for (SkillEffect effect : skillsPanel.getEffects()) {
//                    if (!effect.minusTime()) {
//                        skillsPanel.removeEffect(effect);
//                        repaint = true;
//                    }
//                }
                    for (int i = 0; i < skillsPanel.getEffects().size(); i++) {
                        if (!skillsPanel.getEffect(i).minusTime()) {
                            skillsPanel.removeEffect(skillsPanel.getEffect(i));
                            repaint = true;
                        }
                    }

                    if (repaint) {
                        skillsPanel.setVisible(false);
                        skillsPanel.setVisible(true);
                    }
                }
            }
        }
    }
