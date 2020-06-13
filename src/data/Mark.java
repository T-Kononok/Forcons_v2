package data;

public class Mark {

    private int mark = -1;
    private String string = "";
    private int bites = 0;
    private String bodyBag = "";
    private boolean bomb = false;
    private boolean poison = false;
    private boolean review = false;

    public boolean isBomb() {
        return bomb;
    }
    public boolean isPoison() {
        return poison;
    }
    public boolean isReview() {
        return review;
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
    public void setBodyBag(String bodyBag) {
        this.bodyBag = bodyBag;
    }
    public void setString(String string) {
        this.string = string;
    }

    public void setBites(int bites) {
        this.bites = inRange(bites,0,3);
    }

    public void set(int mark) {
        this.mark = inRange(mark,0,10);
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

    private int inRange(int value, int min, int max) {
        if (value > max)
            value = max;
        else
            value = Math.max(value, min);
        return value;
    }

}
