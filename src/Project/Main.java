package Project;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import oo.Game.Game;

public class Main extends Application {
    public static Game game;
	public static SceneManager sceneManager;

    public static void main(String[] args) {
        /*MCQ<String> m = new MCQ<>("lol", "mo", "kp", "jo", "mo");
        m.display();
        System.out.println(m.checkAnswer("mo"));
        Question<?> q = new Question(m, "mdr", Difficulty.easy);
        q.display();

        Themes t = new Themes();
        t.display();
        ListQuestions l = new ListQuestions("music");
        l.display();*/

    	game = new Game();
		launch();
    }


	@Override
	public void start (Stage primaryStage) {
		setWindowParameters(primaryStage);

        sceneManager = new SceneManager(primaryStage);
        sceneManager.activate("Starting");
	}
	
	private void setWindowParameters (Stage primaryStage) {
        primaryStage.setTitle("Weakest Link - The new entertainment game for the whole family");
        primaryStage.getIcons().add(new Image("file:resources/img/weakest-link-icon.png"));
        primaryStage.setResizable(false);
        primaryStage.show();
	}
}
