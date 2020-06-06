package Project;

import ProjectUtilities.JSONParser;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.HashMap;

/**
 * Scene manager
 * It is used to move between different interfaces
 * May need to be redesigned as it needs to instantiate a new Scene everytime
 */
public class SceneManager {
    private final HashMap<String, URL> screenList = new HashMap<>();
    private final Stage main;

    public SceneManager(Stage main) {
        this.main = main;
        loadScenes();
    }

    /**
     * Load scenes in memory from JSON file
     */
    private void loadScenes() {
        // Loading json file
        JSONObject scenes = JSONParser.parseFile("scenes.json");

        if (scenes != null) {
            screenList.clear();

            for (Object j : scenes.getJSONArray("scenes")) {
                try {
                    JSONObject json = (JSONObject) j;

                    // Path to FXML
                    URL url = new File(json.getString("path")).toURI().toURL();

                    screenList.put(json.getString("name"), url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
/*
    public void addScreen(String name, Scene scene) {
        screenList.put(name, scene);
    }

    public void deleteScreen(String name) {
        screenList.remove(name);
    }*/

    /**
     * Active a screen and put it on top
     *
     * @param name Name of the scene
     */
    public void activate(String name) {
        if (screenList.get(name) != null) {
            try {
                Parent root = FXMLLoader.load(screenList.get(name));
                main.setScene(new Scene(root));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Serializable getSceneUrl(String name) {
        if (screenList.get(name) != null) {
            return screenList.get(name);
        }
        return "";
    }
}
