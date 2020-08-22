package data;

import data.skills.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SkillsData {

    private static final Map<String,ArrayList<YX>> bodyBagMap = new HashMap<>();
    private static final ArrayList<Integer> light = new ArrayList<>();

    public static Map<String, ArrayList<YX>> getBodyBagMap() {
        return bodyBagMap;
    }

    public static ArrayList<Integer> getLight() {
        return light;
    }

    public static void noExiled(String name) {
        bodyBagMap.get(name).forEach((xy) -> MarksData.getMark(xy).minus(1));
    }

    public static void exiled(String name) {
        bodyBagMap.get(name).forEach((xy) -> MarksData.getMark(xy).setBodyBag(""));
        bodyBagMap.remove(name);
    }

    public static void addBodyBag(YX xy) {
        String[] string = ForconsList.getSelectedValue().split(",");
        if (bodyBagMap.get(string[1]) == null) {
            ArrayList<YX> array = new ArrayList<>();
            array.add(xy);
            bodyBagMap.put(string[1],array);
        } else
            bodyBagMap.get(string[1]).add(xy);
    }

    public static void addLight(int number) {
        if (number >= 0 && number < MarksData.getRowCount())
            light.add(number);
        else
            System.out.println("Ошибка addLight");
    }

    public static boolean lightContains(int row) {
        for (Integer integer : light)
            if (integer == row)
                return true;
        return false;
    }



    //    public static Mark checkMark(YX yx) {
//        for (int i = -1; i <= 1; i++) {
//            for (int j = -1; j <= 1; j++) {
//                if (MainData.getMark(yx.getY()+i,yx.getX()+j).isBad()) {
//
//                }
//            }
//        }
//        if (MainData.getMark(yx).is)
//    }

}
