package frame;

import data.ForconsList;
import data.MainData;
import data.Mark;
import elements.*;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import elements.skills.SkillButtonActionListener;
import org.apache.batik.swing.JSVGCanvas;

public class MainFrame extends JFrame {

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private final ImagePanel panelFull = new ImagePanel("image/begin_fon.jpg",false);
    private final TableNoGaps tableNoGaps = new TableNoGaps(0,35,1035,585);
    private final ForconsList forconsList = new ForconsList();
    private final MainData mainData = new MainData(this,forconsList);

    private final ArrayList<String> classNames = new ArrayList<>();

    private JFileChooser fileChooser = null;
    private ArrayList<ImagePanel> leftImageArray = null;

    private MainFrame() throws IOException {

        panelFull.setSize(WIDTH,HEIGHT);
        panelFull.setLayout(null);

        addTableNoGaps();

        addOpenButton(addOpenPanel(),
                addCancelButton(),
                addForconsListScroll(),
                addSortPointButtons(),
                addSortClassButtons());

        putMap();
        addClassCanvases();
        addNameLabel();
        addSkillButtons();
        addPoints();

        getContentPane().add(panelFull);
    }

    private void putMap() {
        classNames.add("ba");
        classNames.add("in");
        classNames.add("sa");
        classNames.add("sm");
    }

    private ImagePanel addOpenPanel() {
        ImagePanel openPanel = new ImagePanel("image/begin_fon_highlighted.png",false);
        toPlace(openPanel,WIDTH,HEIGHT,0,0);
        openPanel.setVisible(false);
        openPanel.setOpaque(false);
        return openPanel;
    }

    private void addTableNoGaps() {
        tableNoGaps.addIn(panelFull);
        tableNoGaps.setVisible(false);
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

    private JScrollPane addForconsListScroll() {
        JScrollPane forconsListScroll = new JScrollPane(forconsList.getList());
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

    private JButton addSortPointButtons() {
        JButton sortPointButton = new JButton();
        toPlace(sortPointButton,84,25,1077,10);
        sortPointButton.setVisible(false);
        sortPointButton.setBorderPainted(false);
//        sortPointButton.setContentAreaFilled(false);
        sortPointButton.addActionListener(ev -> forconsList.sortPoint());
        return sortPointButton;
    }

    private JButton addSortClassButtons() {
        JButton sortClassButton = new JButton();
        toPlace(sortClassButton,84,25,1161,10);
        sortClassButton.setVisible(false);
        sortClassButton.setBorderPainted(false);
//        sortClassButton.setContentAreaFilled(false);
        sortClassButton.addActionListener(ev -> forconsList.sortClass());
        return sortClassButton;
    }

    private void addOpenButton(ImagePanel openPanel, JButton cancelButton, JScrollPane scrollPane, JButton pointButton,
                               JButton classButton) {
        OvalButton openButton = new OvalButton(OvalButton.SHAPE_OVAL,OvalButton.VERTICAL);
        toPlace(openButton,210,235, 537,260);
        openButton.setPanel(openPanel);
        openButton.setMessageImage(addBeginMessageImage());
        openButton.addActionListener(ev -> {
            mainData.readTable(selectionFile("Открыть жунал"));
//            mainData.readTable("D:\\Джава\\Forcons_v2\\10_FM-3.xls");
            forconsList.read(selectionFile("Открыть форсонов"));
            openButton.setVisible(false);
            openPanel.setVisible(false);
            tableNoGaps.setVisible(true);
            cancelButton.setVisible(true);
            scrollPane.setVisible(true);
            pointButton.setVisible(true);
            classButton.setVisible(true);

            tableNoGaps.startThread();
            panelFull.setImageFile("image/fon2.jpg");
        });
    }

    private ImagePanel addBeginMessageImage() {
        ImagePanel messageImage = new ImagePanel("image/begin_fon_message.png",true,false);
        toPlace(messageImage,450,250,720,70);
        return messageImage;
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

    ////////////////////////////////////////////////

    private JSVGCanvas addCanvas(int width, int height, int x, int y) {
        JSVGCanvas canvas = new JSVGCanvas();
        toPlace(canvas,width,height,x,y);
        canvas.setBackground(new Color(0,0,0,0));
        return canvas;
    }

    private void addClassCanvases() {
        Map<String, JSVGCanvas> classCanvases = new HashMap<>();
        classNames.forEach(s -> classCanvases.put(
                s, getClassCanvas(s)));
        forconsList.getList().addListSelectionListener(evt -> {
            if (!evt.getValueIsAdjusting() && forconsList.getSelectedIndex() != -1) {
                String val = forconsList.getSelectedValue();
                String[] subStr = val.split(",");
                classCanvases.forEach((s,c) -> c.setVisible(s.equals(subStr[0])));
            }
        });
    }
    private JSVGCanvas getClassCanvas(String fileName) {
        JSVGCanvas canvas = addCanvas(100, 100, 0, 620);
        canvas.setURI("file:/D:/Джава/Forcons_v2/image/svg/" + fileName + ".svg");
        canvas.setVisible(false);
        return canvas;
    }

    private ReSizeLabel addLabel(int width, int height, int x, int y) {
        Font font = null;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new BufferedInputStream(
                    new FileInputStream("Fortuna Gothic FlorishC.ttf"))).
                    deriveFont(Font.PLAIN, 50);
        } catch (Exception ignored) { }
        ReSizeLabel label = new ReSizeLabel(font);
        label.setForeground(Color.BLACK);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);
        toPlace(label,width,height,x,y);

        return label;
    }

