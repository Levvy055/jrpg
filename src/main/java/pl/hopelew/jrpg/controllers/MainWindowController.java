package pl.hopelew.jrpg.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;
import pl.hopelew.jrpg.Main;

public class MainWindowController implements Initializable {
	private @Getter static MainWindowController instance;

	private @FXML Button btnClose;
	private @FXML Button btnMinimize;
	private @FXML HBox header;
	private @FXML VBox startWindow;
	private @FXML BorderPane pane;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initHeader();

		try {
			Node newGameNode = FXMLLoader.load(getClass().getResource("/pl/hopelew/jrpg/NewGameWindow.fxml"),
					resources);
			newGameNode.setVisible(false);
			newGameNode.setDisable(true);
			pane.setCenter(newGameNode);
		} catch (IOException e) {
			e.printStackTrace();
			Main.exit();
		}

		// startWindow.setVisible(false);
		new Timer().schedule(new TimerTask() {

			@Override
			public void run() {
				startWindow.setVisible(true);
			}
		}, 3000l);
		System.out.println("MW initialized");
	}

	private void initHeader() {
		btnClose.setOnAction(event -> Main.exit());
		btnMinimize.setOnAction(event -> Main.minimize());
		header.setCursor(Cursor.MOVE);
		header.setOnMousePressed(event -> {
			Main.beginDragging(event);
		});

		header.setOnMouseDragged(event -> {
			if (event.isPrimaryButtonDown()) {
				Main.endDragging(event);
			}
		});
	}

	public void showSubWindow(SubWindows sub) {
		switch (sub) {
		case NONE:

			break;
		case CONFIRM:
			break;
		case LOAD:
			break;
		case NEW:
			break;
		case OPTIONS:
			break;
		default:
			break;
		}
	}

	public enum SubWindows {
		NONE, NEW, LOAD, OPTIONS, CONFIRM,
	}
}
