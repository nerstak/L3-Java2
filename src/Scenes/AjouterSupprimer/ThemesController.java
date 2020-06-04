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
        int i = 0;
        for(Tab tab : themesInterface.getTabs())
        {
            if(i < themes.getSize())
                tab.setText(themes.getAtIndex(i++));
        }
    }

}
