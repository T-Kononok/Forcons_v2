package data;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Comparator;

public class ForsonsListModel extends AbstractListModel<String>
{
    private final ArrayList<String> array = new ArrayList<>();

    public int getSize() {
        return array.size();
    }

    public String getElementAt(int i) {
        return array.get(i);
    }

    public String get(int i) {
        return getElementAt(i);
    }

    public void add(int index, String string) {
        array.add(index, string);
        fireIntervalAdded(this, index, index);
    }

    public void add(String s) {
        array.add(s);
        fireIntervalAdded(this, array.size(), array.size());
    }

    public void set(int index, String string) {
        array.set(index, string);
        fireContentsChanged(this, index, index);
    }

    public void remove(int i) {
        array.remove(i);
        fireIntervalRemoved(this, i, i);
    }

    public void clear() {
        array.clear();
        fireIntervalRemoved(this, 0, array.size());
    }

    public void sortPoint() {
        Comparator<String> comparatorPoint = (s1, s2) -> {
            int pointS1 = Integer.parseInt(s1.substring(s1.lastIndexOf(",")+1));
            int pointS2 = Integer.parseInt(s2.substring(s2.lastIndexOf(",")+1));
            if (pointS1 >= pointS2)
                if (pointS1 == pointS2)
                    return 0;
                else
                    return -1;
            else
                return 1;
        };
        array.sort(comparatorPoint);
        fireContentsChanged(this, 0, array.size());
    }

    public void sortClass() {
        Comparator<String> comparatorClass = (s1, s2) -> {
            String sub1 = s1.substring(0,2);
            String sub2 = s2.substring(0,2);
            switch (sub1){
                case ("ba"):
                    if (sub2.equals("ba"))
                        return 0;
                    else
                        return -1;
                case ("sa"):
                    switch (sub2){
                        case ("ba"):
                            return 1;
                        case ("sa"):
                            return 0;
                        default:
                            return -1;
                    }
                case ("in"):
                    switch (sub2) {
                        case ("sm"):
                            return -1;
                        case ("in"):
                            return 0;
                        default:
                            return 1;
                    }
                default:
                    if (sub2.equals("sm"))
                        return 0;
                    else
                        return 1;
            }
        };
        array.sort(comparatorClass);
        fireContentsChanged(this, 0, array.size());
    }
}