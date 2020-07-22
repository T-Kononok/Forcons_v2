package elements;

import data.Mark;

import javax.swing.*;

public class TableTimer extends Thread {

    private final JTable table;
    private final Mark mark;

    public TableTimer(JTable table, Mark mark) {
        this.table = table;
        this.mark = mark;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mark.setChange(false);
        table.setVisible(false);
        table.setVisible(true);
    }
}
