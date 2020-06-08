package Scenes.ModifyQuestions;

import Project.Main;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import oo.Questions.ListQuestions;
import oo.Questions.Question;
import oo.Questions.Themes;

import oo.Game.Difficulty;


public class ThemesController {
    private Themes themes = new Themes();
    public static String themeSelected = "";

    @FXML
    private TabPane themesInterface;
    @FXML
    private TextField deleting;
    @FXML
    private ComboBox<String> level;


    public ThemesController() {
        themes.readThemes();
    }

    @FXML
    private void initialize() {
        deleting.setText("");
        level.getItems().addAll(" ", "easy", "medium", "hard");

        for(int i = 0; i < themes.getSize(); i++) {
            Tab tab = new Tab(themes.getAtIndex(i));
            ListQuestions listQuestions = new ListQuestions(themes.getAtIndex(i));
            tab.setContent(instantiateTab(listQuestions));
            themesInterface.getTabs().add(tab);
        }
    }

    @FXML
    private void changeLevel() {
        Difficulty levelDifficulty = Difficulty.fromString(level.getValue());
        if (levelDifficulty == null) {
            for (int i = 0; i < themes.getSize(); i++) {
                Tab tab = themesInterface.getTabs().get(i);
                ListQuestions listQuestions = new ListQuestions(themes.getAtIndex(i));
                tab.setContent(instantiateTab(listQuestions));
            }
        } else {
            for (int i = 0; i < themes.getSize(); i++) {
                Tab tab = themesInterface.getTabs().get(i);
                ListQuestions listQuestions = new ListQuestions(themes.getAtIndex(i));
                ListQuestions listToSend = listQuestions;

                for (int j = 0; j < listQuestions.getList().size(); j++) {
                    if(listQuestions.get(j).getDifficulty() != levelDifficulty) {
                        listToSend.deleteQuestion(j);
                        j = -1;
                    }
                }
                tab.setContent(instantiateTab(listToSend));
            }
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
            ListQuestions listToShow = new ListQuestions(themeSelected);

            // have a list corresponding of the table
            switch (level.getSelectionModel().getSelectedIndex()) {
                case 1: {
                    for(int i = 0; i < listToShow.getList().size(); i++)
                    {
                        if(listToShow.getList().get(i).getDifficulty() != Difficulty.easy)
                        {
                            listToShow.deleteQuestion(i);
                            i = -1;
                        }
                    }
                    break;
                }

                case 2: {
                    for(int i = 0; i < listToShow.getList().size(); i++)
                    {
                        if(listToShow.getList().get(i).getDifficulty() != Difficulty.medium)
                        {
                            listToShow.deleteQuestion(i);
                            i = -1;
                        }
                    }
                    break;
                }

                case 3: {
                    for(int i = 0; i < listToShow.getList().size(); i++)
                    {
                        if(listToShow.getList().get(i).getDifficulty() != Difficulty.hard)
                        {
                            listToShow.deleteQuestion(i);
                            i = -1;
                        }
                    }
                    break;
                }

                default: {
                    break;
                }
            }

            try {
                if(Integer.parseInt(deleting.getText()) > 0 && Integer.parseInt(deleting.getText()) <= listToShow.getList().size()) {

                    int i = 0;
                    while(!listToShow.getList().get(Integer.parseInt(deleting.getText()) - 1).getStatement().getText().equals(listQuestions.getList().get(i).getStatement().getText()))
                        i++;

                    listQuestions.deleteQuestion(i);
                    //listQuestions.deleteQuestion(Integer.parseInt(deleting.getText()) - 1);
                    deleting.setText("");
                    level.getSelectionModel().select(0);
                    listQuestions.writeJson(themeSelected);

                    tab.setContent(instantiateTab(listQuestions));
                }
                else
                    missingParameters("This Question does not exist, check again its index (from 1 to " + listToShow.getList().size() + ")");
            } catch (NumberFormatException e) {
                missingParameters("You must request the NUMBER of the Question");
            }

        }
        else {
            missingParameters("You have to choose the Question you want to delete");
        }
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
