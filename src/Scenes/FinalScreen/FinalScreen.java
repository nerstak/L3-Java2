package Scenes.FinalScreen;

import Project.Main;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import oo.Players.Player;
import oo.Players.PlayerStatus;

import java.util.Collections;
import java.util.Vector;

public class FinalScreen {
    private final Vector<Player> ranking;
    public TableColumn playerName;
    public TableColumn score;
    public TableColumn time;


    public FinalScreen() {
        ranking = Main.listPlayers.selectPlayers(PlayerStatus.waiting);
        ranking.sort(Player.PlayerRanking);
        Collections.sort(ranking, Collections.reverseOrder());
    }

    @FXML
    private void initialize() {

    }
}
