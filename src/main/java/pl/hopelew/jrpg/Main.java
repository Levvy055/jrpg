package pl.hopelew.jrpg;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Main extends Application {

	private static Main instance;
	private Stage primaryStage;

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		instance = this;
		Platform.setImplicitExit(false);
		Thread.currentThread().setName("JavaFx Thread");
		Group root = new Group();
		Scene scene = new Scene(root, 300, 250);
		Button btn = new Button();
		btn.setLayoutX(100);
		btn.setLayoutY(80);
		btn.setText("Hello World");
		btn.setOnAction(actionEvent -> System.out.println("Hello World"));
		root.getChildren().add(btn);
		primaryStage.setScene(scene);
		primaryStage.show();
		System.out.println("a");
	}

	public static void main(String[] args) {
		try {
			Application.launch(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
