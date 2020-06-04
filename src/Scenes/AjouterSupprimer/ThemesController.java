package Scenes.AjouterSupprimer;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import oo.Questions.ListQuestions;
import oo.Questions.Question;
import oo.Questions.Themes;

public class ThemesController {
    private Themes themes = new Themes();

    @FXML
    public TabPane themesInterface;

    @FXML
    private void initialize() {
        for(int i =0; i < themes.getSize(); i++)
        {
            Tab tab = new Tab(themes.getAtIndex(i));

            TableView<ListQuestions> tableView = new TableView();
            //TableColumn<int, Question> tableColumn = new TableColumn<int, Question>();


            themesInterface.getTabs().add(tab);
        }
    }

}
