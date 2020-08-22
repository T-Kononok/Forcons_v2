package elements;

import auxiliary.Auxiliary;
import data.ForconsList;
import data.ReadWriteData;
import data.SkillsData;
import data.SkillsMap;
import data.skills.BardBalladSkill;
import data.skills.BardCoinsSkill;
import data.skills.BardDefenseSkill;

import javax.swing.*;

public class UpElementsPanel{

    private static final ImagePanel fon = new ImagePanel("image/upParchments.png",false);
    private static final JButton saveButton;
    private static final JLabel attackLabel;
    private static final JLabel defenseLabel;
    private static final JLabel coinsLabel;
    private static final JButton sortPointButton;
    private static final JButton sortClassButton;

    static {

        fon.setSize(1280,720);
        fon.setLayout(null);
        fon.setOpaque(false);

        saveButton = addSaveButton();

        attackLabel = Auxiliary.addLabel(fon,200,30,265,0,25);
        defenseLabel = Auxiliary.addLabel(fon,200,30,530,0,25);
        coinsLabel = Auxiliary.addLabel(fon,200,30,795,0,25);

        sortPointButton = addSortPointButtons();
        sortClassButton = addSortClassButtons();

        changeElements();
    }

    private static JButton addSaveButton() {
        JButton cancelButton = Auxiliary.addButton(fon,200,30,0,0);
        cancelButton.addActionListener(ev -> {
            ReadWriteData.writeTable();
            ReadWriteData.writeOther();
        });
        return cancelButton;
    }

    private static JButton addSortPointButtons() {
        JButton sortPointButton = Auxiliary.addButton(fon,84,25,1077,10);
        sortPointButton.addActionListener(ev -> ForconsList.sortPoint());
        return sortPointButton;
    }

    private static JButton addSortClassButtons() {
        JButton sortClassButton = Auxiliary.addButton(fon,84,25,1161,10);
        sortClassButton.addActionListener(ev -> ForconsList.sortClass());
        return sortClassButton;
    }

    public static void addIn(JComponent component, int x, int y) {
        fon.setLocation(x, y);
        component.add(fon);
    }

    public static void changeElements() {
        attackLabel.setText("+ " + String.format("%.1f", BardBalladSkill.getDouble()) + " урона");
        defenseLabel.setText("+ " + BardDefenseSkill.get()  + "% защиты" );
        coinsLabel.setText("+ " + BardCoinsSkill.get() + " долг");
    }

    public static void setVisible(boolean flag) {
        fon.setVisible(flag);
        saveButton.setVisible(flag);
        sortPointButton.setVisible(flag);
        sortClassButton.setVisible(flag);
    }
}

