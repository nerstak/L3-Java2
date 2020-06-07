package Scenes.Questions.MCQ;


import Project.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import oo.Questions.MCQ;
import oo.Questions.Question;

public class MCQController extends Scenes.Questions.Question {
    public Label question;
    public Button firstAnswer;
    public Button secondAnswer;
    public Button thirdAnswer;
    public AnchorPane tableAnchor;
    public AnchorPane topAnchor;

    private Question<?> questionT;
    private MCQ<String> mcq;


    @FXML
    private void initialize() {
        questionT = Main.game.getSelectedQuestion();
        mcq = (MCQ<String>) questionT.getStatement();

        // Loading sub nodes
        loadContentBar(tableAnchor, topAnchor);

        question.setText(questionT.getStatement().getText());
        firstAnswer.setText(((MCQ<?>) questionT.getStatement()).getAnswers().get(0));
        secondAnswer.setText(((MCQ<?>) questionT.getStatement()).getAnswers().get(1));
        thirdAnswer.setText(((MCQ<?>) questionT.getStatement()).getAnswers().get(2));
    }

    @FXML
    private void handleButtonOne() {
        submitAnswer(0);
    }

    @FXML
    private void handleButtonTwo() {
        submitAnswer(1);
    }

    @FXML
    private void handleButtonThree() {
        submitAnswer(2);
    }

    private void submitAnswer(int index) {
        String selectAnswer = mcq.getAnswers().get(index);
        boolean result = mcq.checkAnswer(selectAnswer);

        answerAlert(result, mcq.getCorrectAnswer());

        Main.game.handleResult(result);
    }
}
