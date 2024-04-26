package objects;

import graphics.GraphicsLibrary;

import java.awt.*;

public abstract class GameObject {
    protected float posX;
    protected float posY;
    protected float width;
    protected float height;
    protected ObjectID id;
    private float velX;
    private float velY;
    private boolean hit;
    protected int movedX;

    public GameObject(float posX, float posY, ObjectID id, float width, float height, int movedX) {
        this.posX = posX;
        this.posY = posY;
        this.id = id;
        this.width = width;
        this.height = height;
        this.movedX = movedX;
    }

    public abstract void tick();

    public abstract void render(GraphicsLibrary g);

    public abstract Rectangle getBounds();

    public void gravity() {
        velY += 0.4f;
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public float getVelX() {
        return velX;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }

    public float getVelY() {
        return velY;
    }

    public void setVelY(float velY) {
        this.velY = velY;
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public int getMovedX() {
        return movedX;
    }

    public void setMovedX(int movedX) {
        this.movedX = movedX;
    }

    public ObjectID getId() {
        return id;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public abstract GameObject clone();
}

