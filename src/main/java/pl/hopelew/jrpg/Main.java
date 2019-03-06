package pl.hopelew.jrpg;

import java.net.URL;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

	private static Main instance;
	private Stage primaryStage;

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		instance = this;
		Platform.setImplicitExit(false);
		Thread.currentThread().setName("JavaFx Thread");
		primaryStage.setTitle("jRPG NoName");
		primaryStage.setWidth(1100);
		primaryStage.setHeight(900);
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		URL uri = getClass().getResource("/pl/hopelew/jrpg/MainWindow.fxml");
		Parent root=FXMLLoader.load(uri);
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
		System.out.println("end main thread");
	}

	public static void main(String[] args) {
		try {
			Application.launch(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
