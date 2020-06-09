package Scenes.ModifyQuestions.Add;

import Project.Main;
import Scenes.ModifyQuestions.Themes.ThemesController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import oo.Game.Difficulty;
import oo.Questions.*;

public class AddController {
    @FXML
    public TextField TextAnswer1;
    public TextField TextAnswer2;
    public TextField TextAnswer3;
    public TextField CorrectAnswer;
    public TextField Text;
    public ChoiceBox difficulty;
    public ChoiceBox typeQuestion;
    public Label LabelAnswer1;
    public Label LabelAnswer2;
    public Label LabelAnswer3;

    private String errorMsg = "";

    @FXML
    private void initialize() {
        TextAnswer1.setEditable(false);
        TextAnswer2.setEditable(false);
        TextAnswer3.setEditable(false);

        typeQuestion.getItems().addAll("", "TrueFalse", "ShortAnswer", "MCQ");
        typeQuestion.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, number2) -> {
            boolean itemEqualsMCQ = typeQuestion.getItems().get((Integer) number2).equals("MCQ");

            // if we want to add a MCQ, we have to be able to write different answers
            setAvailability(itemEqualsMCQ);
        });

        setAvailability(false);
        difficulty.getItems().addAll("", 1, 2, 3);
    }

    @FXML
    private void handleButtonBack () {
        Main.sceneManager.activate("Themes");
    }

    @FXML
    private void handleButtonAdd () {
        if (!checkAll())
            return;

        creatingNewQuestion();
        difficulty.getSelectionModel().select(0);
        typeQuestion.getSelectionModel().select(0);
        Text.setText("");
        CorrectAnswer.setText("");
        TextAnswer1.setText("");
        TextAnswer2.setText("");
        TextAnswer3.setText("");
    }

    /**
     * Check general fields
     *
     * @return
     */
    private boolean checkAll() {
        errorMsg = "";

        // all of this is just to have a message with all the errors to print
        if (difficulty.getValue() == null)
            errorMsg += "difficulty is NULL\n";

        if (Text.getText().equals(""))
            errorMsg += "There is no Text\n";

        boolean check = checkType();

        // if we have a difficulty, a text and the question correct
        if (difficulty.getValue() != null && !Text.getText().equals("") && check)
            return true;

        missingParameters();
        return false;
    }

    /**
     * Check fields depending on type of question
     *
     * @return Assert
     */
    private boolean checkType() {
        if (CorrectAnswer.getText().equals("") || typeQuestion.getValue() == null) {
            if (typeQuestion.getValue() == null)
                errorMsg += "Which type of question do you want?\n";

            if (CorrectAnswer.getText().equals(""))
                errorMsg += "There is no Correct Answer\n";

            return false;
        } else {
            switch ((String) typeQuestion.getValue()) {

                // case TrueFalse : it must true or false
                case "TrueFalse" : {
                    if(CorrectAnswer.getText().equalsIgnoreCase("true") || CorrectAnswer.getText().equalsIgnoreCase("false"))
                        return true;

                    errorMsg += "The answer has to be True or False\n";
                    return false;
                }

                // case MCQ : the answer has to be in the list of the different proposed answers
                case "MCQ" :
                    // each answer is different from the others
                    if(TextAnswer1.getText().equals(TextAnswer2.getText())
                            || TextAnswer1.getText().equals(TextAnswer3.getText())
                            || TextAnswer2.getText().equals(TextAnswer3.getText())) {
                        errorMsg += "You can not have multiple times the same answer !!!\n";
                        return false;
                    }

                    if(CorrectAnswer.getText().equals(TextAnswer1.getText())
                            || CorrectAnswer.getText().equals(TextAnswer2.getText())
                            || CorrectAnswer.getText().equals(TextAnswer3.getText()))
                        return true;

                    errorMsg += "There must be a Correct Answer !\n";
                    return false;

                // case ShortAnswer : if there is an answer it is good
                default :
                    return true;
            }
        }
    }

    private void creatingNewQuestion () {
        AbstractStatement<?> s;
        ListQuestions lq = new ListQuestions(ThemesController.getThemeSelected());
        switch ((String) typeQuestion.getValue()){
            case "TrueFalse":
                // just to be sure the answer corresponds to the json file
                Boolean answer = CorrectAnswer.getText().equalsIgnoreCase("true");
                s = new TrueFalse<>(Text.getText(), answer);
                break;

            case "MCQ":
                s = new MCQ<>(Text.getText(), TextAnswer1.getText(), TextAnswer2.getText(), TextAnswer3.getText(), CorrectAnswer.getText());
                break;

            default:
                s = new ShortAnswer<>(Text.getText(), CorrectAnswer.getText());
                break;
        }
        lq.addQuestion(new Question(s, ThemesController.getThemeSelected(), Difficulty.fromInteger((Integer) difficulty.getValue()) ));
        lq.writeJson(ThemesController.getThemeSelected());
    }

    @FXML
    private void missingParameters() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error in creation of a Question");
        alert.setHeaderText("There are some points you have to modify !");
        alert.setContentText(errorMsg);

        alert.showAndWait();
    }

    /**
     * Change availability of field's answers
     *
     * @param b Availability
     */
    private void setAvailability(boolean b) {
        TextAnswer1.setVisible(b);
        TextAnswer2.setVisible(b);
        TextAnswer3.setVisible(b);

        TextAnswer1.setDisable(!b);
        TextAnswer2.setDisable(!b);
        TextAnswer3.setDisable(!b);

        LabelAnswer1.setVisible(b);
        LabelAnswer2.setVisible(b);
        LabelAnswer3.setVisible(b);

        // if not, we should not be able to write in the TextFields (I reset them to prevent errors)
        if (!b) {
            TextAnswer1.setText("");
            TextAnswer2.setText("");
            TextAnswer3.setText("");
        }
    }
}
