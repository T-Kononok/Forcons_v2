package elements;

import org.apache.batik.dom.svg.SVGOMDocument;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.swing.gvt.GVTTreeRendererEvent;
import org.apache.batik.swing.gvt.GVTTreeRendererListener;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class ForconsRenderer implements ListCellRenderer<String> {

    private final JPanel fonBack = new JPanel();
    private final JPanel fonForeg = new JPanel();
    private final JLabel numberLabel = new JLabel("1");
    private final JLabel nameLabel = new JLabel("Имя");
    private final JLabel levelLabel = new JLabel("3");
    private final JLabel pointLabel = new JLabel("5");
    private final JSVGCanvas classSVG = new JSVGCanvas();
    private final JSVGCanvas pointSVG = new JSVGCanvas();

    private final Font basicFont = new Font("Verdana", Font.BOLD, 15);

    public ForconsRenderer() {
        pointSVG.setURI("file:image/svg/point3.svg");
        classSVG.setURI("file:image/svg/ba.svg");

        fonBack.setBackground(Color.ORANGE);
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
        fonForeg.add(nameLabel);
    }

    private void addLevelLabel() {
        setAlignmentAndFont(levelLabel,basicFont);
        levelLabel.setSize(30, 30);
        levelLabel.setLocation(160, 0);
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
        classSVG.setSize(60, 60);
        classSVG.setLocation(20,0);
        classSVG.setBackground(new Color(0, 0, 0, 0));
        fonForeg.add(classSVG);
        fonBack.add(fonForeg);
    }

    private void addPointSVG() {
        pointSVG.setBackground(new Color(0, 0, 0, 0));
        pointSVG.setSize(110, 30);
        pointSVG.setLocation(80, 30);
        fonForeg.add(pointSVG);
    }


    @Override
    public Component getListCellRendererComponent(JList<? extends String> jList, String s, int i, boolean b, boolean b1) {

        String[] subStr = s.split(",");

//        numberLabel.setText(subStr[0]);
        numberLabel.setText("4");

        return fonBack;
    }
}