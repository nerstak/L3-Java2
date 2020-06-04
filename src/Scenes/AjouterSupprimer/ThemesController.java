package Scenes.AjouterSupprimer;

import Project.Main;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import oo.Players.PlayerStatus;
import oo.Questions.ListQuestions;
import oo.Questions.Question;
import oo.Questions.Themes;

import java.awt.*;

public class ThemesController {
    private Themes themes = new Themes();

    @FXML
    private TabPane themesInterface;

    @FXML
    private void initialize() {
        int i = 0;
        // TODO: 04/06/2020 : change the foreach to create the number of tabs in function of the number of themes
        for(Tab tab : themesInterface.getTabs())
        {
            if(i < themes.getSize())
            {
                tab.setText(themes.getAtIndex(i++));


// TODO: 04/06/2020 : The aim is to create automatically the table filled with all the questions
/*
                listQuestions = new ListQuestions(themes.getAtIndex(i++));
                TableView questionTable = new TableView();
                TableColumn<ListQuestions, String> questionColumn = new TableColumn("Questions");
                questionColumn.setPrefWidth(790);
                questionColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.toString()));
                questionTable.setItems(FXCollections.observableArrayList(listQuestions));
                questionTable.getColumns().add(questionColumn);tab.setContent(questionTable);
*/

            }
        }
    }

    @FXML
    private void handleButtonAdd(){Main.sceneManager.activate("AddQuestion");}

}
