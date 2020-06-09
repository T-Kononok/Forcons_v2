package frame;

import elements.ImagePanel;
import elements.MenuBasicComboBoxUI;

import javax.swing.*;

public class MainFrame extends JFrame {

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;

    private MainFrame() {

        setUndecorated(true);
        setExtendedState(MAXIMIZED_BOTH);

        ImagePanel panelFull = addPanelFull();
        addJournalTable(panelFull);
        addMenuComboBox(panelFull);
        addCancelButton(panelFull);


        getContentPane().add(panelFull);
    }

    private ImagePanel addPanelFull() {
        ImagePanel panelFull = new ImagePanel("image/fon.jpg");
        panelFull.setLayout(null);
        panelFull.setSize(WIDTH,HEIGHT);
        panelFull.setLocation(0,0);
        return panelFull;
    }

    private void addJournalTable(JPanel panel) {
        JTable journalTable = new JTable();
        journalTable.setTableHeader(null);
        journalTable.setBorder(BorderFactory.createEmptyBorder());
        journalTable.setSize(1050,500);
        journalTable.setLocation(5,5);
        panel.add(journalTable);
    }

    private void addMenuComboBox(JPanel panel) {
        String[] items = {"Fe", "Fi", "Fo", "Fum"};
        JComboBox<String> combo = new JComboBox<>(items);
        combo.setUI(new MenuBasicComboBoxUI());
        combo.setSize(100,25);
        combo.setLocation(1100,5);
        panel.add(combo);
    }

    private void addCancelButton(JPanel panel) {
        JButton cancelButton = new JButton();
        cancelButton.setSize(25,25);
        cancelButton.setLocation(WIDTH-cancelButton.getWidth()-5,5);
        cancelButton.addActionListener(e -> dispose());
        panel.add(cancelButton);
    }

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.setVisible(true);
    }
}
