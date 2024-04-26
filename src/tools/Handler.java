package tools;

import objects.GameObject;
import graphics.GraphicsLibrary;

import java.util.LinkedList;
import java.util.List;

import main.Game;
import objects.Player;

public class Handler {
    private List<GameObject> gameObjects;
    private Player player;

    public Handler() {
        gameObjects = new LinkedList<GameObject>();

    }

    public void tick() {
        int renderLeft = (int) (player.getPosX() - Game.getMAX_RENDER());
        int renderRight = (int) (player.getPosX() + Game.getMAX_RENDER());
        for (GameObject obj : gameObjects) {
            if (obj.getPosX() < renderLeft && obj.getPosX() > renderRight) {
                obj.tick();
            }
        }
    }

    public void render(GraphicsLibrary g) {
        int renderLeft = (int) (player.getPosX() - Game.getMAX_RENDER());
        int renderRight = (int) (player.getPosX() + Game.getMAX_RENDER());
        for (GameObject obj : gameObjects) {
            if (obj.getPosX() > renderLeft && obj.getPosX() < renderRight) {
                obj.render(g);
            }
        }
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void addObject(GameObject obj) {
        gameObjects.add(obj);
    }

    public void removeObject(GameObject obj) {
        gameObjects.remove(obj);
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

}
