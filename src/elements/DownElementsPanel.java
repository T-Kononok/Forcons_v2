package elements;

import data.ForconsList;
import data.MainData;
import elements.skills.SkillButtonActionListener;
import org.apache.batik.swing.JSVGCanvas;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DownElementsPanel{

    private final ImagePanel fon = new ImagePanel("image/parchmentBig.png",false);
    private final ForconsList forconsList;
    private final MainData mainData;
    private final Map<String, JSVGCanvas> classCanvases;
    private final ReSizeLabel nameLabel;
    private final Map<Integer, JButton> skillButtons;
    private final Map<Integer,Map<String, JSVGCanvas>> skillCanvases;
    private final ArrayList<SkillButtonActionListener> actionListeners;
    private final Map<Integer,JSVGCanvas> pointsCanvases;
    private final ReSizeLabel pointsLabel;
    private final ArrayList<String> classNames = new ArrayList<>();

    public DownElementsPanel(MainData mainData) {
        this.mainData = mainData;
        this.forconsList = mainData.getForconsList();

        fon.setSize(1280,720);
        fon.setLayout(null);
        fon.setOpaque(false);

        addClassNames();

        classCanvases = addClassCanvases();

        nameLabel = addLabel(225,100,110,620);

        int initialX = 340;
        int size = 100;
        skillButtons = addSkillButtons(initialX,size);
        skillCanvases = addSkillCanvases(initialX,size);
        actionListeners = addActionListeners(skillButtons);

        pointsCanvases = addPointsCanvases();
        pointsLabel = addLabel(
                pointsCanvases.get(0).getWidth(),
                pointsCanvases.get(0).getHeight(),
                pointsCanvases.get(0).getX(),
                pointsCanvases.get(0).getY());

        addForconsListListener();
    }

    public void addIn(JComponent component, int x, int y) {
        fon.setLocation(x, y);
        component.add(fon);
    }

    private void addClassNames() {
        classNames.add("ba");
        classNames.add("in");
        classNames.add("sa");
        classNames.add("sm");
    }

    private void addForconsListListener() {
        forconsList.getList().addListSelectionListener(evt -> {
            if (evt.getValueIsAdjusting() && forconsList.getSelectedIndex() != -1) {
                mainData.setIndexSelectForcon(forconsList.getSelectedIndex());
                if (!fon.isVisible())
                    fon.setVisible(true);
                changeElements();
            }
        });
    }

    public void changeElements() {
        String val = forconsList.getSelectedValue();
        String[] subStr = val.split(",");

        classCanvases.forEach((s,c) -> c.setVisible(s.equals(subStr[0])));

        nameLabel.setTextReSize(subStr[1]);

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

        int pointCount = Integer.parseInt(subStr[3]);
        if (pointCount < 8) {
            pointsCanvases.forEach((i,c) -> c.setVisible(i == pointCount));
            pointsLabel.setTextReSize("");
        } else {
            pointsCanvases.forEach((i,c) -> c.setVisible(false));
            pointsLabel.setTextReSize(subStr[3] + " о. д.");
        }
    }

    private JSVGCanvas addCanvas(int width, int height, int x, int y) {
        JSVGCanvas canvas = new JSVGCanvas();
        toPlace(canvas,width,height,x,y);
        canvas.setBackground(new Color(0,0,0,0));
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

    private Map<String, JSVGCanvas> addClassCanvases() {
        Map<String, JSVGCanvas> classCanvases = new HashMap<>();
        classNames.forEach(s -> classCanvases.put(
                s, getClassCanvas(s)));
        return classCanvases;
    }
    private JSVGCanvas getClassCanvas(String fileName) {
        JSVGCanvas canvas = addCanvas(100, 100, 5, 620);
        canvas.setURI("file:/D:/Джава/Forcons_v2/image/svg/" + fileName + ".svg");
        canvas.setVisible(false);
        return canvas;
    }

    private Map<Integer, JButton> addSkillButtons(int initialX, int size) {
        Map<Integer, JButton> skillButtons = new HashMap<>();
        for (int i = 0; i < 6; i++) {
            skillButtons.put(i, getSkillButton(size,initialX + size * i));
        }
        return skillButtons;
    }
    private JButton getSkillButton(int size, int x) {
        JButton skillButton = new JButton();
        toPlace(skillButton,size,size,x, 620);
        //совместить в один метод создание кнопки с сотировками
        skillButton.setBorderPainted(false);
        skillButton.setContentAreaFilled(false);
        skillButton.setVisible(false);
        return skillButton;
    }
    private ArrayList<SkillButtonActionListener> addActionListeners(Map<Integer,JButton> skillButtons) {
        ArrayList<SkillButtonActionListener> actionListeners = new ArrayList<>();
        for (int i = 0; i < 6; i++)
            actionListeners.add(new SkillButtonActionListener());
        skillButtons.forEach((i,b) -> b.addActionListener(actionListeners.get(i)));
        return actionListeners;
    }

    private Map<Integer,Map<String, JSVGCanvas>> addSkillCanvases(int initialX, int size) {
        Map<Integer,Map<String, JSVGCanvas>> skillCanvases = new HashMap<>();
        for (int i = 0; i < 6; i++) {
            int fI = i;
            Map<String, JSVGCanvas> oneSkillCanvases = new HashMap<>();
            classNames.forEach(s -> oneSkillCanvases.put(
                    s, getOneSkillCanvases(size,initialX+size*fI,620,s,fI+1)));
            skillCanvases.put(i, oneSkillCanvases);
        }
        return skillCanvases;
    }
    private JSVGCanvas getOneSkillCanvases(int size, int x, int y, String fileName, int number) {
        JSVGCanvas canvas = addCanvas(size, size, x, y);
        canvas.setURI("file:/D:/Джава/Forcons_v2/image/svg/" + fileName + "Skill" + number + ".svg");
        canvas.setVisible(false);
        return canvas;
    }

    private Map<Integer,JSVGCanvas> addPointsCanvases() {
        Map<Integer,JSVGCanvas> pointsCanvases = new HashMap<>();
        for (int i = 0; i < 8; i++)
            pointsCanvases.put(i, getPointCanvas(i));
        return  pointsCanvases;
    }
    private JSVGCanvas getPointCanvas(int number) {
        JSVGCanvas canvas = addCanvas(285,100,940,620);
        canvas.setURI("file:/D:/Джава/Forcons_v2/image/svg/point" + number + ".svg");
        canvas.setVisible(false);
        return canvas;
    }

    private void toPlace(JComponent component,int width, int height, int x, int y) {
        component.setSize(width,height);
        component.setLocation(x,y);
        fon.add(component);
    }

    public void setVisible(boolean flag) {
        fon.setVisible(flag);
    }
}
