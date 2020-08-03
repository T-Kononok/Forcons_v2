package frame;

import data.ForconsList;
import data.MainData;
import elements.*;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import elements.skills.SkillButtonActionListener;
import org.apache.batik.swing.JSVGCanvas;

public class MainFrame extends JFrame {

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private final ImagePanel panelFull = new ImagePanel("image/begin_fon.jpg",false);
    private final TableNoGaps tableNoGaps;
    private int xFirstButton;
    private int xLastButton;
    private final MainData mainData;
    private JFileChooser fileChooser = null;
    private ArrayList<ImagePanel> leftImageArray = null;

    public ImagePanel getPanelFull() {
        return panelFull;
    }

    private MainFrame() throws IOException {

        panelFull.setSize(WIDTH,HEIGHT);
        panelFull.setLayout(null);

        ForconsList forconsList = new ForconsList();
        forconsList.getList().setOpaque(false);
        tableNoGaps = new TableNoGaps(20,35,1035,585);
        panelFull.add(tableNoGaps.getSkillsPanel());
        panelFull.add(tableNoGaps);
        tableNoGaps.setVisible(false);

        mainData = new MainData(this);
        addOpenButton(addCancelButton(),
                addForconsListScroll(forconsList),
                addSortPointButtons(forconsList),
                addSortClassButtons(forconsList),
                forconsList);

        addSvgCanvasClass(forconsList);
        addSkillButtons(forconsList);
        addNameLabel(forconsList);
        addSvgCanvasPoint(forconsList);

        getContentPane().add(panelFull);
    }

    private JButton addCancelButton() {
        JButton cancelButton = new JButton();
        toPlace(cancelButton,25,25,1250,5);
        cancelButton.setVisible(false);
        cancelButton.setBorderPainted(false);
//        cancelButton.setContentAreaFilled(false);
        cancelButton.addActionListener(ev -> {
            tableNoGaps.stopThread();
            dispose();
        });
        return cancelButton;
    }

    private JScrollPane addForconsListScroll(ForconsList list) {
        JScrollPane forconsListScroll = new JScrollPane(list.getList());
        toPlace(forconsListScroll,220,585,1060,35);
        forconsListScroll.setOpaque(false);
        forconsListScroll.getViewport().setOpaque(false);
        forconsListScroll.setVisible(false);
//        forconsListScroll.setBorder(BorderFactory.createLineBorder(Color.RED));
        forconsListScroll.setBorder(BorderFactory.createEmptyBorder());
        forconsListScroll.getVerticalScrollBar().setUI(new ImageScrollBarUI());
        forconsListScroll.getVerticalScrollBar().setPreferredSize(new Dimension(18,580));
        forconsListScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        return forconsListScroll;
    }

    private JButton addSortPointButtons(ForconsList forconsList) {
        JButton sortPointButton = new JButton();
        toPlace(sortPointButton,84,25,1077,10);
        sortPointButton.setVisible(false);
        sortPointButton.setBorderPainted(false);
//        sortPointButton.setContentAreaFilled(false);
        sortPointButton.addActionListener(ev -> forconsList.sortPoint());
        return sortPointButton;
    }

    private JButton addSortClassButtons(ForconsList forconsList) {
        JButton sortClassButton = new JButton();
        toPlace(sortClassButton,84,25,1161,10);
        sortClassButton.setVisible(false);
        sortClassButton.setBorderPainted(false);
//        sortClassButton.setContentAreaFilled(false);
        sortClassButton.addActionListener(ev -> forconsList.sortClass());
        return sortClassButton;
    }

    private void addOpenButton(JButton cancelButton, JScrollPane pane, JButton pointButton,
                               JButton classButton, ForconsList list) {
        Color zeroColor = new Color(0,0,0,0);
        OvalButton openButton = new OvalButton(OvalButton.SHAPE_OVAL,OvalButton.VERTICAL,zeroColor,zeroColor,zeroColor,zeroColor);
        toPlace(openButton,210,235,
                537,260);
        openButton.setPanel(panelFull);
        openButton.setMessageImage(addBeginMessageImage());
        openButton.addActionListener(ev -> {
            mainData.readTable(selectionFile("Открыть жунал"));
            tableNoGaps.resizeTable();
            //тоже костыль
            tableNoGaps.getRenderer().setSize(tableNoGaps.getCellSize());
            list.read(selectionFile("Открыть форсонов"));
            addLeftImage(tableNoGaps);
            cancelButton.setVisible(true);
            tableNoGaps.setVisible(true);
            tableNoGaps.startThread();
            pane.setVisible(true);
            pointButton.setVisible(true);
            classButton.setVisible(true);
            openButton.setVisible(false);
            openButton.setPanel(null);
            panelFull.setImageFile("image/fon2.jpg");
        });
    }

