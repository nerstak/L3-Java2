package Project;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import oo.Players.Player;
import oo.Players.PlayerStatus;
import oo.Players.SetPlayers;
import oo.Questions.Themes;


public class Main extends Application {
    public static SetPlayers listPlayers;
    public static Themes themes;
    public static SceneManager sceneManager;
    public static Player currentPlayer;

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
        listPlayers = new SetPlayers();
        themes = new Themes();

        // TMP - Should not be conserved like this, as we need to select players
        do {
            listPlayers.selectPlayer().setStatus(PlayerStatus.waiting);
        } while (listPlayers.countPlayers(PlayerStatus.waiting) < 4);

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Setting window parameter
        primaryStage.setTitle("Weakest Link - The new entertainment game for the whole family");
        primaryStage.getIcons().add(new Image("file:resources/img/weakest-link-icon.png"));
        primaryStage.setResizable(false);
        primaryStage.show();

        // Scene manager
        sceneManager = new SceneManager(primaryStage);
        sceneManager.activate("Themes");
    }
}
