package pl.hopelew.jrpg.controllers.mainmenu;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import pl.hopelew.jrpg.Main;
import pl.hopelew.jrpg.utils.Strings;

@Log4j2
public class NewGameWindowController implements Initializable {
	private static @Getter NewGameWindowController instance;
	private @FXML Button btnCreate;
	private @FXML Button btnCancel;
	private @FXML ToggleButton tbSex;
	private @FXML TextField tfHeroName;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		instance = this;
		tbSex.setOnAction(event -> {
			tbSex.setText(Strings.get(tbSex.isSelected() ? "female" : "male"));
		});
		tbSex.setText(Strings.get(tbSex.isSelected() ? "female" : "male"));
		btnCancel.setOnAction(event -> MainMenuWindowController.getInstance().showSubWindow(SubWindows.NONE));
		tfHeroName.textProperty().addListener((e, o, n) -> {
			if (!StringUtils.isBlank(n)) {
				btnCreate.setDisable(n.trim().length() < 3);
			} else {
				btnCreate.setDisable(true);
			}
		});
		btnCreate.setOnAction(event -> {
			try {
				Main.startNewGame(tfHeroName.getText(), !tbSex.isSelected());
			} catch (Exception e1) {
				e1.printStackTrace();
				log.throwing(Level.ERROR, e1);
			}
		});
	}

	/**
	 * Moves focus to text field of Hero Name
	 */
	public void requestFocus() {
		tfHeroName.requestFocus();
	}

}
