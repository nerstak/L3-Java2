package Scenes.SA;

import Project.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;

public class SAController {
    public AnchorPane topAnchor;
    public AnchorPane tableAnchor;
    public Label question;
    public TextField answer;
    public Button submit;

    public SAController() {
    }

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
    private void handleButton() {

    }
}
