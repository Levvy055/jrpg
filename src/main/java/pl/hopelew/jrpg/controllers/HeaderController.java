package pl.hopelew.jrpg.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import pl.hopelew.jrpg.Main;

public class HeaderController implements Initializable {
	private @FXML HBox header;
	private @FXML Button btnClose;
	private @FXML Button btnMinimize;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
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

}
