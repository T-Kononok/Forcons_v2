import elements.ImagePanel;
import javax.swing.*;

public class MainFrame extends JFrame {

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;

    private MainFrame() {

        setUndecorated(true);
        setExtendedState(MAXIMIZED_BOTH);

        ImagePanel panelFull = addPanelFull();

        addCancelButton(panelFull);
        addJornalTable(panelFull);

        getContentPane().add(panelFull);
    }

    private ImagePanel addPanelFull() {
        ImagePanel panelFull = new ImagePanel("image/fon.jpg");
        panelFull.setLayout(null);
        panelFull.setBounds(0,0,WIDTH,HEIGHT);
        return panelFull;
    }

    private void addCancelButton(JPanel panel) {
        JButton cancelButton = new JButton("Выход");
        cancelButton.setBounds(1170,10,100,50);
        cancelButton.addActionListener(e -> dispose());
        panel.add(cancelButton);
    }

    private void addJornalTable(JPanel panel) {
        JTable journalTable = new JTable();
        journalTable.setTableHeader(null);
        journalTable.setBorder(BorderFactory.createEmptyBorder());
        journalTable.setBounds(0,25,1050,500);
        panel.add(journalTable);
    }

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.setVisible(true);
    }
}
