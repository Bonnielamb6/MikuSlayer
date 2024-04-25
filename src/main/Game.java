package main;

import graphics.Camera;
import graphics.GraphicsLibrary;
import levelCreator.LvlCreator;
import levelCreator.ObjectSquare;
import objects.GameObject;
import objects.Player;
import tools.Handler;
import tools.KeyInput;
import tools.ObjectFactory;

import java.awt.*;

public class Game extends Canvas implements Runnable {
    private Thread mainThread;
    private boolean running;
    private Handler handler;
    private Window window;
    private Camera camera;
    private static final int SCREEN_WIDHT = 600;
    private static final int SCREEN_HEIGHT = 600;
    private static final int MAX_RENDER = 600;
    private KeyInput keyInput;
    private Player player;
    private GraphicsLibrary graphics = new GraphicsLibrary(SCREEN_WIDHT, SCREEN_HEIGHT);

    public Game() {
        initComponents();
    }

    public void initComponents() {
        handler = new Handler();
        window = new Window(SCREEN_WIDHT, SCREEN_HEIGHT, "MIKU SLAYER");
        camera = new Camera(0, 0);
        player = new Player(32, 32, handler);
        keyInput = new KeyInput(player);

        addKeyListener(keyInput);
        handler.setPlayer(player);
        window.setCanvas(this);
    }

    public void loadLevel(String fileName){
        ObjectSquare[][] levelMatrix;
        levelMatrix = LvlCreator.loadLevelMatrix("levelBase");

        for (ObjectSquare[] objects : levelMatrix) {
            for (ObjectSquare object : objects) {
                if (!object.getObjectName().isEmpty()) {
                    GameObject obj = ObjectFactory.createObject(object.getObjectName());
                    obj.setPosX(object.getX() * 32);
                    obj.setPosY(object.getY() * 32);
                    handler.addObject(obj);
                }
            }
        }

    }

    @Override
    public void run() {

    }

    public void render() {

    }

    public static int getSCREEN_WIDHT() {
        return SCREEN_WIDHT;
    }

    public static int getSCREEN_HEIGHT() {
        return SCREEN_HEIGHT;
    }

    public static int getMAX_RENDER() {
        return MAX_RENDER;
    }
}
