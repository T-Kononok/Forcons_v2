package elements.skills;

import data.YX;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SkillEffect {

    private static int StatNumber = 0;
    private final int number;

    private final BufferedImage image;
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private int time = 100;
    private boolean endlessly = false;

    public SkillEffect(String filename, int x, int y, int width, int height) throws IOException {
        StatNumber++;
        number = StatNumber;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        BufferedImage readImage = ImageIO.read(new File(filename));
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_SPEED);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2.drawImage(readImage, 0, 0, width, height, null);
        g2.dispose();
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight() {
        return height;
    }

    public YX getYX() {
        return new YX(y,x);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void write() {
        System.out.println(number + " : " + time);
    }

    public void onEndlessly() {
        endlessly = true;
    }

    public boolean minusTime() {
        if (!endlessly)
            time--;
        return time > 0;
    }

    public void paint(Graphics2D g) {
        g.drawImage(image, x, y, null);
    }
}
