package pl.hopelew.jrpg;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pl.hopelew.jrpg.utils.Strings;

public class MainWindowController implements Initializable {
	@FXML
	private Button btnClose;
	@FXML
	private Button btnMinimize;
	@FXML
	private HBox header;
	@FXML
	private VBox startWindow;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initHeader();
		btnClose.setOnMouseClicked(event -> Main.exit());
		btnMinimize.setOnMouseClicked(event -> Main.minimize());
		header.setCursor(Cursor.MOVE);
		// startWindow.setVisible(false);
		System.out.println("MW initialized");
		System.out.println(Strings.get("hi"));
		new Timer().schedule(new TimerTask() {

			@Override
			public void run() {
				startWindow.setVisible(true);
			}
		}, 3000l);
	}

	private void initHeader() {
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
