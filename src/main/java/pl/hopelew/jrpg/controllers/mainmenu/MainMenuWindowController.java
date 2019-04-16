package pl.hopelew.jrpg.controllers.mainmenu;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import pl.hopelew.jrpg.Main;

public class MainMenuWindowController implements Initializable {
	private @Getter static MainMenuWindowController instance;

	private @FXML VBox startWindow;
	private @FXML BorderPane pane;

	private Node newGameNode;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		instance = this;
		try {
			newGameNode = FXMLLoader.load(getClass().getResource("/fxmls/mainmenu/NewGameWindow.fxml"), resources);
			// newGameNode.disableProperty().addListener((e,oldV,newV)->{if();});
		} catch (IOException e) {
			e.printStackTrace();
			Main.exit();
		}

		// startWindow.setVisible(false);
		new Timer().schedule(new TimerTask() {

			@Override
			public void run() {
				startWindow.setVisible(true);
			}
		}, 3000l);
		System.out.println("MW Initialized");
	}

	/**
	 * Changes the MainWindow subwindow into one specified by sub parameter
	 * @param sub
	 */
	public void showSubWindow(SubWindows sub) {
		switch (sub) {
		case NONE:
			pane.setCenter(null);
			break;
		case CONFIRM:
			break;
		case LOAD:// TODO: implement load game screen
			break;
		case NEW:
			pane.setCenter(newGameNode);
			NewGameWindowController.getInstance().requestFocus();
			break;
		case OPTIONS:// TODO: implement options screen
			break;
		default:
			break;
		}
	}
}
