package objects;

import graphics.Animation;
import graphics.GraphicsLibrary;
import graphics.Textures;
import tools.Handler;

import java.awt.*;

public class Player extends GameObject {
    private Animation walkingAnimation;
    private int hp = 3;
    private int running = 1;//1 means is not running, 2 means its running
    private boolean walkingRight;
    private boolean walkingLeft;
    private boolean jumping;

    private Handler handler;

    public Player(float x, float y, Handler handler) {
        super(x, y, ObjectID.Player, 32, 32, 0);
        this.handler = handler;
    }

    @Override
    public void tick() {
        movement();
        gravity();
        colisions();
        walkingAnimation.animate();
    }

    @Override
    public void render(GraphicsLibrary g) {
        if (getVelX() > 0) {
            walkingAnimation.drawSprite(g, (int) getPosX(), (int) getPosY());
        } else if (getVelX() < 0) {
            walkingAnimation.drawReversedSprite(g, (int) getPosX(), (int) getPosY());
        } else {
            g.drawImage(Textures.getMikuTextures("mikuFront"), (int) getPosX(), (int) getPosY());
        }
    }

    public void movement() {
        if (walkingRight || walkingLeft) {
            if (walkingRight) {
                setVelX(getVelX() + 0.1f);
                if (getVelX() > 3 * running) {
                    setVelX(3 * running);
                }
            } else {
                setVelX(getVelX() - 0.1f);
                if (getVelX() < -3 * running) {
                    setVelX(-3 * running);
                }
            }
        } else {
            setVelX(0);
        }
        setPosX(getVelX() + getPosX());

    }

    public void colisions() {
        for (GameObject temp : handler.getGameObjects()) {
            switch (temp.getId()) {
                case Block:
                case Floor:
                    solidColision(temp);
                    break;
            }
        }
    }

    public void solidColision(GameObject tempObject) {
        if (getBounds().intersects(tempObject.getBounds())) {
            setPosY(tempObject.getPosY());
            setVelY(0);
            jumping = false;
        }
        if (getBoundsRight().intersects(tempObject.getBounds())) {
            setPosX(tempObject.getPosX() - getWidth());
        }
        if (getBoundsLeft().intersects(tempObject.getBounds())) {
            setPosX(tempObject.getPosX() + getWidth());
        }
        if (!(getVelY() >= 0)) {
            jumping = true;
        }
    }

    @Override
    public Rectangle getBounds() {//BOTTOM RECTANGLE
        return new Rectangle(
                (int) (getPosX() + getWidth() / 2 - getWidth() / 4),
                (int) (getPosY() + getHeight() / 2),
                (int) (getWidth() / 2),
                (int) (getHeight() / 2));

    }

    public Rectangle getBoundsRight() {
        return new Rectangle(
                (int) (getPosX() + getWidth() - 8),
                (int) (getPosY() + 4),
                8,
                (int) (getHeight() - 8));
    }

    public Rectangle getBoundsLeft() {
        return new Rectangle(
                (int) (getPosX()),
                (int) (getPosY() + 4),
                8,
                (int) (getHeight() - 8));
    }

    @Override
    public GameObject clone() {
        return null;
    }

    public void gotHit() {
        hp--;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setJumping(Boolean jum) {
        jumping = jum;
    }

    public boolean hasJumped() {
        return jumping;
    }


    public int isRunning() {
        return running;
    }

    public void setRunning(int running) {
        this.running = running;
    }

    public boolean isWalkingRight() {
        return walkingRight;
    }

    public void setWalkingRight(boolean walkingRight) {
        this.walkingRight = walkingRight;
    }

    public boolean isWalkingLeft() {
        return walkingLeft;
    }

    public void setWalkingLeft(boolean walkingLeft) {
        this.walkingLeft = walkingLeft;
    }

    public boolean isJumping() {
        return jumping;
    }


    private void showBounds(GraphicsLibrary g) {
        g.drawRectangle(getBounds(), Color.red);

    }
}
