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
import pl.hopelew.jrpg.entities.Player;
import pl.hopelew.jrpg.entities.data.Sex;
import pl.hopelew.jrpg.utils.Res;
import pl.hopelew.jrpg.utils.Resources;

public class SidebarController implements Initializable {
	private static @Getter SidebarController instance;

	private @FXML ImageView avatar;
	private @FXML GridPane eqGrid;
	private @FXML GridPane invGrid;
	private @FXML Label lblHp;
	private @FXML JFXProgressBar pbHp;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		instance = this;
	}

	public void init(Player player) {
		setAvatar(player.getSex());
		setHp(player.getHp());
		player.addListener(ge -> {
			Platform.runLater(() -> {
				pbHp.setProgress((double) ge.getValue("newV") / 100d);
				pbHp.setSecondaryProgress((double) ge.getValue("oldV") / 100d);
				lblHp.setText(ge.getValue("newV") + "");
			});
		});
//		new Timer(1000, new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				player.changeHp(-1);
//			}
//		}).start();
	}

	private void setHp(double hp) {
		pbHp.setProgress(hp / 100d);
		lblHp.setText(hp + "");
	}

	private void setAvatar(Sex sex) {
		avatar.setImage(Resources.getFxImage(sex == Sex.MALE ? Res.HERO_AVATAR_MALE : Res.HERO_AVATAR_FEMALE));
	}

}
