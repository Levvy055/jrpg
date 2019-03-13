package pl.hopelew.jrpg;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import pl.hopelew.jrpg.utils.Strings;

public class MainWindowController implements Initializable {
	private static final Stage STAGE = Main.getInstance().getStage();
	@FXML private Button btnClose;
	@FXML private Button btnMinimize;
	@FXML private HBox header;
	private double initX;
	private double initY;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initHeader();
		btnClose.setOnMouseClicked(event -> STAGE.close());
		btnMinimize.setOnMouseClicked(event -> STAGE.setIconified(true));
		System.out.println("MW initialized");
		System.out.println(Strings.get("hi"));
	}

	private void initHeader() {
		header.setOnMousePressed(event -> {
            initX = event.getSceneX();
            initY = event.getSceneY();  
        });

		header.setOnMouseDragged(event -> {
            if (event.isPrimaryButtonDown()) {
            	STAGE.setX(event.getScreenX() - initX);
            	STAGE.setY(event.getScreenY() - initY);
                header.setCursor(Cursor.MOVE);
            }  
        });
	}

}
