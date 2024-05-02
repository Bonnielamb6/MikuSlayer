package tools;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

public class PS5Controller {
    public static void main(String[] args) {
        // Configura el entorno del controlador
        ControllerEnvironment ce = ControllerEnvironment.getDefaultEnvironment();
        Controller[] cs = ce.getControllers();

        // Encuentra el controlador de PS5
        Controller ps5Controller = null;
        for (Controller c : cs) {
            if (c.getName().toLowerCase().contains("ps5")) {
                ps5Controller = c;
                break;
            }
        }

        if (ps5Controller == null) {
            System.out.println("No se encontró el controlador de PS5.");
            return;
        }

        // Imprime los nombres de los componentes del controlador
        Component[] components = ps5Controller.getComponents();
        System.out.println("Nombres de los componentes del controlador:");
        for (Component component : components) {
            System.out.println(component.getIdentifier().getName());
        }
    }
}
