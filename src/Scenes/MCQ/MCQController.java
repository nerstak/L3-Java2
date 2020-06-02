package Scenes.MCQ;


import Project.Main;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import oo.Players.Player;
import oo.Players.PlayerStatus;

public class MCQController {
    @FXML
    private TableView<Player> personTable;
    @FXML
    private TableColumn<Player, String> playerActive;

    @FXML
    private void initialize() {
        playerActive.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getName()));

        personTable.setItems(FXCollections.observableArrayList(Main.listPlayers.selectPlayers(PlayerStatus.waiting)));
    }
}