    public TableNoGaps getTableNoGaps() {
        return tableNoGaps;
    }

    public void addLeftImage(TableNoGaps tableNoGaps) {
        leftImageArray = new ArrayList<>();
        int cellSize = tableNoGaps.getCellSize();
        for (int i = 0; i < tableNoGaps.getRowCount(); i++) {
            leftImageArray.add(addOneLeftImage(tableNoGaps.getX()-cellSize, tableNoGaps.getY()+5+i*cellSize,cellSize));
        }
    }

    public ImagePanel addOneLeftImage(int x, int y, int size) {
        if (size > 20)
            size = 20;
        ImagePanel leftImage = new ImagePanel();
        toPlace(leftImage,size,size,x,y);
        panelFull.add(leftImage);
        return leftImage;
    }

    public void changeLeftImage() {
        for (ImagePanel imagePanel : leftImageArray)
            imagePanel.setVisible(false);
        for (int i = 0; i < mainData.getLight().size(); i++) {
            leftImageArray.get(mainData.getLight().get(i)).setImageFile("image/skills/leftLight.png");
            leftImageArray.get(mainData.getLight().get(i)).setVisible(true);
            leftImageArray.get(mainData.getLight().get(i)).repaint();
        }
    }

    private String selectionFile(String string) {
        if (fileChooser==null) {
            fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
        }
        fileChooser.setDialogTitle(string);
        if (fileChooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
//            System.out.println(fileChooser.getSelectedFile().getPath());
            return fileChooser.getSelectedFile().getPath();
        }
        return null;
    }

    private ImagePanel addBeginMessageImage() {
        ImagePanel messageImage = new ImagePanel("image/begin_fon_message.png",true,false);
        toPlace(messageImage,450,250,720,70);
        return messageImage;
    }

    private void addSvgCanvasClass(ForconsList forconsList) {
        JSVGCanvas svgCanvasClass = new JSVGCanvas();
        toPlace(svgCanvasClass,100,100,0,620);
        svgCanvasClass.setBackground(new Color(0,0,0,0));
        forconsList.getList().addListSelectionListener(evt -> {
            if (!evt.getValueIsAdjusting() && forconsList.getList().getSelectedIndex() != -1) {
                String val = forconsList.getList().getSelectedValue();
                String[] subStr = val.split(",");
                svgCanvasClass.setURI("file:/D:/Джава/Forcons_v2/image/svg/" + subStr[0] + ".svg");
            }
        });
    }

    private void addSkillButtons(ForconsList forconsList) {
        ArrayList<JSVGCanvas> skillSVGArray = new ArrayList<>();
        ArrayList<JButton> skillButtonsArray = new ArrayList<>();
        int size = 100;
        int strut = 0;
        xFirstButton = (WIDTH - size*6 - strut*5)/2;
        int x = xFirstButton;
        for (int i = 0; i < 6; i++) {
            skillButtonsArray.add(addOneSkillButton(x,size));
            skillSVGArray.add(addOneSkillSVG(x,size));
            x += size+strut;
        }
        xLastButton = x - strut;
        addForconsListListener(forconsList,skillSVGArray,skillButtonsArray);
    }

    private void addForconsListListener(ForconsList forconsList, ArrayList<JSVGCanvas> skillSVGArray,
                                        ArrayList<JButton> skillButtonsArray) {
        ArrayList<SkillButtonActionListener> actionListenerArray = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            actionListenerArray.add(new SkillButtonActionListener());
            skillButtonsArray.get(i).addActionListener(actionListenerArray.get(i));
        }

