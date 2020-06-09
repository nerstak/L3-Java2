package Scenes.Questions.TF;

import Project.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import oo.Questions.Question;
import oo.Questions.TrueFalse;

/**
 * Controller of TrueFalse questions
 */
public class TFController extends Scenes.Questions.Question {
    public Button trueAnswer;
    public Button falseAnswer;
    private final Question<?> questionT;
    public AnchorPane tableAnchor;
    public AnchorPane topAnchor;
    private final TrueFalse<Boolean> trueFalse;
    public Label question;

    public TFController() {
        questionT = Main.game.getSelectedQuestion();
        trueFalse = (TrueFalse<Boolean>) questionT.getStatement();
    }

    @FXML
    private void initialize() {
        // Loading sub nodes
        loadContentBar(tableAnchor, topAnchor);
        question.setText(questionT.getStatement().getText());
    }

    @FXML
    private void handleButtonTrue() {
        submitAnswer(true);
    }

    @FXML
    private void handleButtonFalse() {
        submitAnswer(false);
    }

    /**
     * Submit the answer and load the next question
     *
     * @param b Answer
     */
    private void submitAnswer(boolean b) {
        boolean result = trueFalse.checkAnswer(b);

        answerAlert(result, trueFalse.getCorrectAnswer());

        Main.game.handleResult(result);
    }
}
