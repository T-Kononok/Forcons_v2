package backend.skillsData;

import backend.skills.Skill;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class SkillButtonActionListener implements ActionListener {
    private Skill skill = null;

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public Skill getSkill() {
        return skill;
    }

    synchronized public void actionPerformed(ActionEvent e) {
        if (skill != null) {
            try {
                skill.begin();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }


}
