package elements;

import data.MainData;
import data.Mark;
import java.util.ArrayList;

public class TableTimer extends Thread {

    private final MainData mainData;
    private final ArrayList<Mark> marks;

    public TableTimer(MainData mainData, Mark mark) {
        this.mainData = mainData;
        marks = new ArrayList<>();
        marks.add(mark);
    }

    public TableTimer(MainData mainData, ArrayList<Mark> marks) {
        this.mainData = mainData;
        this.marks = marks;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (Mark mark : marks) {
            if (mark!= null)
                mark.offChange();
        }
        mainData.getTableWithoutGaps().repaint();
    }
}
