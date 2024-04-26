package objects;

import graphics.GraphicsLibrary;
import graphics.Textures;

import java.awt.*;

public class SolidBlock extends GameObject {

    private Textures texture;
    String objectName;

    public SolidBlock(int posX, int posY, int width, int height, int movedX, String objectName) {
        super(posX, posY, ObjectID.Floor, width, height, movedX);
        this.objectName = objectName;
        texture = new Textures();
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(GraphicsLibrary g) {
        g.drawImage(Textures.getTexture(objectName), (int) (getPosX()), (int) (getPosY()));
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) (getPosX()), (int) (getPosY()), (int) (getWidth()), (int) (getHeight()));
    }

    @Override
    public GameObject clone() {
        return new SolidBlock((int) posX, (int) posY, (int) width, (int) height, (int) movedX, objectName);
    }
}
