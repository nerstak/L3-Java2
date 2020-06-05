package Scenes.MCQ;


import Project.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import oo.Questions.ListQuestions;
import oo.Questions.MCQ;
import oo.Questions.Question;

import java.net.URL;

public class MCQController {
    public Label question;
    public Button firstAnswer;
    public Button secondAnswer;
    public Button thirdAnswer;
    public AnchorPane tableAnchor;
    public AnchorPane topAnchor;

    private Question<?> questionT;
    private final MCQ<String> mcq;


    public MCQController() {
        // TMP, just proof of concept
        String t = Main.themes.getAtIndex(((int) (Math.random() * 10)));
        ListQuestions lq = new ListQuestions(t);
        do {
            questionT = lq.selectQuestion();
        } while (!(questionT.getStatement() instanceof MCQ));
        mcq = (MCQ<String>) questionT.getStatement();
    }

    @FXML
    private void initialize() {
        // Loading sub nodes
        try {
            tableAnchor.getChildren().setAll(
                    (Node) FXMLLoader.load(
                            (URL) Main.sceneManager.getSceneUrl("TablePlayer")));

            topAnchor.getChildren().setAll(
                    (Node) FXMLLoader.load(
                            (URL) Main.sceneManager.getSceneUrl("TopBar")));
        } catch (Exception e) {
            e.printStackTrace();
        }

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

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");

        if (result) {
            alert.setHeaderText("Wow ur good");
            alert.setContentText("The answer was indeed " + selectAnswer);
        } else {
            alert.setHeaderText("boooh");
            alert.setContentText("The real answer was " + mcq.getCorrectAnswer());
        }
        alert.showAndWait();
        Main.sceneManager.activate("Transition");
    }
}
