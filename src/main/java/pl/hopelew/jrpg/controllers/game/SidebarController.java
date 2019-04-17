package pl.hopelew.jrpg.controllers.game;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import lombok.Getter;
import pl.hopelew.jrpg.entities.Player.Sex;
import pl.hopelew.jrpg.utils.Res;
import pl.hopelew.jrpg.utils.Resources;

public class SidebarController implements Initializable {
	private static @Getter SidebarController instance;

	private @FXML ImageView avatar;
	private @FXML GridPane eqGrid;
	private @FXML GridPane invGrid;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		instance = this;
	}

	public void setAvatar(Sex sex) {
		avatar.setImage(Resources.getFxImage(sex == Sex.MALE ? Res.HERO_AVATAR_MALE : Res.HERO_AVATAR_FEMALE));
	}
}
