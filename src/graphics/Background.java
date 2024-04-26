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
        g.fillRectangle((int) getPosX(), (int) getPosY() - SCREEN_OFFSET, (int) (getWidth() + getPosX()), (int) getHeight(), new Color(111, 133, 255));

        // Mostrar cuadricula
//        for (int i = 0; i < getWidth() / 32; i++) {
//            g.drawLine((int) (getX() + i * 32), (int) getY(), (int) (getX() + i * 32), (int) (getY() + getHeight()), new Color(111, 99, 255));
//        }
//        for (int i = 0; i < getHeight() / 32; i++) {
//            g.drawLine((int) getX(), (int) (getY() + i * 32), (int) (getX() + getWidth()), (int) (getY() + i * 32), new Color(111, 99, 255));
//        }
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
