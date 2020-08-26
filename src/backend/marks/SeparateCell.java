package backend.marks;

import java.io.IOException;
import java.io.PushbackInputStream;

public class SeparateCell {

    // поля
    private int mark = 0;
    private int bites = 0;
    private String back = "";
    private String border = "";

    //геттеры
    public int get() {
        return mark;
    }
    public int getBites() {
        return bites;
    }

    public boolean isCr() {
        return back.equals("C");
    }
    public boolean isKr() {
        return back.equals("K");
    }
    public boolean isN() {
        return back.equals("N");
    }
    public boolean isPoison() {
        return back.equals("P");
    }
    public boolean isBad() {
        return border.equals("BC");
    }
    public boolean isBodyBag() {
        return border.equals("BB");
    }
    public boolean isBomb() {
        return border.equals("KQ");
    }


    public boolean isEmpty() {
        return back.equals("") && border.equals("");
    }

    public boolean isNumber() {
        return mark != 0;
    }

    public boolean isCanBite() {
        return isNumber() && bites < 3 && !isBodyBag() && !isKr();
    }

    public boolean isNonBorder() {
        return border.equals("");
    }

    public boolean canInteract() {
        return isNumber() && !isKr();
    }

    //сеттеры
    public void set(int mark) {
        if (this.mark != 0) {
            setBites(bites + this.mark - mark);
            this.mark -= inRange( this.mark - mark, 3);
        } else {
            this.mark = inRange(mark, 10);
            setBites(0);
        }
    }

    public void setBites(int bites) {
        this.bites = inRange(bites, 3);
        back = this.bites+"";
    }

    public void makeEmpty() {
        back = "";
        border = "";
    }

    public void setPoison(boolean flag) {
        if (flag)
            back = "P";
        else
            makeEmpty();
    }

    public void setBad(boolean flag) {
        if (flag)
            border = "BC";
        else
            border = "";
    }

    public void setBodyBag(boolean flag) {
        if (flag)
            border = "BB";
        else
            border = "";
    }

    public void setBomb(boolean flag) {
        if (flag)
            border = "KQ";
        else
            border = "";
    }

    //выделение при пересортировке

    public void setStyle(String style) {
        String[] subStr = style.split("_",3);
        back = subStr[1];
        border = subStr[2];
        String CKNP = "CKNP";
        if (!CKNP.contains(back))
            setBites(Integer.parseInt(back));
    }

    protected void plus(int plusValue) {
        mark = inRange(mark + plusValue, 10);
        bites -= inRange(plusValue, bites);
    }

    public void minus(int minusValue) throws IOException {
        minusValue = inRange(minusValue, 3 - bites);
        mark = inRange(mark - minusValue, 10);
        setBites(bites + minusValue);
    }

    public String toString() {
        if (isN())
            return "н";
        if (mark != 0)
            return mark + "";
        return "";
    }

    public String toStyle() {
        return "cell_" + back + "_" + border;
    }

    private int inRange(int value, int max) {
        if (value > max && max > 0)
            value = max;
        else
            value = Math.max(value, 0);
        return value;
    }
}
