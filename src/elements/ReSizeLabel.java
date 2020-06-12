package elements;

import javax.swing.*;
import java.awt.*;

public class ReSizeLabel extends JLabel {

    public void setTextReSize(String string, Font font) {
        setText(string);
        double textWidth = getFontMetrics(font).stringWidth(getText());

        if (textWidth > getWidth()) {
            int newFontSize = (int) (font.getSize() * getWidth() / (textWidth));
            setFont(new Font(font.getName(), Font.BOLD, newFontSize));
        } else
            setFont(font);
    }
}
