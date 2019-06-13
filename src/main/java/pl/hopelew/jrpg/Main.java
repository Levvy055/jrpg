package pl.hopelew.jrpg;

import java.util.Locale;

import javafx.application.Application;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.extern.log4j.Log4j2;
import pl.hopelew.jrpg.controllers.game.GameWindowController;
import pl.hopelew.jrpg.entities.Player;
import pl.hopelew.jrpg.entities.data.Sex;
import pl.hopelew.jrpg.utils.Strings;

/**
 * Start class of App with main method.
 * 
 * @author lluka
 *
 */
@Log4j2
public class Main extends Application {
	private static Main instance;
	private Stage stage;
	private static double initX, initY;
	private static Game game;
	private static Thread gameThread;
	private Scene sceneMainMenu, sceneGame;

	/**
	 * Initializes locale translations
	 * 
	 * @param args
	 */
	private static void initLocale(String[] args) {
		Locale locale = Locale.getDefault();
		Strings.init(locale);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.stage = primaryStage;
		instance = this;
		// Platform.setImplicitExit(false);

		stage.setTitle(Strings.get("title"));
		stage.setWidth(1100);
		stage.setHeight(900);
		if (Platform.isSupported(ConditionalFeature.TRANSPARENT_WINDOW)) {
			stage.initStyle(StageStyle.TRANSPARENT);
		} else {
			stage.initStyle(StageStyle.UTILITY);
		}

		var mmUri = getClass().getResource("/fxmls/mainmenu/MainWindow.fxml");
		Parent mmRoot = FXMLLoader.load(mmUri, Strings.currentBundle());
		sceneMainMenu = new Scene(mmRoot);
		sceneMainMenu.getStylesheets().add(getClass().getResource("/css/custom.css").toExternalForm());

		var mgUri = getClass().getResource("/fxmls/game/GameWindow.fxml");
		Parent mgRoot = FXMLLoader.load(mgUri, Strings.currentBundle());
		sceneGame = new Scene(mgRoot);
		sceneGame.getStylesheets().add(getClass().getResource("/css/custom.css").toExternalForm());

		stage.setScene(sceneMainMenu);
		stage.show();

		// ScenicView.show(sceneGame);
		log.info("MW Ready");
		new Thread(() -> {
			try {
				Thread.sleep(500);
				startNewGame("Levvy", true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();// TODO: remove in production
	}

	/**
	 * Closes the game
	 */
	public static void exit() {
		log.info("Closing the game");
		if (game != null) {
			game.stop();
		}
		instance.stage.close();
	}

	/**
	 * Hides game in system tray
	 */
	public static void minimize() {
		instance.stage.setIconified(true);
	}

	/**
	 * Called on the start of window dragging (header clicked)
	 * 
	 * @param event
	 */
	public static void beginDragging(MouseEvent event) {
		initX = event.getSceneX();
		initY = event.getSceneY();
	}

	/**
	 * Called at the end of dragging to reposition window to new screen position
	 * 
	 * @param event
	 */
	public static void endDragging(MouseEvent event) {
		instance.stage.setX(event.getScreenX() - initX);
		instance.stage.setY(event.getScreenY() - initY);
	}

	private void goToMainMenuScene() {
		stage.setScene(sceneMainMenu);
	}

	private void goToGameScene() {
		Platform.runLater(() -> stage.setScene(sceneGame));
		GameWindowController.getInstance().initGame(game);
	}

	/**
	 * Starts new game with player data given in parameters
	 * 
	 * @param name
	 * @param isMale
	 * @throws Exception
	 */
	public static void startNewGame(String name, boolean isMale) throws Exception {
		var player = new Player(name, isMale ? Sex.MALE : Sex.FEMALE);
		game = new Game(player);
		gameThread = new Thread(game, "Game Loop Thread");
		gameThread.start();
		instance.goToGameScene();
	}

	/**
	 * Loads saved game from save file
	 */
	public static void loadGame() {
		// TODO Auto-generated method stub
	}

	/**
	 * Main start method of the game
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Thread.currentThread().setName("Main Thread");
		try {
			initLocale(args);
			Application.launch(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
