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
        posX = (int) (-player.getPosX()+Game.getSCREEN_WIDHT());
    }
}
