package elements;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ImagePanel extends JPanel {

    private String imageFile;
    private boolean error;

    public ImagePanel(String imageFile) {
        setImageFile(imageFile);
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

    public void paintComponent(Graphics g)
    {
        if (error) {
            g.setColor(Color.RED);
            g.fillRect(0, 0, getWidth(), getHeight());
        } else
            g.drawImage(new ImageIcon(imageFile).getImage(), 0, 0, null);
    }

}
