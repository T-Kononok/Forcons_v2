package elements;

import auxiliary.Auxiliary;
import data.MainData;
import frame.MainFrame;
import org.apache.batik.swing.JSVGCanvas;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class ForconsRenderer implements ListCellRenderer<String> {

    private final JPanel fon2 = new JPanel();
    private final ImagePanel fon = new ImagePanel("image/parchment.png");
    private final Vector<JSVGCanvas> vectorClassSVG = new Vector<>();
    private final Vector<JSVGCanvas> vectorPointSVG = new Vector<>();
    private final Map<String, Integer> map = new HashMap<>();

    private final ReSizeLabel numberLabel;
    private final ReSizeLabel nameLabel;
    private final ReSizeLabel levelLabel;
    private final ReSizeLabel pointLabel;

    public ForconsRenderer() {

        inMap();
        fon.setLayout(null);
        fon.setBackground(new Color(0, 0, 0, 0));
        Auxiliary.toPlace(fon2,fon,200,80,0,0);

        String fontName = "American TextC Regular.ttf";
        int fontSize = 20;
        numberLabel = Auxiliary.addLabel(fon,18,60,2,10,fontName,15);
        addClassSVG();
        nameLabel = Auxiliary.addLabel(fon,80,30,80,10,fontName,fontSize);
        levelLabel = Auxiliary.addLabel(fon,30,30,160,10,fontName,fontSize);
        pointLabel = Auxiliary.addLabel(fon,110,30,80,40,fontName,fontSize);
        addPointSVG();

        fon2.setPreferredSize(new Dimension(200, 80));
        fon2.setLayout(null);
        fon2.setBackground(new Color(0, 0, 0, 0));
        fon2.add(fon);
    }

    private void inMap() {
        map.put("ba", 0);
        map.put("in", 1);
        map.put("sa", 2);
        map.put("sm", 3);
    }

    private void addClassSVG() {
        String[] item = {"ba","in","sa","sm"};
        for (int i = 0; i < 4; i++) {
            vectorClassSVG.add(Auxiliary.addCanvas(fon,60,60,20,10));
            vectorClassSVG.get(i).setURI("file:image/svg/"+item[i]+".svg");
        }
    }

    private void addPointSVG() {
        for (int i = 0; i < 8; i++) {
            vectorPointSVG.add(Auxiliary.addCanvas(fon,110,30,80,40));
            vectorPointSVG.get(i).setURI("file:image/svg/point"+i+".svg");
        }
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends String> jList, String s, int index, boolean isSelected, boolean cellHasFocus) {

        String[] subStr = s.split(",");
        numberLabel.setText((index+1)+"");
        rendClass(subStr[0]);
        nameLabel.setTextReSize(subStr[1]);
        rendLevel(subStr[2]);
        rendPoint(subStr[3]);

        if (MainFrame.getForconsList().isTwoSelected(index))
            fon2.setBackground(new Color(0, 0, 0, 255));
        else
            fon2.setBackground(new Color(0, 0, 0, 0));

        return fon2;
    }

    private void rendClass(String string) {
        if (map.containsKey(string)) {
            for (int i = 0; i < 4; i++) {
                vectorClassSVG.get(i).setVisible(false);
            }
            vectorClassSVG.get(map.get(string)).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null,
                    "Не правильно написано название класса форсона!",
                    "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void rendLevel(String string) {
        switch (string) {
            case ("1"):
                levelLabel.setText("I");
                break;
            case ("2"):
                levelLabel.setText("II");
                break;
            case ("3"):
                levelLabel.setText("III");
                break;
            default:
                JOptionPane.showMessageDialog(null, "Не правильно написан уровень!",
                        "Ошибка", JOptionPane.ERROR_MESSAGE);
                break;
        }
    }

    private void rendPoint(String string) {
        int pointInt = Integer.parseInt(string);
        if (pointInt >= 0) {
            for (int i = 0; i < 8; i++)
                vectorPointSVG.get(i).setVisible(false);
            if (pointInt < 8) {
                vectorPointSVG.get(pointInt).setVisible(true);
                pointLabel.setText("");
            } else
                pointLabel.setText(string + " о. д.");
        } else {
            JOptionPane.showMessageDialog(null,
                    "Не правильно написано кол-во действий!",
                    "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

}