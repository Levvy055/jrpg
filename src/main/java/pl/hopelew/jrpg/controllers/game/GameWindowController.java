package pl.hopelew.jrpg.controllers.game;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

public class GameWindowController implements Initializable {
	private @FXML BorderPane pane;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("GW initialized");
	}

}
