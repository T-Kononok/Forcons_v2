package frame;

import elements.ImagePanel;
import elements.MenuBasicComboBoxUI;

import javax.swing.*;
import java.util.Vector;

public class MainFrame extends JFrame {

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private ImagePanel panelFull;

    private MainFrame() {

        setUndecorated(true);
        setExtendedState(MAXIMIZED_BOTH);

        addPanelFull();
        addJournalTable();
        addMenuComboBox();
        addSortButtons();
        addCancelButton();
        addForconsList();
        addSkillButtons();


        getContentPane().add(panelFull);
        setVisible(true);
    }

    //костыль дабы не было иногда прозрачного экрана
    public void setImageKost() {
        panelFull.setImageFile("image/fon.jpg");
    }

    private void addPanelFull() {
        panelFull = new ImagePanel("image/fon.jpg");
        panelFull.setLayout(null);
        panelFull.setSize(WIDTH,HEIGHT);
        panelFull.setLocation(0,0);
    }

    private void addJournalTable() {
        JTable journalTable = new JTable();
        journalTable.setTableHeader(null);
        journalTable.setBorder(BorderFactory.createEmptyBorder());
        journalTable.setSize(1050,615);
        journalTable.setLocation(5,5);
        panelFull.add(journalTable);
    }

    private void addMenuComboBox() {
        String[] items = {"10 ФМ-2", "10 ФМ-3", "11 ФМ-3", "Форсоны", "Сохранить"};
        JComboBox<String> combo = new JComboBox<>(items);
        combo.setUI(new MenuBasicComboBoxUI());
        combo.setSize(100,25);
        combo.setLocation(1060,5);
        panelFull.add(combo);
    }

    private void addSortButtons() {
        JButton sortPointButton = new JButton();
        sortPointButton.setSize(40,25);
        sortPointButton.setLocation(1165,10);
//        sortPointButton.addActionListener(ev -> );
        panelFull.add(sortPointButton);
        JButton sortClassButton = new JButton();
        sortClassButton.setSize(40,25);
        sortClassButton.setLocation(1205,10);
//        sortClassButton.addActionListener(ev -> );
        panelFull.add(sortClassButton);
    }

    private void addCancelButton() {
        JButton cancelButton = new JButton();
        cancelButton.setSize(25,25);
        cancelButton.setLocation(1250,5);
        cancelButton.addActionListener(ev -> dispose());
        panelFull.add(cancelButton);
    }

    private void addForconsList() {
        Vector<String> vectorForsons = new Vector<>();
        //
        vectorForsons.addElement(",ba,Горшок,3,6");
        vectorForsons.addElement(",sa,АскаМисатоРей,2,12");
        vectorForsons.addElement(",in,Стив,1,2");
        //
        DefaultListModel<String> forconsListModel = new DefaultListModel<>();
        vectorForsons.forEach(forconsListModel::addElement);

        JList<String> forconsList = new JList<>(forconsListModel);
//        forconsList.setCellRenderer(new ForconsRenderer());
        forconsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane forconsListScroll = new JScrollPane(forconsList);
//        forconsListScroll.getVerticalScrollBar().setUI(new MyScrollbarUI());
        forconsListScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        forconsListScroll.setBorder(BorderFactory.createEmptyBorder());
        forconsListScroll.setSize(215, 615 - 30);
        forconsListScroll.setLocation(1060,35);
        panelFull.add(forconsListScroll);
    }

    private void addSkillButtons() {
        Vector<JButton> skillButtonsVector = new Vector<>();
        int size = 60;
        int strut = 40;
        int x = (WIDTH - size*6 - strut*5)/2;
        for (int i = 0; i < 6; i++) {
            skillButtonsVector.add(addOneSkillButton(x,size));
            x += size+strut;
        }
        skillButtonsVector.get(0).addActionListener(ev -> dispose());

    }

    private JButton addOneSkillButton(int x, int size) {
        JButton skillButton = new JButton();
        skillButton.setSize(size,size);
        skillButton.setLocation(x,HEIGHT - skillButton.getHeight() - 20);
        panelFull.add(skillButton);
        return skillButton;
    }

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        //костыль дабы не было иногда прозрачного экрана
        frame.setImageKost();
    }
}
