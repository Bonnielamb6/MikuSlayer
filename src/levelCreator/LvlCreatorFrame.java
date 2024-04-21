package levelCreator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LvlCreatorFrame extends JFrame {
    private static LvlCreator canvas;
    private final int WIDTH = 1000;
    private final int HEIGHT = 600;
    JMenuBar menuBar = new JMenuBar();

    public LvlCreatorFrame() {
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        initComponents();
        setVisible(true);
    }

    public void initComponents() {
        canvas = new LvlCreator(500, 15);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LvlCreatorFrame frame = new LvlCreatorFrame();
        });
    }
}
