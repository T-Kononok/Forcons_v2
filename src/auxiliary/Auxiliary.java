package auxiliary;

import elements.ReSizeLabel;
import org.apache.batik.swing.JSVGCanvas;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;

public class Auxiliary {
    public static void toPlace(JComponent parentComponent, JComponent childComponent, int width, int height, int x, int y) {
        childComponent.setSize(width,height);
        childComponent.setLocation(x,y);
        parentComponent.add(childComponent);
    }

    public static JSVGCanvas addCanvas(JComponent parentComponent,int width, int height, int x, int y) {
        JSVGCanvas canvas = new JSVGCanvas();
        toPlace(parentComponent,canvas,width,height,x,y);
        canvas.setBackground(new Color(0,0,0,0));
        return canvas;
    }

    public static JSVGCanvas addCanvas(JComponent parentComponent,int width, int height, int x, int y, boolean flag) {
        JSVGCanvas canvas = new JSVGCanvas();
        toPlace(parentComponent,canvas,width,height,x,y);
        canvas.setBackground(new Color(0,0,0,0));
        canvas.setVisible(flag);
        return canvas;
    }

    public static JButton addButton(JComponent parentComponent, int width, int height, int x, int y) {
        JButton button = new JButton();
        Auxiliary.toPlace(parentComponent,button,width,height,x,y);
        button.setVisible(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        return button;
    }

    public static ReSizeLabel addLabel(JComponent parentComponent, int width, int height, int x, int y, Font font) {
        ReSizeLabel label = new ReSizeLabel(font);
        label.setForeground(Color.BLACK);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);
        Auxiliary.toPlace(parentComponent,label,width,height,x,y);
        return label;
    }

    public static ReSizeLabel addLabel(JComponent parentComponent, int width, int height, int x, int y, int size) {
        Font font = null;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new BufferedInputStream(
                    new FileInputStream("Fortuna Gothic FlorishC.ttf"))).
                    deriveFont(Font.PLAIN, size);
        } catch (Exception ignored) { }
        return addLabel(parentComponent, width, height, x, y, font);
    }

    public static ReSizeLabel addLabel(JComponent parentComponent, int width, int height, int x, int y, String fontName, int size) {
        Font font = null;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new BufferedInputStream(
                    new FileInputStream(fontName))).
                    deriveFont(Font.PLAIN, size);
        } catch (Exception ignored) { }
        return addLabel(parentComponent, width, height, x, y, font);
    }
}
