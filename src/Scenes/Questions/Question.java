package Scenes.Questions;

import Project.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

import java.net.URL;

public abstract class Question {
    protected void loadContentBar(AnchorPane tableAnchor, AnchorPane topAnchor) {
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

    protected void answerAlert(boolean result, String correctAnswer) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");

        if (result) {
            alert.setHeaderText("Wow ur good");
            alert.setContentText("The answer was indeed " + correctAnswer);
        } else {
            alert.setHeaderText("boooh");
            alert.setContentText("The real answer was " + correctAnswer);
        }
        alert.showAndWait();
    }
}
