package imageCreator;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ImgCreatorFrame extends JFrame {
    private final int WIDTH = 790;
    private final int HEIGHT = 830;
    private static ImgCreator canvas;
    private static ImgCreatorFrame frame;

    JMenuBar menuBar = new JMenuBar();
    JColorChooser colorChooser = new JColorChooser();

    public ImgCreatorFrame(int rows, int columns) {
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        initComponents();
        setVisible(true);
    }

    public void initComponents() {
        canvas = new ImgCreator(32, 32);
        add(canvas);
        setJMenuBar(menuBar);
        //ADD of the menus
        JMenu fileMenu = new JMenu("File");
        JMenu colorMenu = new JMenu("Color");

        JMenuItem newSprite = new JMenuItem("New");
        JMenuItem saveSprite = new JMenuItem("Save");
        JMenuItem loadSprite = new JMenuItem("Load");

        newSprite.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int tempRows = Integer.parseInt(JOptionPane.showInputDialog("Number of rows"));
                int tempColumns = Integer.parseInt(JOptionPane.showInputDialog("Number of columns"));
                resetCanvas(tempRows,tempColumns);
            }
        });
        saveSprite.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fileName = JOptionPane.showInputDialog("File name");
                canvas.saveFile(fileName);
            }
        });
        loadSprite.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fileName = JOptionPane.showInputDialog("File name");
                canvas.loadFile(fileName);
            }
        });

        fileMenu.add(newSprite);
        fileMenu.add(saveSprite);
        fileMenu.add(loadSprite);
        colorMenu.add(colorChooser);
        menuBar.add(fileMenu);
        menuBar.add(colorMenu);
        colorChooser.getSelectionModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                canvas.setColor(colorChooser.getColor());
            }
        });

    }

    private static void resetCanvas(int tempRows,int tempColumns) {
        canvas = new ImgCreator(tempRows, tempColumns);
        frame.getContentPane().removeAll();
        frame.getContentPane().add(canvas);
        frame.repaint();
        frame.revalidate();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            frame = new ImgCreatorFrame(32, 32);

        });
    }
}
