package pl.hopelew.jrpg.controllers.game;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import lombok.Getter;
import pl.hopelew.jrpg.Game;
import pl.hopelew.jrpg.utils.FileHandler;
import pl.hopelew.jrpg.utils.eventhandlers.EventType;
import pl.hopelew.jrpg.utils.eventhandlers.MapChangedGameEvent;

public class GameWindowController implements Initializable {
	private @FXML BorderPane pane;
	private @Getter static GameWindowController instance;
	private Game game;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		instance = this;
		FileHandler.validateResourcesAndLoad();
		System.out.println("GW Initialized");
	}

	public void initGame(Game game) {
		if (this.game != null) {
			this.game.stop();
		}
		this.game = game;
		this.game.setWindow(this);
		this.game.addListener(EventType.MAP_CHANGED, e -> {
					pane.setCenter(((MapChangedGameEvent)e).getMap().getGrid());
		});

		sidebar().init(game.getPlayer());
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
