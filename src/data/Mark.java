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
    private boolean change = false;
    private String changeFonFile = null;
    private int number;

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

    public String getStyle() {
        return style;
    }
    public boolean isChange() {
        return change;
    }
    public String getChangeFonFile() {
        return changeFonFile;
    }
    public int getNumber() {
        return number;
    }

    //сеттеры
    public void set(int mark) {
        if (this.mark != 0)
            bites += this.mark - mark;
        System.out.println(bites);
        this.mark = inRange(mark,0,10);
        setBoolean = (mark == this.mark);
    }
    public void setBites(int bites) {
        this.bites = inRange(bites,0,3);
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

    public void onChange(String imageFile, int number) {
        this.change = true;
        this.number = number;
        changeFonFile = imageFile;
    }
    public void offChange() {
        this.change = false;
        changeFonFile = null;
    }
    //    public void setChange() {
//        change = true;
//        startTimer();
//    }

//    public void startTimer() {
//        timer = new Timer(2000, ev -> {
//            change = false;
//            timer.stop();
//        });
//        timer.start();
//    }

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
        mark = inRange(mark + plusValue, 0, 10);
        bites -= inRange(plusValue,0,bites);
    }

    public void minus(int minusValue) {
        minusValue = inRange(minusValue, 0, 3 - bites);
        mark = inRange(mark - minusValue, 0, 10);
        bites += minusValue;
    }

    public void bite() {
        if (bites < 3 && mark > 0) {
            mark--;
            bites++;
        }
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
        if (isLr())
            style += "Lr";

        if (style.equals("cell")) {
            if (mark != 0 || !string.equals(""))
                style += "Bite" + inRange(bites, 0, 4);;

            if (isBad())
                style += "Bad";
            if (isBomb())
                style += "Bomb";
            if (!getBodyBag().equals(""))
                style += "BodyBag";
        }

        return style;
    }

    private int inRange(int value, int min, int max) {
        if (value > max)
            value = max;
        else
            value = Math.max(value, min);
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
        this.string = string.replace(number,"");;
    }
    private String findNumber(String string) {
        for (int i = 0; i <= 10; i++) {
            if (string.contains(i+""))
                return i+"";
        }
        return "";
    }
}
