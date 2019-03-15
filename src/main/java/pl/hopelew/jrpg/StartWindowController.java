package pl.hopelew.jrpg;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class StartWindowController implements Initializable {
	@FXML private Button btnNewGame;
	@FXML private Button btnExit;
	@FXML private Button btnLoadGame;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnExit.setOnMouseClicked(event->Main.exit());
		btnNewGame.setOnMouseClicked(event->Main.exit());
	}

}
