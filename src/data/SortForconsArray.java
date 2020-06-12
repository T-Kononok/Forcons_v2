package data;

import java.util.ArrayList;
import java.util.Comparator;

public class SortForconsArray {

    private final ArrayList<String> array  = new ArrayList <>();

    public ArrayList <String> getArray() {
        return array;
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
    }
}
