package elements;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ImagePanel extends JPanel {

    private String imageFile;
    private boolean error;
    private boolean resize;

    public ImagePanel(String imageFile, Boolean bol) {
        super();
        setImageFile(imageFile);
        setResize(bol);
    }

    public ImagePanel(String imageFile) {
        super();
        setImageFile(imageFile);
    }

    public ImagePanel() {
        super();
    }

    public void setImageFile(String imageFile) {
        File file = new File(imageFile);
        if(file.exists()) {
            this.imageFile = imageFile;
            error = false;
        } else {
            error = true;
            System.out.println(imageFile + " не найден!");
        }
        repaint();
    }

    public void setResize(boolean resize) {
        this.resize = resize;
    }

    public void paintComponent(Graphics g) {
        if (error) {
            g.setColor(Color.RED);
            g.fillRect(0, 0, getWidth(), getHeight());
        } else {
            if (resize) {
                ImageIcon icon = new ImageIcon(new ImageIcon(imageFile).getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_FAST));
                g.drawImage(icon.getImage(), 0, 0, null);
            } else {
                g.drawImage(new ImageIcon(imageFile).getImage(), 0, 0, null);
            }
        }
    }

}
