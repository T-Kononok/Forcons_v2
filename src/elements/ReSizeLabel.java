package elements;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ReSizeLabel extends JLabel {

    private final Font font;

    public ReSizeLabel(Font font) {
        super();
        this.font = font;
        setFont(font);
    }

    public void setTextReSize(String string) {
        setText(string.replace(' ', '_'));
        double textWidth = getFontMetrics(font).stringWidth(getText());
        setText(string);

        if (textWidth > getWidth()) {
            int newFontSize = (int) (font.getSize() * getWidth() / (textWidth));
            if (font.getName().equals("Verdana")) {
                setFont(new Font(font.getName(), Font.BOLD, newFontSize));
            }
            else {
                setNewFont(font.getName(),newFontSize);
            }
        } else {
            setFont(font);
        }
    }

    private void setNewFont(String fontName, int size) {
        try {
            Font newFont = Font.createFont(Font.TRUETYPE_FONT, new BufferedInputStream(
                    new FileInputStream(fontName+".ttf"))).deriveFont(Font.PLAIN, size);
            setFont(newFont);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
