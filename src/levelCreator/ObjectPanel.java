package levelCreator;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class ObjectPanel extends JPanel {
    private final int WIDTH = 125;
    private final int HEIGHT = 70;

    public ObjectPanel(BufferedImage objectImg){
        setLocation(8,20);
        setSize(125,70);
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);

    }
}
