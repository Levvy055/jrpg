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
import lombok.Getter;
import pl.hopelew.jrpg.utils.Strings;

public class Main extends Application {

	private static Main instance;
	private @Getter Stage stage;
	private static double initX;
	private static double initY;

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.stage = primaryStage;
		instance = this;
		// Platform.setImplicitExit(false);
		Thread.currentThread().setName("JavaFx Thread");

		URL uri = getClass().getResource("/pl/hopelew/jrpg/MainWindow.fxml");
		Parent root = FXMLLoader.load(uri);

		stage.setTitle("jRPG NoName");
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
		instance.getStage().close();
	}

	public static void minimize() {
		instance.getStage().setIconified(true);
	}

	public static void beginDragging(MouseEvent event) {
		initX = event.getSceneX();
		initY = event.getSceneY();
	}

	public static void endDragging(MouseEvent event) {
		instance.getStage().setX(event.getScreenX() - initX);
		instance.getStage().setY(event.getScreenY() - initY);
	}

}
