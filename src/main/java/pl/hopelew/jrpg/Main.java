package pl.hopelew.jrpg;

import java.net.URL;
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
import pl.hopelew.jrpg.utils.Strings;

public class Main extends Application {
	private static Main instance;
	private Stage stage;
	private static double initX;
	private static double initY;

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.stage = primaryStage;
		instance = this;
		// Platform.setImplicitExit(false);
		Thread.currentThread().setName("JavaFx Thread");

		URL uri = getClass().getResource("/pl/hopelew/jrpg/MainWindow.fxml");
		Parent root = FXMLLoader.load(uri, Strings.currentBundle());

		stage.setTitle(Strings.get("title"));
		stage.setWidth(1100);
		stage.setHeight(900);
		if (Platform.isSupported(ConditionalFeature.TRANSPARENT_WINDOW)) {
			stage.initStyle(StageStyle.TRANSPARENT);
		} else {
			stage.initStyle(StageStyle.UTILITY);
		}

		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/css/custom.css").toExternalForm());
		stage.setScene(scene);
		stage.show();

		// ScenicView.show(scene);
		System.out.println("Main thread finished");
	}

	public static void main(String[] args) {
		try {
			Thread.currentThread().setName("Main Thread");
			initConfig(args);
			Application.launch(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void initConfig(String[] args) {
		Locale locale = Locale.getDefault();
		Strings.init(locale);
	}

	public static void exit() {
		instance.stage.close();
	}

	public static void minimize() {
		instance.stage.setIconified(true);
	}

	public static void beginDragging(MouseEvent event) {
		initX = event.getSceneX();
		initY = event.getSceneY();
	}

	public static void endDragging(MouseEvent event) {
		instance.stage.setX(event.getScreenX() - initX);
		instance.stage.setY(event.getScreenY() - initY);
	}

	public static void startNewGame() {
		// TODO Auto-generated method stub
	}

	public static void loadGame() {
		// TODO Auto-generated method stub
	}

}
