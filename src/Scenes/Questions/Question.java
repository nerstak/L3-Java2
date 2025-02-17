package Scenes.Questions;

import Project.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

import java.net.URL;

/**
 * General functions for interfaces of questions
 */
public abstract class Question {
    /**
     * Load the side bars
     *
     * @param tableAnchor Table containing players information
     * @param topAnchor   Zone containing phase and timer
     */
    public static void loadContentBar(AnchorPane tableAnchor, AnchorPane topAnchor) {
        try {
            tableAnchor.getChildren().setAll(
                    (Node) FXMLLoader.load(
                            (URL) Main.sceneManager.getSceneUrl("TablePlayer")));

            topAnchor.getChildren().setAll(
                    (Node) FXMLLoader.load(
                            (URL) Main.sceneManager.getSceneUrl("TopBar")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Load answer alert
     *
     * @param result        Result of the user
     * @param correctAnswer Correct answer
     */
    protected void answerAlert(boolean result, String correctAnswer) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");

        if (result) {
            alert.setHeaderText("Congratulation!");
            alert.setContentText("The answer was indeed " + correctAnswer);
        } else {
            alert.setHeaderText("Too bad!");
            alert.setContentText("The real answer was " + correctAnswer);
        }
        alert.showAndWait();
    }
}
