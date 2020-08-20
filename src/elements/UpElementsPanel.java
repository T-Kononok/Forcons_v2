package elements;

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
    private final MainData mainData;
    private final ImagePanel fon = new ImagePanel("image/upParchments.png",false);
    private final JButton saveButton;
    private final JLabel attackLabel;
    private final JLabel defenseLabel;
    private final JLabel coinsLabel;
    private final JButton sortPointButton;
    private final JButton sortClassButton;
    private final JButton cancelButton;


    public UpElementsPanel(MainFrame mainFrame, MainData mainData) {
        this.mainFrame = mainFrame;
        this.mainData = mainData;

        fon.setSize(1280,720);
        fon.setLayout(null);
        fon.setOpaque(false);

        saveButton = addSaveButton();

        attackLabel = addLabel(200,30,265,0);
        defenseLabel = addLabel(200,30,530,0);
        coinsLabel = addLabel(200,30,795,0);

        sortPointButton = addSortPointButtons();
        sortClassButton = addSortClassButtons();
        cancelButton = addCancelButton();

        changeElements();
    }

    private JButton addSaveButton() {
        JButton cancelButton = addButton(200,30,0,0);
        cancelButton.addActionListener(ev -> {
            try {
                mainData.writeTable();
                mainData.writeOther();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return cancelButton;
    }

    private JButton addSortPointButtons() {
        JButton sortPointButton = addButton(84,25,1077,10);
        sortPointButton.addActionListener(ev -> mainFrame.getForconsList().sortPoint());
        return sortPointButton;
    }

    private JButton addSortClassButtons() {
        JButton sortClassButton = addButton(84,25,1161,10);
        sortClassButton.addActionListener(ev -> mainFrame.getForconsList().sortClass());
        return sortClassButton;
    }

    private JButton addCancelButton() {
        JButton cancelButton = addButton(25,25,1250,5);
        cancelButton.addActionListener(ev -> {
            mainFrame.getTableNoGaps().stopThread();
            mainFrame.dispose();
        });
        return cancelButton;
    }

    public JButton addButton(int width, int height, int x, int y) {
        JButton button = new JButton();
        toPlace(button,width,height,x,y);
        button.setVisible(false);
        button.setBorderPainted(false);
//        button.setContentAreaFilled(false);
        return button;
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

    private JLabel addLabel(int width, int height, int x, int y) {
        Font font = null;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new BufferedInputStream(
                    new FileInputStream("Fortuna Gothic FlorishC.ttf"))).
                    deriveFont(Font.PLAIN, 25);
        } catch (Exception ignored) { }
        JLabel label = new JLabel();
//        label.setBorder(BorderFactory.createLineBorder(Color.RED));
        label.setForeground(Color.BLACK);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(font);
        toPlace(label,width,height,x,y);
        return label;
    }

    private void toPlace(JComponent component,int width, int height, int x, int y) {
        component.setSize(width,height);
        component.setLocation(x,y);
        fon.add(component);
    }

    public void setVisible(boolean flag) {
        fon.setVisible(flag);
        saveButton.setVisible(flag);
        sortPointButton.setVisible(flag);
        sortClassButton.setVisible(flag);
        cancelButton.setVisible(flag);
    }
}

