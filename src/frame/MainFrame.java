package frame;

import data.ForconsList;
import data.MainData;
import data.Mark;
import elements.*;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import elements.skills.SkillButtonActionListener;
import org.apache.batik.swing.JSVGCanvas;

public class MainFrame extends JFrame {

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private final ImagePanel panelFull = new ImagePanel("image/begin_fon.jpg",false);
    private final TableNoGaps tableNoGaps = new TableNoGaps(0,35,1035,580);
    private final ForconsList forconsList = new ForconsList();
    private final MainData mainData = new MainData(this,forconsList);
    private final UpElementsPanel upElementsPanel = new UpElementsPanel(this);
    private final DownElementsPanel downElementsPanel = new DownElementsPanel(mainData);

    private JFileChooser fileChooser = null;
    private ArrayList<ImagePanel> leftImageArray = null;


    private MainFrame() throws IOException {

        panelFull.setSize(WIDTH,HEIGHT);
        panelFull.setLayout(null);

        downElementsPanel.addIn(panelFull,0,0);
        downElementsPanel.setVisible(false);
        upElementsPanel.addIn(panelFull,0,0);
        upElementsPanel.setVisible(false);

        addTableNoGaps();

        addOpenButton(addOpenPanel(), addForconsListScroll());

        getContentPane().add(panelFull);
    }

    private ImagePanel addOpenPanel() {
        ImagePanel openPanel = new ImagePanel("image/begin_fon_highlighted.png",false);
        toPlace(openPanel,WIDTH,HEIGHT,0,0);
        openPanel.setVisible(false);
        openPanel.setOpaque(false);
        return openPanel;
    }

    private void addTableNoGaps() {
        tableNoGaps.addIn(panelFull);
        tableNoGaps.setVisible(false);
    }

    private JScrollPane addForconsListScroll() {
        JScrollPane forconsListScroll = new JScrollPane(forconsList.getList());
        toPlace(forconsListScroll,220,585,1060,35);
        forconsListScroll.setOpaque(false);
        forconsListScroll.getViewport().setOpaque(false);
        forconsListScroll.setVisible(false);
//        forconsListScroll.setBorder(BorderFactory.createLineBorder(Color.RED));
        forconsListScroll.setBorder(BorderFactory.createEmptyBorder());
        forconsListScroll.getVerticalScrollBar().setUI(new ImageScrollBarUI());
        forconsListScroll.getVerticalScrollBar().setPreferredSize(new Dimension(18,580));
        forconsListScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        return forconsListScroll;
    }

    private void addOpenButton(ImagePanel openPanel, JScrollPane scrollPane) {
        OvalButton openButton = new OvalButton(OvalButton.SHAPE_OVAL,OvalButton.VERTICAL);
        toPlace(openButton,210,235, 537,260);
        openButton.setPanel(openPanel);
        openButton.setMessageImage(addBeginMessageImage());
        openButton.addActionListener(ev -> {
            mainData.readTable(selectionFile("Открыть жунал"));
//            mainData.readTable("D:\\Джава\\Forcons_v2\\10_FM-3.xls");
            forconsList.read(selectionFile("Открыть форсонов"));
            openButton.setVisible(false);
            openPanel.setVisible(false);
            tableNoGaps.setVisible(true);
            upElementsPanel.setVisible(true);
            scrollPane.setVisible(true);

            tableNoGaps.startThread();
            panelFull.setImageFile("image/fon2.jpg");
        });
    }

    private ImagePanel addBeginMessageImage() {
        ImagePanel messageImage = new ImagePanel("image/begin_fon_message.png",true,false);
        toPlace(messageImage,450,250,720,70);
        return messageImage;
    }

    private String selectionFile(String string) {
        if (fileChooser==null) {
            fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
        }
        fileChooser.setDialogTitle(string);
        if (fileChooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
//            System.out.println(fileChooser.getSelectedFile().getPath());
            return fileChooser.getSelectedFile().getPath();
        }
        return null;
    }

    private void toPlace(JComponent component,int width, int height, int x, int y) {
        component.setSize(width,height);
        component.setLocation(x,y);
        panelFull.add(component);
    }

    public TableNoGaps getTableNoGaps() {
        return tableNoGaps;
    }

    public DownElementsPanel getDownElementsPanel() {
        return downElementsPanel;
    }

    public ForconsList getForconsList() {
        return forconsList;
    }

    public UpElementsPanel getUpElementsPanel() {
        return upElementsPanel;
    }

//    public UpElementsPanel getUpElementsPanel() {
//        return upElementsPanel;
//    }

    public static void main(String[] args) throws IOException {
        MainFrame frame = new MainFrame();

        frame.setUndecorated(true);
        frame.setExtendedState(MAXIMIZED_BOTH);
        frame.setVisible(true);
        //костыль дабы не было иногда прозрачного экрана
        frame.setImageKost();
    }

    //костыль дабы не было иногда прозрачного экрана
    public void setImageKost() {
        panelFull.setImageFile("image/begin_fon.jpg");
    }
}