import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import oo.Players.SetPlayers;

import java.io.File;
import java.net.URL;


public class Main extends Application {
    public static SetPlayers listPlayers;

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

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = new File("resources/fxml/Starting.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        primaryStage.setTitle("WEAKEST LINK");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
