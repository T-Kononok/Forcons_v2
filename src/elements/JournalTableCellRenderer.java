package elements;

import data.Mark;
import org.apache.batik.swing.JSVGCanvas;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.Vector;

public class JournalTableCellRenderer implements TableCellRenderer {

    private final JPanel panel = new JPanel();
    private final JSVGCanvas svgCanvas = new JSVGCanvas();
    private final Vector<JSVGCanvas> vectorClassSVG = new Vector<>();

    public JournalTableCellRenderer(){
        panel.setLayout(null);
        panel.setBackground(new Color(0,0,0,0));
        addClassSVG();
//        svgCanvas.setSize(size,size);
//        svgCanvas.setLocation(0,0);
//        svgCanvas.setBackground(new Color(0,0,0,0));
//        panel.add(svgCanvas);
    }

    private void addClassSVG() {
        String[] item = {"ba","in"};
        for (int i = 0; i < 2; i++) {
            vectorClassSVG.add(addOneClassSVG());
            vectorClassSVG.get(i).setURI("file:image/svg/"+item[i]+".svg");
        }
    }

    private JSVGCanvas addOneClassSVG() {
        JSVGCanvas canvas = new JSVGCanvas();
        canvas.setBackground(new Color(0, 0, 0, 0));
        canvas.setSize(5, 5);
        canvas.setLocation(0,0);
        panel.add(canvas);
        return canvas;
    }

    public void setSize(int size) {
        for (int i = 0; i < 2; i++) {
            vectorClassSVG.get(i).setSize(size,size);
        }
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int col) {
        Mark mark = (Mark) value;
        for (int i = 0; i < 2; i++) {
            vectorClassSVG.get(i).setVisible(false);
        }
        vectorClassSVG.get(0).setVisible(true);
//        svgCanvas.setURI("file:/D:/Джава/Forcons_v2/image/svg/ba.svg");
        return panel;
    }
}
