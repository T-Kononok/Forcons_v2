package elements;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImagePanel extends JPanel {

    private String imageFile = null;
    private boolean error = false;
    private boolean resize = true;
    private boolean speed = true;
    private RenderingHints renderingHints;

    private boolean cut = false;
    private int number;
    private int leftX;
    private int leftY;
    private int rightX;
    private int rightY;

    public ImagePanel(String imageFile, Boolean resize, Boolean speed) {
        super();
        setImageFile(imageFile);
        this.resize = resize;
        this.speed = speed;
        setHints();
    }

    public ImagePanel(String imageFile, Boolean resize) {
        super();
        setImageFile(imageFile);
        this.resize = resize;
        this.speed = resize;
        setHints();
    }

    public ImagePanel(String imageFile) {
        super();
        setImageFile(imageFile);
        setHints();
    }

    public ImagePanel() {
        super();
        setHints();
    }

    private void setHints() {
        if (speed) {
            renderingHints = new RenderingHints(RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_SPEED);
            renderingHints.put(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        } else {
            renderingHints = new RenderingHints(RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY);
            renderingHints.put(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        }
    }

    public void setImageFile(String imageFile) {
        if (imageFile == null)
            this.imageFile = null;
        else {
            File file = new File(imageFile);
            if (file.exists()) {
                this.imageFile = imageFile;
                error = false;
            } else {
                error = true;
                System.out.println(imageFile + " не найден!");
            }
        }
        repaint();
    }
    // не забыть хранение пикчи

    public void onCut(int number) {
        cut = true;
        this.number = number;
    }

    public void offCut() {
        cut = false;
    }

    public void setResize(boolean resize) {
        this.resize = resize;
    }

    private void setCut(BufferedImage image) {
        cut = true;
        int thirdWidth = image.getWidth() / 3;
        int thirdHeight = image.getHeight() / 3;
        leftX = (number % 3) * thirdWidth;
        rightX = leftX + thirdWidth;
        switch (number) {
            case 0: case 1: case 2:
                leftY = 0;
                rightY = leftY + thirdHeight;
                break;
            case 3: case 4: case 5:
                leftY = thirdHeight;
                rightY = leftY + thirdHeight;
                break;
            case 6: case 7: case 8:
                leftY = 2 * thirdHeight;
                rightY = leftY + thirdHeight;
                break;
        }
//        System.out.println(number + ": " + leftX + " " + leftY + " " + rightX + " " + rightY);
    }

    @Override
    public void paintComponent(Graphics gr) {
        Graphics2D g = (Graphics2D)gr;
        if (error) {
            g.setColor(Color.ORANGE);
            g.fillRect(0, 0, getWidth(), getHeight());
        } else {
            try {
                BufferedImage image = getImage();
                if (image != null) {
                    if (resize) {
                        if (speed)
                            g.drawImage(getScaledInstance(image), 0, 0, null);
                        else
                            g.drawImage(getQualityScaledInstance(image), 0, 0, null);
                    } else
                        g.drawImage(image, 0, 0, null);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

//    if (resize) {
//        ImageIcon icon = new ImageIcon(new ImageIcon(imageFile).getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_FAST));
//        g.drawImage(icon.getImage(), 0, 0, null);
//    } else {
//        g.drawImage(new ImageIcon(imageFile).getImage(), 0, 0, null);
//    }

    private BufferedImage getImage() throws IOException {
        if (imageFile == null)
            return null;

        File file = new File(imageFile);
        BufferedImage image = ImageIO.read(file);

        if (!cut)
            return image;

        setCut(image);
        int type = (image.getTransparency() == Transparency.OPAQUE) ?
                BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage tmp = new BufferedImage(getWidth(), getHeight(), type);
        Graphics2D g2 = tmp.createGraphics();
        g2.addRenderingHints(renderingHints);
        g2.drawImage(image, 0,0, getWidth(), getHeight(),
                leftX, leftY, rightX, rightY, null);
        g2.dispose();
        return tmp;
    }

    public BufferedImage getScaledInstance(BufferedImage img) {
        int type = (img.getTransparency() == Transparency.OPAQUE) ?
                BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage tmp = new BufferedImage(getWidth(), getHeight(), type);
        Graphics2D g2 = tmp.createGraphics();
        g2.addRenderingHints(renderingHints);
        g2.drawImage(img, 0, 0, getWidth(), getHeight(), null);
        g2.dispose();
        return tmp;
    }

    public BufferedImage getQualityScaledInstance(BufferedImage img) {
        int type = (img.getTransparency() == Transparency.OPAQUE) ?
                BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage ret = img;
        int w = img.getWidth();
        int h = img.getHeight();
        do {
            if (w > getWidth()) {
                w /= 2;
                if (w < getWidth()) {
                    w = getWidth();
                }
            }
            if (h > getHeight()) {
                h /= 2;
                if (h < getHeight()) {
                    h = getHeight();
                }
            }
            BufferedImage tmp = new BufferedImage(w, h, type);
            Graphics2D g2 = tmp.createGraphics();
            g2.addRenderingHints(renderingHints);
            g2.drawImage(ret, 0, 0, w, h, null);
            g2.dispose();
            ret = tmp;
        } while (w != getWidth() || h != getHeight());
        return ret;
    }

}