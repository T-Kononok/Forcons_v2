package frame;

import data.ForconsList;
import data.JournalTableModel;
import data.MainData;
import data.Mark;
import elements.*;

import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.TableView;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Enumeration;

import org.apache.batik.swing.JSVGCanvas;

public class MainFrame extends JFrame {

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private ImagePanel panelFull;
    private int xFirstButton;
    private int xLastButton;
    private MainData mainData = new MainData();
    private JFileChooser fileChooser = null;

    private MainFrame() {

        setUndecorated(true);
        setExtendedState(MAXIMIZED_BOTH);

        addPanelFull();
        JTable table = addJournalTable();
        addMenuComboBox();
        addCancelButton();
        ForconsList forconsList = addForconsList();
        addListButton(forconsList,
                addForconsListScroll(forconsList),
                addSortPointButtons(forconsList),
                addSortClassButtons(forconsList));

        addSvgCanvasClass(forconsList);
        addSkillButtons(forconsList, table);
        addNameLabel(forconsList);
        addSvgCanvasPoint(forconsList);


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

    private JTable addJournalTable() {
        JTable journalTable = new JTable(new JournalTableModel());
        journalTable.setTableHeader(null);
        journalTable.setBorder(BorderFactory.createEmptyBorder());
        journalTable.setBackground(new Color(0,0,0,0));
        journalTable.setShowVerticalLines(false);
        journalTable.setShowHorizontalLines(false);
        journalTable.setRowSelectionAllowed(false);
        journalTable.setSize(1050,580);
        journalTable.setLocation(5,35);
        panelFull.add(journalTable);
        return journalTable;
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
//        forconsList.addInArray();
        return forconsList;
    }

    private JScrollPane addForconsListScroll(ForconsList list) {
        JScrollPane forconsListScroll = new JScrollPane(list.getList());
        forconsListScroll.getVerticalScrollBar().setUI(new ImageScrollBarUI());
        forconsListScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        forconsListScroll.setBorder(BorderFactory.createEmptyBorder());
        forconsListScroll.setSize(220, 580);
        forconsListScroll.setLocation(1060,35);
        forconsListScroll.setVisible(false);
        panelFull.add(forconsListScroll);
        return forconsListScroll;
    }

    private JButton addSortPointButtons(ForconsList forconsList) {
        JButton sortPointButton = new JButton();
        sortPointButton.setSize(40,25);
        sortPointButton.setLocation(1165,10);
        sortPointButton.addActionListener(ev -> forconsList.sortPoint());
        sortPointButton.setVisible(false);
        panelFull.add(sortPointButton);
        return sortPointButton;
    }

    private JButton addSortClassButtons(ForconsList forconsList) {
        JButton sortClassButton = new JButton();
        sortClassButton.setSize(40,25);
        sortClassButton.setLocation(1205,10);
        sortClassButton.addActionListener(ev -> forconsList.sortClass());
        sortClassButton.setVisible(false);
        panelFull.add(sortClassButton);
        return sortClassButton;
    }

    private void addListButton(ForconsList list,JScrollPane pane, JButton pointButton, JButton classButton) {
        JButton listButton = new JButton();
        listButton.setSize(100,50);
        int x = pane.getX()+(pane.getWidth()-listButton.getWidth())/2;
        int y = pane.getY()+(pane.getHeight()-listButton.getHeight())/2;
        listButton.setLocation(x,y);
        listButton.addActionListener(ev -> {
            list.read("D:\\Джава\\Forcons_v2\\Список форсонов.txt");
//            if (fileChooser==null) {
//                fileChooser = new JFileChooser();
//                fileChooser.setCurrentDirectory(new File("."));
//            }
//            if (fileChooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
//                list.read(fileChooser.getSelectedFile().getPath());
//            }
            pane.setVisible(true);
            pointButton.setVisible(true);
            classButton.setVisible(true);
            listButton.setVisible(false);
        });
        panelFull.add(listButton);
    }

    private void addSvgCanvasClass(ForconsList forconsList) {
        JSVGCanvas svgCanvasClass = new JSVGCanvas();
        svgCanvasClass.setSize(90,90);
        svgCanvasClass.setLocation(5,623);
        svgCanvasClass.setBackground(new Color(0,0,0,0));
        panelFull.add(svgCanvasClass);
        forconsList.getList().addListSelectionListener(evt -> {
            if (!evt.getValueIsAdjusting() && forconsList.getList().getSelectedIndex() != -1) {
                    String val = forconsList.getList().getSelectedValue();
                    String[] subStr = val.split(",");
                    svgCanvasClass.setURI("file:/D:/Джава/Forcons_v2/image/svg/" + subStr[0] + ".svg");
            }
        });
    }

    private void addSkillButtons(ForconsList forconsList, JTable table) {
        ArrayList<JSVGCanvas> skillSVGArray = new ArrayList<>();
        ArrayList<JButton> skillButtonsArray = new ArrayList<>();
        int size = 60;
        int strut = 40;
        xFirstButton = (WIDTH - size*6 - strut*5)/2;
        int x = xFirstButton;
        for (int i = 0; i < 6; i++) {
            skillButtonsArray.add(addOneSkillButton(x,size));
            skillSVGArray.add(addOneSkillSVG(x,size));
            x += size+strut;
        }
        xLastButton = x - strut;

        skillButtonsArray.get(0).addActionListener(ev -> dispose());
        skillButtonsArray.get(1).addActionListener(ev -> {
            if (fileChooser==null) {
                fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File("."));
            }
            if (fileChooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
                mainData.readTable(table,fileChooser.getSelectedFile().getPath());
                int cellSize = resizeTable(table);
                table.setDefaultRenderer(Mark.class, new JournalTableCellRenderer(cellSize));
                table.repaint();
            }
        });

        forconsList.getList().addListSelectionListener(evt -> {
            if (!evt.getValueIsAdjusting() && forconsList.getList().getSelectedIndex() != -1) {
                String val = forconsList.getList().getSelectedValue();
                String[] subStr = val.split(",");
                for (int i = 0; i < 6; i++) {
                    skillSVGArray.get(i).setURI(
                            "file:/D:/Джава/Forcons_v2/image/svg/" + subStr[0] + "Skill" + (i+1) + ".svg");
                }
            }
        });
    }

