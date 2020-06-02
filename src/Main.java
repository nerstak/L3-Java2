import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import oo.Questions.ListQuestions;

import java.io.File;
import java.net.URL;


public class Main extends Application {
    public static void main(String[] args) {
        /*MCQ<String> m = new MCQ<>("lol", "mo", "kp", "jo", "mo");
        m.display();
        System.out.println(m.checkAnswer("mo"));
        Question<?> q = new Question(m, "mdr", Difficulty.easy);
        q.display();

        Themes t = new Themes();
        t.display();*/
        ListQuestions l = new ListQuestions("music");
        l.display();

        try {
            launch(args);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = new File("resources/fxml/RootLayout.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }
}
