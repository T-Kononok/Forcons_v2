package frame;

import auxiliary.Auxiliary;
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
    private static final ImagePanel panelFull = new ImagePanel("image/begin_fon.jpg",false);
    private final TableNoGaps tableNoGaps = new TableNoGaps(0,35,1035,580);
    private static final ForconsList forconsList = new ForconsList();
    private final UpElementsPanel upElementsPanel = new UpElementsPanel(this);
    private static final DownElementsPanel downElementsPanel = new DownElementsPanel(forconsList);

    private static JFileChooser fileChooser = null;


    private MainFrame() {
        MainData.setMainFrame(this);

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

    private static ImagePanel addOpenPanel() {
        ImagePanel openPanel = new ImagePanel("image/begin_fon_highlighted.png",false);
        Auxiliary.toPlace(panelFull,openPanel,WIDTH,HEIGHT,0,0);
        openPanel.setVisible(false);
        openPanel.setOpaque(false);
        return openPanel;
    }

    private void addTableNoGaps() {
        tableNoGaps.addIn(panelFull);
        tableNoGaps.setVisible(false);
    }

    private static JScrollPane addForconsListScroll() {
        JScrollPane forconsListScroll = new JScrollPane(forconsList.getList());
        Auxiliary.toPlace(panelFull,forconsListScroll,220,585,1060,35);
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
        Auxiliary.toPlace(panelFull,openButton,210,235, 537,260);
        openButton.setPanel(openPanel);
        openButton.setMessageImage(addBeginMessageImage());
        openButton.addActionListener(ev -> {
            MainData.readTable(selectionFile("Открыть жунал"));
//            MainData.readTable("D:\\Джава\\Forcons_v2\\10_FM-3.xls");
            forconsList.read(selectionFile("Открыть форсонов"));
            openButton.setVisible(false);
            openPanel.setVisible(false);
            tableNoGaps.setVisible(true);
            upElementsPanel.setVisible(true);
            scrollPane.setVisible(true);
            tableNoGaps.startThread();
            panelFull.setImageFile("image/fon2.jpg");
            forconsList.checkTsundere();
        });
    }

    private static ImagePanel addBeginMessageImage() {
        ImagePanel messageImage = new ImagePanel("image/begin_fon_message.png",true,false);
        Auxiliary.toPlace(panelFull,messageImage,450,250,720,70);
        return messageImage;
    }

    private static String selectionFile(String string) {
        if (fileChooser==null) {
            fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
        }
        fileChooser.setDialogTitle(string);
        if (fileChooser.showOpenDialog(panelFull) == JFileChooser.APPROVE_OPTION) {
//            System.out.println(fileChooser.getSelectedFile().getPath());
            return fileChooser.getSelectedFile().getPath();
        }
        return null;
    }

    public TableNoGaps getTableNoGaps() {
        return tableNoGaps;
    }

    public static DownElementsPanel getDownElementsPanel() {
        return downElementsPanel;
    }

    public static ForconsList getForconsList() {
        return forconsList;
    }

    public UpElementsPanel getUpElementsPanel() {
        return upElementsPanel;
    }

//    public UpElementsPanel getUpElementsPanel() {
//        return upElementsPanel;
//    }

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();

        frame.setUndecorated(true);
        frame.setExtendedState(MAXIMIZED_BOTH);
        frame.setVisible(true);
        //костыль дабы не было иногда прозрачного экрана
        setImageKost();
    }

    //костыль дабы не было иногда прозрачного экрана
    public static void setImageKost() {
        panelFull.setImageFile("image/begin_fon.jpg");
    }
}