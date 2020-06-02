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

    @FXML
    private void initialize() {
        welcomeLabel.setAlignment(Pos.CENTER);
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
