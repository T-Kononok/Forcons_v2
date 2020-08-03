package elements;

import data.JournalTableModel;
import data.Mark;
import elements.skills.SkillsPanel;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.io.IOException;

public class TableNoGaps extends JPanel{

    private final JTable table = new JTable(new JournalTableModel());
    private final SkillsPanel skillsPanel = new SkillsPanel(this);

    private final int initialX;
    private final int initialY;
    private final int initialWidth;
    private final int initialHeight;

    private int cellSize = 0;

    private final JournalTableCellRenderer renderer = new JournalTableCellRenderer();

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

        table.setDefaultRenderer(Mark.class, renderer);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
    }

    private void setComponent(JComponent component, int x, int y) {
        component.setLocation(x,y);
        component.setBorder(BorderFactory.createEmptyBorder());
//        component.setBorder(BorderFactory.createLineBorder(Color.RED));
        component.setBackground(new Color(0, 0, 0, 0));
        component.setOpaque(false);
    }

    public SkillsPanel getSkillsPanel(){
        return skillsPanel;
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
        skillsPanel.setSize(width+cellSize*2,height+cellSize*2);
    }

    public void setLocation(int x, int y) {
        super.setLocation(x,y);
        skillsPanel.setLocation(x - cellSize,y - cellSize);
    }

    public void setVisible(boolean bol){
        super.setVisible(bol);
        skillsPanel.setVisible(bol);
    }

    public void resizeTable(){
        if (table.getColumnCount() * initialHeight > table.getRowCount() * initialWidth) {
            cellSize = initialWidth / table.getColumnCount();
            setSize(initialWidth, cellSize * table.getRowCount());
            setLocation(initialX, initialY + initialHeight/2 - getHeight()/2);
        } else {
            cellSize = initialHeight / table.getRowCount();
            setSize(cellSize * table.getColumnCount(), initialHeight);
            setLocation(initialX + initialWidth/2 - getWidth() / 2, initialY);
        }
        table.setRowHeight(cellSize);
        for (int i = 0; i < table.getColumnCount(); i++)
            table.getColumnModel().getColumn(i).setPreferredWidth(cellSize);
    }

    public void startThread(){
        skillsPanel.startThread();
    }

    public void stopThread(){
        skillsPanel.stopThread();
    }

}
