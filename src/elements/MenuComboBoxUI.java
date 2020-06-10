package elements;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;

public class MenuComboBoxUI extends BasicComboBoxUI {

    @Override
    protected JButton createArrowButton() {
        JButton button = new JButton();
        button.setVisible(false);
        return button;
    }

    @Override
    public void paintCurrentValue(Graphics g, Rectangle bounds, boolean hasFocus) {
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, 100, 25);
    }

}
