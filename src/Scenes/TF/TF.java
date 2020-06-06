package Scenes.TF;

import Project.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.net.URL;

public class TF {
    public Button trueAnswer;
    public Button falseAnswer;
    public AnchorPane tableAnchor;
    public AnchorPane topAnchor;

    @FXML
    private void initialize() {
        // Loading sub nodes
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

    @FXML
    private void handleButtonTrue() {
        submitAnswer(true);
    }

    @FXML
    private void handleButtonFalse() {
        submitAnswer(false);
    }

    private void submitAnswer(boolean b) {
        // Todo: Conform answer
    }
}
