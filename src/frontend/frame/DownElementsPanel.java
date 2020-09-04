package frontend.frame;

import auxiliary.Auxiliary;
import backend.ForconsList;
import backend.skillsData.SkillsMap;
import frontend.elements.ImagePanel;
import frontend.elements.ReSizeLabel;
import backend.skillsData.SkillButtonActionListener;
import org.apache.batik.swing.JSVGCanvas;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DownElementsPanel{

    private static final ImagePanel fon = new ImagePanel("image/parchmentBig.png",false);
    private static final Map<String, JSVGCanvas> classCanvases;
    private static final ReSizeLabel nameLabel;
    private static final Map<Integer, JButton> skillButtons;
    private static final Map<Integer,Map<String, JSVGCanvas>> skillCanvases;
    private static final ArrayList<SkillButtonActionListener> actionListeners;
    private static final Map<Integer,JSVGCanvas> pointsCanvases;
    private static final ReSizeLabel pointsLabel;
    private static final ArrayList<String> classNames = new ArrayList<>();

    static {

        fon.setSize(1280,720);
        fon.setLayout(null);
        fon.setOpaque(false);

        addClassNames();

        classCanvases = addClassCanvases();

        nameLabel = Auxiliary.addLabel(fon,225,100,110,620, 50);

        int initialX = 340;
        int size = 100;
        skillButtons = addSkillButtons(initialX,size);
        skillCanvases = addSkillCanvases(initialX,size);
        actionListeners = addActionListeners();

        pointsCanvases = addPointsCanvases();
        pointsLabel = Auxiliary.addLabel(fon,
                pointsCanvases.get(0).getWidth(),
                pointsCanvases.get(0).getHeight(),
                pointsCanvases.get(0).getX(),
                pointsCanvases.get(0).getY(),
                50);

        addForconsListListener();
    }

    public static void addIn(JComponent component, int x, int y) {
        fon.setLocation(x, y);
        component.add(fon);
    }

    private static void addClassNames() {
        classNames.add("ba");
        classNames.add("in");
        classNames.add("sa");
        classNames.add("sm");
    }

    private static void addForconsListListener() {
        ForconsList.getList().addListSelectionListener(evt -> {
            if (!evt.getValueIsAdjusting()) {
                    changeElements();
            }
        });
    }

    public static void onFon() {
        if (!fon.isVisible())
            fon.setVisible(true);
    }

    public static void changeElements() {
        String val = ForconsList.getSelectedValue();
        String[] subStr = val.split("_");

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
            actionListeners.get(i).setSkill(SkillsMap.getSkill(subStr[0],(i+1)));

        int pointCount = Integer.parseInt(subStr[3]);
        if (pointCount < 8) {
            pointsCanvases.forEach((i,c) -> c.setVisible(i == pointCount));
            pointsLabel.setTextReSize("");
        } else {
            pointsCanvases.forEach((i,c) -> c.setVisible(false));
            pointsLabel.setTextReSize(subStr[3] + " о. д.");
        }
    }

    private static Map<String, JSVGCanvas> addClassCanvases() {
        Map<String, JSVGCanvas> classCanvases = new HashMap<>();
        classNames.forEach(s -> classCanvases.put(
                s, getClassCanvas(s)));
        return classCanvases;
    }
    private static JSVGCanvas getClassCanvas(String fileName) {
        JSVGCanvas canvas = Auxiliary.addCanvas(fon,100, 100, 5, 620);
        canvas.setURI("file:/D:/Джава/Forcons_v2/image/svg/" + fileName + ".svg");
        canvas.setVisible(false);
        return canvas;
    }

    private static Map<Integer, JButton> addSkillButtons(int initialX, int size) {
        Map<Integer, JButton> skillButtons = new HashMap<>();
        for (int i = 0; i < 6; i++) {
            skillButtons.put(i, Auxiliary.addButton(fon,size,size,initialX + size * i, 620));
        }
        return skillButtons;
    }

    private static ArrayList<SkillButtonActionListener> addActionListeners() {
        ArrayList<SkillButtonActionListener> actionListeners = new ArrayList<>();
        for (int i = 0; i < 6; i++)
            actionListeners.add(new SkillButtonActionListener());
        DownElementsPanel.skillButtons.forEach((i, b) -> b.addActionListener(actionListeners.get(i)));
        return actionListeners;
    }

    private static Map<Integer,Map<String, JSVGCanvas>> addSkillCanvases(int initialX, int size) {
        Map<Integer,Map<String, JSVGCanvas>> skillCanvases = new HashMap<>();
        for (int i = 0; i < 6; i++) {
            int fI = i;
            Map<String, JSVGCanvas> oneSkillCanvases = new HashMap<>();
            classNames.forEach(s -> oneSkillCanvases.put(
                    s, getOneSkillCanvases(size,initialX+size*fI, s,fI+1)));
            skillCanvases.put(i, oneSkillCanvases);
        }
        return skillCanvases;
    }
    private static JSVGCanvas getOneSkillCanvases(int size, int x, String fileName, int number) {
        JSVGCanvas canvas = Auxiliary.addCanvas(fon,size, size, x, 620);
        canvas.setURI("file:/D:/Джава/Forcons_v2/image/svg/" + fileName + "Skill" + number + ".svg");
        canvas.setVisible(false);
        return canvas;
    }

    private static Map<Integer,JSVGCanvas> addPointsCanvases() {
        Map<Integer,JSVGCanvas> pointsCanvases = new HashMap<>();
        for (int i = 0; i < 8; i++)
            pointsCanvases.put(i, getPointCanvas(i));
        return  pointsCanvases;
    }
    private static JSVGCanvas getPointCanvas(int number) {
        JSVGCanvas canvas = Auxiliary.addCanvas(fon,285,100,940,620);
        canvas.setURI("file:/D:/Джава/Forcons_v2/image/svg/point" + number + ".svg");
        canvas.setVisible(false);
        return canvas;
    }

    public static void setVisible(boolean flag) {
        fon.setVisible(flag);
    }
}
