package elements;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class ImageScrollBarUI extends BasicScrollBarUI {

    private final Image imageTumb = new ImageIcon("image/tumb.jpg").getImage();
    private final Image imageTrack = new ImageIcon("image/fon.jpg").getImage();
    private final ImageIcon upArrow = new ImageIcon("image/upArrow.png");
    private final ImageIcon downArrow = new ImageIcon("image/downArrow.png");

    @Override
    protected JButton createDecreaseButton(int orientation) {
        JButton decreaseButton = new JButton(getAppropriateIcon(orientation)) {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(22, 22);
            }
        };
        decreaseButton.setBorderPainted(false);
        decreaseButton.setContentAreaFilled(false);
        return decreaseButton;
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        JButton increaseButton = new JButton(getAppropriateIcon(orientation)){
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(22, 22);
            }
        };
        increaseButton.setBorderPainted(false);
        increaseButton.setContentAreaFilled(false);
        return increaseButton;
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {

        g.drawImage(imageTrack,-1280-5+22,-35, null);
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

    private ImageIcon getAppropriateIcon(int orientation) {
        if (orientation == SwingConstants.SOUTH) {
            return downArrow;
        }
        return upArrow;
    }
}