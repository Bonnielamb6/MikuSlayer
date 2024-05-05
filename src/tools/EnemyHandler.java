package tools;

import objects.Enemy;
import objects.GameObject;
import graphics.GraphicsLibrary;

import java.util.LinkedList;
import java.util.List;

import main.Game;
public class EnemyHandler {
    private List<GameObject> gameObjects;
    private Enemy enemy;

    public EnemyHandler(){
        gameObjects = new LinkedList<GameObject>();
    }

}
