package objects;

import graphics.Animation;
import graphics.GraphicsLibrary;

import java.awt.*;

public class Player extends GameObject{
    private Animation walkingAnimation;
    private int hp;
    private boolean running;
    private boolean walkingRight;
    private boolean walkingLeft;
    private boolean jumping;

    @Override
    public void tick() {

    }

    @Override
    public void render(GraphicsLibrary g) {

    }

    //TODO maybe have to implement more rectangles for the hitboxes
    @Override
    public Rectangle getBounds() {
    return new Rectangle((int) (getPosX() + getWidth() / 2 - getWidth() / 4),
            (int) (getPosY() + getHeight() / 2),
            (int) (getWidth() / 2),
            (int) (getHeight() / 2));

    }

    @Override
    public GameObject clone() {
        return null;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
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

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }
    private void showBounds(GraphicsLibrary g) {
        g.drawRectangle(getBounds(), Color.red);

    }
}
