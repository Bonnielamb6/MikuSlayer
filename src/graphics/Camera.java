package graphics;

import objects.GameObject;
import main.Game;

public class Camera {
    private int posX;
    private int posY;
    private int lastX;

    public Camera(int x, int y) {
        posX = x;
        posY = y;
    }

    public void tick(GameObject player) {
        posX = (int) (-player.getPosX()+Game.getSCREEN_WIDHT()/2+16);

    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }
}
