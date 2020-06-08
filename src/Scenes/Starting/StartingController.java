package Scenes.Starting;

import Project.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class StartingController {
    @FXML
    public Label welcomeLabel;
    public GridPane gridPane;
    public Button startButton;
    public Label descriptionLabel;
    public Label setting;

    @FXML
    private void initialize() {
        welcomeLabel.setAlignment(Pos.CENTER);
        welcomeLabel.setText("Welcome to the weakest link!");
        descriptionLabel.setAlignment(Pos.CENTER);
        descriptionLabel.setText("You and 3 of your friends have been selected to this game. You will have to play until there is only one of you left...");

        try {
            String path = (new File("README.md")).getAbsolutePath();
            path = path.replace("README.md", "resources/img/setting-icon.png");
            path = path.replace("\\", "/");
            Image image = new Image(new FileInputStream(path));
            setting.setGraphic(new ImageView(image));
        } catch (NullPointerException | FileNotFoundException e) {
            setting.setText("setting");
            e.printStackTrace();
        }
    }

    @FXML
    public void startGame(ActionEvent actionEvent) {
        Main.game.nextQuestion();
    }

    @FXML
    private void settings() {
        Main.sceneManager.activate("Themes");
    }
}
