package Scenes.ThemeSelection;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import oo.Questions.Themes;

import java.io.File;
import java.util.ArrayList;

public class ThemeSelectionController {
	@FXML
	public VBox choicesContainer;
	public ToggleGroup toggleGroup;

	private Themes themes;

	@FXML
	private void initialize() {
		toggleGroup = new ToggleGroup();

		ArrayList<String> filesNames = new ArrayList<>();
		themes = new Themes();
		themes.readThemes();

		for (int i = 0; i < themes.getSize(); i++) {
			filesNames.add(themes.getAtIndex(i));
		}

		for (String fileName : filesNames) {
			ToggleButton tb = new ToggleButton(fileName.substring(0, 1).toUpperCase() + fileName.substring(1));
			tb.setPrefWidth(10000);
			tb.setToggleGroup(toggleGroup);
			choicesContainer.getChildren().add(tb);
		}
	}
	
	@FXML
	public void onPressedSelectButton (ActionEvent actionEvent) {
		ToggleButton selectedToggle = (ToggleButton) toggleGroup.getSelectedToggle();
		if (selectedToggle == null) return;
		
		String themeSelected = selectedToggle.getText();
		File fileSelected = new File ("resources/" + themeSelected.toLowerCase() + ".json");
		System.out.println(fileSelected);
		
		// TODO: what after selection?
	}
	
	private ArrayList<String> getFilesNameInFolder (final File folder) {
		ArrayList<String> filesNames = new ArrayList<String>();
		for (final File fileEntry : folder.listFiles()) {
			if (!fileEntry.isDirectory()) {
				filesNames.add(fileEntry.getName());
			}
		}
		return filesNames;
	}
}
