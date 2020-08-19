package elements;

import data.JournalTableModel;
import data.Mark;
import elements.skills.SkillsPanel;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class TableNoGaps extends JPanel{

    private final JTable table = new JTable(new JournalTableModel());
    private final SkillsPanel skillsPanel = new SkillsPanel(this);

    private final int initialX;
    private final int initialY;
    private final int initialWidth;
    private final int initialHeight;

    private int cellSize = 0;

    private JournalTableCellRenderer renderer;

    public TableNoGaps(int initialX, int initialY, int initialWidth, int initialHeight) throws IOException {
        super();
        this.initialX = initialX;
        this.initialY = initialY;
        this.initialWidth = initialWidth;
        this.initialHeight = initialHeight;

        setLayout(null);

        setSize(initialWidth, initialHeight);
        setComponent(this,initialX,initialY);
        setComponent(skillsPanel,initialX,initialY);
        setComponent(table,0,0);
        add(table);


        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
    }

    public void addRenderer(Map<String, HSSFCellStyle> map) {
        renderer = new JournalTableCellRenderer(map);
        table.setDefaultRenderer(Mark.class, renderer);
    }

    private void setComponent(JComponent component, int x, int y) {
        component.setLocation(x,y);
        component.setBorder(BorderFactory.createEmptyBorder());
//        component.setBorder(BorderFactory.createLineBorder(Color.RED));
        component.setOpaque(false);
    }

    public SkillsPanel getSkillsPanel(){
        return skillsPanel;
    }

    public void addIn(JComponent component) {
        component.add(getSkillsPanel());
        component.add(this);
    }

    public JournalTableCellRenderer getRenderer() {
        return renderer;
    }

    public int getRowCount() {
        return table.getRowCount();
    }

    public int getCellSize(){
        return cellSize;
    }

    public void setModel(TableModel dataModel){
        table.setModel(dataModel);
    }

    public void setSize(int width, int height) {
        super.setSize(width,height);
        table.setSize(width,height);
        skillsPanel.setSize(width+cellSize*3,height+cellSize*2);
    }

    public void setLocation(int x, int y) {
        super.setLocation(x,y);
        skillsPanel.setLocation(x - cellSize * 2,y - cellSize);
    }

    public void setVisible(boolean bol){
        super.setVisible(bol);
        skillsPanel.setVisible(bol);
    }

//    public  ArrayList<ImagePanel> getLeftImages() {
//        ArrayList<ImagePanel> leftImages = new ArrayList<>();
//        int cellSize = tableNoGaps.getCellSize();
//        for (int i = 0; i < tableNoGaps.getRowCount(); i++) {
//            leftImageArray.add(addOneLeftImage(tableNoGaps.getX()-cellSize, tableNoGaps.getY()+5+i*cellSize,cellSize));
//        }
//    }

    public void resizeTable(){
        if (table.getColumnCount() * initialHeight > table.getRowCount() * (initialWidth - cellSize)) {
            cellSize = (initialWidth - cellSize) / table.getColumnCount();
            setSize(initialWidth, cellSize * table.getRowCount());
            setLocation(initialX+cellSize, initialY + initialHeight/2 - getHeight()/2);
        } else {
            cellSize = initialHeight / table.getRowCount();
            setSize(cellSize * table.getColumnCount(), initialHeight);
            setLocation(initialX + initialWidth/2 - getWidth() / 2 + cellSize, initialY);
        }
        table.setRowHeight(cellSize);
        for (int i = 0; i < table.getColumnCount(); i++)
            table.getColumnModel().getColumn(i).setPreferredWidth(cellSize);

        renderer.setSize(cellSize);
    }

    public void startThread(){
        skillsPanel.startThread();
    }

    public void stopThread(){
        skillsPanel.stopThread();
    }

}
