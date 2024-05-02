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
import graphics.Background;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Game extends Canvas implements Runnable {

    private static final int NANOS_POR_SEGUNDO = 1000000000;
    private static final double NUM_FPS = 60.0;
    private int frames = 0;
    private int updates = 0;
    private Background background;


    private Thread mainThread;
    private Thread updateThread;
    private Thread renderThread;
    public Thread playerThread;
    private boolean running;
    private Handler handler;
    private Window window;
    private Camera camera;
    private static final int SCREEN_WIDHT = 800;
    private static final int SCREEN_HEIGHT = 600;
    private static final int MAX_RENDER = 500;
    private KeyInput keyInput;
    private Player player;
    private GraphicsLibrary graphics = new GraphicsLibrary(SCREEN_WIDHT, SCREEN_HEIGHT);

    public Game() {
        initComponents();
    }

    public void initComponents() {
        handler = new Handler();
        window = new Window(SCREEN_WIDHT, SCREEN_HEIGHT, "MIKU SLAYER");
        camera = new Camera(0, 32);
        player = new Player(32, 200, handler);
        background = new Background(0, 0, SCREEN_WIDHT, SCREEN_HEIGHT, 32, camera);

        keyInput = new KeyInput(player);


        addKeyListener(keyInput);
        handler.setPlayer(player);

        loadLevel("levelBase");

        window.setCanvas(this);
        gameLoop();


    }

    public synchronized void gameLoop() {
        mainThread = new Thread(this);
        updateThread = new Thread(this::runUpdate);
        renderThread = new Thread(this::runRender);
        playerThread = new Thread(this::playerUpdate);

        running = true;
        mainThread.start();
    }

    private void runRender() {
        double nsFrames = NANOS_POR_SEGUNDO / NUM_FPS; // cuantos nanosegundos deben pasar entre cada frames para alcanzar amountOfTicks por segundo

        double deltaFrames = 0; // controla cuando se debe ejecutar el metodo render()

        long lastTime = System.nanoTime(); // calcular cuanto tiempo ha pasado desde la ultima renderización del juego

        while (running) {
            long now = System.nanoTime();
            deltaFrames += (now - lastTime) / nsFrames;
            lastTime = now;

            while (deltaFrames >= 1) {
                render();
                frames++;
                deltaFrames--;
            }
        }
    }

    public void playerUpdate() {
        double nsTicks = NANOS_POR_SEGUNDO / NUM_FPS; // cuantos nanosegundos deben pasar entre cada actualizacion para alcanzar amountOfTicks por segundo

        double deltaTicks = 0; // controla cuando se debe ejecutar el metodo tick()

        long lastTime = System.nanoTime(); // calcular cuanto tiempo ha pasado desde la ultima actualizacion del juego

        while (running) {
            long now = System.nanoTime();
            deltaTicks += (now - lastTime) / nsTicks;
            lastTime = now;

            while (deltaTicks >= 1) {
                playerTick();
                deltaTicks--;
            }
        }
    }

    private void runUpdate() {
        double nsTicks = NANOS_POR_SEGUNDO / NUM_FPS; // cuantos nanosegundos deben pasar entre cada actualizacion para alcanzar amountOfTicks por segundo

        double deltaTicks = 0; // controla cuando se debe ejecutar el metodo tick()

        long lastTime = System.nanoTime(); // calcular cuanto tiempo ha pasado desde la ultima actualizacion del juego

        while (running) {
            long now = System.nanoTime();
            deltaTicks += (now - lastTime) / nsTicks;
            lastTime = now;

            while (deltaTicks >= 1) {
                updateObjects();
                background.tick();
                updates++;
                deltaTicks--;
            }
        }
    }


    public void loadLevel(String fileName) {
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
        updateThread.start();
        renderThread.start();
        playerThread.start();

        while (running) {
            try {
                Thread.sleep(1000);

                System.out.println("FPS: " + frames + " TPS: " + updates);
                updates = 0;
                frames = 0;
            } catch (InterruptedException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public synchronized void updateObjects() {
        handler.tick();
    }

    public synchronized void playerTick() {
        player.tick();
        camera.tick(player);

    }

    public synchronized void render() {

        BufferStrategy buf = this.getBufferStrategy();
        if (buf == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = buf.getDrawGraphics();
        graphics.clearBuffer();

        graphics.move(camera.getPosX(), camera.getPosY());

        background.render(graphics);
        handler.render(graphics);
        player.render(graphics);

        g.drawImage(graphics.getBuffer(), 0, 0, null);
        g.dispose();

        buf.show();


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
