package elements;

import data.JournalTableModel;
import data.Mark;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.io.IOException;

public class TableWithoutGaps extends JPanel{

    private final JTable table = new JTable(new JournalTableModel());

    private final int initialX;
    private final int initialY;
    private final int initialWidth;
    private final int initialHeight;

    private int cellSize = 0;

    private final JournalTableCellRenderer renderer = new JournalTableCellRenderer();

    public TableWithoutGaps(int initialX, int initialY, int initialWidth, int initialHeight) throws IOException {
        super();
        this.initialX = initialX;
        this.initialY = initialY;
        this.initialWidth = initialWidth;
        this.initialHeight = initialHeight;

        setSize(initialWidth, initialHeight);
        setComponent(this,initialX,initialY);
        setComponent(table,0,0);
        add(table);

        table.setDefaultRenderer(Mark.class, renderer);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        setVisible(false);
    }

    private void setComponent(JComponent component, int x, int y) {
        component.setLocation(x,y);
        component.setBorder(BorderFactory.createEmptyBorder());
        component.setBackground(new Color(0, 0, 0, 0));
        component.setOpaque(false);
    }

    public void setSize(int width, int height) {
        super.setSize(width,height);
        table.setSize(width,height);
    }

    public void setLocation(int x, int y) {
        super.setLocation(x,y);
    }

    public int getRowCount() {
        return table.getRowCount();
    }

    public void setModel(TableModel dataModel){
        table.setModel(dataModel);
    }

    public int getCellSize(){
        return cellSize;
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

    public JournalTableCellRenderer getRenderer() {
        return renderer;
    }

//    public void repaintTable(){
//        repaint();
//    }

}
