package pl.hopelew.jrpg.controllers.game;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXSpinner;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import pl.hopelew.jrpg.Game;
import pl.hopelew.jrpg.map.MapRenderer;
import pl.hopelew.jrpg.utils.FileHandler;

/**
 * FXML Controller for GameWindow Contains Main Pane and sidebar In main pain we
 * have a Map. The {@link #mapScreenGroup} contains all map graphics (tiles,
 * objects, player, mobs, etc...). Starting from the bottom of the screen = 0:
 * Player's(entity) order is 100. When same order of tile and objects, first are
 * drawn tiles.
 * 
 * @author lluka
 *
 */
@Log4j2
public class GameWindowController implements Initializable {
	private @FXML BorderPane pane;
	private @Getter @FXML Canvas bottomLayer;
	private @Getter @FXML Pane entitiesLayer;
	private @Getter @FXML Canvas upperLayer;
	private @Getter @FXML JFXSpinner mapSpinner;
	private @Getter static GameWindowController instance;
	private Game game;
	private @Getter MapRenderer mapRenderer;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		instance = this;
		FileHandler.validateResourcesAndLoad();
		log.info("GW Initialized");
	}

	/**
	 * Called from Main class
	 * 
	 * @param game
	 */
	public void initGame(Game game) {
		if (this.game != null) {
			this.game.stop();
		}
		this.game = game;
		this.mapRenderer = new MapRenderer(bottomLayer, upperLayer);

		sidebar().init(game.getPlayer());
		this.game.postInitialization(this);
	}

	/**
	 * Shows or hides (depending on show parameter) loading spinner
	 * 
	 * @param show
	 */
	public void showSpinner(boolean show) {
		mapSpinner.setVisible(show);
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
