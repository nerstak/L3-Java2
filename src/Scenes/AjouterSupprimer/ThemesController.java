package Scenes.AjouterSupprimer;

import Project.Main;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import oo.Questions.ListQuestions;
import oo.Questions.Question;
import oo.Questions.Themes;

import java.util.LinkedList;

public class ThemesController {
    private Themes themes = new Themes();
    public static String themeSelected = "";

    @FXML
    private TabPane themesInterface;
    private TableView<Question<?>> table;


    @FXML
    private void initialize() {

        for(int i = 0; i < themes.getSize(); i++) {
            // TODO: 05/06/2020 : create the tabs in the code and not in the .fxlm (if we do not have 10 themes for some reasons) 
            Tab tab = themesInterface.getTabs().get(i);
            tab.setText(themes.getAtIndex(i));

            TableView<Question<?>> questionTable = new TableView<Question<?>>();
            questionTable.setPrefHeight(320);
            questionTable.setEditable(false);
            questionTable.setId(tab.getText() + "Table");

            TableColumn<Question<?>, String> typeColumn = new TableColumn<Question<?>, String>("Type");
            TableColumn<Question<?>, String> difficultyColumn = new TableColumn<Question<?>, String>("Difficulty");
            TableColumn<Question<?>, String> questionColumn = new TableColumn<Question<?>, String>("Questions");

            ListQuestions listQuestions = new ListQuestions(themes.getAtIndex(i));
            questionTable.setItems(FXCollections.observableArrayList(listQuestions.getList()));
            typeColumn.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getStatement().getInstance())));


            difficultyColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDifficulty().toString()));
            questionColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getStatement().getText()));

            typeColumn.setResizable(false);
            difficultyColumn.setResizable(false);
            questionColumn.setResizable(false);

            questionTable.getColumns().addAll(typeColumn, difficultyColumn, questionColumn);

            questionColumn.setPrefWidth(800);


            tab.setContent(questionTable);
        }
    }

    @FXML
    private void handleButtonAdd(){
        Tab tab = themesInterface.getSelectionModel().getSelectedItem();
        themeSelected = tab.getText();
        Main.sceneManager.activate("AddQuestion");
    }

    @FXML
    private void handleButtonDelete() {

        Tab tab = themesInterface.getSelectionModel().getSelectedItem();
        themeSelected = tab.getText();
        table = (TableView<Question<?>>) tab.getContent().lookup(themeSelected + "Table");
        ListQuestions listQuestions = new ListQuestions(themeSelected);

        //if(table.getSelectionModel().getSelectedIndex() != -1)
        {
            //Node content = tab.getContent();
            //table = content.lookup("#" + themeSelected + "Table");

            //listQuestions.deleteQuestion(table.getSelectionModel().getSelectedIndex());
        }

       // System.out.println(table.getSelectionModel().getSelectedIndex());
    }

    @FXML
    private void handleButtonBack() {Main.sceneManager.activate("Starting");}

    // TODO: 06/06/2020 faire la fonction delete 
    
    public static String getThemeSelected(){return themeSelected;}
}
