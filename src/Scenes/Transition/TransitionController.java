package Scenes.Transition;

import Project.Main;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import oo.Players.PlayerStatus;

/**
 * Transition between scene. This controller should be responsible of
 * any data manipulation (in complement with the future game class), and choosing which scene to load
 */
public class TransitionController {
    public AnchorPane anchor;

    public TransitionController() {
    }

    @FXML
    private void initialize() {
        // OK so this is actually junk asf, as most of all of this should be moved to the game class
        // But that should do it for the presentation
        // Getting rid of the setOnMouseMoved would be cool, but I'm not sure how to call
        // a function when scene is showed

        anchor.setOnMouseMoved(e -> {
            if (Main.game.getCurrentPlayer() != null) {
                Main.game.getCurrentPlayer().setStatus(PlayerStatus.waiting);
            }
            Main.game.setCurrentPlayer(Main.game.getListPlayers().selectPlayer(PlayerStatus.waiting));
            Main.game.getCurrentPlayer().setStatus(PlayerStatus.selected);

            Main.sceneManager.activate("MCQ");
        });
    }


}
