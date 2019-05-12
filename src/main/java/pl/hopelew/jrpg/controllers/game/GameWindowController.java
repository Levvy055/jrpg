package pl.hopelew.jrpg.controllers.game;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import pl.hopelew.jrpg.Game;
import pl.hopelew.jrpg.utils.FileHandler;
import pl.hopelew.jrpg.utils.eventhandlers.EventType;
import pl.hopelew.jrpg.utils.eventhandlers.MapChangedGameEvent;

@Log4j2
public class GameWindowController implements Initializable {
	private @FXML BorderPane pane;
	private @FXML Canvas canvas;
	private @Getter static GameWindowController instance;
	private Game game;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		instance = this;
		FileHandler.validateResourcesAndLoad();
		log.info("GW Initialized");
	}

	public void initGame(Game game) {
		if (this.game != null) {
			this.game.stop();
		}
		this.game = game;
		this.game.setWindow(this);
		this.game.addListener(EventType.MAP_CHANGED, e -> {
			MapChangedGameEvent mce = (MapChangedGameEvent) e;
			try {
				mce.getMap().draw(canvas.getGraphicsContext2D());
			} catch (Exception e1) {
				e1.printStackTrace();
				log.throwing(e1);
			}
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
