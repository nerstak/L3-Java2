package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class Starting {
    @FXML
    public Label welcomeLabel;
    public GridPane gridPane;
    public Button startButton;
    public Label descriptionLabel;

    @FXML
    private void initialize() {
        welcomeLabel.setAlignment(Pos.CENTER);
        welcomeLabel.setText("Welcome to the weakest link!");
        descriptionLabel.setAlignment(Pos.CENTER);
        descriptionLabel.setText("You and 3 of your friends have been selected to this game. You will have to play until there is only one of you left...");
    }

    @FXML
    public void startGame(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("OK, so in reality, we don't have more");
        alert.setContentText("But isn't cool??");

        alert.showAndWait();
    }
}
