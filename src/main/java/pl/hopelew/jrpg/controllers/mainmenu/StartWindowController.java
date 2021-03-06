package pl.hopelew.jrpg.controllers.mainmenu;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import pl.hopelew.jrpg.Main;

public class StartWindowController implements Initializable {
	private @FXML Button btnNewGame;
	private @FXML Button btnLoadGame;
	private @FXML Button btnOptions;
	private @FXML Button btnExit;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnExit.setOnAction(event -> Main.exit());
		btnNewGame.setOnAction(event -> MainMenuWindowController.getInstance().showSubWindow(SubWindows.NEW));
		btnLoadGame.setOnAction(event -> MainMenuWindowController.getInstance().showSubWindow(SubWindows.LOAD));
		btnOptions.setOnAction(event -> MainMenuWindowController.getInstance().showSubWindow(SubWindows.OPTIONS));
		btnOptions.setDisable(true);
		btnLoadGame.setDisable(true);
	}

}
