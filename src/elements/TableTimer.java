package elements;

import data.Mark;

import javax.swing.*;
import java.util.ArrayList;

public class TableTimer extends Thread {

    private final JTable table;
    private final ArrayList<Mark> marks;

    public TableTimer(JTable table, Mark mark) {
        this.table = table;
        marks = new ArrayList<>();
        marks.add(mark);
    }

    public TableTimer(JTable table, ArrayList<Mark> marks) {
        this.table = table;
        this.marks = marks;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (Mark mark : marks) {
            if (mark!= null)
                mark.offChange();
        }
        table.setVisible(false);
        table.setVisible(true);
    }
}
