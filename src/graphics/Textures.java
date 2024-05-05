package graphics;

import tools.textureReader;

import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Textures {
    private static LinkedHashMap<String, BufferedImage> texturesMap;

    private static HashMap<String, BufferedImage> mikuTexturesMap;
    private static HashMap<String, BufferedImage> bulletTexturesMap;

    //TODO ADD ENEMIES FOR THE GAME
    private static LinkedHashMap<String, BufferedImage> enemyTexturesMap;

    private static textureReader matrixReader;

    static {
        texturesMap = new LinkedHashMap<>();
        mikuTexturesMap = new HashMap<>();
        enemyTexturesMap = new LinkedHashMap<>();
        matrixReader = new textureReader(2);//1 for the editor, 2 for the game
        bulletTexturesMap = new HashMap<>();

        getFloorTextures();
        getBackgroundTextures();
        getMikuTextures();
        getEnemytextures();
        getBossTextures();
        getBulletsTextures();
    }

    private static void getFloorTextures() {
        texturesMap.put("Floor_Block", matrixReader.drawObject("imgs/blocks/floorBlock"));
        texturesMap.put("brick", matrixReader.drawObject("imgs/blocks/brick"));

    }

    private static void getBackgroundTextures() {
        texturesMap.put("Plant", matrixReader.drawObject("imgs/background/plant"));
        texturesMap.put("Tree", matrixReader.drawObject("imgs/background/tree"));
        texturesMap.put("Cloud", matrixReader.drawObject("imgs/background/cloud"));
        texturesMap.put("Moon", matrixReader.drawObject("imgs/background/moon"));
        texturesMap.put("enemy", matrixReader.drawObject("imgs/enemies/enemy"));
        texturesMap.put("enemyWalking", matrixReader.drawObject("imgs/enemies/enemyWalking"));
    }


    private static void getMikuTextures() {
        mikuTexturesMap.put("miku", matrixReader.drawObject("imgs/miku/mikuFront"));
        mikuTexturesMap.put("mikuLeft", matrixReader.drawObject("imgs/miku/mikuLeft"));
        mikuTexturesMap.put("mikuLeftWalking", matrixReader.drawObject("imgs/miku/mikuLeftWalking"));
        mikuTexturesMap.put("mikuRight", matrixReader.drawObject("imgs/miku/mikuRight"));
        mikuTexturesMap.put("mikuRightWalking", matrixReader.drawObject("imgs/miku/mikuRightWalking"));
    }

    private static void getEnemytextures() {
        enemyTexturesMap.put("enemy", matrixReader.drawObject("imgs/enemies/enemy"));
        enemyTexturesMap.put("enemyWalking", matrixReader.drawObject("imgs/enemies/enemyWalking"));
    }

    private static void getBulletsTextures() {
        bulletTexturesMap.put("bullet", matrixReader.drawObject("imgs/bullets/bullet"));
    }

    public static BufferedImage getBulletTexture(String texture){
        BufferedImage selectedImage = bulletTexturesMap.get(texture);
        return selectedImage;
    }

    private static void getBossTextures() {

    }

    public static BufferedImage getTexture(String texture) {
        BufferedImage selectedImage = texturesMap.get(texture);
        return selectedImage;
    }

    public static LinkedHashMap<String, BufferedImage> getTexturesMap() {
        return texturesMap;
    }

    public static LinkedHashMap<String, BufferedImage> getEnemiesTexturesMap() {
        return enemyTexturesMap;
    }

    public static BufferedImage getMikuTextures(String name) {
        return mikuTexturesMap.get(name);
    }

    //TODO ADD TEXTURES FOR ENEMIES
    public static BufferedImage getEnemyTextures(String name) {
        return enemyTexturesMap.get(name);
    }


}
