package elements;

import auxiliary.Auxiliary;
import data.MainData;
import data.skills.Skill;
import frame.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class UpElementsPanel{

    private final MainFrame mainFrame;
    private final ImagePanel fon = new ImagePanel("image/upParchments.png",false);
    private final JButton saveButton;
    private final JLabel attackLabel;
    private final JLabel defenseLabel;
    private final JLabel coinsLabel;
    private final JButton sortPointButton;
    private final JButton sortClassButton;
    private final JButton cancelButton;


    public UpElementsPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        fon.setSize(1280,720);
        fon.setLayout(null);
        fon.setOpaque(false);

        saveButton = addSaveButton();

        attackLabel = Auxiliary.addLabel(fon,200,30,265,0,25);
        defenseLabel = Auxiliary.addLabel(fon,200,30,530,0,25);
        coinsLabel = Auxiliary.addLabel(fon,200,30,795,0,25);

        sortPointButton = addSortPointButtons();
        sortClassButton = addSortClassButtons();
        cancelButton = addCancelButton();

        changeElements();
    }

    private JButton addSaveButton() {
        JButton cancelButton = Auxiliary.addButton(fon,200,30,0,0);
        cancelButton.addActionListener(ev -> {
            MainData.writeTable();
            MainData.writeOther();
        });
        return cancelButton;
    }

    private JButton addSortPointButtons() {
        JButton sortPointButton = Auxiliary.addButton(fon,84,25,1077,10);
        sortPointButton.addActionListener(ev -> MainFrame.getForconsList().sortPoint());
        return sortPointButton;
    }

    private JButton addSortClassButtons() {
        JButton sortClassButton = Auxiliary.addButton(fon,84,25,1161,10);
        sortClassButton.addActionListener(ev -> MainFrame.getForconsList().sortClass());
        return sortClassButton;
    }

    private JButton addCancelButton() {
        JButton cancelButton = Auxiliary.addButton(fon,25,25,1250,5);
        cancelButton.addActionListener(ev -> {
            mainFrame.getTableNoGaps().stopThread();
            mainFrame.dispose();
        });
        return cancelButton;
    }

    public void addIn(JComponent component, int x, int y) {
        fon.setLocation(x, y);
        component.add(fon);
    }

    public void changeElements() {
        attackLabel.setText("+ " + String.format("%.1f",Skill.getBuffAttack()) + " урона");
        defenseLabel.setText("+ " + Skill.getBuffDefense()  + "% защиты" );
        coinsLabel.setText("+ " + Skill.getCoins() + " долг");
    }

    public void setVisible(boolean flag) {
        fon.setVisible(flag);
        saveButton.setVisible(flag);
        sortPointButton.setVisible(flag);
        sortClassButton.setVisible(flag);
        cancelButton.setVisible(flag);
    }
}