    private void addNameLabel() {
        ReSizeLabel nameLabel = addLabel(220,100,110,620);
        forconsList.getList().addListSelectionListener(evt -> {
            if (!evt.getValueIsAdjusting() && forconsList.getSelectedIndex() != -1) {
                String val = forconsList.getSelectedValue();
                String[] subStr = val.split(",");
                nameLabel.setTextReSize(subStr[1]);
            }
        });
    }

    private void addSkillButtons() {
        Map<Integer, JButton> skillButtons = new HashMap<>();
        Map<Integer,Map<String, JSVGCanvas>> skillCanvases = new HashMap<>();
        int initialX = 340;
        int size = 100;
        for (int i = 0; i < 6; i++) {
            int fI = i;
            skillButtons.put(i, getSkillButton(size,initialX + size * fI,HEIGHT - size));
            Map<String, JSVGCanvas> oneSkillCanvases = new HashMap<>();
            classNames.forEach(s -> oneSkillCanvases.put(
                    s, getOneSkillCanvases(size,initialX+size*fI,HEIGHT-size,s,fI+1)));
            skillCanvases.put(i, oneSkillCanvases);
        }
        addForconsListListener(skillButtons,skillCanvases);
    }
    private JSVGCanvas getOneSkillCanvases(int size, int x, int y, String fileName, int number) {
        JSVGCanvas canvas = addCanvas(size, size, x, y);
        canvas.setURI("file:/D:/Джава/Forcons_v2/image/svg/" + fileName + "Skill" + number + ".svg");
        canvas.setVisible(false);
        return canvas;
    }
    private JButton getSkillButton(int size, int x, int y) {
        JButton skillButton = new JButton();
        toPlace(skillButton,size,size,x,y);
        //совместить в один метод создание кнопки с сотировками
        skillButton.setBorderPainted(false);
        skillButton.setContentAreaFilled(false);
        skillButton.setVisible(false);
        return skillButton;
    }
    private void addForconsListListener(Map<Integer,JButton> skillButtons,
                                        Map<Integer,Map<String, JSVGCanvas>> skillCanvases) {
        ArrayList<SkillButtonActionListener> actionListeners = new ArrayList<>();
        for (int i = 0; i < 6; i++)
            actionListeners.add(new SkillButtonActionListener());
        skillButtons.forEach((i,b) -> b.addActionListener(actionListeners.get(i)));

        forconsList.getList().addListSelectionListener(evt -> {
            if (evt.getValueIsAdjusting() && forconsList.getSelectedIndex() != -1) {
                String val = forconsList.getSelectedValue();
                String[] subStr = val.split(",");

                int countSkillOld = 3;
                switch (subStr[2]) {
                    case "2" :
                        countSkillOld = 5;
                        break;
                    case "3" :
                        countSkillOld = 6;
                }
                int countSkill = countSkillOld;

                skillButtons.forEach((i,b) -> b.setVisible(i + 1 <= countSkill));
                skillCanvases.forEach((i,m) -> m.forEach((s,c) ->
                        c.setVisible(s.equals(subStr[0]) && i + 1 <= countSkill )));

                for (int i = 0; i < countSkill; i++)
                    actionListeners.get(i).setSkill(mainData.getSkill(subStr[0],(i+1)));
            }
        });
    }

    private void addPoints() {
        Map<Integer,JSVGCanvas> pointsCanvases = new HashMap<>();
        for (int i = 0; i < 8; i++)
            pointsCanvases.put(i, getPointCanvas(i));
        ReSizeLabel pointsLabel = addLabel(
                pointsCanvases.get(0).getWidth(),
                pointsCanvases.get(0).getHeight(),
                pointsCanvases.get(0).getX(),
                pointsCanvases.get(0).getY());
        pointsLabel.setTextReSize("");

            forconsList.getList().addListSelectionListener(evt -> {
                if (!evt.getValueIsAdjusting() && forconsList.getList().getSelectedIndex() != -1) {
                    String val = forconsList.getList().getSelectedValue();
                    String[] subStr = val.split(",");

                    int pointCount = Integer.parseInt(subStr[3]);
                    if (pointCount < 8) {
                        pointsCanvases.forEach((i,c) -> c.setVisible(i <= pointCount));
                        pointsLabel.setTextReSize("");
                    } else {
                        pointsCanvases.forEach((i,c) -> c.setVisible(false));
                        pointsLabel.setTextReSize(subStr[3] + " о. д.");
                    }
                }
            });
    }

    private JSVGCanvas getPointCanvas(int number) {
        JSVGCanvas canvas = addCanvas(330,100,950,620);
        canvas.setURI("file:/D:/Джава/Forcons_v2/image/svg/point" + number + ".svg");
        canvas.setVisible(false);
        return canvas;
    }

    private void toPlace(JComponent component,int width, int height, int x, int y) {
        component.setSize(width,height);
        component.setLocation(x,y);
        panelFull.add(component);
    }

    public TableNoGaps getTableNoGaps() {
        return tableNoGaps;
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