package objects;

import graphics.GraphicsLibrary;
import graphics.Textures;
import java.awt.*;

public class BackgroundObject extends GameObject {
    String objectName;

    public BackgroundObject(int posX, int posY, int width, int height, int movedX, String objectName) {
        super(posX, posY, ObjectID.Background, width, height, movedX);
        this.objectName = objectName;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(GraphicsLibrary g) {
        g.drawImage(Textures.getTexture(objectName), (int) getPosX(), (int) getPosY());
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) (getPosX()), (int) (getPosY()), (int) (getWidth()), (int) (getHeight()));

    }

    @Override
    public GameObject clone() {
        return new BackgroundObject((int) posX, (int) posY, (int) width, (int) height, (int) movedX, objectName);
    }
}
