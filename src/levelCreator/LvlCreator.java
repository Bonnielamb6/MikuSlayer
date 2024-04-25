package levelCreator;


import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.*;

import graphics.GraphicsLibrary;
import graphics.Textures;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class LvlCreator extends JPanel implements InterfaceLvlCreator {
    private int ROWS;
    private int COLUMNS;
    private int PIXEL_SIZE = 16;
    private int WIDTH = 750;
    private int HEIGHT = 600;
    private Color currentColor;
    private Boolean leftClick = false;
    private Boolean rightClick = false;
    private Boolean middleClick = false;
    private static BufferedImage buffer;
    private GraphicsLibrary backgroundGraphics;
    private GraphicsLibrary objectsGraphics;
    private Boolean isMoving;
    private Boolean isErasing = false;
    private float velocity;
    private float zoom;
    private Point lastPosition;
    private Point offsetPosition;
    private String selectedObject;
    private ObjectSquare[][] objects;


    public LvlCreator(int r, int c) {
        SwingUtilities.invokeLater(() -> {
            setBackground(Color.white);
            ROWS = r;
            COLUMNS = c;
            System.out.println(getWidth());
            System.out.println(getHeight());

            backgroundGraphics = new GraphicsLibrary(getWidth(), getHeight());
            objectsGraphics = new GraphicsLibrary(getWidth(), getHeight());

            velocity = 1f;
            zoom = 1f;
            isMoving = false;
            lastPosition = new Point(0, 0);
            offsetPosition = new Point();
            selectedObject = "objectFloor";

            clearMatrix(r, c);
            initEvents();
        });
    }

    @Override
    public void selectedObject(String object) {
        selectedObject = object;
    }

    public void clearMatrix(int rows, int columns) {
        COLUMNS = columns;
        ROWS = rows;
        objects = new ObjectSquare[COLUMNS][ROWS];
        for (int i = 0; i < objects.length; i++) {
            for (int j = 0; j < objects[0].length; j++) {
                objects[i][j] = new ObjectSquare();
            }
        }
        repaint();
    }

    public void initEvents() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // MOVING
                if (isMoving) {
                    offsetPosition.x = e.getX();
                    offsetPosition.y = e.getY();
                }

                // PAINTING
                if (!isMoving) {
                    int NEW_TAMANO_CELDA = (int) (PIXEL_SIZE * zoom);
                    int xClick = (int) ((e.getX() - lastPosition.x) / NEW_TAMANO_CELDA);
                    int yClick = (int) ((e.getY() - lastPosition.y) / NEW_TAMANO_CELDA);

                    if (!(xClick >= 0 && yClick >= 0 && xClick < COLUMNS && yClick < ROWS)) {
                        return;
                    }
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        isErasing = true;
                    }
                    updatePixel(xClick, yClick);
                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    isErasing = false;
                }
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                // MOVING
                if (isMoving) {
                    int dx = (int) ((e.getX() - offsetPosition.x) * velocity);
                    int dy = (int) ((e.getY() - offsetPosition.y) * velocity);

                    offsetPosition.x = e.getX();
                    offsetPosition.y = e.getY();

                    lastPosition.x += dx;
                    lastPosition.y += dy;
                }

                // PAINTING
                if (!isMoving) {
                    int NEW_TAMANO_CELDA = (int) (PIXEL_SIZE * zoom);
                    int xClick = (int) ((e.getX() - lastPosition.x) / NEW_TAMANO_CELDA);
                    int yClick = (int) ((e.getY() - lastPosition.y) / NEW_TAMANO_CELDA);

                    if (!(xClick >= 0 && yClick >= 0 && xClick < COLUMNS && yClick < ROWS)) {
                        return;
                    }

                    if (e.getButton() == MouseEvent.BUTTON3) {
                        isErasing = true;
                    }
                    updatePixel(xClick, yClick);
                }
                repaint();
            }

        });

        addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.getWheelRotation() < 0) {
                    zoom += 0.1f;
                    if (zoom >= 2f) {
                        zoom = 2f;
                    }
                } else {
                    zoom -= 0.1f;
                    if (zoom <= 0.4f) {
                        zoom = 0.4f;
                    }
                }
                repaint();
            }
        });
    }

    private void updatePixel(int xClick, int yClick) {
        if (!isErasing) {
            addObject(xClick, yClick);
        } else {
            deleteObject(xClick, yClick);
        }
    }

    private void deleteObject(int xClick, int yClick) {
        objects[xClick][yClick].setObjectImage(null);
        objects[xClick][yClick].setObjectName(null);
        objects[xClick][yClick].setX(xClick);
        objects[xClick][yClick].setY(yClick);
    }

    private void addObject(int xClick, int yClick) {
        objects[xClick][yClick].setObjectImage(Textures.getTexture(selectedObject));
        objects[xClick][yClick].setObjectName(selectedObject);
        objects[xClick][yClick].setX(xClick);
        objects[xClick][yClick].setY(yClick);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        backgroundGraphics.clearBuffer();
        objectsGraphics.clearBuffer();

        for (int i = 0; i < objects.length; i++) {
            for (int j = 0; j < objects[0].length; j++) {
                int newPixelSize = (int) (PIXEL_SIZE * zoom);
                int x = i * newPixelSize + lastPosition.x;
                int y = j * newPixelSize + lastPosition.y;

                if (x + newPixelSize > 0 && y + newPixelSize > 0 && x < getWidth() && y < getWidth()) {
                    backgroundGraphics.fillRectangle(x, y, x + newPixelSize, y + newPixelSize, Color.white);
                    backgroundGraphics.drawRect(x, y, x + newPixelSize, y + newPixelSize, Color.black);

                    if (objects[i][j].getObjectImage() != null) {
                        int newWidth = (int) (objects[i][j].getObjectImage().getWidth() * zoom);
                        int newHeight = (int) (objects[i][j].getObjectImage().getHeight() * zoom);
                        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, objects[i][j].getObjectImage().getType());

                        Graphics2D g2d = resizedImage.createGraphics();
                        g2d.drawImage(objects[i][j].getObjectImage(), 0, 0, newWidth, newHeight, null);
                        g2d.dispose();

                        objectsGraphics.drawImage(resizedImage, x, y);
                    }
                }
            }
        }
        g.drawImage(backgroundGraphics.getBuffer(), 0, 0, null);
        g.drawImage(objectsGraphics.getBuffer(), 0, 0, null);
    }

    public void center() {
        lastPosition.setLocation(0, 0);
        zoom = 1f;
        repaint();
    }

    public void saveLevel(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("Level coordinates: " + fileName + ".txt\n\n\n");
            writer.write("Dimensions: \n" + objects.length + "," + objects[0].length + "\n\n\n");
            StringBuilder content = new StringBuilder();
            for (ObjectSquare[] objectsArray : objects) {
                for (ObjectSquare tempObject : objectsArray) {
                    if (tempObject.getObjectImage() != null) {
                        int xObject = tempObject.getX();
                        int yObject = tempObject.getY();
                        content.append("Coordinates: \n").append(xObject).append(",").append(yObject).append("\n");
                        content.append("Object: \n").append(tempObject.getObjectName()).append("\n\n\n");
                    }
                }
            }
            writer.write(content.toString());
            System.out.println("File saved");
        } catch (IOException e) {
            System.out.println("There was a problem saving your file" + e);
        }
    }

    public void loadLevel(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.equals("Dimensions: ")) {
                    System.out.println("Dimensions");
                    currentLine = reader.readLine();
                    String[] size = currentLine.split(",");
                    int tempRows = Integer.parseInt(size[0]);
                    int tempColumns = Integer.parseInt(size[1]);

                    objects = new ObjectSquare[tempRows][tempColumns];
                    for (int i = 0; i < tempRows; i++) {
                        for (int j = 0; j < tempColumns; j++) {
                            objects[i][j] = new ObjectSquare();
                        }
                    }
                }
                if (currentLine.equals("Coordinates: ")) {
                    System.out.println("Coordinates");
                    currentLine = reader.readLine();
                    String[] position = currentLine.split(",");
                    int xObject = Integer.parseInt(position[0]);
                    int yObject = Integer.parseInt(position[1]);

                    currentLine = reader.readLine();
                    currentLine = reader.readLine();

                    System.out.println(currentLine);
                    String tempObjectName = currentLine;
                    ObjectSquare tempObject = new ObjectSquare();
                    tempObject.setX(xObject);
                    tempObject.setY(yObject);
                    tempObject.setObjectName(tempObjectName);
                    tempObject.setObjectImage(Textures.getTexture(tempObjectName));
                    //TODO Maybe have to swap these
                    objects[xObject][yObject] = tempObject;

                }
            }
            System.out.println("File loaded");
            repaint();
        } catch (IOException e) {
            System.out.println("There was a problem loading your file");
        }

    }

    public static ObjectSquare[][] loadLevelMatrix(
            String fileName) {

        ObjectSquare[][] objects = null;
        String currentLine;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.equals("Coordinates: ")) {
                    currentLine = reader.readLine();
                    String[] size = currentLine.split(",");

                    int xObject = Integer.parseInt(size[0]);
                    int yObject = Integer.parseInt(size[1]);

                    currentLine = reader.readLine(); // Leer siguiente linea (Elemento:)
                    String name = reader.readLine();

                    // Crear la object temporal y agregarla a la Matriz
                    ObjectSquare object = new ObjectSquare();
                    object.setX(xObject);
                    object.setY(yObject);

                    object.setObjectName(name);
                    object.setObjectImage(Textures.getTexture(name));
                    objects[xObject][yObject] = object;
                }

                // Se ejecuta 1 vez para inicializar las dimensiones de la matriz
                if (currentLine.equals("Dimensions: ")) {
                    currentLine = reader.readLine();
                    String[] size = currentLine.split(",");

                    int xObject = Integer.parseInt(size[0]);
                    int yObject = Integer.parseInt(size[1]);

                    objects = new ObjectSquare[xObject][yObject];
                    for (int i = 0; i < objects.length; i++) {
                        for (int j = 0; j < objects[0].length; j++) {
                            objects[i][j] = new ObjectSquare();
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("There was a problem loading your file: " + e.getMessage());
        }

        return objects;
    }

    public void setVelocity(float v) {
        velocity = v;
    }

    public void setZoom(float z) {
        zoom = z;
    }

    public void setIsMoving(Boolean moving) {
        isMoving = moving;
    }

    public ObjectSquare[][] getObjects() {
        return objects;
    }

    public void setObjects(ObjectSquare[][] objectsTemp) {
        objects = objectsTemp;
        repaint();
    }
}
