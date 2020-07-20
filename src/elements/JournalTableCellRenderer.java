package elements;

import data.Mark;
import org.apache.batik.swing.JSVGCanvas;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Vector;

public class JournalTableCellRenderer implements TableCellRenderer {

    private final JPanel panel = new JPanel();
    private ArrayList<ArrayList<JSVGCanvas>> matrixSVG = null;
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
    }

    private void addMatrixSVG() {
        ArrayList<ArrayList<JSVGCanvas>> matrix = new ArrayList<>();
        for (int i = 0; i < countRow; i++) {
            ArrayList<JSVGCanvas> arr = new ArrayList<>();
            for (int j = 0; j < countColumn; j++) {
                arr.add(addOneSVG());
            }
            matrix.add(arr);
        }
        matrixSVG = matrix;
    }

    private JSVGCanvas addOneSVG() {
        JSVGCanvas canvas = new JSVGCanvas();
        canvas.setBackground(new Color(0, 0, 0, 0));
        canvas.setLocation(0,0);
        canvas.setURI("file:image/svg/cell.svg");
        panel.add(canvas);
        return canvas;
    }

    public void setSize(int size) {
        for (int i = 0; i < countRow; i++) {
            for (int j = 0; j < countColumn; j++) {
                matrixSVG.get(i).get(j).setSize(size,size);
            }
        }
        label.setSize(size,size);
    }

    public void setMatrixSize(int row, int column) {
        countRow = row;
        countColumn = column;
        addMatrixSVG();
        setSize(15);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int col) {
        Mark mark = (Mark) value;

//        matrixSVG.get(row).get(col).setURI("file:image/svg/"+ mark.toStyle() +".svg");
//        matrixSVG.get(row).get(col).repaint();
//        panel.repaint();

//        switch (mark.getString()) {
//            case (""):
//                showCanvas(0);
//                label.setText("");
//                break;
//            case ("int"):
//                showCanvas(1);
//                label.setText(mark.get()+"");
//                break;
//            case ("(f)"):
//                showCanvas(2);
//                label.setText("(f)");
//                break;
//            default:
//                showCanvas(3);
//                label.setText("н");
//                break;
//        }

        return panel;
    }

//    private void showCanvas(int number) {
//        falseVisible();
//        vectorClassSVG.get(number).setVisible(true);
//    }
//
//    private void falseVisible() {
//        for (int i = 0; i < 4; i++) {
//            vectorClassSVG.get(i).setVisible(false);
//        }
//    }
}
