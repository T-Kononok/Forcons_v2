package elements;

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
    private final JLabel numberLabel = new JLabel();
    private final ReSizeLabel nameLabel;
    private final JLabel levelLabel = new JLabel();
    private final JLabel pointLabel = new JLabel();
    private final Vector<JSVGCanvas> vectorClassSVG = new Vector<>();
    private final Vector<JSVGCanvas> vectorPointSVG = new Vector<>();
    private final Map<String, Integer> map = new HashMap<>();

    private Font basicFont;

    private final MainData mainData;

    private int selected = 0;

    public ForconsRenderer(MainData mainData) {
        this.mainData = mainData;
        try {
            basicFont = Font.createFont(Font.TRUETYPE_FONT, new BufferedInputStream(
                    new FileInputStream("American TextC Regular.ttf"))).
                    deriveFont(Font.PLAIN, 20);
        } catch (Exception e) {
            e.printStackTrace();
        }
        nameLabel = new ReSizeLabel(basicFont);

        inMap();
        fon.setSize(new Dimension(200, 80));
        fon.setLayout(null);
        fon.setBackground(new Color(0, 0, 0, 0));
        addNumberLabel();
        addClassSVG();
        addNameLabel();
        addLevelLabel();
        addPointLabel();
        addPointSVG();

        fon2.setPreferredSize(new Dimension(200, 80));
        fon2.setLayout(null);
        fon2.setBackground(new Color(0, 0, 0, 0));
        fon.setLocation(0,0);
        fon2.add(fon);
    }

    private void inMap() {
        map.put("ba", 0);
        map.put("in", 1);
        map.put("sa", 2);
        map.put("sm", 3);
    }

    private void addNumberLabel() {
        Font fontN = null;
        try {
            fontN = Font.createFont(Font.TRUETYPE_FONT, new BufferedInputStream(
                    new FileInputStream("American TextC Regular.ttf"))).
                    deriveFont(Font.PLAIN, 15);
        } catch (Exception e) {
            e.printStackTrace();
        }
        numberLabel.setForeground(Color.BLACK);
        setAlignmentAndFont(numberLabel,fontN);
        numberLabel.setSize(18, 60);
        numberLabel.setLocation(2, 10);
        fon.add(numberLabel);
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
        canvas.setLocation(20,10);
        fon.add(canvas);
        return canvas;
    }

    private void addNameLabel() {
        nameLabel.setForeground(Color.BLACK);
        setAlignmentAndFont(nameLabel,basicFont);
        nameLabel.setSize(80, 30);
        nameLabel.setLocation(80, 10);
//        nameLabel.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        fon.add(nameLabel);
    }

    private void addLevelLabel() {
        levelLabel.setForeground(Color.BLACK);
        setAlignmentAndFont(levelLabel,basicFont);
        levelLabel.setSize(30, 30);
        levelLabel.setLocation(160, 10);
//        levelLabel.setBorder(BorderFactory.createLineBorder(Color.RED));
        fon.add(levelLabel);
    }

    private void addPointLabel() {
        pointLabel.setForeground(Color.BLACK);
        setAlignmentAndFont(pointLabel,basicFont);
        pointLabel.setSize(110, 30);
        pointLabel.setLocation(80, 40);
        fon.add(pointLabel);
    }

    private void setAlignmentAndFont(JLabel label, Font font) {
        label.setFont(font);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);
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
        canvas.setLocation(80, 40);
        fon.add(canvas);
        return canvas;
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends String> jList, String s, int index, boolean isSelected, boolean cellHasFocus) {

        String[] subStr = s.split(",");
        numberLabel.setText((index+1)+"");
        rendClass(subStr[0]);
        nameLabel.setTextReSize(subStr[1]);
        rendLevel(subStr[2]);
        rendPoint(subStr[3]);

        if (mainData.getMainFrame().getForconsList().isTwoSelected(index))
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