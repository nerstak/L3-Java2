package Scenes.AjouterSupprimer;

import Project.Main;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import oo.Game.Difficulty;
import oo.Questions.*;

import javax.xml.transform.stream.StreamSource;
import java.awt.*;

public class AddControler {

    @FXML
    public TextField TextAnswer1;
    public TextField TextAnswer2;
    public TextField TextAnswer3;
    public TextField CorrectAnswer;
    public TextField Text;
    public ChoiceBox difficulty;
    public ChoiceBox typeQuestion;

    private String errorMsg = "";

    @FXML
    private void initialize() {

        TextAnswer1.setEditable(false);
        TextAnswer2.setEditable(false);
        TextAnswer3.setEditable(false);

        typeQuestion.getItems().addAll("", "TrueFalse", "ShortAnswer", "MCQ");
        typeQuestion.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                // if we want to add a MCQ, we have to be able to write different answers
                if(typeQuestion.getItems().get((Integer) number2).equals("MCQ")){
                    TextAnswer1.setEditable(true);
                    TextAnswer2.setEditable(true);
                    TextAnswer3.setEditable(true);
                }
                // if not, we should not be able to write in the TextFields (I reset them to prevent from errors)
                else
                {
                    TextAnswer1.setEditable(false);
                    TextAnswer2.setEditable(false);
                    TextAnswer3.setEditable(false);
                    TextAnswer1.setText("");
                    TextAnswer2.setText("");
                    TextAnswer3.setText("");
                }
            }
        });


        difficulty.getItems().addAll("", 1, 2, 3);

    }

    @FXML
    private void handleButtonBack() {Main.sceneManager.activate("Themes");}

    @FXML
    private void handleButtonAdd(){
        if(checkAll())
        {
            creatingNewQuestion();
            // TODO: 06/06/2020 add the question on the json file
            difficulty.getSelectionModel().select(0);
            typeQuestion.getSelectionModel().select(0);
            Text.setText("");
            CorrectAnswer.setText("");
            TextAnswer1.setText("");
            TextAnswer2.setText("");
            TextAnswer3.setText("");
        }
    }

    private boolean checkAll(){

        errorMsg = "";

        // all of this is just to have a message with all the errors to print
        if(difficulty.getValue() == null)
            errorMsg += "difficulty is NULL\n";
        if(Text.getText().equals(""))
            errorMsg += "There is no Text\n";
        boolean check = checkType();

        // if we have a difficulty, a text and the question correct
        if(difficulty.getValue() != null && !Text.getText().equals("") && check)
            return true;

        missingParameters();
        return false;
    }


    private boolean checkType()
    {
        if(CorrectAnswer.getText().equals("") || typeQuestion.getValue() == null)
        {
            if(typeQuestion.getValue() == null)
                errorMsg += "Which type of Question do you want?\n";

            if(CorrectAnswer.getText().equals(""))
                errorMsg += "There is no Correct Answer\n";

            return false;
        }
        else
        {
            switch ((String) typeQuestion.getValue()) {

                // case TrueFalse : it must true or false
                case "TrueFalse" : {
                    if(CorrectAnswer.getText().equalsIgnoreCase("true") || CorrectAnswer.getText().equalsIgnoreCase("false"))
                        return true;

                    errorMsg += "The answer has to be True or False\n";
                    return false;
                }

                // case MCQ : the answer has to be in the list of the different proposed answers
                case "MCQ" : {

                    // each answer is different from the others
                    if(TextAnswer1.getText().equals(TextAnswer2.getText())
                            || TextAnswer1.getText().equals(TextAnswer3.getText())
                            || TextAnswer2.getText().equals(TextAnswer3.getText()))
                    {
                        errorMsg += "You can not have multiple times the same answer !!!\n";
                        return false;
                    }

                    if(CorrectAnswer.getText().equals(TextAnswer1.getText())
                            || CorrectAnswer.getText().equals(TextAnswer2.getText())
                            || CorrectAnswer.getText().equals(TextAnswer3.getText()))
                        return true;

                    errorMsg += "There must be a Correct Answer !\n";
                    return false;
                }

                // case ShortAnswer : if there is an answer it is good
                default : {
                    return true;
                }
            }
        }
    }


    private void creatingNewQuestion() {
        AbstractStatement<?> s = null;
        ListQuestions lq = new ListQuestions(ThemesController.getThemeSelected());
        switch ((String) typeQuestion.getValue()) {

            case "TrueFalse": {

                // just to be sure the answer corresponds to the json file
                Boolean answer;
                if(CorrectAnswer.getText().equalsIgnoreCase("true"))
                    answer = true;
                else
                    answer = false;

                s = new TrueFalse(Text.getText(), answer);
                break;
            }

            case "MCQ": {
                s = new MCQ(Text.getText(), TextAnswer1.getText(), TextAnswer2.getText(), TextAnswer3.getText(), CorrectAnswer.getText());
                break;
            }

            default: {
                s = new ShortAnswer(Text.getText(), CorrectAnswer.getText());
                break;
            }
        }

        lq.addQuestion(new Question(s, ThemesController.getThemeSelected(), Difficulty.fromInteger((Integer) difficulty.getValue()) ));
        lq.display();
    }

    @FXML
    private void missingParameters()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error in creation of a Question");
        alert.setHeaderText("There are some points you have to modify !");
        alert.setContentText(errorMsg);

        alert.showAndWait();
    }
}
