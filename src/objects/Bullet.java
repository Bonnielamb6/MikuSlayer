package objects;

import graphics.GraphicsLibrary;
import graphics.Textures;
import tools.Handler;

import java.awt.*;

public class Bullet extends GameObject {
    private String objectName;
    private boolean lookingRight;
    private Handler handler;
    private Boolean active = true;

    public Bullet(int posX, int posY, int width, int height, int movedX, boolean lookingRight, String objectName) {
        super(posX, posY, ObjectID.Bullet, width, height, 0);
        this.objectName = objectName;
        this.lookingRight = lookingRight;
        setVelX(10);
    }

    public void collisions() {
        if (isActive()) {
            for (GameObject temp : handler.getGameObjects()) {
                switch (temp.getId()) {
                    case Block:
                    case Floor:
                        solidColision(temp);
                        break;
                    case Enemy:
                        eraseEnemy((Enemy) temp);
                    default:
                        break;
                }
            }
        }
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

    public void eraseEnemy(Enemy enemy) {
        if (getBounds().intersects(enemy.getBounds())) {
            enemy.setAlive(false);
            active = false;
        }

    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean isActive() {
        return active;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void tick() {
        collisions();
        if (lookingRight) {
            setPosX(getPosX() + getVelX());
        } else {
            setPosX(getPosX() - getVelX());
        }
    }

    @Override
    public void render(GraphicsLibrary g) {
        if(active){
            if (lookingRight) {
                g.drawImage(Textures.getBulletTexture(objectName), (int) (getPosX()), (int) (getPosY()), (int) getWidth(), (int) getHeight());
            } else {
                g.drawImage(Textures.getBulletTexture(objectName), (int) (getPosX()), (int) (getPosY()), -(int) getWidth(), (int) getHeight());
            }
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) getPosX(), (int) getPosY(), 8, 8);
    }

    @Override
    public GameObject clone() {
        return new Bullet((int) posX, (int) posY, (int) width, (int) height, (int) movedX, lookingRight, objectName);
    }


}
