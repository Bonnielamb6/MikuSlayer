package tools;

import objects.*;

import java.util.HashMap;
import java.util.Map;

public class ObjectFactory {
    private static final Map<String, GameObject> prototypes = new HashMap<>();

    static {
        prototypes.put("Cloud", new BackgroundObject(
                0, 0, 20, 20, 0, "Cloud"));
        prototypes.put("Plant", new BackgroundObject(
                0, 0, 16, 16, 0, "Plant"
        ));
        prototypes.put("Tree", new BackgroundObject(
                0, 0, 32, 32, 0, "Tree"
        ));
        prototypes.put("Moon", new BackgroundObject(
                0, 0, 16, 16, 0, "Moon"
        ));
        prototypes.put("Floor_Block", new SolidBlock(
                0, 0, 16, 16, 0, "Floor_Block"
        ));
        prototypes.put("brick", new SolidBlock(
                0, 0, 16, 16, 0, "brick"
        ));
        prototypes.put("enemy", new Enemy(
                0, 0, "enemy"
        ));
    }

    public static GameObject createObject(String type) {
        GameObject prototype = prototypes.get(type);
        if (prototype != null) {
            return prototype.clone();
        }
        return null;
    }

}
