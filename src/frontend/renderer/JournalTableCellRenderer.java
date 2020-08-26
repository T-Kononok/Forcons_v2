package frontend.renderer;

import auxiliary.Auxiliary;
import backend.marks.Cell;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.*;

public class JournalTableCellRenderer implements TableCellRenderer {

    private final JPanel panel = new JPanel();
    private final Map<String, JSVGCanvas> mapSVG = new HashMap<>();
    private final JLabel label;

    private final Map<String, HSSFCellStyle> map;
//

    public JournalTableCellRenderer(Map<String, HSSFCellStyle> map) {
        this.map = map;
        panel.setLayout(null);
        panel.setBackground(new Color(0,0,0,0));

        label = Auxiliary.addLabel(panel,15,15,0,0,"American TextC Regular.ttf",15);
        panel.add(label);
        addSVG();
    }

    private void addSVG() {
        map.forEach((s,cs) -> mapSVG.put(s, Auxiliary.addCanvas(panel,5,5,0,0,false)));
        for(Map.Entry<String, JSVGCanvas> entry: mapSVG.entrySet()) {
            mapSVG.get(entry.getKey()).setURI("file:image/svg/"+entry.getKey()+".svg");
        }
    }

    public void setSize(int size) {
        for(Map.Entry<String, JSVGCanvas> entry: mapSVG.entrySet()) {
            mapSVG.get(entry.getKey()).setSize(size,size);
        }
        label.setSize(size,size);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int col) {
        Cell cell = (Cell) value;
        falseVisible();
//        System.out.println(mark.toStyle());
        showCanvas(cell.toStyle());
        label.setText(cell.toString());
//        fon.setImageFile(mark.getChangeFonFile());
        return panel;
    }

    private void showCanvas(String string) {
        mapSVG.forEach((s,c) -> c.setVisible(s.equals(string)));
    }

    private void falseVisible() {
//        for(Map.Entry<String, JSVGCanvas> entry: mapSVG.entrySet()) {
//            mapSVG.get(entry.getKey()).setVisible(false);
//        }

    }
}
