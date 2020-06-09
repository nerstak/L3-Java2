package Scenes.FinalScreen;

import Project.Main;
import ProjectUtilities.Utilities;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import oo.Game.Game;
import oo.Players.Player;

import java.util.Collections;
import java.util.Vector;

public class FinalScreen {
    private final Vector<Player> ranking;
    public TableColumn<Player, String> playerName;
    public TableColumn<Player, String> score;
    public TableColumn<Player, String> time;
    public Button restartButton;
    public Button quitButton;
    @FXML
    private TableView<Player> personTable;


    public FinalScreen() {
        ranking = Main.game.getListPlayers().getPlaying();
        ranking.sort(Player.PlayerRanking);
        Collections.sort(ranking, Collections.reverseOrder());
    }

    @FXML
    private void initialize() {
        playerName.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getName()));
        score.setCellValueFactory(c -> new SimpleStringProperty(
                String.valueOf(c.getValue().getScore())
        ));
        time.setCellValueFactory(c -> new SimpleStringProperty(
                Utilities.convertTimestampToString(c.getValue().getTimer())
        ));


        ObservableList<Player> observableList = FXCollections.observableArrayList(ranking);
        personTable.setItems(observableList);
    }

    @FXML
    public void restartGame(ActionEvent actionEvent) {
        Main.game = new Game();
        Main.game.nextQuestion();
    }

    @FXML
    private void quit() {
        // get a handle to the stage
        Stage stage = (Stage) quitButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
}
