package frame;

import data.ForconsList;
import data.SortForconsVector;
import elements.ForconsRenderer;
import elements.ImagePanel;
import elements.MenuComboBoxUI;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

import elements.ImageScrollBarUI;
import org.apache.batik.swing.JSVGCanvas;

public class MainFrame extends JFrame {

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private ImagePanel panelFull;
    private int xFirstButton;
    private int xLastButton;

    private MainFrame() {

        setUndecorated(true);
        setExtendedState(MAXIMIZED_BOTH);

        addPanelFull();
        addJournalTable();
        addMenuComboBox();
        addCancelButton();
        ForconsList forconsList = addForconsList();
        addSortButtons(forconsList);
        addSvgCanvasClass();

        addSkillButtons();
        addNameLabel();
        addSvgCanvasPoint();


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
        String[] items = {"10 ФМ-2", "10 ФМ-3", "11 ФМ-3", "Форсоны","Отменить", "Сохранить"};
        JComboBox<String> combo = new JComboBox<>(items);
        combo.setUI(new MenuComboBoxUI());
        combo.setSize(100,25);
        combo.setLocation(1060,5);
        panelFull.add(combo);
    }

    private void addCancelButton() {
        JButton cancelButton = new JButton();
        cancelButton.setSize(25,25);
        cancelButton.setLocation(1250,5);
        cancelButton.addActionListener(ev -> dispose());
        panelFull.add(cancelButton);
    }

    private ForconsList addForconsList() {

        ForconsList forconsList = new ForconsList();
        addInVector(forconsList);

        JScrollPane forconsListScroll = new JScrollPane(forconsList.getList());
        forconsListScroll.getVerticalScrollBar().setUI(new ImageScrollBarUI());
        forconsListScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        forconsListScroll.setBorder(BorderFactory.createEmptyBorder());
        forconsListScroll.setSize(220, 615 - 30);
        forconsListScroll.setLocation(1060,35);
        panelFull.add(forconsListScroll);
        return forconsList;
    }

    private void addInVector(ForconsList forconsList) {
        forconsList.getVector().addElement("ba,Горшок,3,0");
        forconsList.getVector().addElement("ba,Горшок,3,6");
        forconsList.getVector().addElement("sa,АскаМисатоРей,2,12");
        forconsList.getVector().addElement("in,Стив,1,2");
        forconsList.getVector().addElement("sm,Стив,1,0");
        forconsList.getVector().addElement("ba,Горшок,3,6");
        forconsList.getVector().addElement("in,Аска,2,2");
        forconsList.getVector().addElement("in,Стив,1,8");
        forconsList.getVector().addElement("sa,Стив,1,11");
        forconsList.getVector().addElement("ba,Горшок,3,0");
        for (int i = 0; i < 5; i++) {
            forconsList.getVector().addElement("sa,Аска,2,2");
            forconsList.getVector().addElement("sa,Стив,1,5");
            forconsList.getVector().addElement("sm,Стив,1,4");
            forconsList.getVector().addElement("ba,Горшок,3,6");
        }
        forconsList.sortClass();
    }

    private void addSortButtons(ForconsList forconsList) {
        JButton sortPointButton = new JButton();
        sortPointButton.setSize(40,25);
        sortPointButton.setLocation(1165,10);
        sortPointButton.addActionListener(ev -> forconsList.sortPoint());
        panelFull.add(sortPointButton);
        JButton sortClassButton = new JButton();
        sortClassButton.setSize(40,25);
        sortClassButton.setLocation(1205,10);
        sortClassButton.addActionListener(ev -> forconsList.sortClass());
        panelFull.add(sortClassButton);
    }

    private void addSvgCanvasClass() {
        JSVGCanvas svgCanvasClass = new JSVGCanvas();
        svgCanvasClass.setSize(90,90);
        svgCanvasClass.setLocation(5,625);
        svgCanvasClass.setBackground(new Color(0,0,0,0));
        svgCanvasClass.setURI("file:image/svg/ba.svg");
        panelFull.add(svgCanvasClass);
    }

    private void addSkillButtons() {
        Vector<JButton> skillButtonsVector = new Vector<>();
        int size = 60;
        int strut = 40;
        xFirstButton = (WIDTH - size*6 - strut*5)/2;
        int x = xFirstButton;
        for (int i = 0; i < 6; i++) {
            skillButtonsVector.add(addOneSkillButton(x,size));
            x += size+strut;
        }
        xLastButton = x - strut;
        skillButtonsVector.get(0).addActionListener(ev -> dispose());
    }

    private void addNameLabel() {
        JLabel nameLabel = new JLabel("Имечко");
        Font fontName = new Font("Verdana", Font.BOLD, 30);
        nameLabel.setFont(fontName);
        nameLabel.setVerticalAlignment(JLabel.CENTER);
        nameLabel.setHorizontalAlignment(JLabel.CENTER);
        nameLabel.setSize(xFirstButton-105,50);
        nameLabel.setLocation(100,670-(nameLabel.getHeight()/2));

//        nameLabel.setBorder(BorderFactory.createLineBorder(Color.RED));
        panelFull.add(nameLabel);
    }

    private void addSvgCanvasPoint() {
        JSVGCanvas svgCanvasPoint = new JSVGCanvas();
        int widthPoint = WIDTH - xLastButton - 10;
        svgCanvasPoint.setSize(widthPoint, 90);
        svgCanvasPoint.setLocation(xLastButton + 5,670-(svgCanvasPoint.getHeight()/2));
        svgCanvasPoint.setBackground(new Color(0,0,0,0));
        svgCanvasPoint.setURI("file:image/svg/point3.svg");
        addLabelPoint(svgCanvasPoint);
        panelFull.add(svgCanvasPoint);
    }

    private void addLabelPoint(JSVGCanvas svgCanvasPoint) {
        JLabel lebelPoint = new JLabel("Очки поинтов");
        lebelPoint.setSize(svgCanvasPoint.getSize());
        lebelPoint.setLocation(svgCanvasPoint.getLocation());

        Font fontPoint = new Font("Verdana", Font.BOLD, 30);
        lebelPoint.setFont(fontPoint);
        lebelPoint.setVerticalAlignment(JLabel.CENTER);
        lebelPoint.setHorizontalAlignment(JLabel.CENTER);

//        lebelPoint.setBorder(BorderFactory.createLineBorder(Color.RED));

        panelFull.add(lebelPoint);
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
