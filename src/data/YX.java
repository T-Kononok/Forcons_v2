package data;

public class YX {

    private Integer y;
    private Integer x;

    public YX(Integer y, Integer x) {
        this.y = y;
        this.x = x;
    }

    public YX() {
        y = 0;
        x = 0;
    }

    public YX(String string) {
        inString(string);
    }

    public Integer getY() {
        return y;
    }

    public Integer getX() {
        return x;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public String toString() {
        return "[" + getY() + "," + getX() + "]";
    }

    public void inString(String string) {
        y = Integer.parseInt(string.substring(string.indexOf("[")+1,string.indexOf(",")));
        x = Integer.parseInt(string.substring(string.indexOf(",")+1,string.indexOf("]")));
    }
}
