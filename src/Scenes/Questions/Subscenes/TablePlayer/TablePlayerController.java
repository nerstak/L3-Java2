package Scenes.Questions.Subscenes.TablePlayer;

import Project.Main;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import oo.Players.Player;

import static oo.Players.Player.PlayerStatusComparator;

/**
 * Controller for the Table of Players in question's scene
 */
public class TablePlayerController {
    public TableColumn<Player, String> playerScore;
    @FXML
    private TableView<Player> personTable;
    @FXML
    private TableColumn<Player, String> playerName;
    @FXML
    private TableColumn<Player, String> playerStatus;

    @FXML
    private void initialize() {
        playerName.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getName()));
        playerStatus.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getStatus().toString()
        ));
        playerScore.setCellValueFactory(c -> new SimpleStringProperty(
                String.valueOf(c.getValue().getScore())
        ));


        ObservableList<Player> observableList = FXCollections.observableArrayList(Main.game.getListPlayers().getPlaying());
        observableList.sort(PlayerStatusComparator);

        personTable.setItems(observableList);
    }
}
