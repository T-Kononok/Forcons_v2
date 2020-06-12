package elements;

import org.apache.batik.dom.svg.SVGOMDocument;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.swing.gvt.GVTTreeRendererEvent;
import org.apache.batik.swing.gvt.GVTTreeRendererListener;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class ForconsRenderer implements ListCellRenderer<String> {

    private final JPanel fonBack = new JPanel();
    private final JPanel fonForeg = new JPanel();
    private final JLabel numberLabel = new JLabel();
    private final JLabel nameLabel = new JLabel();
    private final JLabel levelLabel = new JLabel();
    private final JLabel pointLabel = new JLabel();
    private final JSVGCanvas pointSVG = new JSVGCanvas();
    private final Vector<JSVGCanvas> vectorClassSVG = new Vector<>();
    private final Vector<JSVGCanvas> vectorPointSVG = new Vector<>();
    private final Map<String, Integer> map = new HashMap<>();

    private final Font basicFont = new Font("Verdana", Font.BOLD, 15);

    public ForconsRenderer() {

        inMap();
        fonForeg.setBackground(Color.GRAY);
        fonForeg.setPreferredSize(new Dimension(190, 60));
        fonForeg.setLayout(null);
        addNumberLabel();
        addClassSVG();
        addNameLabel();
        addLevelLabel();
        addPointLabel();
        addPointSVG();
        fonBack.add(fonForeg);
    }

    private void inMap() {
        map.put("ba", 0);
        map.put("in", 1);
        map.put("sa", 2);
        map.put("sm", 3);
    }

    private void addNumberLabel() {
        Font fontN = new Font("Verdana", Font.BOLD, 10);
        setAlignmentAndFont(numberLabel,fontN);
        numberLabel.setSize(20, 60);
        numberLabel.setLocation(0, 0);
        fonForeg.add(numberLabel);
    }

    private void addNameLabel() {
        setAlignmentAndFont(nameLabel,basicFont);
        nameLabel.setSize(80, 30);
        nameLabel.setLocation(80, 0);
//        nameLabel.setBorder(BorderFactory.createLineBorder(Color.RED));
        fonForeg.add(nameLabel);
    }

    private void addLevelLabel() {
        setAlignmentAndFont(levelLabel,basicFont);
        levelLabel.setSize(30, 30);
        levelLabel.setLocation(160, 0);
//        levelLabel.setBorder(BorderFactory.createLineBorder(Color.RED));
        fonForeg.add(levelLabel);
    }

    private void addPointLabel() {
        setAlignmentAndFont(pointLabel,basicFont);
        pointLabel.setSize(110, 30);
        pointLabel.setLocation(80, 30);
        fonForeg.add(pointLabel);
    }

    private void setAlignmentAndFont(JLabel label, Font font) {
        label.setFont(font);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);
    }

    private void addClassSVG() {
        String[] item = {"ba","in","sa","sm"};
        for (int i = 0; i < 4; i++) {
            vectorClassSVG.add(addOneClassSVG());
            vectorClassSVG.get(i).setURI("file:image/svg/"+item[i]+".svg");
        }
    }

    private JSVGCanvas addOneClassSVG() {
        JSVGCanvas canvas = new JSVGCanvas();
        canvas.setBackground(new Color(0, 0, 0, 0));
        canvas.setSize(60, 60);
        canvas.setLocation(20,0);
        fonForeg.add(canvas);
        return canvas;
    }

    private void addPointSVG() {
        for (int i = 0; i < 8; i++) {
            vectorPointSVG.add(addOnePointSVG());
            vectorPointSVG.get(i).setURI("file:image/svg/point"+i+".svg");
        }
    }

    private JSVGCanvas addOnePointSVG() {
        JSVGCanvas canvas = new JSVGCanvas();
        canvas.setBackground(new Color(0, 0, 0, 0));
        canvas.setSize(110, 30);
        canvas.setLocation(80, 30);
        fonForeg.add(canvas);
        return canvas;
    }


    @Override
    public Component getListCellRendererComponent(JList<? extends String> jList, String s, int index, boolean isSelected, boolean cellHasFocus) {

        String[] subStr = s.split(",");
        numberLabel.setText(index+"");
        rendClass(subStr[1]);
        rendName(subStr[2]);
        rendLevel(subStr[3]);
        rendPoint(subStr[4]);
        Color fonFors;
        if (isSelected)
            fonFors = new Color(218, 165, 32);
        else
            fonFors = new Color(192, 192, 192);
        fonBack.setBackground(fonFors);

        return fonBack;
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

    private void rendName(String string) {
        nameLabel.setText(string);

        double textWidth = nameLabel.getFontMetrics(basicFont).stringWidth(nameLabel.getText());

        if (textWidth > nameLabel.getWidth()) {
            int newFontSize = (int) (basicFont.getSize() * nameLabel.getWidth() / (textWidth+10));
            nameLabel.setFont(new Font(basicFont.getName(), Font.BOLD, newFontSize));
        } else
            nameLabel.setFont(basicFont);
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