package graphics;

import java.awt.Color;
import java.awt.Rectangle;

import objects.BackgroundObject;
import objects.GameObject;
import objects.ObjectID;

public class Background extends GameObject {

    // CONSTANTES
    private final int SCREEN_OFFSET;

    // VARIABLES
    private Camera camara;

    public Background(int x, int y, int width, int height, int SCREEN_OFFSET, Camera camara) {
        super(x, y, ObjectID.Background, width + 20, height, 1);
        this.SCREEN_OFFSET = SCREEN_OFFSET;
        this.camara = camara;
    }

    @Override
    public void tick() {
        if (camara != null) {
            this.setPosX((int) (-camara.getPosX() - 10));
        }
    }

    @Override
    public void render(GraphicsLibrary g) {
        g.fillRectangle((int) getPosX(), (int) getPosY() - SCREEN_OFFSET, (int) (getWidth() + getPosX()), (int) getHeight(), new Color(45, 43, 99));
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) (getPosX()), (int) (getPosY()), (int) (getWidth()), (int) (getHeight()));
    }

    @Override
    public GameObject clone() {
        return new BackgroundObject((int) posX, (int) posY, (int) width, (int) height, 1,"Cloud");
    }
}
