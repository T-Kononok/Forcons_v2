package data;

public class Mark {

    private int mark = -1;
    private String string = "int";
    private int bites = 0;
    private String bodyBag = "";
    private boolean bomb = false;
    private boolean poison = false;
    private boolean review = false;
    private boolean cr = false;
    private boolean kr = false;
    private boolean lr = false;

    public boolean isBomb() {
        return bomb;
    }
    public boolean isPoison() {
        return poison;
    }
    public boolean isReview() {
        return review;
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
    public int get() {
        return mark;
    }
    public String getString() {
        return string;
    }
    public int getBites() {
        return bites;
    }
    public String getBodyBag() {
        return bodyBag;
    }

    public void setBomb(boolean bomb) {
        this.bomb = bomb;
    }
    public void setPoison(boolean poison) {
        this.poison = poison;
    }
    public void setReview(boolean review) {
        this.review = review;
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

    public void setBodyBag(String bodyBag) {
        this.bodyBag = bodyBag;
    }

    public void setString(String string) {
        this.string = string;
        mark = findNumber();
    }

    public void setBites(int bites) {
        this.bites = inRange(bites,0,3);
    }

    public boolean set(int mark) {
        string = "int";
        this.mark = inRange(mark,0,10);
        return mark == this.mark;
    }

    public void plus(int plusValue) {
        mark += plusValue;
        bites -= inRange(plusValue,0,3);
    }

    public void minus(int minusValue) { ///
        minusValue = inRange(minusValue, 0, 3 - bites);
        mark -= minusValue;
        bites += minusValue;
    }

    public void bite() {
        if (bites < 3)
            bites++;
    }

    public String toString() {
        if (string.equals("int"))
            return mark + "";
        return string;
    }

    private int inRange(int value, int min, int max) {
        if (value > max)
            value = max;
        else
            value = Math.max(value, min);
        return value;
    }

    private int findNumber() {
        for (int i = 0; i <= 10; i++) {
            if (string.contains(i+""))
                return i;
        }
        return -1;
    }

}