        forconsList.getList().addListSelectionListener(evt -> {
            if (!evt.getValueIsAdjusting() && forconsList.getList().getSelectedIndex() != -1) {
                String val = forconsList.getList().getSelectedValue();
                String[] subStr = val.split(",");
                for (int i = 0; i < 6; i++) {
                    skillSVGArray.get(i).setVisible(false);
                    skillButtonsArray.get(i).setVisible(false);
                }
                int countSkill = 0;
                if (subStr[2].equals("1"))
                    countSkill = 3;
                if (subStr[2].equals("2"))
                    countSkill = 5;
                if (subStr[2].equals("3"))
                    countSkill = 6;
                for (int i = 0; i < countSkill; i++) {
                    skillSVGArray.get(i).setVisible(true);
                    skillButtonsArray.get(i).setVisible(true);
                    skillSVGArray.get(i).setURI(
                            "file:/D:/Джава/Forcons_v2/image/svg/" + subStr[0] + "Skill" + (i+1) + ".svg");
                    actionListenerArray.get(i).setSkill(mainData.getSkill(subStr[0],(i+1)));
                }
            }
        });
    }

    private JButton addOneSkillButton(int x, int size) {
        JButton skillButton = new JButton();
        toPlace(skillButton,size,size,x,HEIGHT - size);
        skillButton.setBorderPainted(false);
        skillButton.setContentAreaFilled(false);
        return skillButton;
    }

    private JSVGCanvas addOneSkillSVG(int x, int size) {
        JSVGCanvas canvas = new JSVGCanvas();
        toPlace(canvas,size,size,x,HEIGHT - size);
        canvas.setBackground(new Color(0, 0, 0, 0));
        return canvas;
    }

    private void addNameLabel(ForconsList forconsList) {
        ReSizeLabel nameLabel = new ReSizeLabel();
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, new BufferedInputStream(
                    new FileInputStream("Fortuna Gothic FlorishC.ttf"))).
                    deriveFont(Font.PLAIN, 50);
            nameLabel.setFont(font);
            nameLabel.setForeground(Color.BLACK);
            nameLabel.setVerticalAlignment(JLabel.CENTER);
            nameLabel.setHorizontalAlignment(JLabel.CENTER);
            toPlace(nameLabel,xFirstButton-105,50,100,667-(50/2));

            //        nameLabel.setBorder(BorderFactory.createLineBorder(Color.RED));

            forconsList.getList().addListSelectionListener(evt -> {
                if (!evt.getValueIsAdjusting() && forconsList.getList().getSelectedIndex() != -1) {
                    String val = forconsList.getList().getSelectedValue();
                    String[] subStr = val.split(",");
                    nameLabel.setTextReSize(subStr[1],font);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addSvgCanvasPoint(ForconsList forconsList) {
        JSVGCanvas svgCanvasPoint = new JSVGCanvas();
        toPlace(svgCanvasPoint,WIDTH - xLastButton - 10,90,
                xLastButton + 5,667-(90/2));
        svgCanvasPoint.setBackground(new Color(0,0,0,0));
        ReSizeLabel label = addLabelPoint(svgCanvasPoint);
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, new BufferedInputStream(
                    new FileInputStream("Fortuna Gothic FlorishC.ttf"))).
                    deriveFont(Font.PLAIN, 50);
            label.setFont(font);
            label.setTextReSize("", font);

            forconsList.getList().addListSelectionListener(evt -> {
                if (!evt.getValueIsAdjusting() && forconsList.getList().getSelectedIndex() != -1) {
                    String val = forconsList.getList().getSelectedValue();
                    String[] subStr = val.split(",");

                    int pointInt = Integer.parseInt(subStr[3]);
                    if (pointInt < 8) {
                        svgCanvasPoint.setURI("file:/D:/Джава/Forcons_v2/image/svg/point" + subStr[3] + ".svg");
                        svgCanvasPoint.setVisible(true);
                        label.setTextReSize("",font);
                    } else {
                        svgCanvasPoint.setVisible(false);
                        label.setTextReSize(subStr[3] + " о. д.",font);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ReSizeLabel addLabelPoint(JSVGCanvas svgCanvasPoint) {
        ReSizeLabel lebelPoint = new ReSizeLabel();
        lebelPoint.setForeground(Color.BLACK);
        lebelPoint.setVerticalAlignment(JLabel.CENTER);
        lebelPoint.setHorizontalAlignment(JLabel.CENTER);
        lebelPoint.setSize(svgCanvasPoint.getSize());
        lebelPoint.setLocation(svgCanvasPoint.getLocation());
//      lebelPoint.setBorder(BorderFactory.createLineBorder(Color.RED));

        panelFull.add(lebelPoint);
        return lebelPoint;
    }

    private void toPlace(JComponent component,int width, int height, int x, int y) {
        component.setSize(width,height);
        component.setLocation(x,y);
        panelFull.add(component);
    }

    public static void main(String[] args) throws IOException {
        MainFrame frame = new MainFrame();

        frame.setUndecorated(true);
        frame.setExtendedState(MAXIMIZED_BOTH);
        frame.setVisible(true);
        //костыль дабы не было иногда прозрачного экрана
        frame.setImageKost();
    }

    //костыль дабы не было иногда прозрачного экрана
    public void setImageKost() {
        panelFull.setImageFile("image/begin_fon.jpg");
    }
}