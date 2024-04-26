package main;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    public Window(int WIDHT, int HEIGHT, String title) {
        setSize(WIDHT, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle(title);
        setResizable(true);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void setCanvas(Canvas canvas) {
        getContentPane().removeAll();
        add(canvas);
        repaint();
        revalidate();

    }
}
