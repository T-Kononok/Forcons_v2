package elements;

import data.JournalTableModel;
import data.Mark;
import elements.skills.SkillsPanel;
import frame.MainFrame;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.Map;

public class TableNoGaps{

    private static final JTable table = new JTable(new JournalTableModel());
    private static final SkillsPanel skillsPanel = new SkillsPanel();
    private static final JPanel fon = new JPanel();

    private static int initialX;
    private static int initialY;
    private static int initialWidth;
    private static int initialHeight;

    private static int cellSize = 0;

    private static JournalTableCellRenderer renderer;

    public static void addTableNoGaps(MainFrame component, int x, int y, int w, int h) {
        initialX = x;
        initialY = y;
        initialWidth = w;
        initialHeight = h;

        fon.setLayout(null);

        setSize(initialWidth, initialHeight);
        setComponent(fon,initialX,initialY);
        setComponent(skillsPanel,initialX,initialY);
        setComponent(table,0,0);
        fon.add(table);

        component.add(fon);

        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
    }

    public static void addRenderer(Map<String, HSSFCellStyle> map) {
        renderer = new JournalTableCellRenderer(map);
        table.setDefaultRenderer(Mark.class, renderer);
    }

    private static void setComponent(JComponent component, int x, int y) {
        component.setLocation(x,y);
        component.setBorder(BorderFactory.createEmptyBorder());
//        component.setBorder(BorderFactory.createLineBorder(Color.RED));
        component.setOpaque(false);
    }

    public static SkillsPanel getSkillsPanel(){
        return skillsPanel;
    }

    public static void addIn(JComponent component) {
        component.add(getSkillsPanel());
        component.add(fon);
    }

    public static int getCellSize(){
        return cellSize;
    }

    public static void setModel(TableModel dataModel){
        table.setModel(dataModel);
    }

    public static void setSize(int width, int height) {
        fon.setSize(width,height);
        table.setSize(width,height);
        skillsPanel.setSize(width+cellSize*3,height+cellSize*2);
    }

    public static void setLocation(int x, int y) {
        fon.setLocation(x,y);
        skillsPanel.setLocation(x - cellSize * 2,y - cellSize);
    }

    public static void setVisible(boolean bol){
        fon.setVisible(bol);
        skillsPanel.setVisible(bol);
    }

//    public  ArrayList<ImagePanel> getLeftImages() {
//        ArrayList<ImagePanel> leftImages = new ArrayList<>();
//        int cellSize = tableNoGaps.getCellSize();
//        for (int i = 0; i < tableNoGaps.getRowCount(); i++) {
//            leftImageArray.add(addOneLeftImage(tableNoGaps.getX()-cellSize, tableNoGaps.getY()+5+i*cellSize,cellSize));
//        }
//    }

    public static void resizeTable(){
        if (table.getColumnCount() * initialHeight > table.getRowCount() * (initialWidth - cellSize)) {
            cellSize = (initialWidth - cellSize) / table.getColumnCount();
            setSize(initialWidth, cellSize * table.getRowCount());
            setLocation(initialX+cellSize, initialY + initialHeight/2 - fon.getHeight()/2);
        } else {
            cellSize = initialHeight / table.getRowCount();
            setSize(cellSize * table.getColumnCount(), initialHeight);
            setLocation(initialX + initialWidth/2 - fon.getWidth() / 2 + cellSize, initialY);
        }
        table.setRowHeight(cellSize);
        for (int i = 0; i < table.getColumnCount(); i++)
            table.getColumnModel().getColumn(i).setPreferredWidth(cellSize);

        renderer.setSize(cellSize);
    }

    public static void startThread(){
        skillsPanel.startThread();
    }

    public static void stopThread(){
        skillsPanel.stopThread();
    }

}
