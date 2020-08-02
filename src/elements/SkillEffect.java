package elements;

import data.YX;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SkillEffect {

    private final BufferedImage image;
    private final int x;
    private final int y;
    private int time = 10;

    public SkillEffect(String filename, int x, int y, int width, int height) throws IOException {
        this.x = x;
        this.y = y;
        File file = new File(filename);
        BufferedImage readImage = ImageIO.read(file);
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_SPEED);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2.drawImage(readImage, 0, 0, width, height, null);
        g2.dispose();
    }

    public void write() {
        System.out.println(time);
    }

    public void paint(Graphics2D g) {
        g.drawImage(image, x, y, null);
    }

    public boolean minusTime() {
        time--;
        return time > 0;
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
}
