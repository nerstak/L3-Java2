package Project;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import oo.Game.Game;

/**
 * Entry point of the program
 */
public class Main extends Application {
    public static Game game;
	public static SceneManager sceneManager;

    public static void main(String[] args) {
        game = new Game();
        launch();
    }


    @Override
    public void start(Stage primaryStage) {
        setWindowParameters(primaryStage);

        sceneManager = new SceneManager(primaryStage);
        sceneManager.activate("Starting");
    }

    /**
     * Define window properties
     *
     * @param primaryStage Stage
     */
    private void setWindowParameters(Stage primaryStage) {
        primaryStage.setTitle("Weakest Link - The new entertainment game for the whole family");
        primaryStage.getIcons().add(new Image("file:resources/img/weakest-link-icon.png"));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
