package frame;

import auxiliary.Auxiliary;
import data.ForconsList;
import data.MainData;
import elements.*;
import elements.skills.SkillsPanel;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MainFrame extends JFrame {

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private static final ImagePanel panelFull = new ImagePanel("image/begin_fon.jpg",false);
    private final TableNoGaps tableNoGaps = new TableNoGaps(0,35,1035,580);

    private static JFileChooser fileChooser = null;
    private static JButton cancelButton;


    private MainFrame() {
        MainData.setTableNoGaps(tableNoGaps);

        panelFull.setSize(WIDTH,HEIGHT);
        panelFull.setLayout(null);

        DownElementsPanel.addIn(panelFull,0,0);
        DownElementsPanel.setVisible(false);
        UpElementsPanel.addIn(panelFull,0,0);
        UpElementsPanel.setVisible(false);

        addTableNoGaps();

        cancelButton = addCancelButton();

        addOpenButton(addOpenPanel(), addForconsListScroll());

        getContentPane().add(panelFull);
    }

    private JButton addCancelButton() {
        JButton cancelButton = Auxiliary.addButton(panelFull,25,25,1250,5);
        cancelButton.addActionListener(ev -> {
            tableNoGaps.stopThread();
            dispose();
        });
        return cancelButton;
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
        JScrollPane forconsListScroll = new JScrollPane(ForconsList.getList());
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
            ForconsList.read(selectionFile("Открыть форсонов"));
            openButton.setVisible(false);
            openPanel.setVisible(false);
            tableNoGaps.setVisible(true);
            UpElementsPanel.setVisible(true);
            cancelButton.setVisible(true);
            scrollPane.setVisible(true);
            tableNoGaps.startThread();
            panelFull.setImageFile("image/fon2.jpg");
            ForconsList.checkTsundere();
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