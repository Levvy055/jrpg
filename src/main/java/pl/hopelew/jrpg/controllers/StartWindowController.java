package pl.hopelew.jrpg.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import pl.hopelew.jrpg.Main;
import pl.hopelew.jrpg.controllers.MainWindowController.SubWindows;

public class StartWindowController implements Initializable {
	private @FXML Button btnNewGame;
	private @FXML Button btnLoadGame;
	private @FXML Button btnOptions;
	private @FXML Button btnExit;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnExit.setOnAction(event -> Main.exit());
		btnNewGame.setOnAction(event -> MainWindowController.getInstance().showSubWindow(SubWindows.NEW));
		btnLoadGame.setOnAction(event -> MainWindowController.getInstance().showSubWindow(SubWindows.LOAD));
		btnOptions.setOnAction(event -> MainWindowController.getInstance().showSubWindow(SubWindows.OPTIONS));
		btnOptions.setDisable(true);
		btnLoadGame.setDisable(true);
	}

}
