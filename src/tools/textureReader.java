package tools;

import graphics.GraphicsLibrary;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class textureReader {

    private GraphicsLibrary graphics;
    private int WIDTH;
    private int HEIGHT;
    private int ROWS;
    private int COLUMNS;
    private Color[][] pixels;
    private int pixelSize;

    public textureReader(int size) {
        pixelSize = size;
    }

    public void loadMatrix(String fileNameTemp) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileNameTemp))) {
            String currentLine;
            Color color = null;
            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.equals("Dimensions: ")) {
                    currentLine = reader.readLine();
                    String[] size = currentLine.split(",");
                    ROWS = Integer.parseInt(size[0]);
                    COLUMNS = Integer.parseInt(size[1]);
                    pixels = new Color[ROWS][COLUMNS];
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
        } catch (IOException e) {
            System.out.println("There was a problem loading your file" + e.getMessage());
        }

    }

    public BufferedImage drawObject(String name) {
        loadMatrix(name);
        WIDTH = COLUMNS * pixelSize;
        HEIGHT = ROWS * pixelSize;

        graphics = new GraphicsLibrary(WIDTH, HEIGHT);

        int tempPixelSize = WIDTH / pixels[0].length;
        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[0].length; j++) {
                int x = j * tempPixelSize;
                int y = i * tempPixelSize;
                graphics.fillRectangle(x, y, x + tempPixelSize, y + tempPixelSize, pixels[j][i]);
            }
        }

        return graphics.getBuffer();
    }
}
