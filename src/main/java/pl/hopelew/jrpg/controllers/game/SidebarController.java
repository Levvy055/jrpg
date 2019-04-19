package pl.hopelew.jrpg.controllers.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.Timer;

import com.jfoenix.controls.JFXProgressBar;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import lombok.Getter;
import pl.hopelew.jrpg.entities.Player;
import pl.hopelew.jrpg.entities.data.Sex;
import pl.hopelew.jrpg.utils.Res;
import pl.hopelew.jrpg.utils.Resources;
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

	public void init(Player player) {
		setAvatar(player.getSex());
		setHp(player.getHp());
		pbHp.setSecondaryProgress(player.getHp() / 100d);
		player.addListener(EventType.HP_CHANGED, ge -> {
			var vge = (ValueChangedGameEvent) ge;
			Platform.runLater(() -> {
				setHp((double) vge.getNewValue());
				pbHp.setSecondaryProgress((double) vge.getOldValue() / 100d);
			});
		});
		player.addListener(EventType.MP_CHANGED, ge -> {
			var vge = (ValueChangedGameEvent) ge;
			Platform.runLater(() -> {
				setMp((double) vge.getNewValue());
				pbMp.setSecondaryProgress((double) vge.getOldValue() / 100d);
			});
		});
		new Timer(1000, new ActionListener() { // TODO: remove as for tests it is only

			@Override
			public void actionPerformed(ActionEvent e) {
				player.changeMp(-1);
			}
		}).start();
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
		avatar.setImage(Resources.getFxImage(sex == Sex.MALE ? Res.HERO_AVATAR_MALE : Res.HERO_AVATAR_FEMALE));
	}

}
