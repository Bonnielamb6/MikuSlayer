package levelCreator;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.*;


public class ElementPanel extends JPanel {
    private javax.swing.JLabel lblElementName;
    private final BufferedImage OBJECT_IMAGE;
    private final String OBJECT_NAME;
    private final int OBJECT_WIDTH;
    private final int OBJECT_HEIGHT;
    private InterfaceLvlCreator clickListener;

    public ElementPanel(BufferedImage objectImg, String objectName, InterfaceLvlCreator clickListener) {
        initComponents();
        initEvents();

        OBJECT_IMAGE = objectImg;
        OBJECT_NAME = objectName;
        OBJECT_WIDTH = objectImg.getWidth();
        OBJECT_HEIGHT = objectImg.getHeight();
        this.clickListener = clickListener;

        setSize(140, 100);
        add(new ObjectPanel(OBJECT_IMAGE));

        lblElementName.setText(OBJECT_NAME + "-" + (OBJECT_WIDTH / 2) + "x" + (OBJECT_HEIGHT / 2));

    }

    public void initEvents() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                clickListener.selectedObject(OBJECT_NAME);
            }
        });
    }

    public void initComponents() {
        lblElementName = new JLabel();

        setBackground(Color.white);
        setLayout(null);

        lblElementName.setBackground(Color.BLACK);
        lblElementName.setForeground(Color.BLACK);
        lblElementName.setHorizontalAlignment(SwingConstants.CENTER);
        lblElementName.setText("SolidBlock block");
        add(lblElementName);
        lblElementName.setBounds(0, 0, 160, 17);
    }
}
