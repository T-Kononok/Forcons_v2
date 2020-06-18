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
    }

    private void addClassSVG() {
        String[] item = {"ba","sa","in","sm"};
        for (int i = 0; i < 4; i++) {
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
        canvas.setVisible(false);
        return canvas;
    }

    public void setSize(int size) {
        for (int i = 0; i < 4; i++) {
            vectorClassSVG.get(i).setSize(size,size);
        }
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int col) {
        Mark mark = (Mark) value;

        switch (mark.getString()) {
            case (""):
                falseVisible();
                break;
            case ("int"):
                showCanvas(1);
                break;
            case ("(f)"):
                showCanvas(2);
                break;
            default:
                showCanvas(3);
                break;
        }

        return panel;
    }

    private void showCanvas(int number) {
        falseVisible();
        vectorClassSVG.get(number).setVisible(true);
    }

    private void falseVisible() {
        for (int i = 0; i < 4; i++) {
            vectorClassSVG.get(i).setVisible(false);
        }
    }
}
