package pl.hopelew.jrpg.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import pl.hopelew.jrpg.Main;

public class StartWindowController implements Initializable {
	private @FXML Button btnNewGame;
	private @FXML Button btnExit;
	private @FXML Button btnLoadGame;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnExit.setOnAction(event -> Main.exit());
		btnNewGame.setOnAction(event -> Main.startNewGame());
		btnLoadGame.setOnAction(event -> Main.loadGame());
	}

}
