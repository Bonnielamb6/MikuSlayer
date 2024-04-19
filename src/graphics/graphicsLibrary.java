package graphics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class graphicsLibrary extends Canvas {
    private final int WIDTH;
    private final int HEIGHT;
    private BufferedImage buffer;
    private int moveX = 0;
    private int moveY = 0;

    public graphicsLibrary(int w, int h) {
        WIDTH = w;
        HEIGHT = h;
        setSize(WIDTH, HEIGHT);
        buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
    }

    public void fillRectangle(int x0, int y0, int x1, int y1, Color c) {
        x0 += moveX;
        y0 += moveY;
        x1 += moveX;
        y1 += moveY;

        int nx0 = Math.min(x0, x1);
        int ny0 = Math.min(y0, y1);
        int nx1 = Math.max(x0, x1);
        int ny1 = Math.max(y0, y1);

        for (int y = ny0; y <= ny1; y++) {
            int xInicio = -1;
            for (int x = nx0; x <= nx1; x++) {
                if (xInicio == -1) {
                    xInicio = x;
                }
                if (xInicio != -1) {
                    putPixel(xInicio, y, c);
                    xInicio = -1;
                }
            }
        }
    }

    public void drawRectangle(Rectangle rec, Color c) {
        drawLine(rec.x, rec.y, rec.x + rec.width, rec.y, c);
        drawLine(rec.x + rec.width, rec.y, rec.x + rec.width, rec.y + rec.height, c);
        drawLine(rec.x + rec.width, rec.y + rec.height, rec.x, rec.y + rec.height, c);
        drawLine(rec.x, rec.y + rec.height, rec.x, rec.y, c);
    }

    public void drawRect(int x0, int y0, int x1, int y1, Color c) {
        drawLine(x0, y0, x1, y0, c);
        drawLine(x0, y1, x1, y1, c);
        drawLine(x0, y0, x0, y1, c);
        drawLine(x1, y0, x1, y1, c);
    }

    public void drawLine(int x0, int y0, int x1, int y1, Color c) {
        x0 += moveX;
        y0 += moveY;
        x1 += moveX;
        y1 += moveY;

        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;
        int err = dx - dy;

        while (true) {
            putPixel(x0, y0, c);

            if (x0 == x1 && y0 == y1) {
                break;
            }

            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x0 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y0 += sy;
            }
        }
    }

    public void drawImage(BufferedImage img, int x, int y) {
        Graphics g = buffer.getGraphics();
        g.drawImage(img, x + moveX, y + moveY, null);
    }

    public void drawImage(BufferedImage img, int x, int y, int width, int height) {
        Graphics g = buffer.getGraphics();
        g.drawImage(img, x + moveX, y + moveY, width, height, null);
    }

    private void putPixel(int x, int y, Color color) {
        if (x >= 0 && x < buffer.getWidth() && y >= 0 && y < buffer.getHeight()) {
            buffer.setRGB(x, y, color.getRGB());
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(buffer, 0, 0, this);
    }

    public BufferedImage getBuffer() {
        return buffer;
    }

    public void move(int x, int y) {
        moveX = x;
        moveY = y;
    }

    public void clearBuffer() {
        buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
    }
}
