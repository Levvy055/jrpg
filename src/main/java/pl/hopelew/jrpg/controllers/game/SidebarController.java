package pl.hopelew.jrpg.controllers.game;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXProgressBar;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import lombok.Getter;
import pl.hopelew.jrpg.Game;
import pl.hopelew.jrpg.entities.data.Sex;
import pl.hopelew.jrpg.utils.FileHandler;
import pl.hopelew.jrpg.utils.Res;
import pl.hopelew.jrpg.utils.eventhandlers.EventType;
import pl.hopelew.jrpg.utils.eventhandlers.ValueChangedGameEvent;

public class SidebarController implements Initializable {
	private static @Getter SidebarController instance;

	private @FXML ImageView avatar;
	private @FXML GridPane eqGrid;
	private @FXML GridPane invGrid;

	private @FXML Label lblHp;
	private @FXML JFXProgressBar pbHp;

	private @FXML Label lblMp;
	private @FXML JFXProgressBar pbMp;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		instance = this;
	}

	/**
	 * Initializes sidebar with player data and adds listeners to it.
	 * 
	 * @param player
	 */
	public void init(Game game) {
		var player = game.getPlayer();
		setAvatar(player.getSex());
		setHp(player.getHp());
		pbHp.setSecondaryProgress(player.getHp() / 100d);
		game.addListener(EventType.HP_CHANGED, ge -> {
			var vge = (ValueChangedGameEvent) ge;
			Platform.runLater(() -> {
				setHp((double) vge.getNewValue());
				pbHp.setSecondaryProgress((double) vge.getOldValue() / 100d);
			});
		}, player);
		game.addListener(EventType.MP_CHANGED, ge -> {
			var vge = (ValueChangedGameEvent) ge;
			Platform.runLater(() -> {
				setMp((double) vge.getNewValue());
				pbMp.setSecondaryProgress((double) vge.getOldValue() / 100d);
			});
		}, player);
	}

	private void setHp(double hp) {
		pbHp.setProgress(hp / 100d);
		lblHp.setText(hp + "");
	}

	private void setMp(double mp) {
		pbMp.setProgress(mp / 100d);
		lblMp.setText(mp + "");
	}

	private void setAvatar(Sex sex) {
		avatar.setImage(FileHandler.getFxImage(sex == Sex.MALE ? Res.HERO_AVATAR_MALE : Res.HERO_AVATAR_FEMALE));
	}

}
