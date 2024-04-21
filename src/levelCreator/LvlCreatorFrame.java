package levelCreator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Map;

import graphics.Textures;
import tools.textureReader;

public class LvlCreatorFrame extends JFrame {
    private static LvlCreator canvas;
    private final int WIDTH = 1000;
    private final int HEIGHT = 600;
    JMenuBar menuBar = new JMenuBar();

    public LvlCreatorFrame() {
        initComponents();
        initObjectsBar();
        setVisible(true);
    }

    public void initComponents() {
        mainPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1200, 620));
        setName("Level Creator");
        setResizable(false);
        getContentPane().setLayout(null);

        mainPanel.setLayout(null);

        texturesPanel = new javax.swing.JPanel();
        JScrollPane texturesPanelScroll = new JScrollPane();

        texturesPanelScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        texturesPanelScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        texturesPanelScroll.getHorizontalScrollBar().setUnitIncrement(20);

        texturesPanel.setLayout(new BoxLayout(texturesPanel, BoxLayout.Y_AXIS));

        texturesPanelScroll.setViewportView(texturesPanel);
        mainPanel.add(texturesPanelScroll);
        texturesPanelScroll.setBounds(10,40,550,180);
        getContentPane().add(mainPanel);
        mainPanel.setBounds(1000, 0, 200, 600);


        canvas = new LvlCreator(15, 500);
        canvas.setSize(800, 400);
        add(canvas);
        setJMenuBar(menuBar);
        //ADD of the menus
        JMenu fileMenu = new JMenu("File");

        JMenuItem newSprite = new JMenuItem("New");
        JMenuItem saveSprite = new JMenuItem("Save");
        JMenuItem loadSprite = new JMenuItem("Load");

        newSprite.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int tempRows = Integer.parseInt(JOptionPane.showInputDialog("Number of rows"));
                int tempColumns = Integer.parseInt(JOptionPane.showInputDialog("Number of columns"));
                //resetCanvas(tempRows, tempColumns);
            }
        });
        saveSprite.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fileName = JOptionPane.showInputDialog("File name");
                //canvas.saveFile(fileName);
            }
        });
        loadSprite.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fileName = JOptionPane.showInputDialog("File name");
                //canvas.loadFile(fileName);
            }
        });

        fileMenu.add(newSprite);
        fileMenu.add(saveSprite);
        fileMenu.add(loadSprite);
        menuBar.add(fileMenu);


    }

    private void initObjectsBar() {
        for (Map.Entry<String, BufferedImage> element : Textures.getTexturesMap().entrySet()) {
            String key = element.getKey();
            BufferedImage imgElemento = element.getValue();

            // Se crea el elemento para la barra y se asigna su interfaz
            ElementPanel panelElementoTemp = new ElementPanel(imgElemento, key, canvas);
            texturesPanel.add(panelElementoTemp);
        }

    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LvlCreatorFrame frame = new LvlCreatorFrame();
        });
    }

    private javax.swing.JPanel texturesPanel;
    private javax.swing.JPanel mainPanel;
}
