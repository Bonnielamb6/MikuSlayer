package objects;

import graphics.GraphicsLibrary;
import graphics.Textures;
import tools.Handler;

import java.awt.*;

public class Enemy extends GameObject {
    private String objectName;
    private Handler handler;
    private boolean alive = true;

    public Enemy(float posX, float posY, String objectName) {
        super(posX, posY, ObjectID.Enemy, 64, 64, 0);
        this.objectName = objectName;
        setVelX(1);
    }

    public void collisions() {
//        for (GameObject temp : handler.getGameObjects()) {
//            switch (temp.getId()) {
//                case Block:
//                case Floor:
//                    solidColision(temp);
//                    break;
//                case Bullet:
//                    setAlive(false);
//                default:
//                    break;
//            }
//        }
    }

    public void solidColision(GameObject tempObject) {
//        if (getBounds().intersects(tempObject.getBounds())) {
//            setPosY(tempObject.getPosY() - 64);
//            setVelY(0);
//            jumping = false;
//        }
//        if (getBoundsRight().intersects(tempObject.getBounds())) {
//            setPosX(tempObject.getPosX() - getWidth()-18);
//        }
//        if (getBoundsLeft().intersects(tempObject.getBounds())) {
//            setPosX(tempObject.getPosX() + getWidth()-18);
//        }
//        if (getBoundsTop().intersects(tempObject.getBounds())) {
//            setVelY(1);
//        }
//        if (!(getVelY() >= 0)) {
//            jumping = true;
//        }
    }


    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public void setAlive(Boolean alive) {
        this.alive = alive;
    }

    public Boolean getAlive() {
        return alive;
    }

    @Override
    public void tick() {
        collisions();
        setPosX(getPosX() - getVelX());
    }

    @Override
    public void render(GraphicsLibrary g) {
        if (alive) {
            g.drawImage(Textures.getEnemyTextures(objectName), (int) getPosX(), (int) getPosY(), -(int) getWidth(), (int) getHeight());
            showBounds(g);
        }

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) (getPosX()-getWidth()), (int) getPosY(), (int)getWidth(), (int)getHeight());
    }

    public void showBounds(GraphicsLibrary g){
        g.drawRectangle(getBounds(), Color.green);
    }
    @Override
    public GameObject clone() {
        return new Enemy(posX, posY, objectName);
    }
}
