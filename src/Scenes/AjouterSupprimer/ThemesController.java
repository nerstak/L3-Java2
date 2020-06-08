package Scenes.AjouterSupprimer;

import Project.Main;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import oo.Questions.ListQuestions;
import oo.Questions.Question;
import oo.Questions.Themes;


public class ThemesController {
    private Themes themes = new Themes();
    public static String themeSelected = "";

    @FXML
    private TabPane themesInterface;
    @FXML
    private TextField deleting;


    @FXML
    private void initialize() {
        deleting.setText("");

        for(int i = 0; i < themes.getSize(); i++) {
            Tab tab = new Tab(themes.getAtIndex(i));
            ListQuestions listQuestions = new ListQuestions(themes.getAtIndex(i));
            tab.setContent(instantiateTab(listQuestions));
            themesInterface.getTabs().add(tab);
        }
    }

    private TableView instantiateTab(ListQuestions listQuestions) {

        TableView<Question<?>> questionTable = new TableView<Question<?>>();
        questionTable.setPrefHeight(320);
        questionTable.setEditable(false);

        TableColumn<Question<?>, String> typeColumn = new TableColumn<Question<?>, String>("Type");
        TableColumn<Question<?>, String> difficultyColumn = new TableColumn<Question<?>, String>("Difficulty");
        TableColumn<Question<?>, String> questionColumn = new TableColumn<Question<?>, String>("Questions");

        questionTable.setItems(FXCollections.observableArrayList(listQuestions.getList()));
        typeColumn.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getStatement().getInstance())));
        difficultyColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDifficulty().toString()));
        questionColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getStatement().getText()));

        typeColumn.setResizable(false);
        difficultyColumn.setResizable(false);
        questionColumn.setResizable(false);
        questionColumn.setPrefWidth(755);

        questionTable.getColumns().addAll(typeColumn, difficultyColumn, questionColumn);
        return questionTable;
    }

    @FXML
    private void handleButtonAdd(){
        Tab tab = themesInterface.getSelectionModel().getSelectedItem();
        themeSelected = tab.getText();
        Main.sceneManager.activate("AddQuestion");
    }

    @FXML
    private void handleButtonDelete() {

        if(!deleting.getText().equals("")) {
            Tab tab = themesInterface.getSelectionModel().getSelectedItem();
            themeSelected = tab.getText();
            ListQuestions listQuestions = new ListQuestions(themeSelected);

// TODO: 08/06/2020 check the String can be converted into an int
            
            if(Integer.parseInt(deleting.getText()) > 0 && Integer.parseInt(deleting.getText()) <= listQuestions.getList().size()) {
                listQuestions.deleteQuestion(Integer.parseInt(deleting.getText()) - 1);
                deleting.setText("");
                listQuestions.writeJson(themeSelected);

                tab.setContent(instantiateTab(listQuestions));
            }
            else
                missingParameters("This Question does not exist, check again its index (from 1 to " + listQuestions.getList().size() + ")");

        }
        else
            missingParameters("You have to choose the Question you want to delete");
    }

    @FXML
    private void handleButtonBack() {Main.sceneManager.activate("Starting");}
    
    public static String getThemeSelected(){return themeSelected;}

    @FXML
    private void missingParameters(String errorMsg){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error while deleting a Question");
        alert.setHeaderText("There are some points you have to modify !");
        alert.setContentText(errorMsg);

        alert.showAndWait();
    }
}
