package pl.hopelew.jrpg.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import pl.hopelew.jrpg.utils.Strings;

public class NewGameWindowController implements Initializable {
	private @FXML Button btnCreate;
	private @FXML ToggleButton tbSex;
	private @FXML TextField tfHeroName;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tbSex.setOnAction(event -> {
			tbSex.setText(Strings.get(tbSex.isSelected() ? "female" : "male")); 
		});
		tbSex.setText(Strings.get(tbSex.isSelected() ? "female" : "male"));
		
	}

}
