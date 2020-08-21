package elements;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class ImageScrollBarUI extends BasicScrollBarUI {

    private final Image imageTumb = new ImageIcon("image/tumb.png").getImage();
    private final Image imageTrack = new ImageIcon("image/fon2.jpg").getImage();
    private final int SIZE = 18;

    @Override
    protected JButton createDecreaseButton(int orientation) {
        JButton decreaseButton = new JButton();
        decreaseButton.setPreferredSize(new Dimension(SIZE, SIZE));
        decreaseButton.setBorderPainted(false);
        decreaseButton.setContentAreaFilled(false);
        return decreaseButton;
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        JButton increaseButton = new JButton();
        increaseButton.setPreferredSize(new Dimension(SIZE, SIZE));
        increaseButton.setBorderPainted(false);
        increaseButton.setContentAreaFilled(false);
        return increaseButton;
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {

        g.drawImage(imageTrack,-1280+SIZE,-35, null);
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle r) {

        g.drawImage(imageTumb,r.x, r.y, r.width, r.height, null);

    }

    @Override
    protected void layoutHScrollbar(JScrollBar sb) {
        sb.setBorder(BorderFactory.createEmptyBorder());
        sb.setOpaque(true);
        super.layoutHScrollbar(sb);
    }
}