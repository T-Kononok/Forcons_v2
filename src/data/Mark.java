package data;

public class Mark {

    // поля
    private int mark = 0;
    private int bites = 0;

    private String bodyBag = "";
    private boolean bad = false;
    private boolean bomb = false;
    private boolean poison = false;
    private boolean cr = false;
    private boolean kr = false;
    private boolean lr = false;
    private boolean setBoolean = true;

    private String style = "cell";

    //геттеры
    public int get() {
        return mark;
    }
    public int getBites() {
        return bites;
    }

    public String getBodyBag() {
        return bodyBag;
    }
    public boolean isBad() {
        return bad;
    }
    public boolean isBomb() {
        return bomb;
    }
    public boolean isPoison() {
        return poison;
    }
    public boolean isCr() {
        return cr;
    }
    public boolean isKr() {
        return kr;
    }
    public boolean isLr() {
        return lr;
    }
    public boolean isSetBoolean() {
        return setBoolean;
    }

    public boolean canBite() {
        return !(cr || kr);
    }

    //сеттеры
    public void set(int mark) {
        if (this.mark != 0) {
            setBites(bites + this.mark - mark);
            this.mark -= inRange( this.mark - mark, 4);
        } else
            this.mark = inRange(mark, 10);
        setBoolean = (mark == this.mark);
    }

    public void setBites(int bites) {
        this.bites = inRange(bites, 4);
    }

    public void setBodyBag(String bodyBag) {
        this.bodyBag = bodyBag;
    }
    public void setBad(boolean review) {
        this.bad = review;
    }
    public void setBomb(boolean bomb) {
        this.bomb = bomb;
    }
    public void setPoison(boolean poison) {
        this.poison = poison;
    }
    public void setCr(boolean cr) {
        this.cr = cr;
    }
    public void setKr(boolean kr) {
        this.kr = kr;
    }
    public void setLr(boolean lr) {
        this.lr = lr;
    }

    public void setStyle(String style) {
        this.style = style;

        if (style.contains("Poison"))
            setPoison(true);
        if (style.contains("Cr"))
            setCr(true);
        if (style.contains("Kr"))
            setKr(true);
        if (style.contains("Lr"))
            setLr(true);

        if (style.contains("Bite"))
            setBites(Integer.parseInt(style.substring(8,9)));

        if (style.contains("Bad"))
            setBad(true);
        if (style.contains("Bomb"))
            setBomb(true);
        //доделать потом комментарии
        if (style.contains("BodyBag"))
            setBodyBag("true");
    }


    //другое
    public void plus(int plusValue) {
        mark = inRange(mark + plusValue, 10);
        bites -= inRange(plusValue, bites);
    }

    public void minus(int minusValue) {
        minusValue = inRange(minusValue, 3 - bites);
        mark = inRange(mark - minusValue, 10);
        bites += minusValue;
    }

    public String toString() {
        if (mark != 0)
            return mark + string;
        return string;
    }

    public String toStyle() {
        style = "cell";
        if (isPoison())
            style += "Poison";
        if (isCr())
            style += "Cr";
        if (isKr())
            style += "Kr";

        if (style.equals("cell")) {
            if (mark != 0 || !string.equals(""))
                style += "Bite" + bites;

            if (isBad())
                style += "Bad";
            if (isBomb())
                style += "Bomb";
            if (!getBodyBag().equals(""))
                style += "BodyBag";
        }

        if (style.equals("cellBite0") && isLr())
                style = "cellLr";

        return style;
    }

    private int inRange(int value, int max) {
        if (value > max && max > 0)
            value = max;
        else
            value = Math.max(value, 0);
        return value;
    }




    //на будущее для работы с условынми обозначениями АВ
    private String string = "";
    public String getString() {
        return string;
    }
    public void setString(String string) {
        String number = findNumber(string);
        if (!number.equals(""))
            mark = Integer.parseInt(string);
        this.string = string.replace(number,"");
    }
    private String findNumber(String string) {
        for (int i = 0; i <= 10; i++) {
            if (string.contains(i+""))
                return i+"";
        }
        return "";
    }
}
