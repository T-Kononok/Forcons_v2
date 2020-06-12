package data;

import java.util.Comparator;
import java.util.Vector;

public class SortForconsVector {

    private final Vector<String> vector  = new Vector<>();

    public Vector<String> getVector() {
        return vector;
    }

    public void sortPoint() {
        Comparator<String> comparatorPoint = (s1, s2) -> {
            int is = Integer.parseInt(s1.substring(s1.lastIndexOf(",")+1));
            int it = Integer.parseInt(s2.substring(s2.lastIndexOf(",")+1));
            if (is >= it)
                if (is == it)
                    return 0;
                else
                    return -1;
            else
                return 1;
        };
        vector.sort(comparatorPoint);
        for (int i = 0; i < vector.size(); i++)
            vector.set(i, (i+1) + vector.get(i).substring(vector.get(i).indexOf(",")));
    }

    public void sortClass() {
        Comparator<String> comparatorClass = (s, t1) -> {
            String ss = s.substring(s.indexOf(",")+1,s.indexOf(",")+3);
            String st = t1.substring(t1.indexOf(",")+1,t1.indexOf(",")+3);
            switch (ss){
                case ("ba"):
                    if (st.equals("ba"))
                        return 0;
                    else
                        return -1;
                case ("sa"):
                    switch (st){
                        case ("ba"):
                            return 1;
                        case ("sa"):
                            return 0;
                        default:
                            return -1;
                    }
                case ("in"):
                    switch (st) {
                        case ("sm"):
                            return -1;
                        case ("tn"):
                            return 0;
                        default:
                            return 1;
                    }
                default:
                    if (st.equals("sm"))
                        return 0;
                    else
                        return 1;
            }
        };
        vector.sort(comparatorClass);
        for (int i = 0; i < vector.size(); i++)
            vector.set(i, (i+1) + vector.get(i).substring(vector.get(i).indexOf(",")));
    }
}
