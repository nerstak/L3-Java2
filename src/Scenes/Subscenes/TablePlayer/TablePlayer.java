package Scenes.Subscenes.TablePlayer;

import Project.Main;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import oo.Game.Game;
import oo.Players.Player;
import oo.Players.PlayerStatus;

public class TablePlayer {
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


        ObservableList<Player> observableList = FXCollections.observableArrayList(Main.game.getListPlayers().selectPlayers(PlayerStatus.waiting));
        observableList.addAll(Main.game.getListPlayers().selectPlayers(PlayerStatus.hasPlayed));
        observableList.addAll(Main.game.getListPlayers().selectPlayers(PlayerStatus.selected));
        personTable.setItems(observableList);

        if (Main.game.getListPlayers().countPlayers(PlayerStatus.waiting) < 2) {
            Game g = Main.game;
        }
    }
}
