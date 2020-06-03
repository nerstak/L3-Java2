package Scenes.MCQ;


import Project.Main;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import oo.Players.Player;
import oo.Players.PlayerStatus;
import oo.Questions.ListQuestions;
import oo.Questions.MCQ;
import oo.Questions.Question;

public class MCQController {
    public Label timerLabel;
    public Label phaseInformations;
    public Label question;
    public Button firstAnswer;
    public Button secondAnswer;
    public Button thirdAnswer;

    private Question questionT;

    @FXML
    private TableView<Player> personTable;
    @FXML
    private TableColumn<Player, String> playerActive;

    public MCQController() {
        String t = Main.themes.getAtIndex(1);
        ListQuestions lq = new ListQuestions(t);
        do {
            questionT = lq.selectQuestion();
        } while (!(questionT.getStatement() instanceof MCQ));

    }

    @FXML
    private void initialize() {
        playerActive.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getName()));
        personTable.setItems(FXCollections.observableArrayList(Main.listPlayers.selectPlayers(PlayerStatus.waiting)));

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
    }
}
