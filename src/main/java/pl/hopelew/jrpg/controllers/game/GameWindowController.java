package pl.hopelew.jrpg.controllers.game;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXSpinner;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import pl.hopelew.jrpg.Game;
import pl.hopelew.jrpg.entities.Entity;
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
	private @Getter static GameWindowController instance;
	private @FXML BorderPane pane;
	private @Getter @FXML JFXSpinner mapSpinner;
	private @Getter @FXML Canvas bottomLayer;
	private @Getter @FXML Pane entitiesLayer;
	private @Getter @FXML Canvas upperLayer;
	private @Getter MapRenderer mapRenderer;
	private @Getter Scene scene;
	private Game game;

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

		sidebar().init(game);
		scene = pane.getScene();
		this.game.postInitialization(this);
	}

	public void addEntitySprite(Entity entity) {
		var sprite = entity.getSprite();
		if (!entitiesLayer.getChildren().contains(sprite)) {
			Platform.runLater(() -> {
				entitiesLayer.getChildren().add(sprite);
			});
		}
	}

	public void removeEntitySprite(Entity entity) {
		var sprite = entity.getSprite();
		if (entitiesLayer.getChildren().contains(sprite)) {
			Platform.runLater(() -> {
				entitiesLayer.getChildren().remove(sprite);
			});
		}
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
