package elements;

import data.skills.Skill;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SkillButtonActionListener implements ActionListener {
    private Skill skill = null;

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public Skill getSkill() {
        return skill;
    }

    synchronized public void actionPerformed(ActionEvent e) {
        if (skill != null)
            skill.begin();
    }


}
