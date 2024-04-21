package levelCreator;

import java.awt.image.BufferedImage;

public class ObjectSquare {
    private String objectName;
    private int x;
    private int y;
    private BufferedImage objectImage;

    public ObjectSquare() {
        objectName = "";
        x = 0;
        y = 0;
        objectImage = null;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public BufferedImage getObjectImage() {
        return objectImage;
    }

    public void setObjectImage(BufferedImage objectImage) {
        this.objectImage = objectImage;
    }
}
