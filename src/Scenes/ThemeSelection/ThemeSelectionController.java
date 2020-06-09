package Scenes.ThemeSelection;

import Project.Main;
import Scenes.Questions.Question;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import oo.Questions.Themes;

import java.util.ArrayList;

/**
 * Controller for the Theme selection in phase2
 */
public class ThemeSelectionController {
	@FXML
	public VBox choicesContainer;
	private final ArrayList<String> themesName;
	public AnchorPane tableAnchor;
	public AnchorPane topAnchor;

	public ThemeSelectionController() {
		themesName = new ArrayList<>();
		Themes themes = Main.game.getNextThemes();

		for (int i = 0; i < Main.game.getNextThemes().getSize(); i++) {
			themesName.add(themes.getAtIndex(i));
		}
	}

	@FXML
	private void initialize() {
		// Subscenes
		Question.loadContentBar(tableAnchor, topAnchor);

		// Placing buttons
		int i = 0;
		for (String fileName : themesName) {
			Button tb = new Button(fileName.substring(0, 1).toUpperCase() + fileName.substring(1));
			tb.setPrefWidth(150);
			tb.setPrefHeight(40);
			tb.setPadding(new Insets(5, 10, 5, 10));

			int finalI = i;
			tb.setOnMouseClicked(e -> {
				Main.game.loadQuestion(finalI);
			});

			i++;
			choicesContainer.getChildren().add(tb);
		}
	}
}
