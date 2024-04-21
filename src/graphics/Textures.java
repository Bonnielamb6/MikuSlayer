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

    //TODO ADD ENEMIES FOR THE GAME
    private static HashMap<String, BufferedImage> enemyTexturesMap;

    private static textureReader matrixReader;

    static {
        texturesMap = new LinkedHashMap<>();
        mikuTexturesMap = new HashMap<>();
        enemyTexturesMap = new HashMap<>();
        matrixReader = new textureReader(2);

        getFloorTextures();
        getPlantsTextures();
        getTreesTextures();
        getMikuTextures();
        getEnemytextures();
        getBossTextures();
    }

    private static void getFloorTextures() {

    }

    private static void getPlantsTextures() {

    }

    private static void getTreesTextures() {

    }

    private static void getMikuTextures() {

    }

    private static void getEnemytextures() {

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

    public static BufferedImage getMikuTextures(String name) {
        return mikuTexturesMap.get(name);
    }

//TODO ADD TEXTURES FOR ENEMIES
    public static BufferedImage getEnemyTextures(String name){
        return enemyTexturesMap.get(name);
    }


}
