package Scenes.Questions.SA;

import Project.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import oo.Questions.Question;
import oo.Questions.ShortAnswer;

public class SAController extends Scenes.Questions.Question {
    private final Question<?> questionT;
    private final ShortAnswer<String> shortAnswer;
    public AnchorPane topAnchor;
    public AnchorPane tableAnchor;
    public Label question;
    public TextField answer;
    public Button submit;


    public SAController() {
        questionT = Main.game.getSelectedQuestion();
        shortAnswer = (ShortAnswer<String>) questionT.getStatement();
    }

    @FXML
    private void initialize() {
        // Loading sub nodes
        loadContentBar(tableAnchor, topAnchor);


        question.setText(questionT.getStatement().getText());
    }

    @FXML
    private void handleButton() {
        String selectedAnswer = answer.getText();
        submitAnswer(selectedAnswer);
    }

    private void submitAnswer(String s) {
        boolean result = shortAnswer.checkAnswer(s);

        answerAlert(result, shortAnswer.getCorrectAnswer());

        Main.game.handleResult(result);
    }
}
