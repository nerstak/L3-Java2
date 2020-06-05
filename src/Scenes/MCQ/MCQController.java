package Scenes.MCQ;


import Project.Main;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Duration;
import oo.Players.Player;
import oo.Players.PlayerStatus;
import oo.Questions.ListQuestions;
import oo.Questions.MCQ;
import oo.Questions.Question;

public class MCQController {
    public Label timerLabel;
    public Label phaseInformation;
    public Label question;
    public Button firstAnswer;
    public Button secondAnswer;
    public Button thirdAnswer;

    private Question<?> questionT;
    private final MCQ<String> mcq;

    @FXML
    private TableView<Player> personTable;
    @FXML
    private TableColumn<Player, String> playerName;
    @FXML
    private TableColumn<Player, String> playerStatus;

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
        playerName.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getName()));
        playerStatus.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getStatus().toString()
        ));


        ObservableList<Player> observableList = FXCollections.observableArrayList(Main.listPlayers.selectPlayers(PlayerStatus.waiting));
        observableList.addAll(Main.listPlayers.selectPlayers(PlayerStatus.selected));
        personTable.setItems(observableList);

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