    private JButton addOneSkillButton(int x, int size) {
        JButton skillButton = new JButton();
        skillButton.setSize(size,size);
        skillButton.setLocation(x,HEIGHT - skillButton.getHeight() - 23);
        skillButton.setBorderPainted(false);
        skillButton.setContentAreaFilled(false);
        panelFull.add(skillButton);
        return skillButton;
    }

    private JSVGCanvas addOneSkillSVG(int x, int size) {
        JSVGCanvas canvas = new JSVGCanvas();
        canvas.setBackground(new Color(0, 0, 0, 0));
        canvas.setSize(size, size);
        canvas.setLocation(x,HEIGHT - canvas.getHeight() - 23);
        panelFull.add(canvas);
        return canvas;
    }

    private void addNameLabel(ForconsList forconsList) {
        ReSizeLabel nameLabel = new ReSizeLabel();
        Font fontName = new Font("Verdana", Font.BOLD, 30);
        nameLabel.setFont(fontName);
        nameLabel.setVerticalAlignment(JLabel.CENTER);
        nameLabel.setHorizontalAlignment(JLabel.CENTER);
        nameLabel.setSize(xFirstButton-105,50);
        nameLabel.setLocation(100,667-(nameLabel.getHeight()/2));

//        nameLabel.setBorder(BorderFactory.createLineBorder(Color.RED));
        panelFull.add(nameLabel);

        forconsList.getList().addListSelectionListener(evt -> {
            if (!evt.getValueIsAdjusting() && forconsList.getList().getSelectedIndex() != -1) {
                String val = forconsList.getList().getSelectedValue();
                String[] subStr = val.split(",");
                nameLabel.setTextReSize(subStr[1],fontName);
            }
        });
    }

    private void addSvgCanvasPoint(ForconsList forconsList) {
        JSVGCanvas svgCanvasPoint = new JSVGCanvas();
        int widthPoint = WIDTH - xLastButton - 10;
        svgCanvasPoint.setSize(widthPoint, 90);
        svgCanvasPoint.setLocation(xLastButton + 5,667-(svgCanvasPoint.getHeight()/2));
        svgCanvasPoint.setBackground(new Color(0,0,0,0));
        JLabel label = addLabelPoint(svgCanvasPoint);
        panelFull.add(svgCanvasPoint);

        forconsList.getList().addListSelectionListener(evt -> {
            if (!evt.getValueIsAdjusting() && forconsList.getList().getSelectedIndex() != -1) {
                String val = forconsList.getList().getSelectedValue();
                String[] subStr = val.split(",");

                int pointInt = Integer.parseInt(subStr[3]);
                if (pointInt < 8) {
                    svgCanvasPoint.setURI("file:/D:/Джава/Forcons_v2/image/svg/point" + subStr[3] + ".svg");
                    svgCanvasPoint.setVisible(true);
                    label.setText("");
                } else {
                    svgCanvasPoint.setVisible(false);
                    label.setText(subStr[3] + " о. д.");
                }
            }
        });
    }

    private JLabel addLabelPoint(JSVGCanvas svgCanvasPoint) {
        JLabel lebelPoint = new JLabel("");
        lebelPoint.setSize(svgCanvasPoint.getSize());
        lebelPoint.setLocation(svgCanvasPoint.getLocation());

        Font fontPoint = new Font("Verdana", Font.BOLD, 40);
        lebelPoint.setFont(fontPoint);
        lebelPoint.setVerticalAlignment(JLabel.CENTER);
        lebelPoint.setHorizontalAlignment(JLabel.CENTER);

//        lebelPoint.setBorder(BorderFactory.createLineBorder(Color.RED));

        panelFull.add(lebelPoint);
        return lebelPoint;
    }

    private int resizeTable(JTable table){
        int cellSize;
        if (table.getColumnCount() > table.getRowCount() * 1050 / 580) {
            cellSize = 1050 / table.getColumnCount();
            table.setSize(1050, cellSize * table.getRowCount());
            table.setLocation(5, 325 - table.getHeight() / 2);
        } else {
            cellSize = 580 / table.getRowCount();
            table.setSize(cellSize * table.getColumnCount(), 580);
            table.setLocation(530 - table.getWidth() / 2, 35);
        }
        table.setRowHeight(cellSize);
        return cellSize;
    }

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        //костыль дабы не было иногда прозрачного экрана
        frame.setImageKost();
    }
}
