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

public class TopBar {
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
                                long timer = Main.game.getCurrentPlayer().getTimer();

                                String seconds = Utilities.lengthTime(String.valueOf(timer / 1000 % 60));
                                String minutes = Utilities.lengthTime(String.valueOf(timer / 1000000 % 60));
                                String hours = Utilities.lengthTime(String.valueOf(timer / 1000000000));

                                timerLabel.setText(hours + "h " + minutes + "m " + seconds + "s");
                            }
                        }
                ),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}
