package elements;

import data.Mark;
import org.apache.batik.swing.JSVGCanvas;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class JournalTableCellRenderer implements TableCellRenderer {

    private final JPanel panel = new JPanel();
    private final JSVGCanvas svgCanvas = new JSVGCanvas();

    public JournalTableCellRenderer(int size){
        panel.setLayout(null);
        panel.setBackground(new Color(0,0,0,0));
        svgCanvas.setSize(size,size);
        svgCanvas.setLocation(0,0);
        svgCanvas.setBackground(new Color(0,0,0,0));
        panel.add(svgCanvas);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int col) {
        Mark mark = (Mark) value;
        svgCanvas.setURI("file:/D:/Джава/Forcons_v2/image/svg/ba.svg");
        return panel;
    }
}
