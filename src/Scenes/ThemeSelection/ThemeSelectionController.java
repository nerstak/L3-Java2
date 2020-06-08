package Scenes.ThemeSelection;

import Project.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import oo.Questions.Themes;

import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class ThemeSelectionController {
	@FXML
	public VBox choicesContainer;
	private final ArrayList<String> themesName;
	public AnchorPane tableAnchor;

	public ThemeSelectionController() {
		themesName = new ArrayList<>();
		Themes themes = Main.game.getNextThemes();

		for (int i = 0; i < Main.game.getNextThemes().getSize(); i++) {
			themesName.add(themes.getAtIndex(i));
		}
	}

	@FXML
	private void initialize() {
		// Table of player
		try {
			tableAnchor.getChildren().setAll(
					(Node) FXMLLoader.load(
							(URL) Main.sceneManager.getSceneUrl("TablePlayer")));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Placing buttons
		AtomicInteger i = new AtomicInteger();
		for (String fileName : themesName) {
			Button tb = new Button(fileName.substring(0, 1).toUpperCase() + fileName.substring(1));
			tb.setPrefWidth(150);
			tb.setPrefHeight(40);
			tb.setPadding(new Insets(5, 10, 5, 10));

			tb.setOnMouseClicked(e -> {
				Main.game.loadQuestion(i.getAndIncrement());
			});

			choicesContainer.getChildren().add(tb);
		}
	}
}
