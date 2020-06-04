package Scenes.AjouterSupprimer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.awt.*;

public class AddControler {

    @FXML
    public TextField TextAnswer1;
    public TextField TextAnswer2;
    public TextField TextAnswer3;
    public TextField Difficulty;
    public TextField CorrectAnswer;
    public TextField Text;
    public ChoiceBox typeQuestion;

    @FXML
    private void initialize() {

        TextAnswer1.setEditable(false);
        TextAnswer2.setEditable(false);
        TextAnswer3.setEditable(false);

        typeQuestion.getItems().addAll("TrueFalse", "ShortAnswer", "MCQ");
        typeQuestion.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                System.out.println(typeQuestion.getItems().get((Integer) number2));
                if(typeQuestion.getItems().get((Integer) number2).equals("MCQ")){
                    TextAnswer1.setEditable(true);
                    TextAnswer2.setEditable(true);
                    TextAnswer3.setEditable(true);
                }
                else
                {
                    TextAnswer1.setEditable(false);
                    TextAnswer2.setEditable(false);
                    TextAnswer3.setEditable(false);
                }
            }
        });

    }
}
