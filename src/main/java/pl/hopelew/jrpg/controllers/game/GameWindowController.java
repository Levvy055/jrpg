package pl.hopelew.jrpg.controllers.game;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import lombok.Getter;
import pl.hopelew.jrpg.Game;
import pl.hopelew.jrpg.utils.Resources;

public class GameWindowController implements Initializable {
	private @FXML BorderPane pane;
	private @Getter static GameWindowController instance;
	private Game game;
	private Pane win;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		instance = this;
		Resources.validate();
		System.out.println("GW Initialized");
	}

	public void initGame(Game game) {
		if (this.game != null) {
			this.game.stop();
		}
		this.game = game;
		this.game.setWindow(this);
		win = new Pane();
		pane.setCenter(win);
		
		sidebar().setAvatar(game.getPlayer().getSex());
	}

	/**
	 * Gets access to Sidebar fxml controller
	 * 
	 * @return
	 */
	private SidebarController sidebar() {
		return SidebarController.getInstance();
	}
}
