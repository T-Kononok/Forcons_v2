package elements;

import data.Mark;
import org.apache.batik.swing.JSVGCanvas;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.*;

public class JournalTableCellRenderer implements TableCellRenderer {

    private final JPanel panel = new JPanel();
    private ArrayList<JSVGCanvas> arraySVG = new ArrayList<>();
    private Map<String, JSVGCanvas> mapSVG = new HashMap<>();
    private int countRow = 0;
    private int countColumn = 0;
    private final JLabel label = new JLabel();

    public JournalTableCellRenderer(){
        panel.setLayout(null);
        panel.setBackground(new Color(0,0,0,0));
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, new BufferedInputStream(
                    new FileInputStream("American TextC Regular.ttf"))).
                    deriveFont(Font.PLAIN, 15);
            label.setFont(font);
        } catch (Exception e) {
            e.printStackTrace();
        }
        label.setForeground(Color.BLACK);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setLocation(0,0);
        panel.add(label);
        addSVG();
    }

    private void addSVG() {
        mapSVG.put("cell", addOneSVG());
        mapSVG.put("cellBite0", addOneSVG());
        mapSVG.put("cellBite0Bad", addOneSVG());
        mapSVG.put("cellBite0BodyBag", addOneSVG());
        mapSVG.put("cellBite0Bomb", addOneSVG());
        mapSVG.put("cellBite1", addOneSVG());
        mapSVG.put("cellBite1Bad", addOneSVG());
        mapSVG.put("cellBite1BodyBag", addOneSVG());
        mapSVG.put("cellBite1Bomb", addOneSVG());
        mapSVG.put("cellBite2", addOneSVG());
        mapSVG.put("cellBite2Bad", addOneSVG());
        mapSVG.put("cellBite2BodyBag", addOneSVG());
        mapSVG.put("cellBite2Bomb", addOneSVG());
        mapSVG.put("cellBite3", addOneSVG());
        mapSVG.put("cellBite3Bad", addOneSVG());
        mapSVG.put("cellBite3BodyBag", addOneSVG());
        mapSVG.put("cellBite3Bomb", addOneSVG());
        mapSVG.put("cellBite4", addOneSVG());
        mapSVG.put("cellBite4Bad", addOneSVG());
        mapSVG.put("cellBite4BodyBag", addOneSVG());
        mapSVG.put("cellBite4Bomb", addOneSVG());
        mapSVG.put("cellCr", addOneSVG());
        mapSVG.put("cellKr", addOneSVG());
        mapSVG.put("cellLr", addOneSVG());
        mapSVG.put("cellPoison", addOneSVG());

        for(Map.Entry<String, JSVGCanvas> entry: mapSVG.entrySet()) {
            mapSVG.get(entry.getKey()).setURI("file:image/svg/"+entry.getKey()+".svg");
        }
    }

    private JSVGCanvas addOneSVG() {
        JSVGCanvas canvas = new JSVGCanvas();
        canvas.setBackground(new Color(0, 0, 0, 0));
        canvas.setSize(5, 5);
        canvas.setLocation(0,0);
        panel.add(canvas);
        canvas.setVisible(false);
        return canvas;
    }

    public void setSize(int size) {
        for(Map.Entry<String, JSVGCanvas> entry: mapSVG.entrySet()) {
            mapSVG.get(entry.getKey()).setSize(size,size);
        }
        label.setSize(size,size);
    }

//    public void setMatrixSize(int row, int column) {
//        countRow = row;
//        countColumn = column;
//        addMatrixSVG();
//        setSize(15);
//    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int col) {
        Mark mark = (Mark) value;
        falseVisible();
        showCanvas(mark.toStyle());
        label.setText(mark.toString());
        return panel;
    }

    private void showCanvas(String string) {
        falseVisible();
        mapSVG.get(string).setVisible(true);
    }

    private void falseVisible() {
        for(Map.Entry<String, JSVGCanvas> entry: mapSVG.entrySet()) {
            mapSVG.get(entry.getKey()).setVisible(false);
        }
    }
}
