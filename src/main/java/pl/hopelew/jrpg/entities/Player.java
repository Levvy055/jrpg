package pl.hopelew.jrpg.entities;

import javafx.application.Platform;
import javafx.scene.effect.BlendMode;
import pl.hopelew.jrpg.entities.data.EntityState;
import pl.hopelew.jrpg.entities.data.Sex;
import pl.hopelew.jrpg.entities.data.Sprite;
import pl.hopelew.jrpg.map.GameMap;
import pl.hopelew.jrpg.utils.Res;
import pl.hopelew.jrpg.utils.eventhandlers.EventType;
import pl.hopelew.jrpg.utils.eventhandlers.KeyGameEvent;
import pl.hopelew.jrpg.utils.eventhandlers.MapSwitchedGameEvent;

public class Player extends Entity {

	public Player(String name, Sex sex) throws Exception {
		super(name, sex);
		switch (sex) {
		case MALE:
			sprite = new Sprite(Res.HERO_SPRITE_MALE);
			break;
		case FEMALE:
			// TODO: sprite = new Sprite(Res.HERO_SPRITE_FEMALE);
			break;
		}

	}

	@Override
	protected void initialize() {
		sprite.setBlendMode(BlendMode.SRC_OVER);// lub SRC_OVER
		game.addListener(EventType.KEY_PRESSED, e -> {
			var ge = (KeyGameEvent) e;
			switch (ge.getKey()) {
			case D:
				position.moveX(1);
				state = EntityState.WALKING;
				break;
			case A:
				position.moveX(-1);
				state = EntityState.WALKING;
				break;
			case W:
				position.moveY(-1);
				state = EntityState.WALKING;
				break;
			case S:
				position.moveY(1);
				state = EntityState.WALKING;
				break;
			default:
				break;
			}
		}, this);
		game.addListener(EventType.MAP_SWITCHED, ge -> {
			var msge = (MapSwitchedGameEvent) ge;
			GameMap map = msge.getMap();
			var ep = map.getEntrance();
			position.set(ep.getX(), ep.getY(), ep.getDirection());
		}, this);
	}

	@Override
	protected void update(GameMap map) {
		sprite.update(position, state);
		Platform.runLater(() -> sprite.toFront());
	}
}
