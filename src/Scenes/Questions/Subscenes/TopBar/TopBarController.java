package Scenes.Questions.Subscenes.TopBar;

import Project.Main;
import ProjectUtilities.Utilities;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;

/**
 * Controller for the Top Bar in question's scene
 */
public class TopBarController {
    public Label timerLabel;
    public Label phaseInformation;

    @FXML
    private void initialize() {
        displayTimer();
        phaseInformation.setText(Main.game.getCurrentPhase().toString());
    }

    /**
     * Display the timer
     */
    private void displayTimer() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                if (Main.game.getCurrentPlayer() != null) {
                                    long timer = Main.game.getCurrentPlayer().getTimer();

                                    timerLabel.setText(Utilities.convertTimestampToString(timer));
                                }
                            }
                        }
                ),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}
