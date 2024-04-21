package imageCreator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class ImgCreator extends JPanel {

    private int WIDTH;
    private int HEIGHT;
    private int ROWS;
    private int COLUMNS;
    private int PIXEL_SIZE;

    private Color[][] pixels;
    private Color currentColor;
    private Boolean leftClick = false;
    private Boolean rightClick = false;
    private Boolean middleClick = false;
    private static BufferedImage buffer;

    public ImgCreator(int r, int c) {
        SwingUtilities.invokeLater(() -> {
            pixelSize(r, c);
            ROWS = r;
            COLUMNS = c;
            setSize(WIDTH, HEIGHT);
            //setBackground(Color.red);
            //PIXEL_SIZE = 50;

            initPixels();
            initEvents();
        });
    }

    public void pixelSize(int rows, int columns) {
        HEIGHT = 770;
        WIDTH = 776;
        ROWS = rows;
        COLUMNS = columns;
        currentColor = Color.BLACK;

        int maxRowsColumns = Math.max(rows, columns);
        int maxWidthHeight = Math.max(HEIGHT, WIDTH);
        PIXEL_SIZE = maxWidthHeight / maxRowsColumns;

        HEIGHT = PIXEL_SIZE * ROWS;
        WIDTH = PIXEL_SIZE * COLUMNS;

        this.buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);

        int newX = (getParent().getWidth() - WIDTH) / 2;
        int newY = (getParent().getHeight() - HEIGHT) / 2;
        setSize(new Dimension(WIDTH, HEIGHT));
        setLocation(newX, newY);
    }

    public void initPixels() {
        pixels = new Color[ROWS][COLUMNS];

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                pixels[i][j] = new Color(0, 0, 0, 0);
            }
        }

    }

    public void initEvents() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    leftClick = true;
                } else if (e.getButton() == MouseEvent.BUTTON2) {
                    middleClick = true;
                    int x = e.getX() / PIXEL_SIZE;
                    int y = e.getY() / PIXEL_SIZE;
                    Color clickC = pixels[x][y];
                    fillPaint(x, y, clickC);
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    rightClick = true;
                }
                updatePixel(e);

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    leftClick = false;
                } else if (e.getButton() == MouseEvent.BUTTON2) {
                    middleClick = false;
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    rightClick = false;
                }
            }
        });
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                updatePixel(e);
            }
        });
    }

    private void updatePixel(MouseEvent e) {
        int row = e.getX() / PIXEL_SIZE;
        int column = e.getY() / PIXEL_SIZE;
        if (row >= 0 && row < ROWS && column >= 0 && column < COLUMNS) {
            if (leftClick) {
                pixels[row][column] = currentColor;
            } else if (rightClick) {
                pixels[row][column] = new Color(0, 0, 0, 0);
            }
        }
        repaint();
    }

    private void fillPaint(int x, int y, Color clickC) {
        if (pixels[y][x].equals(clickC) && !pixels[y][x].equals(currentColor)) {
            pixels[y][x] = currentColor;
            if (x > 0) {
                fillPaint(x - 1, y, clickC);
            }
            if (y > 0) {
                fillPaint(x, y - 1, clickC);
            }
            if (x < COLUMNS - 1) {
                fillPaint(x + 1, y, clickC);
            }
            if (y < ROWS - 1) {
                fillPaint(x, y + 1, clickC);
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        paintPixels();
        paintMail();
        g.drawImage(buffer, 0, 0, null);
    }

    public void paintPixels() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                int x = i * PIXEL_SIZE;
                int y = j * PIXEL_SIZE;
                fillRectangle(x, y, x + PIXEL_SIZE, y + PIXEL_SIZE, pixels[i][j]);
            }
        }
    }

    public void paintMail() {
        for (int i = 0; i < ROWS; i++) {
            drawLine(0, i * PIXEL_SIZE, pixels.length * PIXEL_SIZE, i * PIXEL_SIZE, Color.black);
        }
        for (int j = 0; j < COLUMNS; j++) {
            drawLine(j * PIXEL_SIZE, 0, j * PIXEL_SIZE, pixels.length * PIXEL_SIZE, Color.black);
        }
        drawLine(0, ROWS * PIXEL_SIZE - 1, WIDTH, ROWS * PIXEL_SIZE - 1, Color.black);
        drawLine(COLUMNS * PIXEL_SIZE - 1, 0, COLUMNS * PIXEL_SIZE - 1, HEIGHT, Color.black);

    }

    public void fillRectangle(int x0, int y0, int x1, int y1, Color c) {
        int nx0 = Math.min(x0, x1);
        int ny0 = Math.min(y0, y1);
        int nx1 = Math.max(x0, x1);
        int ny1 = Math.max(y0, y1);

        for (int y = ny0; y <= ny1; y++) {
            int beginningX = -1;
            for (int x = nx0; x <= nx1; x++) {
                if (beginningX == -1) {
                    beginningX = x;
                }
                if (beginningX != -1) {
                    putPixel(beginningX, y, c);
                    beginningX = -1;
                }
            }
        }
    }

    public void drawLine(int x0, int y0, int x1, int y1, Color c) {
        int dx = x1 - x0;
        int dy = y1 - y0;

        int steps = Math.max(Math.abs(dx), Math.abs(dy));

        float xInc = (float) dx / steps;
        float yInc = (float) dy / steps;

        float x = x0;
        float y = y0;

        putPixel(Math.round(x), Math.round(y), c);
        for (int i = 0; i <= steps; i++) {
            x += xInc;
            y += yInc;
            putPixel(Math.round(x), Math.round(y), c);
        }
    }

    private void putPixel(int x, int y, Color c) {
        if (x >= 0 && x < buffer.getWidth() && y >= 0 && y < buffer.getHeight()) {
            buffer.setRGB(x, y, c.getRGB());
        }
    }

    public void saveFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("Color and coordinates: " + fileName + ".txt\n\n");
            writer.write("Dimensions: \n" + pixels.length + "," + pixels[0].length + "\n\n");

            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLUMNS; j++) {
                    Color c = pixels[i][j];
                    int red = c.getRed();
                    int blue = c.getBlue();
                    int green = c.getGreen();
                    int alpha = c.getAlpha();
                    writer.write("Color: \n" + red + "," + green + "," + blue + "," + alpha + "\n\n");
                    writer.write("Coordinate: \n" + i + "," + j + "\n");
                }
            }
            System.out.println("File created");
        } catch (IOException e) {
            System.out.println("There was a problem creating your file");
        }
    }

    public void loadFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String currentLine;
            Color color = null;
            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.equals("Dimensions: ")) {
                    currentLine = reader.readLine();
                    String[] size = currentLine.split(",");
                    int tempRows = Integer.parseInt(size[0]);
                    int tempColumns = Integer.parseInt(size[1]);
                    pixels = new Color[tempRows][tempColumns];
                    pixelSize(tempRows, tempColumns);
                    this.getParent().repaint();
                    this.getParent().revalidate();
                }
                if (currentLine.equals("Color: ")) {
                    currentLine = reader.readLine();
                    String[] tempColor = currentLine.split(",");
                    int red = Integer.parseInt(tempColor[0]);
                    int green = Integer.parseInt(tempColor[1]);
                    int blue = Integer.parseInt(tempColor[2]);
                    int alpha = Integer.parseInt(tempColor[3]);
                    color = new Color(red, green, blue, alpha);

                }
                if (currentLine.equals("Coordinate: ")) {
                    currentLine = reader.readLine();
                    String[] coordinate = currentLine.split(",");
                    int tempRow = Integer.parseInt(coordinate[0]);
                    int tempColumn = Integer.parseInt(coordinate[1]);
                    pixels[tempRow][tempColumn] = color;
                }
            }
            repaint();
            System.out.println("File loaded succesfully");
        } catch (IOException e) {
            System.out.println("There was a problem loading your file");
        }
    }

    public void setColor(Color c) {
        currentColor = c;
    }
}
